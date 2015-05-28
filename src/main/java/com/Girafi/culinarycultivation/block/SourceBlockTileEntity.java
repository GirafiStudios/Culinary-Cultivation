package com.Girafi.culinarycultivation.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;

public abstract class SourceBlockTileEntity extends BlockContainer {
    public SourceBlockTileEntity(Material material){
        super(material);
    }

    public SourceBlockTileEntity(){
        this(Material.rock);
    }
}