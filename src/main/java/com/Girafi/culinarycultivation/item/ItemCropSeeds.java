package com.girafi.culinarycultivation.item;

import com.girafi.culinarycultivation.init.ModBlocks;
import com.girafi.culinarycultivation.util.reference.Paths;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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

public class ItemCropSeeds extends Item implements IPlantable {

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (SeedType seedType : SeedType.values()) {
            subItems.add(new ItemStack(this, 1, seedType.getMetadata()));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "item." + Paths.MOD_ASSETS + SeedType.byItemStack(stack).getSeedName();
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        SeedType seedType = SeedType.byItemStack(stack);
        IBlockState state = world.getBlockState(pos);
        if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, stack) && state.getBlock().canSustainPlant(state, world, pos, EnumFacing.UP, this) && world.isAirBlock(pos.up())) {
            world.setBlockState(pos.up(), seedType.crop.getDefaultState(), 11);
            --stack.stackSize;
            return EnumActionResult.SUCCESS;
        } else {
            return EnumActionResult.FAIL;
        }
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return net.minecraftforge.common.EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        for (SeedType seedType : SeedType.values()) {
            return seedType.crop.getDefaultState();
        }
        return null;
    }

    public enum SeedType {
        BLACKPEPPERDRUPE(0, "blackPepperDrupe", ModBlocks.BLACK_PEPPER);

        private static final Map<Integer, SeedType> META_LOOKUP = Maps.newHashMap();
        private final int meta;
        private final String name;
        private final Block crop;

        SeedType(int meta, String name, Block crop) {
            this.meta = meta;
            this.name = name;
            this.crop = crop;
        }

        public int getMetadata() {
            return this.meta;
        }

        public String getSeedName() {
            return this.name;
        }

        public static SeedType byMetadata(int meta) {
            SeedType seedType = META_LOOKUP.get(meta);
            return seedType == null ? BLACKPEPPERDRUPE : seedType;
        }

        public static SeedType byItemStack(ItemStack stack) {
            return stack.getItem() instanceof ItemCropSeeds ? byMetadata(stack.getMetadata()) : BLACKPEPPERDRUPE;
        }

        static {
            for (SeedType seedValues : values()) {
                META_LOOKUP.put(seedValues.getMetadata(), seedValues);
            }
        }
    }
}