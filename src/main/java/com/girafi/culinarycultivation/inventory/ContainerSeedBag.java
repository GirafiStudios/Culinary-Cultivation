package com.girafi.culinarycultivation.inventory;

import com.girafi.culinarycultivation.item.equipment.tool.ItemSeedBag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ContainerSeedBag extends Container {
    private SeedBagInventory seedBagInventory;

    public ContainerSeedBag(InventoryPlayer playerInventory, SeedBagInventory seedBagInv, EntityPlayer player) {
        this.seedBagInventory = seedBagInv;
        seedBagInventory.openInventory(player);

        for (int j = 0; j < seedBagInventory.getSizeInventory(); ++j) {
            this.addSlotToContainer(new Slot(seedBagInventory, j, 80 + j * 22, 20));
        }

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
    public boolean canInteractWith(EntityPlayer player) {
        ItemStack heldStack = player.getHeldItemMainhand();
        return seedBagInventory != null && heldStack != null && heldStack.getItem() instanceof ItemSeedBag;
    }

    @Nullable
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