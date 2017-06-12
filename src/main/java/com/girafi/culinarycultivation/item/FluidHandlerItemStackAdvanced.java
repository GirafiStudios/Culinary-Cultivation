package com.girafi.culinarycultivation.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

import javax.annotation.Nonnull;

public class FluidHandlerItemStackAdvanced extends FluidHandlerItemStack {
    private int maxTemperature;
    private boolean destroyByTemperature;

    /**
     * @param container The container stack, data is stored on it directly as NBT.
     * @param capacity  The maximum capacity of this fluid tank.
     */
    public FluidHandlerItemStackAdvanced(@Nonnull ItemStack container, int capacity) {
        super(container, capacity);
    }

    /**
     * @param container            The container stack, data is stored on it directly as NBT.
     * @param capacity             The maximum capacity of this fluid tank.
     * @param maxTemperature       The maximum temperature the container stack can contain.
     * @param destroyByTemperature If the container stack should be destroyed, if attempting to fill it with a too hot fluid.
     */
    public FluidHandlerItemStackAdvanced(@Nonnull ItemStack container, int capacity, int maxTemperature, boolean destroyByTemperature) {
        super(container, capacity);
        this.maxTemperature = maxTemperature;
        this.destroyByTemperature = destroyByTemperature;
    }

    @Override
    protected void setContainerToEmpty() {
        FluidStack fluidStack = getFluid();
        if (fluidStack == null) return;
        Fluid fluid = fluidStack.getFluid();

        if (destroyByTemperature && fluid.getTemperature(fluidStack) > maxTemperature) {
            container.shrink(1);
        }
        container = new ItemStack(container.getItem(), 1, 0);
    }

    @Override
    public boolean canFillFluidType(FluidStack fluidStack) {
        return contentsAllowed(fluidStack);
    }

    @Override
    public boolean canDrainFluidType(FluidStack fluidStack) {
        return contentsAllowed(fluidStack);
    }

    @Override
    protected void setFluid(FluidStack fluid) {
        super.setFluid(fluid);
        container.setItemDamage(1);
    }

    private boolean contentsAllowed(FluidStack fluidStack) {
        Fluid fluid = fluidStack.getFluid();
        if (fluid == null) {
            return false;
        }
        if (fluid.getTemperature() > maxTemperature) {
            setContainerToEmpty();
            return false;
        }
        return true;
    }
}