package com.Girafi.culinarycultivation.item;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import com.Girafi.culinarycultivation.reference.Paths;
import net.minecraft.block.Block;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemStack;

public class SourceSeedFood extends ItemSeedFood {

    public SourceSeedFood(int amount, float saturation, Block blockCrop, Block placeableOnBlock) {
        super(amount, saturation, blockCrop, placeableOnBlock);
        this.setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Paths.ModAssets, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s", Paths.ModAssets, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    /*@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }*/

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}