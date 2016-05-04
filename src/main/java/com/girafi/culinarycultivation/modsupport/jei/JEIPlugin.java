package com.girafi.culinarycultivation.modsupport.jei;

import com.girafi.culinarycultivation.init.ModBlocks;
import com.girafi.culinarycultivation.modsupport.jei.winnowing.WinnowingRecipeCategory;
import com.girafi.culinarycultivation.modsupport.jei.winnowing.WinnowingRecipeHandler;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IItemBlacklist;
import mezz.jei.api.IModRegistry;
import net.minecraft.item.ItemStack;

@mezz.jei.api.JEIPlugin
public class JEIPlugin extends BlankModPlugin {

    @Override
    public void register(IModRegistry registry) {
        addBlacklist(registry);

        registry.addRecipeCategories(new WinnowingRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeHandlers(new WinnowingRecipeHandler());
        //registry.addRecipes();
    }

    public void addBlacklist(IModRegistry registry) {
        IItemBlacklist blacklist = registry.getJeiHelpers().getItemBlacklist();

        blacklist.addItemToBlacklist(new ItemStack(ModBlocks.BLACK_PEPPER));
        blacklist.addItemToBlacklist(new ItemStack(ModBlocks.CAULDRON));
        blacklist.addItemToBlacklist(new ItemStack(ModBlocks.CUCUMBER));
        blacklist.addItemToBlacklist(new ItemStack(ModBlocks.TOMATO));
    }
}