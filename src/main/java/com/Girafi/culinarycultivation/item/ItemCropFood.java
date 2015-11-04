package com.Girafi.culinarycultivation.item;

import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.reference.Paths;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Map;

public class ItemCropFood extends SourceFood implements IPlantable {
    public ItemCropFood() {
        super(0, 0.0F, false);
    }

    @Override
    public int getHealAmount(ItemStack stack) {
        CropType cropType = CropType.byItemStack(stack);
        return cropType.getHungerAmount();
    }

    @Override
    public float getSaturationModifier(ItemStack stack) {
        CropType cropType = CropType.byItemStack(stack);
        return cropType.getSaturation();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
        CropType[] aCropType = CropType.values();
        int i = aCropType.length;

        for (int j = 0; j < i; ++j) {
            CropType cropType = aCropType[j];
            subItems.add(new ItemStack(this, 1, cropType.getMetadata()));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        CropType cropType = CropType.byItemStack(stack);
        return "item." + Paths.ModAssets + cropType.getUnlocalizedName();
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        CropType cropType = CropType.byItemStack(stack);
        if (cropType.isSeed) {
            if (side != EnumFacing.UP) {
                return false;
            } else if (!player.canPlayerEdit(pos.offset(side), side, stack)) {
                return false;
            } else if (world.getBlockState(pos).getBlock().canSustainPlant(world, pos, EnumFacing.UP, this) && world.isAirBlock(pos.up())) {
                world.setBlockState(pos.up(), cropType.crop.getDefaultState());
                --stack.stackSize;
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return net.minecraftforge.common.EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        CropType[] aCropType = CropType.values();
        int i = aCropType.length;

        for (int j = 0; j < i; ++j) {
            CropType cropType = aCropType[j];
            if (cropType.isSeed) {
                return cropType.crop.getDefaultState();
            }
        }
        return null;
    }

    public static enum CropType { //TODO Make proper values
        CUCUMBER(0, "cucumber", 3, 0.4F, ModBlocks.cucumber),
        TOMATO(1, "tomato", 4, 0.5F, ModBlocks.tomato);

        private static final Map META_LOOKUP = Maps.newHashMap();
        private final int meta;
        private final String unlocalizedName;
        private final int hungerAmount;
        private final float saturationModifier;
        private final boolean isSeed;
        private final Block crop;

        private CropType(int meta, String unlocalizedName, int hungerAmount, float saturationModifier) {
            this.meta = meta;
            this.unlocalizedName = unlocalizedName;
            this.hungerAmount = hungerAmount;
            this.saturationModifier = saturationModifier;
            this.isSeed = false;
            this.crop = null;
        }

        private CropType(int meta, String unlocalizedName, int hungerAmount, float saturationModifier, Block crop) {
            this.meta = meta;
            this.unlocalizedName = unlocalizedName;
            this.hungerAmount = hungerAmount;
            this.saturationModifier = saturationModifier;
            this.isSeed = true;
            this.crop = crop;
        }

        public int getMetadata() {
            return this.meta;
        }

        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }

        public int getHungerAmount() {
            return this.hungerAmount;
        }

        public float getSaturation() {
            return this.saturationModifier;
        }

        public static CropType byMetadata(int meta) {
            CropType cropType = (CropType) META_LOOKUP.get(Integer.valueOf(meta));
            return cropType == null ? CUCUMBER : cropType;
        }

        public static CropType byItemStack(ItemStack stack) {
            return stack.getItem() instanceof ItemCropFood ? byMetadata(stack.getMetadata()) : CUCUMBER;
        }

        static {
            CropType[] values = values();
            int var1 = values.length;

            for (int value = 0; value < var1; ++value) {
                CropType cropValues = values[value];
                META_LOOKUP.put(Integer.valueOf(cropValues.getMetadata()), cropValues);
            }
        }
    }
}