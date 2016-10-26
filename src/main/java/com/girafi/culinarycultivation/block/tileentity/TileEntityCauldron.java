package com.girafi.culinarycultivation.block.tileentity;

import com.girafi.culinarycultivation.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import javax.annotation.Nonnull;

public class TileEntityCauldron extends TileEntity implements ITickable {
    private int timer = 2400;

    @Override
    public void update() {
        IBlockState state = worldObj.getBlockState(pos);
        if (state == ModBlocks.CAULDRON.getStateFromMeta(12)) {
            if (timer > 0) timer--;
            if (timer == 0 && !worldObj.isRemote) {
                worldObj.setBlockState(pos, state.getBlock().getStateFromMeta(13), 2);
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        timer = tag.getInteger("timer");
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("timer", timer);

        return compound;
    }
}