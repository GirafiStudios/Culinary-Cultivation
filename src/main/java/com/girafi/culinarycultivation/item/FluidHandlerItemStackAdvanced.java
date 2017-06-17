package com.girafi.culinarycultivation.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

import javax.annotation.Nonnull;

public class FluidHandlerItemStackAdvanced extends FluidHandlerItemStack {
    private int maxTemperature;

    /**
     * @param container The container stack, data is stored on it directly as NBT.
     * @param capacity  The maximum capacity of this fluid tank.
     */
    public FluidHandlerItemStackAdvanced(@Nonnull ItemStack container, int capacity) {
        super(container, capacity);
    }

    /**
     * @param container      The container stack, data is stored on it directly as NBT.
     * @param capacity       The maximum capacity of this fluid tank.
     * @param maxTemperature The maximum temperature the container stack can contain.
     */
    public FluidHandlerItemStackAdvanced(@Nonnull ItemStack container, int capacity, int maxTemperature) {
        super(container, capacity);
        this.maxTemperature = maxTemperature;
    }

    @Override
    protected void setContainerToEmpty() {
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
        return fluid != null && fluid.getTemperature(fluidStack) <= maxTemperature;
    }
}