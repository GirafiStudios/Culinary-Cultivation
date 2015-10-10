package com.Girafi.culinarycultivation.modSupport.thaumcraft;

import com.Girafi.culinarycultivation.block.BlockModCauldron;
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
        BlockModCauldron cauldron = (BlockModCauldron) ModBlocks.cauldron;
        WandTriggerRegistry.registerWandBlockTrigger(new CrucibleSupport(), 1, cauldron.getDefaultState(), Reference.MOD_ID); //TODO Make it able to transform no matter what the state is.
    }

    @Override
    public void clientSide() {
    }

    @Override
    public void clientInit() {
    }
}