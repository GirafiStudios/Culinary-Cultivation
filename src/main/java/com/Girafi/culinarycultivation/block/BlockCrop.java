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
    public Item itemSeed;
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
        return itemSeed;
    }

    public BlockCrop setModCrop(ItemStack item, int minDropValue, int maxDropValue) {
        this.itemCrop = item;
        this.minDropValueCrop = minDropValue;
        this.maxDropValueCrop = maxDropValue;
        return this;
    }

    public Block setModSeed(Item item) {
        this.itemSeed = item;
        return this;
    }

    @Override
    protected Item getSeed() {
        return itemSeed;
    }

    @Override
    protected Item getCrop() {
        return itemCrop.getItem();
    }
}