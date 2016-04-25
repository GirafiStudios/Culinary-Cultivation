package com.Girafi.culinarycultivation.item.equipment.tool;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import java.util.Set;

public class ItemCakeKnife extends ItemTool {

    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.CAKE);

    public ItemCakeKnife(ToolMaterial material) {
        super(-2.0F, -3.1F, material, EFFECTIVE_ON);
        maxStackSize = 1;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        stack.damageItem(0, attacker);
        return true;
    }

    @Override
    public int getItemEnchantability() {
        return ToolMaterial.WOOD.getHarvestLevel();
    }
}