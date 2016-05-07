package com.girafi.culinarycultivation.modsupport.jei;

import com.girafi.culinarycultivation.init.ModBlocks;
import com.girafi.culinarycultivation.modsupport.jei.winnowing.WinnowingRecipeCategory;
import com.girafi.culinarycultivation.modsupport.jei.winnowing.WinnowingRecipeHandler;
import com.girafi.culinarycultivation.util.reference.Reference;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IItemBlacklist;
import mezz.jei.api.IModRegistry;
import net.minecraft.item.ItemStack;

@mezz.jei.api.JEIPlugin
public class JEIPlugin extends BlankModPlugin {
    public static final String WINNOWING = Reference.MOD_ID + "." + "winnowing";

    @Override
    public void register(IModRegistry registry) {
        addBlacklist(registry);

        registry.addRecipeCategories(new WinnowingRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.FAN_HOUSING), WINNOWING);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.SEPARATOR), WINNOWING);
        registry.addRecipeHandlers(new WinnowingRecipeHandler());
        registry.addRecipes(WinnowingRecipeHandler.getRecipes());
    }

    private void addBlacklist(IModRegistry registry) {
        IItemBlacklist blacklist = registry.getJeiHelpers().getItemBlacklist();

        blacklist.addItemToBlacklist(new ItemStack(ModBlocks.BLACK_PEPPER));
        blacklist.addItemToBlacklist(new ItemStack(ModBlocks.CAULDRON));
        blacklist.addItemToBlacklist(new ItemStack(ModBlocks.CUCUMBER));
        blacklist.addItemToBlacklist(new ItemStack(ModBlocks.TOMATO));
    }
}