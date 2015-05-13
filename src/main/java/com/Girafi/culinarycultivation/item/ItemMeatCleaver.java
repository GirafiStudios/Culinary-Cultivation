package com.Girafi.culinarycultivation.item;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import com.Girafi.culinarycultivation.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import java.util.List;

public class ItemMeatCleaver extends ItemSword {

    public ItemMeatCleaver(ToolMaterial material) {
        super(material);
        setUnlocalizedName(Reference.MOD_ID.toLowerCase() + ":" + "meatCleaver");
        setTextureName(Reference.MOD_ID.toLowerCase() + ":" + "meatCleaver");
        setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
        maxStackSize=1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stackIn, EntityPlayer playerIn, List list, boolean par4) {
        list.add("A finer way to cleave meat");
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldRotateAroundWhenRendering()
    {
        return true;
    }
}