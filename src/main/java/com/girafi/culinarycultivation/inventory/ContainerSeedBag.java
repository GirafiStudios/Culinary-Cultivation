package com.girafi.culinarycultivation.inventory;

import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.item.equipment.tool.ItemSeedBag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ContainerSeedBag extends ContainerBase {
    private SeedBagInventory seedBagInventory;

    public ContainerSeedBag(InventoryPlayer playerInventory, SeedBagInventory seedBagInv, EntityPlayer player) {
        this.seedBagInventory = seedBagInv;
        this.seedBagInventory.openInventory(player);
        this.setMaxStackSize(seedBagInventory.getInventoryStackLimit());

        this.addSlotToContainer(new Slot(this.seedBagInventory, 0, 80, 20) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return seedBagInventory.isItemValidForSlot(0, stack);
            }
        });

        for (int l = 0; l < 3; ++l) {
            for (int k = 0; k < 9; ++k) {
                this.addSlotToContainer(new Slot(playerInventory, k + l * 9 + 9, 8 + k * 18, l * 18 + 51));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlotToContainer(new Slot(playerInventory, i1, 8 + i1 * 18, 109));
        }
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer player) {
        ItemStack heldStack = player.getHeldItemMainhand();
        return this.seedBagInventory != null && heldStack != null && heldStack.getItem() instanceof ItemSeedBag;
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickType, EntityPlayer player) {
        ItemStack slotStack = slotId < 0 || slotId > this.inventorySlots.size() ? null : this.inventorySlots.get(slotId).getStack();
        if (slotStack != null && slotStack.getItem() == ModItems.SEED_BAG) {
            return null;
        }
        return super.slotClick(slotId, dragType, clickType, player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack stack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            stack = slotStack.copy();

            if (index < this.seedBagInventory.getSizeInventory()) {
                if (!this.mergeItemStack(slotStack, this.seedBagInventory.getSizeInventory(), this.inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(slotStack, 0, this.seedBagInventory.getSizeInventory(), false)) {
                return null;
            }

            if (slotStack.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }
        return stack;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        this.seedBagInventory.closeInventory(player);
    }
}