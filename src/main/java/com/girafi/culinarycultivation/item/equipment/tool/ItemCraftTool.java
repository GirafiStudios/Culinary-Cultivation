package com.girafi.culinarycultivation.item.equipment.tool;

import com.girafi.culinarycultivation.util.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

public class ItemCraftTool extends ItemTool {

    protected ItemCraftTool(float attackDamage, float attackSpeed, ToolMaterial material, Set<Block> effectiveBlocks) {
        super(attackDamage, attackSpeed, material, effectiveBlocks);
    }

    protected ItemCraftTool(Item.ToolMaterial material, Set<Block> effectiveBlocks) {
        super(material, effectiveBlocks);
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        if (advanced && stack.getItemDamage() < 2) {
            int damage = NBTHelper.getInt(stack, "damage");
            int maxDamage = this.getMaxDamage();
            tooltip.add("Durability: " + (maxDamage - damage) + " / " + maxDamage);
        }
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    @Nonnull
    public ItemStack getContainerItem(@Nonnull ItemStack stack) {
        int damage = NBTHelper.getInt(stack, "damage") + 1;
        if (damage < this.getMaxDamage()) {
            ItemStack container = stack.copy();
            NBTHelper.setInt(container, "damage", damage);
            return container;
        }
        return null;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return (NBTHelper.getInt(stack, "damage") > 0);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return NBTHelper.getInt(stack, "damage") / this.getMaxDamage();
    }
}