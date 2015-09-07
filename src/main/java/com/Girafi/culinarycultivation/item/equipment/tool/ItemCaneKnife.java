package com.Girafi.culinarycultivation.item.equipment.tool;

import com.Girafi.culinarycultivation.reference.Paths;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemTool;

import java.util.Set;

public class ItemCaneKnife extends ItemTool {

    private static final Set EFFECTIVE_ON = Sets.newHashSet(new Block[]{Blocks.reeds, Blocks.vine, Blocks.cocoa, Blocks.deadbush, Blocks.leaves, Blocks.leaves2, Blocks.tallgrass, Blocks.double_plant});

    public ItemCaneKnife(ToolMaterial material) {
        super(3.0F, material, EFFECTIVE_ON);
        setUnlocalizedName(Paths.ModAssets + "caneKnife");
    }

    @Override
    public int getItemEnchantability() {return this.toolMaterial.WOOD.getHarvestLevel(); }
}