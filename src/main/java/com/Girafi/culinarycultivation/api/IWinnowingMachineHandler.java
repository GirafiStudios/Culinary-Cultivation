package com.Girafi.culinarycultivation.api;

import com.google.common.collect.Multimap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

public interface IWinnowingMachineHandler {
    public void addRecipe(ItemStack input, ItemStack output, int outputChance, ItemStack junk, int junkChance);
    public void addRecipe(ItemStack input, ItemStack output, int outputChance);
    public void addRecipe(ItemStack input, IWinnowingMachineRecipe recipe);

    public IWinnowingMachineRecipe getProcessingResult(ItemStack stack);
    public Multimap<Pair<Item, Integer>, IWinnowingMachineRecipe> getProcessingList();
}
