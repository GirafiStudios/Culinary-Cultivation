package com.girafi.culinarycultivation.api.crafting;

import net.minecraft.item.ItemStack;

public interface IWinnowingMachineHandler {
    /**
     * Call this to add a junk result for a recipe
     *
     * @param input      the input item
     * @param junkOutput the item to be output as junk
     * @param junkWeight this is the weighting for the junk,
     *                   which determines how likely it is to be picked compared to other junk items,
     *                   The total weighting for an input cannot add up to more than 100D
     */
    void addJunk(ItemStack input, ItemStack junkOutput, double junkWeight);

    /**
     * Call this to add a normal aka good result for a recipe
     *
     * @param input  the input item
     * @param output the item to be output
     * @param weight this is the weighting for the item,
     *               which determines how likely it is to be picked compared to other items
     *               The total weighting for an input cannot add up to more than 100D
     */
    void addOutput(ItemStack input, ItemStack output, double weight);

    /**
     * This is a convenience method for adding a pair of junk and output at a time
     **/
    void addRecipe(ItemStack input, ItemStack output, double weight, ItemStack junkOutput, double junkWeight);

    /**
     * This is a convenience method for adding a pair of junk and output at a time
     * It will default to adding a pile of chaff with 10% chance as the junk output
     **/
    void addRecipe(ItemStack input, ItemStack output, double weight);
}