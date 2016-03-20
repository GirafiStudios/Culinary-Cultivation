package com.Girafi.culinarycultivation.item.equipment.tool;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Set;

public class ItemKnife extends ItemTool {
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[]{});

    public ItemKnife(Item.ToolMaterial material) {
        super(1.5F, -2.9F, material, EFFECTIVE_ON);
        maxStackSize = 1;
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

    @Override
    public int getItemEnchantability() {
        return ToolMaterial.WOOD.getHarvestLevel();
    }
}