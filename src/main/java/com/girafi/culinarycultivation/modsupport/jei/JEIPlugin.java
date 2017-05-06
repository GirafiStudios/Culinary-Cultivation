package com.girafi.culinarycultivation.modsupport.jei;

import com.girafi.culinarycultivation.init.ModBlocks;
import com.girafi.culinarycultivation.init.recipes.WinnowingMachineRecipe;
import com.girafi.culinarycultivation.modsupport.jei.winnowing.WinnowingRecipeCategory;
import com.girafi.culinarycultivation.modsupport.jei.winnowing.WinnowingRecipeWrapper;
import com.girafi.culinarycultivation.util.reference.Reference;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

@mezz.jei.api.JEIPlugin
public class JEIPlugin extends BlankModPlugin {
    public static final String WINNOWING = Reference.MOD_ID + "." + "winnowing";

    @Override
    public void register(@Nonnull IModRegistry registry) {
        addBlacklist(registry);

        registry.addRecipeCategories(new WinnowingRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.handleRecipes(WinnowingMachineRecipe.class, new IRecipeWrapperFactory<WinnowingMachineRecipe>() {
            @Override
            @Nonnull
            public IRecipeWrapper getRecipeWrapper(@Nonnull WinnowingMachineRecipe recipe) {
                return new WinnowingRecipeWrapper(null, recipe);
            }
        }, WINNOWING);
        registry.addRecipes(WinnowingRecipeWrapper.getRecipes(), WINNOWING);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.FAN_HOUSING), WINNOWING);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.SEPARATOR), WINNOWING);
    }

    private void addBlacklist(@Nonnull IModRegistry registry) {
        IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();

        blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.BLACK_PEPPER));
        blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.CORN));
        blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.CUCUMBER));
        blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.TOMATO));
        //TODO Add hashmap for all crops, for easier blacklisting
    }
}