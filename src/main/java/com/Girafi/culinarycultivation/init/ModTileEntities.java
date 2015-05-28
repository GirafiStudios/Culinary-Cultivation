package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.reference.Reference;
import com.Girafi.culinarycultivation.tileentity.TileEntityCauldron;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModTileEntities {
    public static void init() {
        GameRegistry.registerTileEntity(TileEntityCauldron.class, Reference.MOD_ID + ":" + "cauldron");
    }
}
