package com.girafi.culinarycultivation.item;

import com.girafi.culinarycultivation.init.ModBlocks;
import com.girafi.culinarycultivation.util.reference.Paths;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
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

public class ItemCropProduct extends ItemFood implements IPlantable {
    private final boolean isSeed;

    public ItemCropProduct(boolean isSeed) {
        super(0, 0.0F, false);
        this.isSeed = isSeed;
        this.setHasSubtypes(true);
    }

    @Override
    public int getHealAmount(ItemStack stack) {
        ProductType productType = ProductType.byItemStack(stack);
        if (productType.isHasCrop() &! this.isSeed) {
            return productType.getHungerAmount();
        }
        return super.getHealAmount(stack);
    }

    @Override
    public float getSaturationModifier(ItemStack stack) {
        ProductType productType = ProductType.byItemStack(stack);
        if (productType.isHasCrop() &! this.isSeed) {
            return productType.getSaturation();
        }
        return super.getSaturationModifier(stack);
    }


    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        if (!this.isSeed) {
            return super.getItemUseAction(stack);
        }
        return EnumAction.NONE;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems) {
        for (ProductType productType : ProductType.values()) {
            if (!productType.isHasCrop() && this.isSeed && productType.isHasSeed()) {
                subItems.add(new ItemStack(this, 1, productType.getMetadata()));
            }
            if (productType.isHasCrop() && (this.isSeed || productType.isHasSeed())) {
                subItems.add(new ItemStack(this, 1, productType.getMetadata()));
            }
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (this.isSeed) {
            return "item." + Paths.MOD_ASSETS + ProductType.byItemStack(stack).getCropName() + "_seed";
        }
        return "item." + Paths.MOD_ASSETS + ProductType.byItemStack(stack).getCropName();
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ProductType productType = ProductType.byItemStack(stack);
        IBlockState state = world.getBlockState(pos);
        if ((productType.canPlantCrop() || this.isSeed) && facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, stack) && state.getBlock().canSustainPlant(state, world, pos, EnumFacing.UP, this) && world.isAirBlock(pos.up())) {
            world.setBlockState(pos.up(), productType.crop.getDefaultState(), 11);
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
        for (ProductType productType : ProductType.values()) {
            if (productType.canPlantCrop() || this.isSeed) {
                return productType.crop.getDefaultState();
            }
        }
        return null;
    }

    public enum ProductType {
        CUCUMBER(0, "cucumber", 3, 0.5F, ModBlocks.CUCUMBER),
        TOMATO(1, "tomato", 3, 0.7F, ModBlocks.TOMATO),
        BLACK_PEPPER_DRUPE(2, "black_pepper_drupe", ModBlocks.BLACK_PEPPER);

        private static final Map<Integer, ProductType> META_LOOKUP = Maps.newHashMap();
        private final int meta;
        private final String name;
        private final int hungerAmount;
        private final float saturationModifier;
        private final boolean hasSeed;
        private final boolean hasCrop;
        private final boolean isCropPlantable;
        private final Block crop;

        ProductType(int meta, String name, int hungerAmount, float saturationModifier, boolean hasSeed, boolean hasCrop, boolean isCropPlantable, Block crop) {
            this.meta = meta;
            this.name = name;
            this.hungerAmount = hungerAmount;
            this.saturationModifier = saturationModifier;
            this.hasSeed = hasSeed;
            this.hasCrop = hasCrop;
            this.isCropPlantable = isCropPlantable;
            this.crop = crop;
        }

        ProductType(int meta, String name, int hungerAmount, float saturationModifier, Block crop) {
            this(meta, name, hungerAmount, saturationModifier, true, true, false, crop);
        }

        ProductType(int meta, String name, int hungerAmount, float saturationModifier, Block crop, boolean isCropPlantable) {
            this(meta, name, hungerAmount, saturationModifier, true, true, isCropPlantable, crop);
        }

        ProductType(int meta, String name, Block crop) {
            this(meta, name, 0, 0.0F, true, false, false, crop);
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

        public Block getCropBlock() {
            return this.crop;
        }

        public boolean isHasSeed() {
            return this.hasSeed;
        }

        public boolean isHasCrop() {
            return this.hasCrop;
        }

        public boolean canPlantCrop() {
            return this.isCropPlantable;
        }

        public static ProductType byMetadata(int meta) {
            ProductType productType = META_LOOKUP.get(meta);
            return productType == null ? CUCUMBER : productType;
        }

        public static ProductType byItemStack(ItemStack stack) {
            return stack.getItem() instanceof ItemCropProduct ? byMetadata(stack.getMetadata()) : CUCUMBER;
        }

        static {
            for (ProductType cropValues : values()) {
                META_LOOKUP.put(cropValues.getMetadata(), cropValues);
            }
        }
    }
}