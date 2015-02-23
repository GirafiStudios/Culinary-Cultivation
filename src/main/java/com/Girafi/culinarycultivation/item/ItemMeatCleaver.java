package com.Girafi.culinarycultivation.item;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import com.Girafi.culinarycultivation.reference.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import java.util.List;

public class ItemMeatCleaver extends ItemSword {

    public ItemMeatCleaver(ToolMaterial material) {
        super(material);
        setUnlocalizedName("meatCleaver");
        setTextureName(Reference.MOD_ID.toLowerCase() + ":" + "meatCleaver");
        setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
        maxStackSize=1;
    }

    @Override
    public void addInformation(ItemStack stackIn, EntityPlayer playerIn, List list, boolean par4)
    {
        list.add("A finer way to cleave meat");
    }

////Setting unlocalized name
//Copyed from SourceItem
    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}