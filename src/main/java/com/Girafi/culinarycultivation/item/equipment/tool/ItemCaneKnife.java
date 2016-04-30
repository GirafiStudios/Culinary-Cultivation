package com.girafi.culinarycultivation.item.equipment.tool;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;

import java.util.Set;

public class ItemCaneKnife extends ItemTool {
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.REEDS, Blocks.VINE, Blocks.COCOA, Blocks.DEADBUSH, Blocks.LEAVES, Blocks.LEAVES2, Blocks.TALLGRASS, Blocks.DOUBLE_PLANT);

    public ItemCaneKnife(ToolMaterial material) {
        super(3.0F, -2.0F, material, EFFECTIVE_ON);
    }

    @Override
    public int getItemEnchantability() {
        return Item.ToolMaterial.WOOD.getHarvestLevel();
    }
}