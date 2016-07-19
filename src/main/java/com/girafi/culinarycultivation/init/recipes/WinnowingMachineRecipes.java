package com.girafi.culinarycultivation.init.recipes;

import com.girafi.culinarycultivation.api.crafting.IWinnowingMachineHandler;
import com.girafi.culinarycultivation.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;

public class WinnowingMachineRecipes implements IWinnowingMachineHandler {
    private static final WinnowingMachineRecipes WINNOWING_MACHINE_INSTANCE = new WinnowingMachineRecipes();
    private final HashMap<Pair<Item, Integer>, WinnowingMachineRecipe> recipes = new HashMap<Pair<Item, Integer>, WinnowingMachineRecipe>();

    public static WinnowingMachineRecipes instance() {
        return WINNOWING_MACHINE_INSTANCE;
    }

    private WinnowingMachineRecipes() {}

    @Override
    public void addJunk(ItemStack input, ItemStack output, double weight) {
        getRecipe(input).getJunk().add(output, weight);
    }

    @Override
    public void addOutput(ItemStack input, ItemStack output, double weight) {
        getRecipe(input).getOutput().add(output, weight);
    }

    @Override
    public void addRecipe(ItemStack input, ItemStack output, double weight, ItemStack junkOutput, double junkWeight) {
        WinnowingMachineRecipe recipe = getRecipe(input);
        recipe.getOutput().add(output, weight);
        recipe.getJunk().add(junkOutput, junkWeight);
    }

    @Override
    public void addRecipe(ItemStack input, ItemStack output, double weight) {
        addRecipe(input, output, weight, new ItemStack(ModItems.CHAFF_PILE), 10D);
    }

    //Returns a recipe for this input, if this input has nothing create a new recipe
    private WinnowingMachineRecipe getRecipe(ItemStack input) {
        WinnowingMachineRecipe recipe = recipes.get(Pair.of(input.getItem(), input.getItemDamage()));
        if (recipe == null) {
            recipe = new WinnowingMachineRecipe();
            recipes.put(Pair.of(input.getItem(), input.getItemDamage()), recipe);
        }

        return recipe;
    }

    public WinnowingMachineRecipe getProcessingResult(final ItemStack stack) {
        return recipes.get(Pair.of(stack.getItem(), stack.getItemDamage()));
    }

    public HashMap<Pair<Item, Integer>, WinnowingMachineRecipe> getRecipes() {
        return recipes;
    }
}