package com.girafi.culinarycultivation.item.equipment.tool;

import com.girafi.culinarycultivation.api.annotations.RegisterEvent;
import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.item.FluidHandlerItemStackAdvanced;
import com.girafi.culinarycultivation.util.InventoryHandlerHelper;
import com.girafi.culinarycultivation.util.NBTHelper;
import com.girafi.culinarycultivation.util.StringUtils;
import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
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
    @Nonnull
    public ItemStack getContainerItem(@Nonnull ItemStack stack) {
        return new ItemStack(getContainerItem(), 1, 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        if (NBTHelper.hasKey(stack, FluidHandlerItemStackAdvanced.FLUID_NBT_KEY)) {
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(NBTHelper.getTag(stack).getCompoundTag(FluidHandlerItemStackAdvanced.FLUID_NBT_KEY));
            if (GuiScreen.isShiftKeyDown() && fluidStack != null) {
                tooltip.add(StringUtils.translateFormatted(Reference.MOD_ID + ".fluid", fluidStack.getFluid().getLocalizedName(fluidStack)));
                tooltip.add(StringUtils.translateFormatted(Reference.MOD_ID + ".fluid_amount", fluidStack.amount + " / " + JAR_VOLUME));
            } else {
                tooltip.add(StringUtils.shiftTooltip());
            }
        }
    }

    @Override
    @Nonnull
    public String getItemStackDisplayName(@Nonnull ItemStack stack) {
        FluidHandlerItemStackAdvanced fluidHandler = new FluidHandlerItemStackAdvanced(stack, JAR_VOLUME);
        FluidStack fluidStack = fluidHandler.getFluid();

        return fluidStack == null ? super.getItemStackDisplayName(stack) : super.getItemStackDisplayName(stack) + " (" + fluidStack.getLocalizedName() + ")";
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull EnumHand hand) { //TODO Handle vanilla fluids
        ItemStack heldStack = player.getHeldItem(hand);
        RayTraceResult rayTrace = this.rayTrace(world, player, true);

        if (rayTrace == null || rayTrace.typeOfHit != RayTraceResult.Type.BLOCK || NBTHelper.hasTag(heldStack)) {
            return new ActionResult<>(EnumActionResult.PASS, heldStack);
        }
        IBlockState state = world.getBlockState(rayTrace.getBlockPos());
        FluidHandlerItemStackAdvanced fluidHandler = new FluidHandlerItemStackAdvanced(getContainerItem(heldStack), JAR_VOLUME, MAX_TEMPERATURE);
        if (isFluid(state)) {
            FluidStack fluidStackBlock = new FluidStack(FluidRegistry.lookupFluidForBlock(state.getBlock()), JAR_VOLUME);
            if (!fluidHandler.canFillFluidType(fluidStackBlock)) {
                player.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1.0F, 1.0F);
                heldStack.shrink(1);
                return new ActionResult<>(EnumActionResult.PASS, heldStack);
            }
        }

        int fluid = 0;
        if (isFluid(world.getBlockState(rayTrace.getBlockPos().north()))) fluid++;
        if (isFluid(world.getBlockState(rayTrace.getBlockPos().east()))) fluid++;
        if (isFluid(world.getBlockState(rayTrace.getBlockPos().south()))) fluid++;
        if (isFluid(world.getBlockState(rayTrace.getBlockPos().west()))) fluid++;

        if (fluid >= 2) { // Make sure the fluid source is infinite
            player.playSound(SoundEvents.ITEM_BOTTLE_FILL, 1.0F, 1.0F);
            InventoryHandlerHelper.fillContainer(fluidHandler.getContainer(), new FluidStack(FluidRegistry.lookupFluidForBlock(state.getBlock()), JAR_VOLUME), heldStack, player, hand);
            return new ActionResult<>(EnumActionResult.SUCCESS, heldStack);
        }
        return new ActionResult<>(EnumActionResult.FAIL, heldStack);
    }

    private boolean isFluid(IBlockState state) {
        return (state.getBlock() instanceof IFluidBlock || state.getBlock() instanceof BlockLiquid) && !(state.getBlock() instanceof BlockFluidFinite) && state.getValue(BlockLiquid.LEVEL) == 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(@Nonnull Item item, @Nullable CreativeTabs tab, @Nonnull NonNullList<ItemStack> subItems) {
        subItems.add(new ItemStack(item));
        for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
            FluidStack fluidStack = new FluidStack(fluid, JAR_VOLUME);
            IFluidHandlerItem fluidHandler = new FluidHandlerItemStackAdvanced(new ItemStack(item, 1, 1), JAR_VOLUME, MAX_TEMPERATURE);
            if (fluidHandler.fill(fluidStack, true) == fluidStack.amount) {
                subItems.add(fluidHandler.getContainer());
            }
        }
    }

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new FluidHandlerItemStackAdvanced(stack, JAR_VOLUME, MAX_TEMPERATURE);
    }

    @SubscribeEvent
    public void entityInteract(PlayerInteractEvent.EntityInteract event) {
        EntityPlayer player = event.getEntityPlayer();
        ItemStack heldStack = player.getHeldItem(event.getHand());
        if (event.getTarget() instanceof EntityCow & !event.getEntityLiving().isChild() && FluidRegistry.isFluidRegistered("milk")) {
            if (heldStack.getItem() == ModItems.STORAGE_JAR && !NBTHelper.hasTag(heldStack) && !player.capabilities.isCreativeMode) {
                player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
                InventoryHandlerHelper.fillContainer(getContainerItem(heldStack), new FluidStack(FluidRegistry.getFluid("milk"), JAR_VOLUME), heldStack, player, event.getHand());
            }
        }
    }
}