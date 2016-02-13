package com.Girafi.culinarycultivation.block.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.IItemHandler;

public class TileInventoryBase extends TileEntity implements IItemHandler {

    ItemStack[] inventory = new ItemStack[getSlots()];

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        NBTTagList var2 = par1NBTTagCompound.getTagList("Items", 10);
        inventory = new ItemStack[getSlots()];
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            byte var5 = var4.getByte("Slot");
            if (var5 >= 0 && var5 < inventory.length)
                inventory[var5] = ItemStack.loadItemStackFromNBT(var4);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        NBTTagList var2 = new NBTTagList();
        for (int var3 = 0; var3 < inventory.length; ++var3) {
            if (inventory[var3] != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var3);
                inventory[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }
        par1NBTTagCompound.setTag("Items", var2);
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return inventory[slot] = stack;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (inventory[slot] != null) {
            ItemStack stackAt;

            if (inventory[slot].stackSize <= amount) {
                stackAt = inventory[slot];
                inventory[slot] = null;
                return stackAt;
            } else {
                stackAt = inventory[slot].splitStack(amount);

                if (inventory[slot].stackSize == 0)
                    inventory[slot] = null;

                return stackAt;
            }
        }

        return null;
    }
}