package com.girafi.culinarycultivation.item.equipment.tool;

import com.girafi.culinarycultivation.api.annotations.RegisterEvent;
import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.item.FluidHandlerItemStackAdvanced;
import com.girafi.culinarycultivation.util.InventoryHandlerHelper;
import com.girafi.culinarycultivation.util.NBTHelper;
import com.girafi.culinarycultivation.util.StringUtils;
import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@RegisterEvent
public class ItemStorageJar extends Item {
    public static final int JAR_VOLUME = 250;
    private static final int MAX_TEMPERATURE = FluidRegistry.LAVA.getTemperature() - 1;

    public ItemStorageJar() {
        setContainerItem(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        if (NBTHelper.hasKey(stack, FluidHandlerItemStack.FLUID_NBT_KEY)) {
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(NBTHelper.getTag(stack).getCompoundTag(FluidHandlerItemStack.FLUID_NBT_KEY));
            if (GuiScreen.isShiftKeyDown() && fluidStack != null) {
                Fluid fluid = fluidStack.getFluid();

                tooltip.add(StringUtils.translateFormatted(Reference.MOD_ID + ".fluid", fluid.getLocalizedName(fluidStack)));
                tooltip.add(StringUtils.translateFormatted(Reference.MOD_ID + ".fluid_amount", fluidStack.amount + " / " + JAR_VOLUME));
            } else {
                tooltip.add(StringUtils.shiftTooltip());
            }
        }
    }

    @Override
    @Nonnull
    public String getItemStackDisplayName(@Nonnull ItemStack stack) {
        FluidHandlerItemStack fluidHandler = new FluidHandlerItemStack(stack, JAR_VOLUME);
        FluidStack fluidStack = fluidHandler.getFluid();

        return fluidStack == null ? super.getItemStackDisplayName(stack) : super.getItemStackDisplayName(stack) + " (" + fluidStack.getLocalizedName() + ")";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(@Nonnull Item item, @Nullable CreativeTabs tab, @Nonnull NonNullList<ItemStack> subItems) {
        subItems.add(new ItemStack(item));
        for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
            if (fluid.getTemperature() < MAX_TEMPERATURE) {
                FluidStack fluidStack = new FluidStack(fluid, JAR_VOLUME);
                IFluidHandlerItem fluidHandler = new FluidHandlerItemStack(new ItemStack(item, 1, 1), JAR_VOLUME);
                if (fluidHandler.fill(fluidStack, true) == fluidStack.amount) {
                    subItems.add(fluidHandler.getContainer());
                }
            }
        }
    }

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new FluidHandlerItemStackAdvanced(stack, JAR_VOLUME, MAX_TEMPERATURE, true);
    }

    @SubscribeEvent
    public void entityInteract(PlayerInteractEvent.EntityInteract event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack heldStack = player.getHeldItem(event.getHand());
        if (event.getTarget() instanceof EntityCow & !event.getEntityLiving().isChild() && FluidRegistry.isFluidRegistered("milk")) {
            if (heldStack.getItem() == ModItems.STORAGE_JAR && !NBTHelper.hasTag(heldStack) && !player.capabilities.isCreativeMode) {
                FluidStack fluidStack = new FluidStack(FluidRegistry.getFluid("milk"), JAR_VOLUME);
                ItemStack container = new ItemStack(ModItems.STORAGE_JAR, 1, 1);
                NBTTagCompound fluidTag = new NBTTagCompound();

                fluidStack.writeToNBT(fluidTag);
                NBTHelper.getTag(container).setTag(FluidHandlerItemStack.FLUID_NBT_KEY, fluidTag);

                player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                InventoryHandlerHelper.giveItem(player, event.getHand(), container);
                heldStack.shrink(1);
            }
        }
    }
}