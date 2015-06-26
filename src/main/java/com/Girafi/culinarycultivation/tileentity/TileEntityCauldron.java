package com.Girafi.culinarycultivation.tileentity;

import com.Girafi.culinarycultivation.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityCauldron extends SourceTileEntity {
    private int timer = 2400;

    @Override
    public void updateContainingBlockInfo() {
        Block block = (Block) worldObj.getBlockState(pos);
        IBlockState state = worldObj.getBlockState(pos);
        if (block == ModBlocks.cauldron && getBlockMetadata() == 14) {
        if (timer > 0) timer--;
        if (timer == 0 && !worldObj.isRemote) {
                worldObj.setBlockState(pos, state); //ModBlocks.cauldron, 15, 2
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        timer = tag.getInteger("timer");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("timer", timer);
    }
}