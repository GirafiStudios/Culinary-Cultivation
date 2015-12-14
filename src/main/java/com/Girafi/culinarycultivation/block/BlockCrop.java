package com.Girafi.culinarycultivation.block;

import com.Girafi.culinarycultivation.handler.ConfigurationHandler;
import com.Girafi.culinarycultivation.reference.Paths;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockCrop extends BlockCrops {
    public ItemStack itemCrop;
    public ItemStack itemSeed;
    private int minDropValueCrop;
    private int maxDropValueCrop;
    private int minDropValueSeed;
    private int maxDropValueSeed;
    private boolean canRightClickHarvest;

    @Override
    public String getUnlocalizedName() {
        String name = "tile." + GameData.getItemRegistry().getNameForObject(Item.getItemFromBlock(getBlockState().getBlock()));
        return name;
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World world, BlockPos pos) {
        if (itemSeed == null) {
            return itemCrop.getItem();
        }
        return itemSeed.getItem();
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (ConfigurationHandler.CanRightClickHarvestAllCulinaryCultivationCrops) {
            this.rightClickHarvest(world, pos, state);
        } else if (canRightClickHarvest && ConfigurationHandler.CanRightClickHarvestCulinaryCultivationCrops) {
            this.rightClickHarvest(world, pos, state);
        }
        return super.onBlockActivated(world, pos, state, player, side, hitX, hitY, hitZ);
    }

    public boolean rightClickHarvest (World world, BlockPos pos, IBlockState state) {
        int age = (state.getValue(AGE)).intValue();
        if (age >= 7) {
            super.dropBlockAsItem(world, pos, state, 0);
            world.setBlockState(pos, state.withProperty(AGE, 0), 2);
            return true;
        }
        return true;
    }

    public BlockCrop setModCrop(ItemStack item, int minDropValue, int maxDropValue) {
        itemCrop = item;
        minDropValueCrop = minDropValue;
        maxDropValueCrop = maxDropValue;
        return this;
    }

    public BlockCrop setModSeed(ItemStack stack, int minDropValue, int maxDropValue) {
        itemSeed = stack;
        minDropValueSeed = minDropValue;
        maxDropValueSeed = maxDropValue;
        return this;
    }

    public boolean setRightClickHarvest () {
        this.canRightClickHarvest = true;
        return canRightClickHarvest;
    }

    protected ItemStack notGrownDrop() {
        if (itemSeed == null) {
            return itemCrop;
        }
        return itemSeed;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return (state.getValue(AGE)).intValue() == 7 ? itemCrop.getItem() : notGrownDrop().getItem();
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        List<ItemStack> ret = new java.util.ArrayList<ItemStack>();
        int age = (state.getValue(AGE)).intValue();
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;

        if (age >= 7) {
            int cropDrop = MathHelper.getRandomIntegerInRange(rand, minDropValueCrop, maxDropValueCrop);
            if (cropDrop == 0) {
                if (rand.nextInt(100) >= 50) {
                    ret.add(itemCrop.copy());
                }
            }
            for (int i = 0; i < cropDrop + fortune; ++i) {
                ret.add(itemCrop.copy());
            }

            if (itemSeed != null) {
                int seedDrop = MathHelper.getRandomIntegerInRange(rand, minDropValueSeed, maxDropValueSeed);
                if (seedDrop == 0) {
                    if (rand.nextInt(100) >= 25) {
                        ret.add(itemSeed.copy());
                    }
                }
                for (int i = 0; i < seedDrop + fortune; ++i) {
                    ret.add(itemSeed.copy());
                }
            }
        }

        if (age <= 6) {
            if (notGrownDrop() != null) {
                ret.add(notGrownDrop().copy());
            }
        }
        return ret;
    }
}