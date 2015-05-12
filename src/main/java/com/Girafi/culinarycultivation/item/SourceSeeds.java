package com.Girafi.culinarycultivation.item;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import com.Girafi.culinarycultivation.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class SourceSeeds extends ItemSeeds {

    public static final ArrayList<SourceSeeds> seeds = new ArrayList();
    protected Item item;

    public SourceSeeds(Block blockCrop, Block placeableOnBlock) {
        super(blockCrop, placeableOnBlock);
        this.setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
        seeds.add(this);
    }

    public SourceSeeds setItem(Item item) {
        this.item = item;
        return this;
    }

    public ItemStack getSeedStack() {
        return new ItemStack(item);
    }

    public boolean hasItemAssigned() {
        return item != null;
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    private String getName()
    {
        return this.getUnlocalizedName();
    }

}