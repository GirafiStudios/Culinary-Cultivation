package com.girafi.culinarycultivation.item.equipment.tool;

import com.girafi.culinarycultivation.api.item.ICraftingTool;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemCakeKnife extends ItemTool implements ICraftingTool {

    public ItemCakeKnife() {
        super(ToolMaterial.IRON, Sets.newHashSet());
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, @Nullable EntityLivingBase attacker) {
        return false;
    }

    @Override
    @Nonnull
    public Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, ItemStack stack) {
        return HashMultimap.create();
    }

    @Override
    public float getStrVsBlock(@Nullable ItemStack stack, IBlockState state) {
        Material material = state.getMaterial();
        return material == Material.CAKE ? this.efficiencyOnProperMaterial : super.getStrVsBlock(stack, state);
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }
}