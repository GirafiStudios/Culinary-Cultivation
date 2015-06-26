package com.Girafi.culinarycultivation.item;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import com.Girafi.culinarycultivation.reference.Paths;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class SourceFood extends ItemFood {

    public SourceFood(int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        this.setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
    }

    public SourceFood(int fish, boolean cooked) { this(fish, 0.6F, cooked); }

    @Override
    public String getUnlocalizedName() {
        return String.format("item.%s%s", Paths.ModAssets, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return String.format("item.%s%s", Paths.ModAssets, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

//    @Override
//    @SideOnly(Side.CLIENT)
//    public void registerIcons(IIconRegister iconRegister) {
//        itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
//    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}