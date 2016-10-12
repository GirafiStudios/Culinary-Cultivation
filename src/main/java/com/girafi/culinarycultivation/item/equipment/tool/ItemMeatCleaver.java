package com.girafi.culinarycultivation.item.equipment.tool;

import com.girafi.culinarycultivation.api.item.ICraftingTool;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemMeatCleaver extends ItemSword implements ICraftingTool {

    public ItemMeatCleaver() {
        super(Item.ToolMaterial.IRON);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        tooltip.add("A finer way to cleave meat");
    }
}