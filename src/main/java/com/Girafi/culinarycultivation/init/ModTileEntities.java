package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.reference.Paths;
import com.Girafi.culinarycultivation.tileentity.TileEntityCauldron;
import com.Girafi.culinarycultivation.tileentity.TileEntityWinnowingMachine;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {
    public static void init() {
        GameRegistry.registerTileEntity(TileEntityCauldron.class, Paths.ModAssets + "cauldron");
        GameRegistry.registerTileEntity(TileEntityWinnowingMachine.class, Paths.ModAssets + "winnowingMachine");
    }
}