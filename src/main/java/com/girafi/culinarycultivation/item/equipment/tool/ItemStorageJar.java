package com.girafi.culinarycultivation.item.equipment.tool;

import com.girafi.culinarycultivation.item.FluidStorageJarWrapper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemStorageJar extends Item {

    public static final int JAR_VOLUME = 250;

    public ItemStorageJar() {
        setContainerItem(this);
        setHasSubtypes(true);
        setMaxStackSize(1);
    }

    @Nonnull
    @Override
    public ItemStack getContainerItem(@Nonnull ItemStack stack) {
        if (!hasContainerItem(stack) || stack.getItemDamage() == 0) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(getContainerItem());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(@Nonnull Item item, @Nullable CreativeTabs tab, @Nonnull NonNullList<ItemStack> subItems) {
        for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
            if (fluid.getTemperature() < FluidRegistry.LAVA.getTemperature()) {
                FluidStack fluidStack = new FluidStack(fluid, JAR_VOLUME);
                ItemStack stack = new ItemStack(this);
                IFluidHandlerItem fluidHandler = new FluidStorageJarWrapper(stack);
                if (fluidHandler.fill(fluidStack, true) == fluidStack.amount) {
                    ItemStack filled = fluidHandler.getContainer();
                    subItems.add(filled);
                }
            }
        }
    }

    @Nonnull
    public static ItemStack getFilledJar(@Nonnull ItemStorageJar item, Fluid fluid) {
        ItemStack jar = new ItemStack(item);

        if (FluidRegistry.getBucketFluids().contains(fluid)) {
            NBTTagCompound tag = new NBTTagCompound();
            FluidStack fluidStack = new FluidStack(fluid, JAR_VOLUME);
            fluidStack.writeToNBT(tag);
            jar.setTagCompound(tag);
        }
        return jar;
    }

    @Nullable
    public FluidStack getFluid(@Nonnull ItemStack container) {
        return FluidStack.loadFluidStackFromNBT(container.getTagCompound());
    }

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, NBTTagCompound nbt) {
        return new FluidStorageJarWrapper(stack);
    }
}