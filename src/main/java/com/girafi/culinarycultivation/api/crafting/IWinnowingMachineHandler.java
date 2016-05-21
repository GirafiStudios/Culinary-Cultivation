package com.girafi.culinarycultivation.api.crafting;

import com.google.common.collect.Multimap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

public interface IWinnowingMachineHandler {
    void addRecipe(ItemStack input, ItemStack output, int outputChance, ItemStack junk, int junkChance);
    void addRecipe(ItemStack input, ItemStack output, int outputChance);
    void addRecipe(ItemStack input, IWinnowingMachineRecipe recipe);

    IWinnowingMachineRecipe getProcessingResult(ItemStack stack);
    Multimap<Pair<Item, Integer>, IWinnowingMachineRecipe> getProcessingList();
}