package com.girafi.culinarycultivation.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.oredict.OreDictionary;

public class InventoryHandlerHelper {
   public static ItemStack insertStackIntoInventory(IInventory inventory, ItemStack stack, EnumFacing facing, boolean ignoreStackLimit) {
        if (stack == null || inventory == null) {
            return null;
        }

        int stackSize = stack.stackSize;
        if (inventory instanceof ISidedInventory) {
            ISidedInventory sidedInv = (ISidedInventory) inventory;
            int slots[] = sidedInv.getSlotsForFace(facing);
            if (slots == null) {
                return stack;
            }

            for (int i = 0; i < slots.length && stack != null; i++) {
                ItemStack existingStack = inventory.getStackInSlot(slots[i]);
                if (existingStack == null) {
                    continue;
                }

                int stackLimit = ignoreStackLimit ? inventory.getInventoryStackLimit() : Math.min(existingStack.getMaxStackSize(), inventory.getInventoryStackLimit());
                ItemStack toInsert = copyStackWithAmount(stack, stackLimit - existingStack.stackSize);
                if (sidedInv.canInsertItem(slots[i], toInsert, facing)) {
                    if (OreDictionary.itemMatches(existingStack, stack, true) && ItemStack.areItemStackTagsEqual(stack, existingStack))
                        stack = addToOccupiedSlot(sidedInv, slots[i], stack, existingStack, ignoreStackLimit);
                }
            }

            for (int i = 0; i < slots.length && stack != null; i++)
                if (inventory.getStackInSlot(slots[i]) == null && sidedInv.canInsertItem(slots[i], copyStackWithAmount(stack, inventory.getInventoryStackLimit()), facing)) {
                    stack = addToEmptyInventorySlot(sidedInv, slots[i], stack);
                }
        } else {
            int invSize = inventory.getSizeInventory();
            for (int i = 0; i < invSize && stack != null; i++) {
                ItemStack existingStack = inventory.getStackInSlot(i);
                if (OreDictionary.itemMatches(existingStack, stack, true) && ItemStack.areItemStackTagsEqual(stack, existingStack)) {
                    stack = addToOccupiedSlot(inventory, i, stack, existingStack, ignoreStackLimit);
                }
            }
            for (int i = 0; i < invSize && stack != null; i++)
                if (inventory.getStackInSlot(i) == null) {
                    stack = addToEmptyInventorySlot(inventory, i, stack);
                }
        }

        if (stack == null || stack.stackSize != stackSize) {
            inventory.markDirty();
        }

        return stack;
    }

    private static ItemStack addToEmptyInventorySlot(IInventory inventory, int slot, ItemStack stack) {
        if (!inventory.isItemValidForSlot(slot, stack)) {
            return stack;
        }

        int stackLimit = inventory.getInventoryStackLimit();
        inventory.setInventorySlotContents(slot, copyStackWithAmount(stack, Math.min(stack.stackSize, stackLimit)));
        return stackLimit >= stack.stackSize ? null : stack.splitStack(stack.stackSize - stackLimit);
    }

    private static ItemStack addToOccupiedSlot(IInventory inventory, int slot, ItemStack stack, ItemStack existingStack, boolean ignoreStackSize) {
        int stackLimit = ignoreStackSize ? inventory.getInventoryStackLimit() : Math.min(inventory.getInventoryStackLimit(), stack.getMaxStackSize());
        if (stack.stackSize + existingStack.stackSize > stackLimit) {
            int stackDiff = stackLimit - existingStack.stackSize;
            existingStack.stackSize = stackLimit;
            stack = copyStackWithAmount(stack, stack.stackSize - stackDiff);
            inventory.setInventorySlotContents(slot, existingStack);
            return stack;
        }

        existingStack.stackSize += Math.min(stack.stackSize, stackLimit);
        inventory.setInventorySlotContents(slot, existingStack);
        return null;
    }

    private static ItemStack copyStackWithAmount(ItemStack stack, int amount) {
        if (stack == null) {
            return null;
        }

        ItemStack copyStack = stack.copy();
        copyStack.stackSize = amount;
        return copyStack;
    }
}