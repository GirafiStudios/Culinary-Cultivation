package com.Girafi.culinarycultivation.block;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import com.Girafi.culinarycultivation.reference.Paths;
import com.Girafi.culinarycultivation.tileentity.TileEntityWinnowingMachine;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class BlockWinnowingMachine extends SourceBlockTileEntity {

    public BlockWinnowingMachine() {
        super(Material.iron);
        setUnlocalizedName(Paths.ModAssets + "winnowingMachine");
        setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityWinnowingMachine();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public boolean isVisuallyOpaque() {
        return false;
    }

    /*@Override
    public boolean canRenderInLayer(EnumWorldBlockLayer layer) {
        return layer == EnumWorldBlockLayer.SOLID || layer == EnumWorldBlockLayer.TRANSLUCENT;
    }*/
}