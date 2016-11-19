package com.girafi.culinarycultivation.item.equipment.tool;

import com.girafi.culinarycultivation.api.item.ICraftingTool;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemKitchenKnife extends ItemTool implements ICraftingTool {

    public ItemKitchenKnife() {
        super(1.0F, -2.F, ToolMaterial.IRON, Sets.newHashSet(new Block[]{}));
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, @Nullable EntityLivingBase attacker) {
        stack.damageItem(1, attacker);
        return true;
    }

    @Override
    public boolean onBlockDestroyed(@Nullable ItemStack stack, @Nullable World world, @Nullable IBlockState state, @Nullable BlockPos pos, @Nullable EntityLivingBase entityLiving) {
        if ((double) state.getBlockHardness(world, pos) != 0.0D) {
            stack.damageItem(2, entityLiving);
        }
        return true;
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }
}