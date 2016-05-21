package com.girafi.culinarycultivation.api.crafting;

import net.minecraft.item.ItemStack;

public interface IWinnowingMachineRecipe {
    /** Return the main output for this recipe, can be null **/
    ItemStack getOutput();

    /** Return the output chance, should be a value from 0-100 **/
    int getOutputChance();

    /** Return the junk output for this recipe, can be null **/
    ItemStack getJunk();

    /** Return the junk chance, should be a value from 0-100 **/
    int getJunkChance();
}