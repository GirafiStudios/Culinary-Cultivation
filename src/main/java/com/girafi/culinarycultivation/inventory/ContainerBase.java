package com.girafi.culinarycultivation.inventory;

import com.google.common.collect.Sets;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Set;

public class ContainerBase extends Container {
    private int maxStackSize;
    private int dragMode = -1;
    private int dragEvent;
    private final Set<Slot> dragSlots = Sets.newHashSet();

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer player) {
        return true;
    }

    @Override
    @Nonnull
    public ItemStack slotClick(int slotId, int dragType, ClickType clickType, EntityPlayer player) {
        return this.getMaxStackSize() > 64 ? specialClick(slotId, dragType, clickType, player) : super.slotClick(slotId, dragType, clickType, player);
    }

    protected int getMaxStackSize() {
        return maxStackSize;
    }

    protected void setMaxStackSize(int size) {
        this.maxStackSize = size;
    }

    private int getMaxStackSize(@Nonnull ItemStack stack, boolean reverse) {
        if (reverse) return getMaxStackSize();
        return stack.getMaxStackSize();
    }

    @Override
    protected boolean mergeItemStack(@Nonnull ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        boolean movingIn = startIndex == 0;
        boolean flag = false;
        int indexStart = startIndex;

        if (reverseDirection) {
            indexStart = endIndex - 1;
        }

        if (stack.isStackable()) {
            while (!stack.isEmpty() && (!reverseDirection && indexStart < endIndex || reverseDirection && indexStart >= startIndex)) {
                Slot slot = this.inventorySlots.get(indexStart);
                ItemStack slotStack = slot.getStack();

                if (areItemStacksEqual(stack, slotStack)) {
                    int combinedSize = slotStack.getCount() + stack.getCount();

                    if (combinedSize <= getMaxStackSize(stack, movingIn)) {
                        stack.setCount(0);
                        slotStack.setCount(combinedSize);
                        slot.onSlotChanged();
                        flag = true;
                    } else if (slotStack.getCount() < getMaxStackSize(stack, movingIn)) {
                        stack.shrink(getMaxStackSize(stack, movingIn) - slotStack.getCount());
                        slotStack.setCount(getMaxStackSize(stack, movingIn));
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

            while (!reverseDirection && indexStart < endIndex || reverseDirection && indexStart >= startIndex) {
                Slot slot = this.inventorySlots.get(indexStart);

                if (slot.isItemValid(stack)) {
                    ItemStack clone = stack.copy();
                    if (stack.getCount() >= stack.getMaxStackSize()) {
                        clone.setCount(stack.getMaxStackSize());
                    }

                    slot.putStack(clone);
                    slot.onSlotChanged();
                    stack.setCount(stack.getCount() - clone.getCount());
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

    //Coped from vanilla
    @Nonnull
    private ItemStack specialClick(int slotId, int dragType, ClickType clickType, EntityPlayer player) {
        ItemStack stack = ItemStack.EMPTY;
        InventoryPlayer playerInv = player.inventory;

        if (clickType == ClickType.QUICK_CRAFT) {
            int dragInt = dragEvent;
            dragEvent = getDragEvent(dragType);

            if ((dragInt != 1 || dragEvent != 2) && dragInt != dragEvent) {
                this.resetDrag();
            } else if (playerInv.getItemStack().isEmpty()) {
                this.resetDrag();
            } else if (dragEvent == 0) {
                dragMode = extractDragMode(dragType);

                if (isValidDragMode(dragMode, player)) {
                    dragEvent = 1;
                    dragSlots.clear();
                } else {
                    this.resetDrag();
                }
            } else if (dragEvent == 1) {
                Slot slot = inventorySlots.get(slotId);
                ItemStack invStack = playerInv.getItemStack();

                if (slot != null && canAddItemToSlot(slot, playerInv.getItemStack(), true) && slot.isItemValid(invStack) && invStack.getCount() > dragSlots.size() && canDragIntoSlot(slot)) {
                    dragSlots.add(slot);
                }
            } else if (dragEvent == 2) {
                if (!dragSlots.isEmpty()) {
                    ItemStack invClone = playerInv.getItemStack().copy();
                    int invStackSize = playerInv.getItemStack().getCount();

                    for (Slot slotDrag : dragSlots) {
                        if (slotDrag != null && canAddItemToSlot(slotDrag, playerInv.getItemStack(), true) && slotDrag.isItemValid(playerInv.getItemStack()) && playerInv.getItemStack().getCount() >= dragSlots.size() && canDragIntoSlot(slotDrag)) {
                            ItemStack clone = invClone.copy();
                            int size = slotDrag.getHasStack() ? slotDrag.getStack().getCount() : 0;
                            computeStackSize(dragSlots, dragMode, clone, size);
                            int maxSize = Math.min(clone.getMaxStackSize(), slotDrag.getItemStackLimit(clone));

                            if (clone.getCount() > getMaxStackSize(clone, true)) {
                                clone.setCount(maxSize);
                            }

                            invStackSize -= clone.getCount() - size;
                            slotDrag.putStack(clone);
                        }
                    }
                    invClone.setCount(invStackSize);
                    playerInv.setItemStack(invClone);
                }
                resetDrag();
            } else {
                resetDrag();
            }
        } else if (dragEvent != 0) {
            resetDrag();
        } else if ((clickType == ClickType.PICKUP || clickType == ClickType.QUICK_MOVE) && (dragType == 0 || dragType == 1)) {
            if (slotId == -999) {
                if (!playerInv.getItemStack().isEmpty()) {
                    if (dragType == 0) {
                        player.dropItem(playerInv.getItemStack(), true);
                        playerInv.setItemStack(ItemStack.EMPTY);
                    }

                    if (dragType == 1) {
                        player.dropItem(playerInv.getItemStack().splitStack(1), true);
                    }
                }
            } else if (clickType == ClickType.QUICK_MOVE) {
                if (slotId < 0) {
                    return ItemStack.EMPTY;
                }
                Slot invSlot = inventorySlots.get(slotId);

                if (invSlot != null && invSlot.canTakeStack(player)) {
                    ItemStack slotStack = transferStackInSlot(player, slotId);

                    if (!slotStack.isEmpty()) {
                        Item item = slotStack.getItem();
                        stack = slotStack.copy();

                        if (invSlot.getStack().getItem() == item) {
                            retrySlotClick(slotId, dragType, true, player);
                        }
                    }
                }
            } else {
                if (slotId < 0) {
                    return ItemStack.EMPTY;
                }
                Slot invSlot = inventorySlots.get(slotId);

                if (invSlot != null) {
                    ItemStack slotStack = invSlot.getStack();
                    ItemStack heldStack = playerInv.getItemStack();

                    if (!slotStack.isEmpty()) {
                        stack = slotStack.copy();
                    }

                    if (slotStack.isEmpty()) {
                        if (!heldStack.isEmpty() && invSlot.isItemValid(heldStack)) {
                            int heldStackSize = dragType == 0 ? heldStack.getCount() : 1;

                            if (heldStackSize > invSlot.getItemStackLimit(heldStack)) {
                                heldStackSize = invSlot.getItemStackLimit(heldStack);
                            }

                            invSlot.putStack(heldStack.splitStack(heldStackSize));
                        }
                    } else if (invSlot.canTakeStack(player)) {
                        if (heldStack.isEmpty()) {
                            if (slotStack.isEmpty()) {
                                invSlot.putStack(ItemStack.EMPTY);
                                playerInv.setItemStack(ItemStack.EMPTY);
                            } else {
                                int slotStackSize = dragType == 0 ? slotStack.getCount() : (slotStack.getCount() + 1) / 2;

                                playerInv.setItemStack(invSlot.decrStackSize(slotStackSize));

                                if (slotStack.isEmpty()) {
                                    invSlot.putStack(ItemStack.EMPTY);
                                }
                                invSlot.onTake(player, playerInv.getItemStack());
                            }
                        } else if (invSlot.isItemValid(heldStack)) {
                            if (slotStack.getItem() == heldStack.getItem() && slotStack.getMetadata() == heldStack.getMetadata() && ItemStack.areItemStackTagsEqual(slotStack, heldStack)) {
                                int heldStackSize = dragType == 0 ? heldStack.getCount() : 1;

                                if (heldStackSize > invSlot.getItemStackLimit(heldStack) - slotStack.getCount()) {
                                    heldStackSize = invSlot.getItemStackLimit(heldStack) - slotStack.getCount();
                                }

                                if (heldStackSize > getMaxStackSize(heldStack, true) - slotStack.getCount()) {
                                    heldStackSize = getMaxStackSize(heldStack, true) - slotStack.getCount();
                                }
                                heldStack.shrink(heldStackSize);
                                heldStack.grow(heldStackSize);
                            } else if (heldStack.getCount() <= invSlot.getItemStackLimit(heldStack)) {
                                invSlot.putStack(heldStack);
                                playerInv.setItemStack(slotStack);
                            }
                        } else if (slotStack.getItem() == heldStack.getItem() && heldStack.getMaxStackSize() > 1 && (!slotStack.getHasSubtypes() || slotStack.getMetadata() == heldStack.getMetadata()) && ItemStack.areItemStackTagsEqual(slotStack, heldStack)) {
                            int slotStackSize = slotStack.getCount();

                            if (slotStackSize + heldStack.getCount() <= heldStack.getMaxStackSize()) {
                                heldStack.grow(slotStackSize);
                                slotStack = invSlot.decrStackSize(slotStackSize);

                                if (slotStack.isEmpty()) {
                                    invSlot.putStack(ItemStack.EMPTY);
                                }
                                invSlot.onTake(player, playerInv.getItemStack());
                            }
                        }
                    }
                    invSlot.onSlotChanged();
                }
            }
        } else if (clickType == ClickType.SWAP && dragType >= 0 && dragType < 9) {
            Slot invSlot = inventorySlots.get(slotId);
            ItemStack slotStack = playerInv.getStackInSlot(dragType);
            ItemStack invSlotStack = invSlot.getStack();

            if (!slotStack.isEmpty() || !invSlotStack.isEmpty()) {
                if (slotStack.isEmpty()) {
                    if (invSlot.canTakeStack(player)) {
                        playerInv.setInventorySlotContents(dragType, invSlotStack);
                        //invSlot.onSwapCraft(invSlotStack.getCount()); //TODO Figure out if Forge wants to fix this, or add AT & what does this even do?
                        invSlot.putStack(ItemStack.EMPTY);
                        invSlot.onTake(player, invSlotStack);
                    }
                } else if (invSlotStack.isEmpty()) {
                    if (invSlot.isItemValid(slotStack)) {
                        int invLimit = invSlot.getItemStackLimit(slotStack);

                        if (slotStack.getCount() > invLimit) {
                            invSlot.putStack(slotStack.splitStack(invLimit));
                        } else {
                            invSlot.putStack(slotStack);
                            playerInv.setInventorySlotContents(dragType, ItemStack.EMPTY);
                        }
                    }
                } else if (invSlot.canTakeStack(player) && invSlot.isItemValid(slotStack)) {
                    int invLimit = invSlot.getItemStackLimit(slotStack);

                    if (slotStack.getCount() > invLimit) {
                        invSlot.putStack(slotStack.splitStack(invLimit));
                        invSlot.onTake(player, invSlotStack);

                        if (!playerInv.addItemStackToInventory(invSlotStack)) {
                            player.dropItem(invSlotStack, true);
                        }
                    } else {
                        invSlot.putStack(slotStack);
                        playerInv.setInventorySlotContents(dragType, invSlotStack);
                        invSlot.onTake(player, invSlotStack);
                    }
                }
            }
        } else if (clickType == ClickType.CLONE && player.capabilities.isCreativeMode && playerInv.getItemStack().isEmpty() && slotId >= 0) {
            Slot invSlot = inventorySlots.get(slotId);

            if (invSlot != null && invSlot.getHasStack()) {
                ItemStack invStack = invSlot.getStack().copy();
                invStack.setCount(invStack.getMaxStackSize());
                playerInv.setItemStack(invStack);
            }
        } else if (clickType == ClickType.THROW && playerInv.getItemStack().isEmpty() && slotId >= 0) {
            Slot invSlot = inventorySlots.get(slotId);

            if (invSlot != null && invSlot.getHasStack() && invSlot.canTakeStack(player)) {
                ItemStack invStackSize = invSlot.decrStackSize(dragType == 0 ? 1 : invSlot.getStack().getCount());
                invSlot.onTake(player, invStackSize);
                player.dropItem(invStackSize, true);
            }
        } else if (clickType == ClickType.PICKUP_ALL && slotId >= 0) {
            Slot invSlot = inventorySlots.get(slotId);
            ItemStack invStack = playerInv.getItemStack();

            if (!invStack.isEmpty() && (invSlot == null || !invSlot.getHasStack() || !invSlot.canTakeStack(player))) {
                int invSlotSize = dragType == 0 ? 0 : inventorySlots.size() - 1;
                int type = dragType == 0 ? 1 : -1;

                for (int i = 0; i < 2; ++i) {
                    for (int slotSize = invSlotSize; slotSize >= 0 && slotSize < inventorySlots.size() && invStack.getCount() < invStack.getMaxStackSize(); slotSize += type) {
                        Slot slot = inventorySlots.get(slotSize);
                        if (slot.getHasStack() && canAddItemToSlot(slot, invStack, true) && slot.canTakeStack(player) && canMergeSlot(invStack, slot)) {
                            ItemStack slotStack = slot.getStack();

                            if (i != 0 || slotStack.getCount() != slotStack.getMaxStackSize()) {
                                int stackSize = Math.min(invStack.getMaxStackSize() - invStack.getCount(), slot.getStack().getCount());
                                ItemStack decrStackSize = slot.decrStackSize(stackSize);
                                invStack.grow(stackSize);

                                if (decrStackSize.isEmpty()) {
                                    slot.putStack(ItemStack.EMPTY);
                                }
                                slot.onTake(player, decrStackSize);
                            }
                        }
                    }
                }
            }
            detectAndSendChanges();
        }
        return stack;
    }

    @Override
    protected void resetDrag() {
        this.dragEvent = 0;
        this.dragSlots.clear();
    }
}