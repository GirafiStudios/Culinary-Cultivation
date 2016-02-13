package com.Girafi.culinarycultivation.block.tileentity;

import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.utility.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityCauldron extends TileEntity implements ITickable {
    private int timer = 2400;

    @Override
    public void update() {
        if (worldObj.getBlockState(pos).getBlock() == ModBlocks.cauldron && getBlockMetadata() == 12) {
            LogHelper.debug(timer);
            if (timer > 0) timer--;
            if (timer == 0 && !worldObj.isRemote) {
                worldObj.setBlockState(pos, worldObj.getBlockState(pos).getBlock().getStateFromMeta(13), 2);
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