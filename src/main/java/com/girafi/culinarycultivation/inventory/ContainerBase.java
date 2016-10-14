package com.girafi.culinarycultivation.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ContainerBase extends Container {
    private int maxStackSize;

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer player) {
        return true;
    }

    protected int getMaxStackSize() {
        return maxStackSize;
    }

    protected void setMaxStackSize(int size) {
        this.maxStackSize = size;
    }

    private int getMaxStackSize(ItemStack stack, boolean reverse) {
        if (reverse) return getMaxStackSize();
        return stack.getMaxStackSize();
    }

    @Override
    protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        boolean movingIn = startIndex == 0;
        boolean flag = false;
        int indexStart = startIndex;

        if (reverseDirection) {
            indexStart = endIndex - 1;
        }

        if (stack.isStackable()) {
            while (stack.stackSize > 0 && (!reverseDirection && indexStart < endIndex || reverseDirection && indexStart >= startIndex)) {
                Slot slot = this.inventorySlots.get(indexStart);
                ItemStack slotStack = slot.getStack();

                if (slotStack != null && areItemStacksEqual(stack, slotStack)) {
                    int combinedSize = slotStack.stackSize + stack.stackSize;

                    if (combinedSize <= getMaxStackSize(stack, movingIn)) {
                        stack.stackSize = 0;
                        slotStack.stackSize = combinedSize;
                        slot.onSlotChanged();
                        flag = true;
                    } else if (slotStack.stackSize < getMaxStackSize(stack, movingIn)) {
                        stack.stackSize -= getMaxStackSize(stack, movingIn) - slotStack.stackSize;
                        slotStack.stackSize = getMaxStackSize(stack, movingIn);
                        slot.onSlotChanged();
                        flag = true;
                    }
                }

                if (reverseDirection) {
                    --indexStart;
                } else {
                    ++indexStart;
                }
            }
        }

        if (stack.stackSize > 0) {
            if (reverseDirection) {
                indexStart = endIndex - 1;
            } else {
                indexStart = startIndex;
            }

            while (!reverseDirection && indexStart < endIndex || reverseDirection && indexStart >= startIndex) {
                Slot slot = this.inventorySlots.get(indexStart);
                ItemStack slotStack = slot.getStack();

                if (slotStack == null && slot.isItemValid(stack)) {
                    ItemStack clone = stack.copy();
                    if (stack.stackSize >= stack.getMaxStackSize()) {
                        clone.stackSize = stack.getMaxStackSize();
                    }

                    slot.putStack(clone);
                    slot.onSlotChanged();
                    stack.stackSize = stack.stackSize - clone.stackSize;
                    flag = true;
                    break;
                }

                if (reverseDirection) {
                    --indexStart;
                } else {
                    ++indexStart;
                }
            }
        }
        return flag;
    }

    private static boolean areItemStacksEqual(ItemStack stackA, ItemStack stackB) {
        return stackB.getItem() == stackA.getItem() && (!stackA.getHasSubtypes() || stackA.getMetadata() == stackB.getMetadata()) && ItemStack.areItemStackTagsEqual(stackA, stackB);
    }
}