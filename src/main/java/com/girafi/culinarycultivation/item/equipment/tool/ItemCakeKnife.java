package com.girafi.culinarycultivation.item.equipment.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class ItemCakeKnife extends ItemCraftTool {
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.CAKE);

    public ItemCakeKnife() {
        super(ToolMaterial.IRON, EFFECTIVE_ON);
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
}