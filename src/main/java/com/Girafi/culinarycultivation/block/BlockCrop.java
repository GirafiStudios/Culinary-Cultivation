package com.Girafi.culinarycultivation.block;

import com.Girafi.culinarycultivation.reference.Paths;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCrop extends BlockCrops {
    public ItemStack itemCrop;
    public ItemStack itemSeed;
    private int minDropValueCrop;
    private int maxDropValueCrop;
    private int minDropValueSeed;
    private int maxDropValueSeed;

    @Override
    public String getUnlocalizedName() {
        String name = "tile." + Paths.ModAssets + GameRegistry.findUniqueIdentifierFor(getBlockState().getBlock()).name;
        return name;
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos) {
        return itemSeed.getItem();
    }

    public BlockCrop setModCrop(ItemStack item, int minDropValue, int maxDropValue) {
        this.itemCrop = item;
        this.minDropValueCrop = minDropValue;
        this.maxDropValueCrop = maxDropValue;
        return this;
    }

    public Block setModSeed(ItemStack stack, int minDropValue, int maxDropValue) {
        this.itemSeed = stack;
        this.minDropValueSeed = minDropValue;
        this.minDropValueCrop = maxDropValue;
        return this;
    }

    @Override
    protected Item getSeed() {
        return itemSeed.getItem();
    }

    @Override
    protected Item getCrop() {
        return itemCrop.getItem();
    }
}