package com.girafi.culinarycultivation.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ContainerBase extends Container {

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer player) {
        return true;
    }

    @Override
    protected boolean mergeItemStack(@Nonnull ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        boolean flag = false;
        int indexStart = startIndex;

        if (reverseDirection) {
            indexStart = endIndex - 1;
        }

        if (stack.isStackable()) {
            while (!stack.isEmpty()) {
                if (reverseDirection) {
                    if (indexStart < startIndex) {
                        break;
                    }
                } else if (indexStart >= endIndex) {
                    break;
                }

                Slot slot = this.inventorySlots.get(indexStart);
                ItemStack slotStack = slot.getStack();

                if (areItemStacksEqual(stack, slotStack)) {
                    int combinedSize = slotStack.getCount() + stack.getCount();
                    int maxSize = slot.getSlotStackLimit();

                    if (combinedSize <= maxSize) {
                        stack.setCount(0);
                        slotStack.setCount(combinedSize);
                        slot.onSlotChanged();
                        flag = true;
                    } else if (slotStack.getCount() < maxSize) {
                        stack.shrink(maxSize - slotStack.getCount());
                        slotStack.setCount(maxSize);
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

        if (!stack.isEmpty()) {
            if (reverseDirection) {
                indexStart = endIndex - 1;
            } else {
                indexStart = startIndex;
            }

            while (true) {
                if (reverseDirection) {
                    if (indexStart < startIndex) {
                        break;
                    }
                } else if (indexStart >= endIndex) {
                    break;
                }

                Slot slot = this.inventorySlots.get(indexStart);
                ItemStack slotStack = slot.getStack();

                if (slotStack.isEmpty() && slot.isItemValid(stack)) {
                    if (stack.getCount() >= slot.getSlotStackLimit()) {
                        slot.putStack(stack.splitStack(slot.getSlotStackLimit()));
                    } else {
                        slot.putStack(stack.splitStack(stack.getCount()));
                    }

                    slot.onSlotChanged();
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

    private static boolean areItemStacksEqual(@Nonnull ItemStack stackA, @Nonnull ItemStack stackB) {
        return stackB.getItem() == stackA.getItem() && (!stackA.getHasSubtypes() || stackA.getMetadata() == stackB.getMetadata()) && ItemStack.areItemStackTagsEqual(stackA, stackB);
    }
}