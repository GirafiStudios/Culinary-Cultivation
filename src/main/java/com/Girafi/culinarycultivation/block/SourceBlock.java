package com.Girafi.culinarycultivation.block;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import com.Girafi.culinarycultivation.reference.Paths;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class SourceBlock extends Block {
    public SourceBlock(Material material) {

        super(material);
        this.setCreativeTab(CreativeTab.CulinaryCultivation_Tab);

    }

    public SourceBlock()
    {
        this(Material.rock);
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("tile.%s%s", Paths.ModAssets, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}