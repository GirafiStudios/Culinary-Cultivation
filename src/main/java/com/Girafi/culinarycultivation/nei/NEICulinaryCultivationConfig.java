package com.Girafi.culinarycultivation.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.reference.Reference;
import net.minecraft.item.ItemStack;

public class NEICulinaryCultivationConfig implements IConfigureNEI {

    @Override
    public String getName() {
        return Reference.MOD_NAME;
    }

    @Override
    public String getVersion() {
        return Reference.MOD_VERSION;
    }

    @Override
    public void loadConfig() {
        API.hideItem(new ItemStack(ModBlocks.beet));
        API.hideItem(new ItemStack(ModBlocks.blackPepper));
    }
}