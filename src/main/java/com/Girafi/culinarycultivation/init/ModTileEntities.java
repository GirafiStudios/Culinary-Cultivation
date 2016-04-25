package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.block.tileentity.TileEntityCauldron;
import com.Girafi.culinarycultivation.block.tileentity.TileEntitySeparator;
import com.Girafi.culinarycultivation.util.reference.Paths;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {
    public static void init() {
        GameRegistry.registerTileEntity(TileEntityCauldron.class, Paths.MOD_ASSETS + "cauldron");
        GameRegistry.registerTileEntity(TileEntitySeparator.class, Paths.MOD_ASSETS + "separator");
    }
}