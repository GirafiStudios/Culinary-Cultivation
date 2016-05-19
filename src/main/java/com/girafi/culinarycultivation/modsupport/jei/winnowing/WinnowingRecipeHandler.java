package com.girafi.culinarycultivation.modsupport.jei.winnowing;

import com.girafi.culinarycultivation.api.CulinaryCultivationAPI;
import com.girafi.culinarycultivation.api.crafting.IWinnowingMachineRecipe;
import com.girafi.culinarycultivation.modsupport.jei.JEIPlugin;
import com.google.common.collect.Multimap;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.ErrorUtil;
import mezz.jei.util.Log;
import net.minecraft.item.Item;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class WinnowingRecipeHandler implements IRecipeHandler<WinnowingRecipeWrapper> {
    public static List<WinnowingRecipeWrapper> getRecipes() {
        List<WinnowingRecipeWrapper> wrappers = new ArrayList<WinnowingRecipeWrapper>();
        Multimap<Pair<Item, Integer>, IWinnowingMachineRecipe> recipes = CulinaryCultivationAPI.winnowing.getProcessingList();
        for (Pair<Item, Integer> pair: recipes.keySet()) {
            for (IWinnowingMachineRecipe recipe: recipes.get(pair)) {
                wrappers.add(new WinnowingRecipeWrapper(pair, recipe));
            }
        }

        return wrappers;
    }

    @Override
    @Nonnull
    public Class<WinnowingRecipeWrapper> getRecipeClass() {
        return WinnowingRecipeWrapper.class;
    }

    @Override
    @Nonnull
    public String getRecipeCategoryUid() {
        return JEIPlugin.WINNOWING;
    }

    @Override
    @Nonnull
    public IRecipeWrapper getRecipeWrapper(@Nonnull WinnowingRecipeWrapper recipe) {
        return recipe;
    }

    @Override
    public boolean isRecipeValid(@Nonnull WinnowingRecipeWrapper recipe) {
        if (recipe.getInputs().isEmpty()) {
            String recipeInfo = ErrorUtil.getInfoFromBrokenRecipe(recipe, this);
            Log.error("Recipe has no inputs. {}", recipeInfo);
        }

        if (recipe.getOutputs().isEmpty()) {
            String recipeInfo = ErrorUtil.getInfoFromBrokenRecipe(recipe, this);
            Log.error("Recipe has no outputs. {}", recipeInfo);
        }

        return true;
    }
}