package com.Girafi.culinarycultivation.handler;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemCakeKnife;
import com.Girafi.culinarycultivation.item.ItemKnife;
import com.Girafi.culinarycultivation.item.ItemMeatCleaver;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class CraftingHandler {

    @SubscribeEvent
    public void onCrafting(PlayerEvent.ItemCraftedEvent itemCraftedEvent) {

        final IInventory knifeCrafting = null;
        ItemStack stackKnife = itemCraftedEvent.crafting;
        if (stackKnife != null && stackKnife.getItem() == ModItems.knife) {
        } else {
            for (int i = 0; i < itemCraftedEvent.craftMatrix.getSizeInventory(); i++) {
                ItemStack stack = itemCraftedEvent.craftMatrix.getStackInSlot(i);
                if (stack != null && stack.getItem() instanceof ItemKnife) {
                    ItemStack itemKnife = new ItemStack(ModItems.knife, 2, stack.getItemDamage() + 1);

                    if (itemKnife.getItemDamage() >= itemKnife.getMaxDamage()) {
                        itemKnife.stackSize--;
                    }
                    itemCraftedEvent.craftMatrix.setInventorySlotContents(i, itemKnife);
                }
            }
        }

        final IInventory cakeKnifeCrafting = null;
        ItemStack stackCakeKnife = itemCraftedEvent.crafting;
        if (stackCakeKnife != null && stackCakeKnife.getItem() == ModItems.cakeKnife) {
        } else {
            for (int i = 0; i < itemCraftedEvent.craftMatrix.getSizeInventory(); i++) {
                ItemStack stack = itemCraftedEvent.craftMatrix.getStackInSlot(i);
                if (stack != null && stack.getItem() instanceof ItemCakeKnife) {
                    ItemStack cakeKnife = new ItemStack(ModItems.cakeKnife, 2, stack.getItemDamage() + 1);

                    if (cakeKnife.getItemDamage() >= cakeKnife.getMaxDamage()) {
                        cakeKnife.stackSize--;
                    }
                    itemCraftedEvent.craftMatrix.setInventorySlotContents(i, cakeKnife);
                }
            }
        }

        final IInventory meatCleaverCrafting = null;
        ItemStack stackMeatCleaver = itemCraftedEvent.crafting;
        if (stackMeatCleaver != null && stackCakeKnife.getItem() == ModItems.meatCleaver) {
        } else {
            for (int i = 0; i < itemCraftedEvent.craftMatrix.getSizeInventory(); i++) {
                ItemStack stack = itemCraftedEvent.craftMatrix.getStackInSlot(i);
                if (stack != null && stack.getItem() instanceof ItemMeatCleaver) {
                    ItemStack meatCleaver = new ItemStack(ModItems.meatCleaver, 2, stack.getItemDamage() + 1);

                    if (meatCleaver.getItemDamage() >= meatCleaver.getMaxDamage()) {
                        meatCleaver.stackSize--;
                    }
                    itemCraftedEvent.craftMatrix.setInventorySlotContents(i, meatCleaver);
                }
            }
        }
    }
}