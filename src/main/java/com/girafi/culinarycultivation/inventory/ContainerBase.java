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
    public ItemStack slotClick(int slotId, int dragType, ClickType clickType, EntityPlayer player) {
        return this.getMaxStackSize() > 64 ? specialClick(slotId, dragType, clickType, player) : super.slotClick(slotId, dragType, clickType, player);
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

    //Coped from vanilla
    private ItemStack specialClick(int slotId, int dragType, ClickType clickType, EntityPlayer player) {
        ItemStack stack = null;
        InventoryPlayer playerInv = player.inventory;

        if (clickType == ClickType.QUICK_CRAFT) {
            int dragInt = dragEvent;
            dragEvent = getDragEvent(dragType);

            if ((dragInt != 1 || dragEvent != 2) && dragInt != dragEvent) {
                this.resetDrag();
            } else if (playerInv.getItemStack() == null) {
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

                if (slot != null && canAddItemToSlot(slot, playerInv.getItemStack(), true) && slot.isItemValid(playerInv.getItemStack()) && playerInv.getItemStack().stackSize > dragSlots.size() && canDragIntoSlot(slot)) {
                    dragSlots.add(slot);
                }
            } else if (dragEvent == 2) {
                if (!dragSlots.isEmpty()) {
                    ItemStack invClone = playerInv.getItemStack().copy();
                    int invStackSize = playerInv.getItemStack().stackSize;

                    for (Slot slotDrag : dragSlots) {
                        if (slotDrag != null && canAddItemToSlot(slotDrag, playerInv.getItemStack(), true) && slotDrag.isItemValid(playerInv.getItemStack()) && playerInv.getItemStack().stackSize >= dragSlots.size() && canDragIntoSlot(slotDrag)) {
                            ItemStack clone = invClone.copy();
                            int k = slotDrag.getHasStack() ? slotDrag.getStack().stackSize : 0;
                            computeStackSize(dragSlots, dragMode, clone, k);

                            if (clone.stackSize > getMaxStackSize(clone, true)) {
                                clone.stackSize = getMaxStackSize(clone, true);
                            }

                            if (clone.stackSize > slotDrag.getItemStackLimit(clone)) {
                                clone.stackSize = slotDrag.getItemStackLimit(clone);
                            }
                            invStackSize -= clone.stackSize - k;
                            slotDrag.putStack(clone);
                        }
                    }
                    invClone.stackSize = invStackSize;

                    if (invClone.stackSize <= 0) {
                        invClone = null;
                    }
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
                if (playerInv.getItemStack() != null) {
                    if (dragType == 0) {
                        player.dropItem(playerInv.getItemStack(), true);
                        playerInv.setItemStack(null);
                    }

                    if (dragType == 1) {
                        player.dropItem(playerInv.getItemStack().splitStack(1), true);

                        if (playerInv.getItemStack().stackSize == 0) {
                            playerInv.setItemStack(null);
                        }
                    }
                }
            } else if (clickType == ClickType.QUICK_MOVE) {
                if (slotId < 0) {
                    return null;
                }
                Slot invSlot = inventorySlots.get(slotId);

                if (invSlot != null && invSlot.canTakeStack(player)) {
                    ItemStack clone = invSlot.getStack();

                    if (clone != null && clone.stackSize <= 0) {
                        stack = clone.copy();
                        invSlot.putStack(null);
                    }

                    ItemStack slotStack = transferStackInSlot(player, slotId);

                    if (slotStack != null) {
                        Item item = slotStack.getItem();
                        stack = slotStack.copy();

                        if (invSlot.getStack() != null && invSlot.getStack().getItem() == item) {
                            retrySlotClick(slotId, dragType, true, player);
                        }
                    }
                }
            } else {
                if (slotId < 0) {
                    return null;
                }
                Slot invSlot = inventorySlots.get(slotId);

                if (invSlot != null) {
                    ItemStack slotStack = invSlot.getStack();
                    ItemStack heldStack = playerInv.getItemStack();

                    if (slotStack != null) {
                        stack = slotStack.copy();
                    }

                    if (slotStack == null) {
                        if (heldStack != null && invSlot.isItemValid(heldStack)) {
                            int heldStackSize = dragType == 0 ? heldStack.stackSize : 1;

                            if (heldStackSize > invSlot.getItemStackLimit(heldStack)) {
                                heldStackSize = invSlot.getItemStackLimit(heldStack);
                            }

                            invSlot.putStack(heldStack.splitStack(heldStackSize));

                            if (heldStack.stackSize == 0) {
                                playerInv.setItemStack(null);
                            }
                        }
                    } else if (invSlot.canTakeStack(player)) {
                        if (heldStack == null) {
                            if (slotStack.stackSize > 0) {
                                int slotStackSize = dragType == 0 ? slotStack.stackSize : (slotStack.stackSize + 1) / 2;
                                if (slotStackSize > getMaxStackSize(slotStack, true)) {
                                    slotStackSize = getMaxStackSize(slotStack, true);
                                }
                                playerInv.setItemStack(invSlot.decrStackSize(slotStackSize));

                                if (slotStack.stackSize <= 0) {
                                    invSlot.putStack(null);
                                }
                                invSlot.onPickupFromSlot(player, playerInv.getItemStack());
                            } else {
                                invSlot.putStack(null);
                                playerInv.setItemStack(null);
                            }
                        } else if (invSlot.isItemValid(heldStack)) {
                            if (slotStack.getItem() == heldStack.getItem() && slotStack.getMetadata() == heldStack.getMetadata() && ItemStack.areItemStackTagsEqual(slotStack, heldStack)) {
                                int heldStackSize = dragType == 0 ? heldStack.stackSize : 1;

                                if (heldStackSize > invSlot.getItemStackLimit(heldStack) - slotStack.stackSize) {
                                    heldStackSize = invSlot.getItemStackLimit(heldStack) - slotStack.stackSize;
                                }

                                if (heldStackSize > getMaxStackSize(heldStack, true) - slotStack.stackSize) {
                                    heldStackSize = getMaxStackSize(heldStack, true) - slotStack.stackSize;
                                }
                                heldStack.splitStack(heldStackSize);

                                if (heldStack.stackSize == 0) {
                                    playerInv.setItemStack(null);
                                }
                                slotStack.stackSize += heldStackSize;
                            } else if (heldStack.stackSize <= invSlot.getItemStackLimit(heldStack)) {
                                invSlot.putStack(heldStack);
                                playerInv.setItemStack(slotStack);
                            }
                        } else if (slotStack.getItem() == heldStack.getItem() && heldStack.getMaxStackSize() > 1 && (!slotStack.getHasSubtypes() || slotStack.getMetadata() == heldStack.getMetadata()) && ItemStack.areItemStackTagsEqual(slotStack, heldStack)) {
                            int slotStackSize = slotStack.stackSize;

                            if (slotStackSize > 0 && slotStackSize + heldStack.stackSize <= heldStack.getMaxStackSize()) {
                                heldStack.stackSize += slotStackSize;
                                slotStack = invSlot.decrStackSize(slotStackSize);

                                if (slotStack.stackSize == 0) {
                                    invSlot.putStack(null);
                                }
                                invSlot.onPickupFromSlot(player, playerInv.getItemStack());
                            }
                        }
                    }
                    invSlot.onSlotChanged();
                }
            }
        } else if (clickType == ClickType.SWAP && dragType >= 0 && dragType < 9) {
            Slot invSlot = inventorySlots.get(slotId);
            ItemStack slotStack = playerInv.getStackInSlot(dragType);

            if (slotStack != null && slotStack.stackSize <= 0) {
                slotStack = null;
                playerInv.setInventorySlotContents(dragType, null);
            }

            ItemStack invSlotStack = invSlot.getStack();
            if (slotStack != null || invSlotStack != null) {
                if (slotStack == null) {
                    if (invSlot.canTakeStack(player)) {
                        playerInv.setInventorySlotContents(dragType, invSlotStack);
                        invSlot.putStack(null);
                        invSlot.onPickupFromSlot(player, invSlotStack);
                    }
                } else if (invSlotStack == null) {
                    if (invSlot.isItemValid(slotStack)) {
                        int invLimit = invSlot.getItemStackLimit(slotStack);

                        if (slotStack.stackSize > invLimit) {
                            invSlot.putStack(slotStack.splitStack(invLimit));
                        } else {
                            invSlot.putStack(slotStack);
                            playerInv.setInventorySlotContents(dragType, null);
                        }
                    }
                } else if (invSlot.canTakeStack(player) && invSlot.isItemValid(slotStack)) {
                    int invLimit = invSlot.getItemStackLimit(slotStack);

                    if (slotStack.stackSize > invLimit) {
                        invSlot.putStack(slotStack.splitStack(invLimit));
                        invSlot.onPickupFromSlot(player, invSlotStack);

                        if (!playerInv.addItemStackToInventory(invSlotStack)) {
                            player.dropItem(invSlotStack, true);
                        }
                    } else {
                        invSlot.putStack(slotStack);
                        playerInv.setInventorySlotContents(dragType, invSlotStack);
                        invSlot.onPickupFromSlot(player, invSlotStack);
                    }
                }
            }
        } else if (clickType == ClickType.CLONE && player.capabilities.isCreativeMode && playerInv.getItemStack() == null && slotId >= 0) {
            Slot invSlot = inventorySlots.get(slotId);

            if (invSlot != null && invSlot.getHasStack()) {
                if (invSlot.getStack().stackSize > 0) {
                    ItemStack invStack = invSlot.getStack().copy();
                    invStack.stackSize = invStack.getMaxStackSize();
                    playerInv.setItemStack(invStack);
                } else {
                    invSlot.putStack(null);
                }
            }
        } else if (clickType == ClickType.THROW && playerInv.getItemStack() == null && slotId >= 0) {
            Slot invSlot = inventorySlots.get(slotId);

            if (invSlot != null && invSlot.getHasStack() && invSlot.canTakeStack(player)) {
                ItemStack invStackSize = invSlot.decrStackSize(dragType == 0 ? 1 : invSlot.getStack().stackSize);
                invSlot.onPickupFromSlot(player, invStackSize);
                player.dropItem(invStackSize, true);
            }
        } else if (clickType == ClickType.PICKUP_ALL && slotId >= 0) {
            Slot invSlot = inventorySlots.get(slotId);
            ItemStack invStack = playerInv.getItemStack();

            if (invStack != null && (invSlot == null || !invSlot.getHasStack() || !invSlot.canTakeStack(player))) {
                int invSlotSize = dragType == 0 ? 0 : inventorySlots.size() - 1;
                int type = dragType == 0 ? 1 : -1;

                for (int i = 0; i < 2; ++i) {
                    for (int slotSize = invSlotSize; slotSize >= 0 && slotSize < inventorySlots.size() && invStack.stackSize < invStack.getMaxStackSize(); slotSize += type) {
                        Slot slot = inventorySlots.get(slotSize);

                        if (slot.getHasStack() && canAddItemToSlot(slot, invStack, true) && slot.canTakeStack(player) && canMergeSlot(invStack, slot) && (i != 0 || slot.getStack().stackSize != slot.getStack().getMaxStackSize())) {
                            int stackSize = Math.min(invStack.getMaxStackSize() - invStack.stackSize, slot.getStack().stackSize);
                            ItemStack decrStackSize = slot.decrStackSize(stackSize);
                            invStack.stackSize += stackSize;

                            if (decrStackSize.stackSize <= 0) {
                                slot.putStack(null);
                            }
                            slot.onPickupFromSlot(player, decrStackSize);
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