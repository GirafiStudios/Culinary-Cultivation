package com.Girafi.culinarycultivation.item;

import com.Girafi.culinarycultivation.block.BlockCrop;
import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class SourceSeeds extends Item implements net.minecraftforge.common.IPlantable {
    private Block crops;
    private BlockCrop blockCrop;

    public SourceSeeds(Block crops) {
        this.crops = crops;
        setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (side != EnumFacing.UP) {
            return false;
        }
        else if (!playerIn.canPlayerEdit(pos.offset(side), side, stack)) {
            return false;
        }
        else if (worldIn.getBlockState(pos).getBlock().canSustainPlant(worldIn, pos, EnumFacing.UP, this) && worldIn.isAirBlock(pos.up())) {
            worldIn.setBlockState(pos.up(), crops.getDefaultState());
            --stack.stackSize;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public net.minecraftforge.common.EnumPlantType getPlantType(net.minecraft.world.IBlockAccess world, BlockPos pos) {
        return crops == net.minecraft.init.Blocks.nether_wart ? net.minecraftforge.common.EnumPlantType.Nether : net.minecraftforge.common.EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(net.minecraft.world.IBlockAccess world, BlockPos pos) {
        return blockCrop.getDefaultState();
    }
}
