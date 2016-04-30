package com.girafi.culinarycultivation.init.recipes;

import com.girafi.culinarycultivation.api.IWinnowingMachineRecipe;
import net.minecraft.item.ItemStack;

public class WinnowingMachineRecipe implements IWinnowingMachineRecipe {
    private final ItemStack output;
    private final ItemStack junk;
    private final int outputChance;
    private final int junkChance;

    public WinnowingMachineRecipe(ItemStack output, int outputChance, ItemStack junk, int junkChance) {
        this.output = output;
        this.outputChance = Math.min(100, Math.max(0, outputChance));
        this.junk = junk;
        this.junkChance = Math.min(100, Math.max(0, junkChance));
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    @Override
    public int getOutputChance() {
        return outputChance;
    }

    @Override
    public ItemStack getJunk() {
        return junk;
    }

    @Override
    public int getJunkChance() {
        return junkChance;
    }
}