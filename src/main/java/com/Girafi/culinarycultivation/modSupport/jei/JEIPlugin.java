package com.Girafi.culinarycultivation.modSupport.jei;

import com.Girafi.culinarycultivation.init.ModBlocks;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IItemBlacklist;
import mezz.jei.api.IModRegistry;
import net.minecraft.item.ItemStack;

@mezz.jei.api.JEIPlugin
public class JEIPlugin extends BlankModPlugin {

    @Override
    public void register(IModRegistry registry) {
        IItemBlacklist blacklist = registry.getJeiHelpers().getItemBlacklist();

        blacklist.addItemToBlacklist(new ItemStack(ModBlocks.BLACK_PEPPER));
        blacklist.addItemToBlacklist(new ItemStack(ModBlocks.CAULDRON));
        blacklist.addItemToBlacklist(new ItemStack(ModBlocks.CUCUMBER));
        blacklist.addItemToBlacklist(new ItemStack(ModBlocks.TOMATO));
    }
}