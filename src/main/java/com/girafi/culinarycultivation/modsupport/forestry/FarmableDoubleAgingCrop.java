package com.girafi.culinarycultivation.modsupport.forestry;

import forestry.api.farming.ICrop;
import forestry.farming.logic.CropDestroy;
import forestry.farming.logic.FarmableAgingCrop;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FarmableDoubleAgingCrop extends FarmableAgingCrop {
    public FarmableDoubleAgingCrop(ItemStack germling, Block cropBlock, IProperty<Integer> ageProperty, int minHarvestAge) {
        super(germling, cropBlock, ageProperty, minHarvestAge, null);
    }

    @Override
    public ICrop getCropAt(World world, BlockPos pos, IBlockState blockState) {
        if (blockState.getBlock() != cropBlock) {
            return null;
        }

        if (blockState.getValue(ageProperty) < minHarvestAge) {
            return null;
        }

        if (replantAge != null) {
            IBlockState replantState = getReplantState(world, pos.up(), blockState);
            for (int i = 0; i <= 1; i++) {
                return new CropDestroy(world, blockState, pos.offset(EnumFacing.UP, i), replantState);
            }
        }
        return new CropDestroy(world, blockState, pos, null);
    }
}