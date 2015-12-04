package com.Girafi.culinarycultivation.modSupport.jei;

import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.modSupport.IModSupport;
import mezz.jei.api.JEIManager;
import net.minecraft.item.ItemStack;

public class JEI implements IModSupport {

    @Override
    public void preInit() {
    }

    @Override
    public void init() {
    }

    @Override
    public void postInit() {
    }

    @Override
    public void clientSide() {
        JEIManager.itemBlacklist.addItemToBlacklist(new ItemStack(ModBlocks.beetroots));
        JEIManager.itemBlacklist.addItemToBlacklist(new ItemStack(ModBlocks.blackPepper));
        JEIManager.itemBlacklist.addItemToBlacklist(new ItemStack(ModBlocks.cauldron));
        JEIManager.itemBlacklist.addItemToBlacklist(new ItemStack(ModBlocks.cucumber));
        JEIManager.itemBlacklist.addItemToBlacklist(new ItemStack(ModBlocks.tomato));
    }
}