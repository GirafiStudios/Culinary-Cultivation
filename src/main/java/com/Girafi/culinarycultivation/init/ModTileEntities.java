package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.block.tileentity.TileEntityCauldron;
import com.Girafi.culinarycultivation.block.tileentity.TileEntitySeparator;
import com.Girafi.culinarycultivation.reference.Paths;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {
    public static void init() {
        GameRegistry.registerTileEntity(TileEntityCauldron.class, Paths.ModAssets + "cauldron");
        GameRegistry.registerTileEntity(TileEntitySeparator.class, Paths.ModAssets + "separator");
    }
}