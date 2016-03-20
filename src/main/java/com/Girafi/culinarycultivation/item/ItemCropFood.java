package com.Girafi.culinarycultivation.item;

import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.reference.Paths;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Map;

public class ItemCropFood extends ItemFood implements IPlantable {

    public ItemCropFood() {
        super(0, 0.0F, false);
    }

    @Override
    public int getHealAmount(ItemStack stack) {
        return CropType.byItemStack(stack).getHungerAmount();
    }

    @Override
    public float getSaturationModifier(ItemStack stack) {
        return CropType.byItemStack(stack).getSaturation();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (CropType cropType : CropType.values()) {
            subItems.add(new ItemStack(this, 1, cropType.getMetadata()));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "item." + Paths.ModAssets + CropType.byItemStack(stack).getCropName();
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        CropType cropType = CropType.byItemStack(stack);
        IBlockState state = world.getBlockState(pos);
        if (cropType.isSeed && facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, stack) && state.getBlock().canSustainPlant(state, world, pos, EnumFacing.UP, this) && world.isAirBlock(pos.up())) {
            world.setBlockState(pos.up(), cropType.crop.getDefaultState(), 11);
            --stack.stackSize;
            return EnumActionResult.SUCCESS;
        } else {
            return EnumActionResult.FAIL;
        }
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        for (CropType cropType : CropType.values()) {
            if (cropType.isSeed) {
                return cropType.crop.getDefaultState();
            }
        }
        return null;
    }

    public enum CropType { //TODO Make proper values
        CUCUMBER(0, "cucumber", 3, 0.4F, ModBlocks.cucumber),
        TOMATO(1, "tomato", 4, 0.5F, ModBlocks.tomato);

        private static final Map<Integer, CropType> META_LOOKUP = Maps.newHashMap();
        private final int meta;
        private final String name;
        private final int hungerAmount;
        private final float saturationModifier;
        private final boolean isSeed;
        private final Block crop;

        CropType(int meta, String name, int hungerAmount, float saturationModifier) {
            this.meta = meta;
            this.name = name;
            this.hungerAmount = hungerAmount;
            this.saturationModifier = saturationModifier;
            this.isSeed = false;
            this.crop = null;
        }

        CropType(int meta, String name, int hungerAmount, float saturationModifier, Block crop) {
            this.meta = meta;
            this.name = name;
            this.hungerAmount = hungerAmount;
            this.saturationModifier = saturationModifier;
            this.isSeed = true;
            this.crop = crop;
        }

        public int getMetadata() {
            return this.meta;
        }

        public String getCropName() {
            return this.name;
        }

        public int getHungerAmount() {
            return this.hungerAmount;
        }

        public float getSaturation() {
            return this.saturationModifier;
        }

        public static CropType byMetadata(int meta) {
            CropType cropType = META_LOOKUP.get(meta);
            return cropType == null ? CUCUMBER : cropType;
        }

        public static CropType byItemStack(ItemStack stack) {
            return stack.getItem() instanceof ItemCropFood ? byMetadata(stack.getMetadata()) : CUCUMBER;
        }

        static {
            for (CropType cropValues : values()) {
                META_LOOKUP.put(cropValues.getMetadata(), cropValues);
            }
        }
    }
}