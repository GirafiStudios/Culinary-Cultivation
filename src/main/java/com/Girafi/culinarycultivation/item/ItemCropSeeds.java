package com.Girafi.culinarycultivation.item;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
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

public class ItemCropSeeds extends Item implements IPlantable {

    public ItemCropSeeds (){
        this.setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
        SeedType[] aSeedType = SeedType.values();
        int i = aSeedType.length;

        for (int j = 0; j < i; ++j) {
            SeedType seedType = aSeedType[j];
            subItems.add(new ItemStack(this, 1, seedType.getMetadata()));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        SeedType seedType = SeedType.byItemStack(stack);
        return "item." + Paths.ModAssets + seedType.getUnlocalizedName();
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        SeedType seedType = SeedType.byItemStack(stack);
        if (side != EnumFacing.UP) {
            return false;
        } else if (!player.canPlayerEdit(pos.offset(side), side, stack)) {
            return false;
        } else if (world.getBlockState(pos).getBlock().canSustainPlant(world, pos, EnumFacing.UP, this) && world.isAirBlock(pos.up())) {
            world.setBlockState(pos.up(), seedType.crop.getDefaultState());
            --stack.stackSize;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return net.minecraftforge.common.EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        SeedType[] aSeedType = SeedType.values();
        int i = aSeedType.length;

        for (int j = 0; j < i; ++j) {
            SeedType seedType = aSeedType[j];
            return seedType.crop.getDefaultState();
        }
        return null;
    }

    public static enum SeedType { //TODO Make proper values
        BLACKPEPPERDRUPE(0, "blackPepperDrupe", ModBlocks.blackPepper);

        private static final Map META_LOOKUP = Maps.newHashMap();
        private final int meta;
        private final String unlocalizedName;
        private final Block crop;

        private SeedType(int meta, String unlocalizedName, Block crop) {
            this.meta = meta;
            this.unlocalizedName = unlocalizedName;
            this.crop = crop;
        }

        public int getMetadata() {
            return this.meta;
        }

        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }

        public static SeedType byMetadata(int meta) {
            SeedType seedType = (SeedType) META_LOOKUP.get(Integer.valueOf(meta));
            return seedType == null ? BLACKPEPPERDRUPE : seedType;
        }

        public static SeedType byItemStack(ItemStack stack) {
            return stack.getItem() instanceof ItemCropSeeds ? byMetadata(stack.getMetadata()) : BLACKPEPPERDRUPE;
        }

        static {
            SeedType[] values = values();
            int var1 = values.length;

            for (int value = 0; value < var1; ++value) {
                SeedType seedValues = values[value];
                META_LOOKUP.put(Integer.valueOf(seedValues.getMetadata()), seedValues);
            }
        }
    }
}