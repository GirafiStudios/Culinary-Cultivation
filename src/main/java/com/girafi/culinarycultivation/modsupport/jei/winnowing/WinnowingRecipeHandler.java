package com.girafi.culinarycultivation.modsupport.jei.winnowing;

import com.girafi.culinarycultivation.init.recipes.WinnowingMachineRecipe;
import com.girafi.culinarycultivation.init.recipes.WinnowingMachineRecipes;
import com.girafi.culinarycultivation.modsupport.jei.JEIPlugin;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.ErrorUtil;
import mezz.jei.util.Log;
import net.minecraft.item.Item;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WinnowingRecipeHandler implements IRecipeHandler<WinnowingRecipeWrapper> {
    public static List<WinnowingRecipeWrapper> getRecipes() {
        List<WinnowingRecipeWrapper> wrappers = new ArrayList<WinnowingRecipeWrapper>();
        Map<Pair<Item, Integer>, WinnowingMachineRecipe> recipes = WinnowingMachineRecipes.instance().getRecipes();
        for (Pair<Item, Integer> pair : recipes.keySet()) {
            wrappers.add(new WinnowingRecipeWrapper(pair, recipes.get(pair)));
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
    public String getRecipeCategoryUid(@Nonnull WinnowingRecipeWrapper recipe) {
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