package com.girafi.culinarycultivation.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;

public abstract class SourceBlockTileEntity extends BlockContainer {

    public SourceBlockTileEntity(Material material) {
        super(material);
    }

    public SourceBlockTileEntity(Material material, MapColor color) {
        super(material, color);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
}