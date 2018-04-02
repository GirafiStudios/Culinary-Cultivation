package com.girafi.culinarycultivation.modsupport.forestry;

import forestry.api.farming.ICrop;
import forestry.farming.logic.crops.CropDestroy;
import forestry.farming.logic.farmables.FarmableBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class FarmableDoubleAgingCrop extends FarmableBase {

    public FarmableDoubleAgingCrop(ItemStack germling, IBlockState plantedState, IBlockState matureState) {
        super(germling, plantedState, matureState, false);
    }

    @Override
    public ICrop getCropAt(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        IBlockState stateUp = world.getBlockState(pos.up());
        if (stateUp != matureState) {
            return null;
        }
        return new CropDestroy(world, stateUp, pos.up(), null);
    }
}