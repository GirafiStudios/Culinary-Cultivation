package com.girafi.culinarycultivation.modsupport.jei;

import com.girafi.culinarycultivation.init.ModBlocks;
import com.girafi.culinarycultivation.init.recipes.WinnowingMachineRecipe;
import com.girafi.culinarycultivation.modsupport.jei.winnowing.WinnowingRecipeCategory;
import com.girafi.culinarycultivation.modsupport.jei.winnowing.WinnowingRecipeWrapper;
import com.girafi.culinarycultivation.util.reference.Reference;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

@mezz.jei.api.JEIPlugin
public class JEIPlugin extends BlankModPlugin {
    public static final String WINNOWING = Reference.MOD_ID + "." + "winnowing";

    @Override
    public void register(@Nonnull IModRegistry registry) {
        registry.handleRecipes(WinnowingMachineRecipe.class, new IRecipeWrapperFactory<WinnowingMachineRecipe>() {
            @Override
            @Nonnull
            public IRecipeWrapper getRecipeWrapper(@Nonnull WinnowingMachineRecipe recipe) {
                return new WinnowingRecipeWrapper(null, recipe);
            }
        }, WINNOWING);
        registry.addRecipes(WinnowingRecipeWrapper.getRecipes(), WINNOWING);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.FAN_HOUSING), WINNOWING);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.SEPARATOR), WINNOWING);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new WinnowingRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }
}