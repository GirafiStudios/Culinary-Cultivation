package com.Girafi.culinarycultivation.block;

import com.Girafi.culinarycultivation.reference.Paths;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;
import java.util.Random;

public class BlockCrop extends BlockCrops {
    private Item itemCrop;
    private Item itemSeed;
    private int minDropValueCrop;
    private int maxDropValueCrop;
    private int minDropValueSeed;
    private int maxDropValueSeed;
    private boolean isDoublePlant;

/*    protected BlockCrop() {
        //TODO List
        *//*
        - Custom block bounds, based on current crop height
         *//*
    }*/

    public boolean setDoublePlant(boolean isDoublePlant) {
        this.isDoublePlant = isDoublePlant;
        return isDoublePlant;
    }

    @Override
    public String getUnlocalizedName() {
        String name = "tile." + Paths.ModAssets + GameRegistry.findUniqueIdentifierFor(getBlockState().getBlock()).name;
        return name;
    }

    @Override
    protected boolean canPlaceBlockOn(Block ground) {
        return ground == Blocks.farmland;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);

        if (isDoublePlant) {

        } else {
            if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
                int i = ((Integer) state.getValue(AGE)).intValue();

                if (i < 7) {
                    float f = getGrowthChance(this, worldIn, pos);

                    if (rand.nextInt((int) (25.0F / f) + 1) == 0) {
                        worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(i + 1)), 2);
                    }
                }
            }
        }
    }

    @Override
    public void grow(World worldIn, BlockPos pos, IBlockState state) {
        if (isDoublePlant) {

        } else {
            int i = ((Integer) state.getValue(AGE)).intValue() + MathHelper.getRandomIntegerInRange(worldIn.rand, 2, 5);

            if (i > 7) {
                i = 7;
            }

            worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(i)), 2);
        }
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        if (isDoublePlant) {

        }
        return ((Integer) state.getValue(AGE)).intValue() < 7;
    }

    protected Item getModCrop() {
        return itemCrop;
    }

    public BlockCrop setModCrop(Item item, int minDropValue, int maxDropValue) {
        this.itemCrop = item;
        this.minDropValueCrop = minDropValue;
        this.maxDropValueCrop = maxDropValue;
        return this;
    }

    protected Item getModSeed() {
        return itemSeed;
    }

    public BlockCrop setModSeed(Item item, int minDropValue, int maxDropValue) {
        this.itemSeed = item;
        this.minDropValueSeed = minDropValue;
        this.maxDropValueSeed = maxDropValue;
        return this;
    }

    public BlockCrop setModSeed(Item item) {
        this.itemSeed = item;
        return this;
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ((Integer) state.getValue(AGE)).intValue() == 7 ? this.getModCrop() : this.getModSeed();
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) { //TODO Fix 0 drops
        List<ItemStack> ret = new java.util.ArrayList<ItemStack>();

        Random rand = world instanceof World ? ((World) world).rand : RANDOM;
        int age = ((Integer) state.getValue(AGE)).intValue();

        int integerInRangeCrop = MathHelper.getRandomIntegerInRange(rand, minDropValueCrop, maxDropValueCrop);
        int integerInRangeSeed = MathHelper.getRandomIntegerInRange(rand, minDropValueSeed, maxDropValueSeed);
        int count = quantityDropped(state, fortune, rand);
        if (age >= 7) {
            for (int i = 0; i < count + integerInRangeCrop; i++) {
                Item item = this.getItemDropped(state, rand, fortune);
                if (item != null) {
                    ret.add(new ItemStack(this.getModCrop(), 1, this.damageDropped(state)));
                }
            }
            for (int i = 0; i < count + integerInRangeSeed; i++) {
                Item item = this.getItemDropped(state, rand, fortune);
                if (item != null) {
                    ret.add(new ItemStack(this.getModSeed(), 1, this.damageDropped(state)));
                }
            }
        }
        if (age <= 6) {
            if (itemSeed == null) {
                ret.add(new ItemStack(this.getModCrop(), 1));
            } else
                ret.add(new ItemStack(this.getModSeed(), 1));
        }
        return ret;
    }
}