package com.Girafi.culinarycultivation.item.equipment.tool;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemMeatCleaver extends ItemSword {

    public ItemMeatCleaver() {
        super(Item.ToolMaterial.IRON);
        this.maxStackSize = 1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stackIn, EntityPlayer player, List<String> list, boolean par4) {
        list.add("A finer way to cleave meat");
    }
}