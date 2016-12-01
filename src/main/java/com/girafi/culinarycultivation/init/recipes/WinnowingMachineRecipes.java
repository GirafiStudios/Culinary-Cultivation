package com.girafi.culinarycultivation.init.recipes;

import com.girafi.culinarycultivation.api.crafting.IWinnowingMachineHandler;
import com.girafi.culinarycultivation.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class WinnowingMachineRecipes implements IWinnowingMachineHandler {
    private static final WinnowingMachineRecipes WINNOWING_MACHINE_INSTANCE = new WinnowingMachineRecipes();
    private final HashMap<Pair<Item, Integer>, WinnowingMachineRecipe> recipes = new HashMap<>();

    public static WinnowingMachineRecipes instance() {
        return WINNOWING_MACHINE_INSTANCE;
    }

    private WinnowingMachineRecipes() {
    }

    @Override
    public void addJunk(@Nonnull ItemStack input, @Nonnull ItemStack output, double weight) {
        getRecipe(input).getJunk().add(output, weight);
    }

    @Override
    public void addOutput(@Nonnull ItemStack input, @Nonnull ItemStack output, double weight) {
        getRecipe(input).getOutput().add(output, weight);
    }

    @Override
    public void addRecipe(@Nonnull ItemStack input, @Nonnull ItemStack output, double weight, ItemStack junkOutput, double junkWeight) {
        WinnowingMachineRecipe recipe = getRecipe(input);
        recipe.getOutput().add(output, weight);
        recipe.getJunk().add(junkOutput, junkWeight);
    }

    @Override
    public void addRecipe(@Nonnull ItemStack input, @Nonnull ItemStack output, double weight) {
        addRecipe(input, output, weight, new ItemStack(ModItems.CHAFF_PILE), 10D);
    }

    //Returns a recipe for this input, if this input has nothing create a new recipe
    private WinnowingMachineRecipe getRecipe(ItemStack input) {
        return recipes.computeIfAbsent(Pair.of(input.getItem(), input.getItemDamage()), k -> new WinnowingMachineRecipe());
    }

    public WinnowingMachineRecipe getProcessingResult(@Nonnull final ItemStack stack) {
        return recipes.get(Pair.of(stack.getItem(), stack.getItemDamage()));
    }

    public HashMap<Pair<Item, Integer>, WinnowingMachineRecipe> getRecipes() {
        return recipes;
    }
}