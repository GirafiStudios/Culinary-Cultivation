package com.girafi.culinarycultivation.item.equipment.tool;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemKitchenKnife extends ItemCraftTool {

    public ItemKitchenKnife() {
        super(1.0F, -2.F, ToolMaterial.IRON, Sets.newHashSet(new Block[]{}));
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        stack.damageItem(1, attacker);
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        if ((double) state.getBlockHardness(world, pos) != 0.0D) {
            stack.damageItem(2, entityLiving);
        }
        return true;
    }
}