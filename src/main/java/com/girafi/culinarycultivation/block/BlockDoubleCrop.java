package com.girafi.culinarycultivation.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class BlockDoubleCrop extends BlockCrop { //TODO Fix a couple of thing, after extending BlockCrop
    public static final PropertyInteger DOUBLE_AGE = PropertyInteger.create("age", 0, 14);

    public BlockDoubleCrop() {
        super();
        this.setDefaultState(this.blockState.getBaseState().withProperty(DOUBLE_AGE, 0));
    }

    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        int age = getAge(state);
        if (age <= 7) {
            return new AxisAlignedBB(0F, 0F, 0F, 1.0F, age == 0 ? 0.25F : age == 1 || age == 2 ? 0.5F : age == 3 || age == 4 ? 0.8F : 1.0F, 1.0F);
        } else if (age >= 8) {
            return new AxisAlignedBB(0F, 0F, 0F, 1.0F, age == 8 || age == 9 || age == 10 ? 0.35F : 1.0F, 1.0F);
        }
        return FULL_BLOCK_AABB;
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state instanceof BlockFarmland || state.getBlock() instanceof BlockDoubleCrop;
    }

    @Override
    @Nonnull
    protected PropertyInteger getAgeProperty() {
        return DOUBLE_AGE;
    }

    @Override
    public int getMaxAge() {
        return 14;
    }

    @Override
    protected void rightClickHarvest(World world, BlockPos pos, IBlockState state) {
        int age = getAge(state);
        if (age == 7 || age >= getMaxAge()) {
            super.dropBlockAsItem(world, pos, state, 0);
            world.setBlockState(pos.down(), withAge(6), 2);
            world.setBlockState(pos, withAge(11), 2);
        }
    }

    @Override
    public void neighborChanged(@Nullable IBlockState state, @Nullable World world, @Nullable BlockPos pos, Block neighborBlock, BlockPos neighborPos) {
        if (getAge(state) == 7 && world.getBlockState(pos.up()).getBlock() instanceof BlockAir) {
            world.setBlockState(pos, withAge(6), 2);
        }
        super.neighborChanged(state, world, pos, neighborBlock, neighborPos);
    }

    @Override
    public void updateTick(World world, BlockPos pos, @Nullable IBlockState state, Random rand) {
        super.updateTick(world, pos, state, rand);

        this.checkAndDropBlock(world, pos, state);

        if (world.getLightFromNeighbors(pos.up()) >= 9) {
            int age = getAge(state);

            if (age < getMaxAge()) {
                float f = getGrowthChance(this, world, pos);

                if (ForgeHooks.onCropsGrowPre(world, pos, state, rand.nextInt((int)(25.0F / f) + 1) == 0)) {
                    if (age == 6 && world.getBlockState(pos.up()).getBlock() instanceof BlockAir) {
                        world.setBlockState(pos.up(), withAge(8), 2);
                    } else if (age != 6 && age != 7 && age != 13)
                        world.setBlockState(pos, withAge(age + 1), 2);
                }
                if (age == 6 && world.getBlockState(pos.up()).getBlock() instanceof BlockDoubleCrop && world.getBlockState(pos.up()) == withAge(13)) {
                    world.setBlockState(pos, withAge(7), 2);
                    world.setBlockState(pos.up(), withAge(getMaxAge()), 2);
                }
            }
        }
    }

    @Override
    public void grow(@Nonnull World world, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        int age = getAge(state);
        int i = age + MathHelper.getInt(world.rand, 1, 1);

        if (i > getMaxAge()) {
            i = getMaxAge();
        }
        if (age == 6 && world.getBlockState(pos.up()).getBlock() instanceof BlockAir) {
            world.setBlockState(pos.up(), withAge(8), 2);
        } else if (age != 6 && age != 7 && age != 13) {
            world.setBlockState(pos, withAge(i), 2);
        }
        if (age == 6 && world.getBlockState(pos.up()).getBlock() instanceof BlockDoubleCrop && world.getBlockState(pos.up()) == withAge(13)) {
            world.setBlockState(pos, withAge(7), 2);
            world.setBlockState(pos.up(), withAge(getMaxAge()), 2);
        }
    }

    @Override
    public boolean canGrow(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, boolean isClient) {
        int age = getAge(state);
        return age < getMaxAge() && age != 7;
    }

    @Override
    public boolean canUseBonemeal(@Nonnull World world, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        int age = getAge(state);
        if (age < getMaxAge()) {
            IBlockState stateDown = world.getBlockState(pos.down());
            if (age == 13 && stateDown.getBlock() instanceof BlockDoubleCrop) {
                if (getAge(stateDown) == 6) {
                    world.playEvent(2005, pos.down(), 0);
                    world.setBlockState(pos.down(), withAge(getAge(stateDown) + 1), 2);
                    world.setBlockState(pos, withAge(age + 1), 2);
                    return true;
                }
            }
            IBlockState stateUp = world.getBlockState(pos.down());
            if (age == 6 && stateUp.getBlock() instanceof BlockDoubleCrop) {
                if (getAge(stateUp) >= 8) {
                    world.playEvent(2005, pos.up(), 0);
                    world.setBlockState(pos.up(), withAge(getAge(stateUp) + 1), 2);
                    return true;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DOUBLE_AGE);
    }
}