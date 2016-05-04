package com.girafi.culinarycultivation.modsupport.jei.winnowing;

import com.girafi.culinarycultivation.init.recipes.WinnowingMachineRecipe;
import com.girafi.culinarycultivation.util.reference.Reference;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class WinnowingRecipeHandler implements IRecipeHandler<WinnowingMachineRecipe> {
    @Override
    public Class<WinnowingMachineRecipe> getRecipeClass() {
        return WinnowingMachineRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return Reference.MOD_ID + "." + "winnowing";
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(WinnowingMachineRecipe recipe) {
        return new WinnowingRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(WinnowingMachineRecipe recipe) {
        return true;
    }
}