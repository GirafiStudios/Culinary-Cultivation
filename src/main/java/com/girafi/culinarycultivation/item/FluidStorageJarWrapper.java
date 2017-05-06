package com.girafi.culinarycultivation.item;

import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.item.equipment.tool.ItemStorageJar;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FluidStorageJarWrapper implements IFluidHandlerItem, ICapabilityProvider {
    @Nonnull
    private ItemStack container;

    public  FluidStorageJarWrapper(ItemStack stack) {
        container = stack;
    }

    @Override
    @Nonnull
    public ItemStack getContainer() {
        return container;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new FluidTankProperties[]{new FluidTankProperties(ModItems.STORAGE_JAR.getFluid(container), ItemStorageJar.JAR_VOLUME)};
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        return ItemStorageJar.JAR_VOLUME;
    }

    @Override
    @Nullable
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (container.getCount() != 1 || resource == null || resource.amount < ItemStorageJar.JAR_VOLUME) {
            return null;
        }

        FluidStack fluidStack = ModItems.STORAGE_JAR.getFluid(container);
        if (fluidStack != null && fluidStack.isFluidEqual(resource)) {
            if (doDrain) {
                setFluid(null);
            }
            return fluidStack;
        }
        return null;
    }

    protected void setFluid(@Nullable Fluid fluid) {
        if (fluid == null) {
            container = getContainer();
        } else if (FluidRegistry.getBucketFluids().contains(fluid)) {
            container = ItemStorageJar.getFilledJar(ModItems.STORAGE_JAR, fluid);
        }
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (container.getCount() != 1 || maxDrain < ItemStorageJar.JAR_VOLUME) {
            return null;
        }

        FluidStack fluidStack = ModItems.STORAGE_JAR.getFluid(container);
        if (fluidStack != null) {
            if (doDrain) {
                setFluid(null);
            }
            return fluidStack;
        }
        return null;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY;
    }

    @Override
    @Nullable
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY.cast(this);
        }
        return null;
    }
}