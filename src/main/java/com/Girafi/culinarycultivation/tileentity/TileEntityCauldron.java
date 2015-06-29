package com.Girafi.culinarycultivation.tileentity;

import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.utility.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;

public class TileEntityCauldron extends SourceTileEntity implements IUpdatePlayerListBox {
    private int timer = 2400;

    @Override
    public void update() {
        if (worldObj.getBlockState(pos).getBlock() == ModBlocks.cauldron && getBlockMetadata() == 14) {
            LogHelper.debug(timer);
            if (timer > 0) timer--;
            if (timer == 0 && !worldObj.isRemote) {
                worldObj.setBlockState(pos, worldObj.getBlockState(pos).getBlock().getStateFromMeta(15), 2);
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