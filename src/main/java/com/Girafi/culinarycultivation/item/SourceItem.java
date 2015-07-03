package com.Girafi.culinarycultivation.item;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import com.Girafi.culinarycultivation.reference.Paths;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SourceItem extends Item {
    public SourceItem() {
        super();
        this.setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("item.%s%s", Paths.ModAssets, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return String.format("item.%s%s", Paths.ModAssets, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}