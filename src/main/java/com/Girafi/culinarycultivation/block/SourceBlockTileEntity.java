package com.Girafi.culinarycultivation.block;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import com.Girafi.culinarycultivation.reference.Paths;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;

public abstract class SourceBlockTileEntity extends BlockContainer {

    public SourceBlockTileEntity(Material material) {
        super(material);
        this.setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
    }

    public SourceBlockTileEntity() {
        this(Material.rock);
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("tile.%s%s", Paths.ModAssets, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @Override
    public int getRenderType() {
        return 3;
    }
}