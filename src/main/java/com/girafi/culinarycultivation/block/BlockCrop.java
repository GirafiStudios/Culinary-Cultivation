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
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class BlockCrop extends BlockCrops {
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
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (getAge(state) >= getMaxAge()) {
            if (ConfigurationHandler.canRightClickHarvestAllCulinaryCultivationCrops) {
                this.rightClickHarvest(world, pos, state);
                return true;
            } else if (canRightClickHarvest && ConfigurationHandler.canRightClickHarvestCulinaryCultivationCrops) {
                this.rightClickHarvest(world, pos, state);
                return true;
            }
        }
        return super.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
    }

    protected void rightClickHarvest(World world, BlockPos pos, IBlockState state) {
        super.dropBlockAsItem(world, pos, state, 0);
        world.setBlockState(pos, withAge(0), 2);
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
        return getAge(state) == getMaxAge() ? crop.getItem() : notGrownDrop().getItem();
    }

    @Override
    public void getDrops(@Nonnull NonNullList<ItemStack> drops, @Nullable IBlockAccess world, @Nullable BlockPos pos, @Nullable IBlockState state, int fortune) {
        int age = getAge(state);
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;

        if (age >= getMaxAge()) {
            if (!crop.isEmpty() && maxDropValueCrop > 0) {
                int cropDrop = MathHelper.getInt(rand, minDropValueCrop, maxDropValueCrop);
                if (cropDrop == 0) {
                    if (rand.nextInt(100) >= 50) {
                        drops.add(crop.copy());
                    }
                }
                for (int i = 0; i < cropDrop + fortune; ++i) {
                    drops.add(crop.copy());
                }
            }

            if (!seed.isEmpty() && maxDropValueSeed > 0) {
                int seedDrop = MathHelper.getInt(rand, minDropValueSeed, maxDropValueSeed);
                if (seedDrop == 0) {
                    if (rand.nextInt(100) >= 25) {
                        drops.add(seed.copy());
                    }
                }
                for (int i = 0; i < seedDrop + fortune; ++i) {
                    drops.add(seed.copy());
                }
            }
        }

        if (age < 7) {
            if (!notGrownDrop().isEmpty()) {
                drops.add(notGrownDrop().copy());
            }
        }
    }
}