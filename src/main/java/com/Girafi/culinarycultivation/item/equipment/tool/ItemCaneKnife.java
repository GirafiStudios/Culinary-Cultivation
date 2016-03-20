package com.Girafi.culinarycultivation.item.equipment.tool;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;

import java.util.Set;

public class ItemCaneKnife extends ItemTool {

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.reeds, Blocks.vine, Blocks.cocoa, Blocks.deadbush, Blocks.leaves, Blocks.leaves2, Blocks.tallgrass, Blocks.double_plant);

    public ItemCaneKnife(ToolMaterial material) {
        super(3.0F, material, EFFECTIVE_ON);
    }

    @Override
    public int getItemEnchantability() {
        return Item.ToolMaterial.WOOD.getHarvestLevel();
    }
}