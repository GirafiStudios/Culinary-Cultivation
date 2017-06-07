package com.girafi.culinarycultivation.item;

import com.girafi.culinarycultivation.api.IOreDictEntry;
import com.girafi.culinarycultivation.api.OreDictHolder;
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
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Map;

public class ItemCropProduct extends ItemFood implements IPlantable, IOreDictEntry {
    private Type type;

    public ItemCropProduct(Type type) {
        super(0, 0.0F, false);
        this.type = type;
        this.setHasSubtypes(true);
    }

    @Override
    public int getHealAmount(@Nonnull ItemStack stack) {
        ProductType productType = ProductType.byItemStack(stack);

        switch (type) {
            case CROP:
                return productType.getCropHunger();
            case CROP_COOKED:
                return productType.getCookedHunger();
            default:
                return 0;
        }
    }

    @Override
    public float getSaturationModifier(@Nonnull ItemStack stack) {
        ProductType productType = ProductType.byItemStack(stack);

        switch (type) {
            case CROP:
                return productType.getCropSaturation();
            case CROP_COOKED:
                return productType.getCookedSaturation();
            default:
                return 0;
        }
    }


    @Override
    @Nonnull
    public EnumAction getItemUseAction(@Nonnull ItemStack stack) {
        switch (type) {
            case CROP:
            case CROP_COOKED:
                return super.getItemUseAction(stack);
            default:
                return EnumAction.NONE;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(@Nonnull Item item, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for (ProductType productType : ProductType.values()) {
            switch (type) {
                case SEED:
                    if (productType.hasSeed()) {
                        subItems.add(new ItemStack(this, 1, productType.getMetadata()));
                    }
                    break;
                case CROP:
                    if (productType.hasCrop()) {
                        subItems.add(new ItemStack(this, 1, productType.getMetadata()));
                    }
                    break;
                case CROP_COOKED:
                    if (productType.hasCookedCrop()) {
                        subItems.add(new ItemStack(this, 1, productType.getMetadata()));
                    }
                    break;
            }
        }
    }

    @Override
    @Nonnull
    public String getUnlocalizedName(@Nonnull ItemStack stack) {
        String name = "item." + Paths.MOD_ASSETS + ProductType.byItemStack(stack).getCropName();
        switch (type) {
            case SEED:
                return name + "_seed";
            case CROP_COOKED:
                return name + "_cooked";
            case CROP:
            default:
                return name;
        }
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        ProductType productType = ProductType.byItemStack(stack);
        IBlockState state = world.getBlockState(pos);
        if ((productType.canPlantCrop() || type == Type.SEED) && facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, stack) && state.getBlock().canSustainPlant(state, world, pos, EnumFacing.UP, this) && world.isAirBlock(pos.up())) {
            world.setBlockState(pos.up(), productType.crop.getDefaultState(), 11);
            stack.shrink(1);
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        for (ProductType productType : ProductType.values()) {
            if (productType.canPlantCrop() || type == Type.SEED) {
                return productType.crop.getDefaultState();
            }
        }
        return null;
    }

    @Override
    public NonNullList getOreDictEntries(OreDictHolder holder) {
        return null;
    }

    public enum ProductType {
        CUCUMBER(0, "cucumber", 3, 0.5F, ModBlocks.CUCUMBER),
        TOMATO(1, "tomato", 3, 0.7F, ModBlocks.TOMATO),
        BLACK_PEPPER_DRUPE(2, "black_pepper_drupe", ModBlocks.BLACK_PEPPER),
        CORN(3, "corn", 2, 0.4F, 4, 0.8F, ModBlocks.CORN);

        private static final Map<Integer, ProductType> META_LOOKUP = Maps.newHashMap();
        private final int meta;
        private final String name;
        private final int cropHunger;
        private final float cropSaturation;
        private final int cookedHunger;
        private final float cookedSaturation;
        private final boolean hasSeed;
        private final boolean isCropPlantable;
        private final Block crop;

        ProductType(int meta, String name, int cropHunger, float cropSat, int cookedHunger, float cookedSat, boolean hasSeed, boolean isCropPlantable, Block crop) {
            this.meta = meta;
            this.name = name;
            this.cropHunger = cropHunger;
            this.cropSaturation = cropSat;
            this.cookedHunger = cookedHunger;
            this.cookedSaturation = cookedSat;
            this.hasSeed = hasSeed;
            this.isCropPlantable = isCropPlantable;
            this.crop = crop;
        }

        ProductType(int meta, String name, int cropHunger, float cropSat, Block crop) {
            this(meta, name, cropHunger, cropSat, 0, 0.0F, true, false, crop);
        }

        ProductType(int meta, String name, int cropHunger, float cropSat, int cookedHunger, float cookedSat, Block crop) {
            this(meta, name, cropHunger, cropSat, cookedHunger, cookedSat, true, false, crop);
        }

        ProductType(int meta, String name, Block crop) {
            this(meta, name, 0, 0.0F, 0, 0.0F, true, false, crop);
        }

        public int getMetadata() {
            return this.meta;
        }

        public String getCropName() {
            return this.name;
        }

        public int getCropHunger() {
            return this.cropHunger;
        }

        public float getCropSaturation() {
            return this.cropSaturation;
        }

        public int getCookedHunger() {
            return this.cookedHunger;
        }

        public float getCookedSaturation() {
            return this.cookedSaturation;
        }

        public Block getCropBlock() {
            return this.crop;
        }

        public boolean hasSeed() {
            return this.hasSeed;
        }

        public boolean hasCrop() {
            return cropHunger > 0 || cropSaturation > 0.0F;
        }

        public boolean hasCookedCrop() {
            return hasCrop() && (cookedHunger > 0 || cookedSaturation > 0.0F);
        }

        public boolean canPlantCrop() {
            return this.isCropPlantable;
        }

        public static ProductType byMetadata(int meta) {
            ProductType productType = META_LOOKUP.get(meta);
            return productType == null ? CUCUMBER : productType;
        }

        public static ProductType byItemStack(@Nonnull ItemStack stack) {
            return stack.getItem() instanceof ItemCropProduct ? byMetadata(stack.getMetadata()) : CUCUMBER;
        }

        static {
            for (ProductType cropValues : values()) {
                META_LOOKUP.put(cropValues.getMetadata(), cropValues);
            }
        }
    }

    public enum Type {
        CROP,
        CROP_COOKED,
        SEED
    }
}