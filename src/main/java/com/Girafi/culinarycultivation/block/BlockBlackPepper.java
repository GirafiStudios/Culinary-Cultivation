package com.Girafi.culinarycultivation.block;


import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBlackPepper extends BlockCrops {

    @Override
    protected boolean canPlaceBlockOn(Block ground) {
        return ground == Blocks.farmland || ground == ModBlocks.blackPepper;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
            int i = ((Integer) state.getValue(AGE)).intValue();
            float f = getGrowthChance(this, worldIn, pos);

            if (rand.nextInt((int) (25.0F / f) + 1) == 0) {
                if (i == 3 && worldIn.getBlockState(pos.up()) == Blocks.air) {
                    worldIn.setBlockState(pos.up(), state.withProperty(AGE, Integer.valueOf(i + 1)), 2);
                }
                if (i == 3 && worldIn.getBlockState(pos.up()) instanceof BlockAir) {
                    worldIn.setBlockState(pos.up(), state.withProperty(AGE, Integer.valueOf(i + 1)), 2);
                }
                if (i == 3 && worldIn.getBlockState(pos.up()) == state.getBlock().getStateFromMeta(5)) {
                    worldIn.setBlockState(pos, state.withProperty(AGE, 6), 2);
                    worldIn.setBlockState(pos.up(), state.withProperty(AGE, 7), 2);
                } else if (i < 5 && i != 3)
                    worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(i + 1)), 2);
            }
        }
    }

    @Override
    public void grow(World worldIn, BlockPos pos, IBlockState state) {
        int i = ((Integer) state.getValue(AGE)).intValue() + MathHelper.getRandomIntegerInRange(worldIn.rand, 0, 1);
        int meta = ((Integer) state.getValue(AGE)).intValue();

        if (i > 7) {
            i = 7;
        }

        if (meta == 3) {
            worldIn.setBlockState(pos.up(), state.withProperty(AGE, 4), 2);
        }
        if (meta == 5 && worldIn.getBlockState(pos.down()) == state.getBlock().getStateFromMeta(3)) {
            worldIn.setBlockState(pos.down(), state.withProperty(AGE, 6), 2);
            worldIn.setBlockState(pos, state.withProperty(AGE, 7), 2);
        } else
            worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(i)), 2);
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        int meta = ((Integer) state.getValue(AGE)).intValue();
        if (meta == 3 && worldIn.getBlockState(pos.up()) == state.getBlock().getStateFromMeta(4) || meta == 6) {
            return false;
        } else
            return meta < 7;
    }

    protected Item getSeed() {
        return ModItems.blackPepperDrupe;
    }

    protected Item getCrop() {
        return ModItems.toolHandle;
    }
}