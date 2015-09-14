package com.Girafi.culinarycultivation.modSupport.thaumcraft;

import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.modSupport.IModSupport;
import com.Girafi.culinarycultivation.reference.Reference;
import thaumcraft.api.wands.WandTriggerRegistry;

public class Thaumcraft implements IModSupport {
    @Override
    public void preInit() {
    }

    @Override
    public void init() {
        Aspects.init();
    }

    @Override
    public void postInit() {
        WandTriggerRegistry.registerWandBlockTrigger(new CrucibleSupport(), 1, ModBlocks.cauldron.getDefaultState(), Reference.MOD_ID);
    }

    @Override
    public void clientSide() {
    }

    @Override
    public void clientInit() {
    }
}