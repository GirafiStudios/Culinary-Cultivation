package com.Girafi.culinarycultivation.modSupport.jei;

import com.Girafi.culinarycultivation.init.ModBlocks;
import mezz.jei.api.*;
import net.minecraft.item.ItemStack;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public boolean isModLoaded() {
        return true;
    }

    @Override
    public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers) {
        jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(ModBlocks.beetroots));
        jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(ModBlocks.blackPepper));
        jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(ModBlocks.cauldron));
        jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(ModBlocks.cucumber));
        jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(ModBlocks.tomato));
    }

    @Override
    public void onItemRegistryAvailable(IItemRegistry itemRegistry) {
    }

    @Override
    public void register(IModRegistry registry) {
    }

    @Override
    public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry) {
    }
}