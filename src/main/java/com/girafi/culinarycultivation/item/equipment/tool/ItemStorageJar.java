package com.girafi.culinarycultivation.item.equipment.tool;

import com.girafi.culinarycultivation.item.FluidHandlerItemStackAdvanced;
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
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemStorageJar extends Item {
    public static final int JAR_VOLUME = 250;
    private static final int MAX_TEMPERATURE = FluidRegistry.LAVA.getTemperature() - 1;

    public ItemStorageJar() {
        setContainerItem(this);
        setHasSubtypes(true);
    }

    @Nonnull
    @Override
    public ItemStack getContainerItem(@Nonnull ItemStack stack) {
        if (!hasContainerItem(stack)) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(getContainerItem());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(@Nonnull Item item, @Nullable CreativeTabs tab, @Nonnull NonNullList<ItemStack> subItems) {
        subItems.add(new ItemStack(item, 1, 0));
        for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
            if (fluid.getTemperature() < MAX_TEMPERATURE) {
                FluidStack fluidStack = new FluidStack(fluid, JAR_VOLUME);
                IFluidHandlerItem fluidHandler = new FluidHandlerItemStack(new ItemStack(item), JAR_VOLUME);
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
}