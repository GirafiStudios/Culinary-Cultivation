package com.girafi.culinarycultivation.inventory;

import com.girafi.culinarycultivation.util.NBTHelper;
import com.girafi.culinarycultivation.util.StringUtils;
import com.girafi.culinarycultivation.util.reference.Paths;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;

public class InventoryItem extends InventoryBasic {
    private final int stackLimit;
    private final ItemStack invItem;
    private final NonNullList<ItemStack> inventory;

    public InventoryItem(@Nonnull ItemStack inventoryItem, String name, int size, int stackLimit) {
        super(name.equals("") ? "" : StringUtils.translateToLocal(Paths.MOD_ASSETS + "container." + name), true, size);
        this.invItem = inventoryItem;
        this.inventory = NonNullList.withSize(size, ItemStack.EMPTY);
        this.stackLimit = stackLimit;

        NBTHelper.getTag(invItem);
        this.readFromNBT();
    }

    public InventoryItem(@Nonnull ItemStack inventoryItem, String name, int size) {
        this(inventoryItem, name, size, 64);
    }

    public void readFromNBT() {
        NBTTagList items = this.invItem.getTagCompound().getTagList("ItemInventory", Constants.NBT.TAG_COMPOUND);

        for (int index = 0; index < items.tagCount(); index++) {
            NBTTagCompound compound = items.getCompoundTagAt(index);
            int slot = compound.getInteger("Slot");

            if (slot >= 0 && slot < getSizeInventory()) {
                this.inventory.set(slot, NBTHelper.readItemStack(compound));
            }
        }
    }

    public void writeToNBT() {
        NBTTagList items = new NBTTagList();

        for (int index = 0; index < getSizeInventory(); index++) {
            ItemStack stack = this.getStackInSlot(index);

            if (!stack.isEmpty()) {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setInteger("Slot", index);
                NBTHelper.writeItemStack(stack, compound);
                items.appendTag(compound);
            }
        }
        this.invItem.getTagCompound().setTag("ItemInventory", items);
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int index) {
        return inventory.get(index);
    }

    @Override
    @Nonnull
    public ItemStack decrStackSize(int index, int count) {
        ItemStack stack = ItemStackHelper.getAndSplit(inventory, index, count);
        this.markDirty();
        return stack;
    }

    @Override
    @Nonnull
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = getStackInSlot(index);
        this.setInventorySlotContents(index, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, @Nonnull ItemStack stack) {
        this.inventory.set(index, stack);

        if (!stack.isEmpty() && stack.getCount() > getInventoryStackLimit()) {
            stack.setCount(getInventoryStackLimit());
        }
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return this.stackLimit;
    }

    @Override
    public void markDirty() {
        for (int index = 0; index < getSizeInventory(); index++) {
            if (getStackInSlot(index).isEmpty()) {
                this.inventory.set(index, ItemStack.EMPTY);
            }
        }
        this.writeToNBT();
    }
}