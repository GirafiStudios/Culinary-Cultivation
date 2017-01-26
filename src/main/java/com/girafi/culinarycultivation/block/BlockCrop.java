package com.girafi.culinarycultivation.block;

import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.item.ItemCropProduct;
import com.girafi.culinarycultivation.util.ConfigurationHandler;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockCrop extends BlockCrops {
    private static final AxisAlignedBB[] CROP_AAAB = new AxisAlignedBB[]{new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.4375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5625D, 1.0D)};
    @Nonnull
    private ItemStack crop = ItemStack.EMPTY;
    @Nonnull
    private ItemStack seed = ItemStack.EMPTY;
    private int minDropValueCrop;
    private int maxDropValueCrop;
    private int minDropValueSeed;
    private int maxDropValueSeed;
    private boolean canRightClickHarvest;

    @Override
    @Nonnull
    public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
        if (seed.isEmpty()) {
            return crop;
        }
        return ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return CROP_AAAB[state.getValue(AGE)];
    }

    @Override
    @Nonnull
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (ConfigurationHandler.canRightClickHarvestAllCulinaryCultivationCrops) {
            this.rightClickHarvest(world, pos, state);
        } else if (canRightClickHarvest && ConfigurationHandler.canRightClickHarvestCulinaryCultivationCrops) {
            this.rightClickHarvest(world, pos, state);
        }
        return super.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
    }

    private boolean rightClickHarvest(World world, BlockPos pos, IBlockState state) {
        int age = state.getValue(AGE);
        if (age >= 7) {
            super.dropBlockAsItem(world, pos, state, 0);
            world.setBlockState(pos, state.withProperty(AGE, 0), 2);
            return true;
        }
        return true;
    }

    public BlockCrop set(@Nonnull ItemStack stack, int minDropValue, int maxDropValue) {
        if (stack.getItem().equals(ModItems.CROP_FOOD)) {
            crop = stack;
            minDropValueCrop = minDropValue;
            maxDropValueCrop = maxDropValue;
        }
        if (stack.getItem().equals(ModItems.CROP_SEEDS)) {
            seed = stack;
            minDropValueSeed = minDropValue;
            maxDropValueSeed = maxDropValue;
        }
        return this;
    }

    public BlockCrop setCrop(ItemCropProduct.ProductType productType, int minDropValue, int maxDropValue) {
        set(new ItemStack(ModItems.CROP_FOOD, 1, productType.getMetadata()), minDropValue, maxDropValue);
        if (productType.hasSeed()) {
            seed = new ItemStack(ModItems.CROP_SEEDS, 1, productType.getMetadata());
        }
        return this;
    }

    public BlockCrop setSeed(ItemCropProduct.ProductType productType, int minDropValue, int maxDropValue) {
        set(new ItemStack(ModItems.CROP_SEEDS, 1, productType.getMetadata()), minDropValue, maxDropValue);
        return this;
    }

    public boolean setRightClickHarvest() {
        return canRightClickHarvest = true;
    }

    @Nonnull
    private ItemStack notGrownDrop() {
        if (seed.isEmpty()) {
            return crop;
        }
        return seed;
    }

    @Override
    @Nonnull
    public Item getItemDropped(@Nullable IBlockState state, Random rand, int fortune) {
        return state.getValue(AGE) == 7 ? crop.getItem() : notGrownDrop().getItem();
    }

    @Override
    @Nonnull
    public List<ItemStack> getDrops(@Nullable IBlockAccess world, @Nullable BlockPos pos, @Nullable IBlockState state, int fortune) {
        List<ItemStack> ret = new java.util.ArrayList<>();
        int age = state.getValue(AGE);
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;

        if (age >= 7) {
            if (!crop.isEmpty() && maxDropValueCrop > 0) {
                int cropDrop = MathHelper.getInt(rand, minDropValueCrop, maxDropValueCrop);
                if (cropDrop == 0) {
                    if (rand.nextInt(100) >= 50) {
                        ret.add(crop.copy());
                    }
                }
                for (int i = 0; i < cropDrop + fortune; ++i) {
                    ret.add(crop.copy());
                }
            }

            if (!seed.isEmpty() && maxDropValueSeed > 0) {
                int seedDrop = MathHelper.getInt(rand, minDropValueSeed, maxDropValueSeed);
                if (seedDrop == 0) {
                    if (rand.nextInt(100) >= 25) {
                        ret.add(seed.copy());
                    }
                }
                for (int i = 0; i < seedDrop + fortune; ++i) {
                    ret.add(seed.copy());
                }
            }
        }

        if (age <= 6) {
            if (!notGrownDrop().isEmpty()) {
                ret.add(notGrownDrop().copy());
            }
        }
        return ret;
    }
}