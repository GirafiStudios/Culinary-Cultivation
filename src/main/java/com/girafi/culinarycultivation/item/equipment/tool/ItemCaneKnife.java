package com.girafi.culinarycultivation.item.equipment.tool;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ItemCaneKnife extends ItemAxe {

    public ItemCaneKnife() {
        super(ToolMaterial.IRON, 2.0F, 2.0F);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, @Nullable EntityLivingBase attacker) {
        stack.damageItem(1, attacker);
        return true;
    }

    @Override
    public boolean canHarvestBlock(IBlockState state) {
        return state.getBlock() == Blocks.WEB;
    }

    @Override
    public float getStrVsBlock(@Nullable ItemStack stack, IBlockState state) {
        for (String type : getToolClasses(stack)) {
            if (state.getBlock().isToolEffective(type, state)) {
                return efficiencyOnProperMaterial;
            }
        }
        Material material = state.getMaterial();
        return material == Material.PLANTS || material == Material.VINE || material == Material.LEAVES || material == Material.GOURD || material == Material.CACTUS ? this.efficiencyOnProperMaterial : 1.0F;
    }
}