package com.girafi.culinarycultivation.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

public class InventoryHandlerHelper {

    @Nonnull
    public static ItemStack insertStackIntoInventory(IInventory inventory, @Nonnull ItemStack stack, EnumFacing facing, boolean ignoreStackLimit) {
        System.out.println("Picked up stack: " + stack);
        if (stack.isEmpty() || inventory == null) {
            System.out.println("Stack or inventory is empty");
            return ItemStack.EMPTY;
        }

        int stackSize = stack.getCount();
        if (inventory instanceof ISidedInventory) {
            ISidedInventory sidedInv = (ISidedInventory) inventory;
            int slots[] = sidedInv.getSlotsForFace(facing);
            if (slots == null) {
                return stack;
            }

            for (int i = 0; i < slots.length && !stack.isEmpty(); i++) {
                ItemStack existingStack = inventory.getStackInSlot(slots[i]);
                if (existingStack.isEmpty()) {
                    continue;
                }

                int stackLimit = ignoreStackLimit ? inventory.getInventoryStackLimit() : Math.min(existingStack.getMaxStackSize(), inventory.getInventoryStackLimit());
                ItemStack toInsert = copyStackWithAmount(stack, stackLimit - existingStack.getCount());
                if (sidedInv.canInsertItem(slots[i], toInsert, facing)) {
                    if (OreDictionary.itemMatches(existingStack, stack, true) && ItemStack.areItemStackTagsEqual(stack, existingStack))
                        stack = addToOccupiedSlot(sidedInv, slots[i], stack, existingStack, ignoreStackLimit);
                }
            }

            for (int i = 0; i < slots.length && !stack.isEmpty(); i++)
                if (inventory.getStackInSlot(slots[i]).isEmpty() && sidedInv.canInsertItem(slots[i], copyStackWithAmount(stack, inventory.getInventoryStackLimit()), facing)) {
                    stack = addToEmptyInventorySlot(sidedInv, slots[i], stack);
                }
        } else {
            System.out.println("Dududu?");
            int invSize = inventory.getSizeInventory();
            for (int i = 0; i < invSize && !stack.isEmpty(); i++) {
                ItemStack existingStack = inventory.getStackInSlot(i);
                if (OreDictionary.itemMatches(existingStack, stack, true) && ItemStack.areItemStackTagsEqual(stack, existingStack)) {
                    System.out.println("Hello Stack before: " + stack);
                    stack = addToOccupiedSlot(inventory, i, stack, existingStack, ignoreStackLimit);
                    System.out.println("Hello Stack after: " + stack);
                }
            }
            for (int i = 0; i < invSize && !stack.isEmpty(); i++)
                if (inventory.getStackInSlot(i).isEmpty()) {
                    System.out.println("I am a stack and I like potatoes");
                    stack = addToEmptyInventorySlot(inventory, i, stack);
                }
        }

        if (stack.isEmpty() || stack.getCount() != stackSize) {
            System.out.println("This stack is about to be a dirty potato!");
            inventory.markDirty();
        }
        return stack;
    }

    @Nonnull
    private static ItemStack addToEmptyInventorySlot(IInventory inventory, int slot, @Nonnull ItemStack stack) {
        if (!inventory.isItemValidForSlot(slot, stack)) {
            return stack;
        }

        int stackLimit = inventory.getInventoryStackLimit();
        inventory.setInventorySlotContents(slot, copyStackWithAmount(stack, Math.min(stack.getCount(), stackLimit)));
        return stackLimit >= stack.getCount() ? ItemStack.EMPTY : stack.splitStack(stack.getCount() - stackLimit);
    }

    @Nonnull
    private static ItemStack addToOccupiedSlot(IInventory inventory, int slot, @Nonnull ItemStack stack, @Nonnull ItemStack existingStack, boolean ignoreStackSize) {
        int stackLimit = ignoreStackSize ? inventory.getInventoryStackLimit() : Math.min(inventory.getInventoryStackLimit(), stack.getMaxStackSize());
        if (stack.getCount() + existingStack.getCount() > stackLimit) {
            int stackDiff = stackLimit - existingStack.getCount();
            existingStack.setCount(stackLimit);
            stack = copyStackWithAmount(stack, stack.getCount() - stackDiff);
            inventory.setInventorySlotContents(slot, existingStack);
            return stack;
        }

        existingStack.grow(Math.min(stack.getCount(), stackLimit));
        inventory.setInventorySlotContents(slot, existingStack);
        return ItemStack.EMPTY;
    }

    @Nonnull
    private static ItemStack copyStackWithAmount(@Nonnull ItemStack stack, int amount) {
        System.out.println("copyStackWithAmount stack " + stack);
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack copyStack = stack.copy();
        copyStack.setCount(amount);
        return copyStack;
    }
}