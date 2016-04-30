package com.girafi.culinarycultivation.init;

import com.girafi.culinarycultivation.block.tileentity.TileEntityCauldron;
import com.girafi.culinarycultivation.block.tileentity.TileEntitySeparator;
import com.girafi.culinarycultivation.util.reference.Paths;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {
    public static void init() {
        GameRegistry.registerTileEntity(TileEntityCauldron.class, Paths.MOD_ASSETS + "cauldron");
        GameRegistry.registerTileEntity(TileEntitySeparator.class, Paths.MOD_ASSETS + "separator");
    }
}