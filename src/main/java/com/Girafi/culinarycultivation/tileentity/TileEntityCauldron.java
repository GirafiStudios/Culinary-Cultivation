package com.Girafi.culinarycultivation.tileentity;

import com.Girafi.culinarycultivation.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityCauldron extends SourceTileEntity {
    private int timer = 2400;

    @Override
    public void updateEntity() {
        Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
        if (block == ModBlocks.cauldron && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 14) {
        if (timer > 0) timer--;
        if (timer == 0 && !worldObj.isRemote) {
                worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.cauldron, 15, 2);
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