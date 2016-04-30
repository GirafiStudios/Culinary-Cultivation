package com.girafi.culinarycultivation.init.recipes;

import com.girafi.culinarycultivation.api.IWinnowingMachineHandler;
import com.girafi.culinarycultivation.api.IWinnowingMachineRecipe;
import com.girafi.culinarycultivation.init.ModItems;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;

public class WinnowingMachineRecipes implements IWinnowingMachineHandler {
    private static WinnowingMachineRecipes WINNOWING_MACHINE_INSTANCE = new WinnowingMachineRecipes();
    private Cache<Pair<Item, Integer>, IWinnowingMachineRecipe> cacheList = CacheBuilder.newBuilder().build();
    private Multimap<Pair<Item, Integer>, IWinnowingMachineRecipe> processingList = HashMultimap.create();

    public static WinnowingMachineRecipes instance() {
        return WINNOWING_MACHINE_INSTANCE;
    }

    private WinnowingMachineRecipes() {
    }

    @Override
    public void addRecipe(ItemStack input, ItemStack output, int outputChance, ItemStack junkOutput, int junkChance) {
        addRecipe(input, new WinnowingMachineRecipe(output, outputChance, junkOutput, junkChance));
    }

    @Override
    public void addRecipe(ItemStack input, ItemStack output, int outputChance) {
        addRecipe(input, new WinnowingMachineRecipe(output, outputChance, new ItemStack(ModItems.CHAFF_PILE), 25));
    }

    @Override
    public void addRecipe(ItemStack input, IWinnowingMachineRecipe recipe) {
        this.processingList.put(Pair.of(input.getItem(), input.getItemDamage()), recipe);
    }

    @Override
    public IWinnowingMachineRecipe getProcessingResult(final ItemStack stack) {
        try {
            return cacheList.get(Pair.of(stack.getItem(), stack.getItemDamage()), new Callable<IWinnowingMachineRecipe>() {
                @Override
                public IWinnowingMachineRecipe call() throws Exception {
                    ArrayList<IWinnowingMachineRecipe> recipes = new ArrayList<IWinnowingMachineRecipe>();
                    Collection<IWinnowingMachineRecipe> collection = processingList.get(Pair.of(stack.getItem(), stack.getItemDamage()));
                    if (collection != null && collection.size() > 0) recipes.addAll(collection);
                    Collection<IWinnowingMachineRecipe> collection2 = processingList.get(Pair.of(stack.getItem(), OreDictionary.WILDCARD_VALUE));
                    if (collection2 != null && collection2.size() > 0) recipes.addAll(collection2);
                    if (recipes.size() == 0) return null;
                    IWinnowingMachineRecipe result = null;
                    for (IWinnowingMachineRecipe recipe : recipes) {
                        if (result == null || recipe.getOutputChance() > result.getOutputChance()) {
                            result = recipe;
                        }
                    }

                    return result;
                }
            });
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Multimap<Pair<Item, Integer>, IWinnowingMachineRecipe> getProcessingList() {
        return this.processingList;
    }
}