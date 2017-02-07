package com.girafi.culinarycultivation.modsupport.jei;

import com.girafi.culinarycultivation.init.ModBlocks;
import com.girafi.culinarycultivation.modsupport.jei.winnowing.WinnowingRecipeCategory;
import com.girafi.culinarycultivation.modsupport.jei.winnowing.WinnowingRecipeHandler;
import com.girafi.culinarycultivation.util.reference.Reference;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

@mezz.jei.api.JEIPlugin
public class JEIPlugin extends BlankModPlugin {
    public static final String WINNOWING = Reference.MOD_ID + "." + "winnowing";

    @Override
    public void register(@Nonnull IModRegistry registry) {
        addBlacklist(registry);

        registry.addRecipeCategories(new WinnowingRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.FAN_HOUSING), WINNOWING);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.SEPARATOR), WINNOWING);
        registry.addRecipeHandlers(new WinnowingRecipeHandler());
        registry.addRecipes(WinnowingRecipeHandler.getRecipes());
    }

    private void addBlacklist(@Nonnull IModRegistry registry) {
        IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();

        blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.BLACK_PEPPER));
        blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.CAULDRON));
        blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.CORN));
        blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.CUCUMBER));
        blacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.TOMATO));
        //TODO Add hashmap for all crops, for easier blacklisting
    }
}