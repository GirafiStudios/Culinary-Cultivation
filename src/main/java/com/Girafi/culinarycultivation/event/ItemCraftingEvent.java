package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemModFishFood;
import com.Girafi.culinarycultivation.item.ItemModMeatFood.MeatType;
import com.Girafi.culinarycultivation.item.equipment.tool.ItemCakeKnife;
import com.Girafi.culinarycultivation.item.equipment.tool.ItemKnife;
import com.Girafi.culinarycultivation.item.equipment.tool.ItemMeatCleaver;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

public class ItemCraftingEvent {

    public static class CraftedEvent {
        @SubscribeEvent
        public void CraftingHandler(ItemCraftedEvent craftedEvent) {
            final IInventory knifeCrafting = null;
            ItemStack stackKnife = craftedEvent.crafting;
            if (stackKnife != null && stackKnife.getItem() == ModItems.kitchenKnife) {
            } else {
                for (int i = 0; i < craftedEvent.craftMatrix.getSizeInventory(); i++) {
                    ItemStack stack = craftedEvent.craftMatrix.getStackInSlot(i);
                    if (stack != null && stack.getItem() instanceof ItemKnife) {
                        ItemStack itemKnife = new ItemStack(ModItems.kitchenKnife, 2, stack.getItemDamage() + 1);

                        if (itemKnife.getItemDamage() >= itemKnife.getMaxDamage()) {
                            itemKnife.stackSize--;
                        }
                        craftedEvent.craftMatrix.setInventorySlotContents(i, itemKnife);
                    }
                }
            }
            final IInventory cakeKnifeCrafting = null;
            ItemStack stackCakeKnife = craftedEvent.crafting;
            if (stackCakeKnife != null && stackCakeKnife.getItem() == ModItems.cakeKnife) {
            } else {
                for (int i = 0; i < craftedEvent.craftMatrix.getSizeInventory(); i++) {
                    ItemStack stack = craftedEvent.craftMatrix.getStackInSlot(i);
                    if (stack != null && stack.getItem() instanceof ItemCakeKnife) {
                        ItemStack cakeKnife = new ItemStack(ModItems.cakeKnife, 2, stack.getItemDamage() + 1);

                        if (cakeKnife.getItemDamage() >= cakeKnife.getMaxDamage()) {
                            cakeKnife.stackSize--;
                        }
                        craftedEvent.craftMatrix.setInventorySlotContents(i, cakeKnife);
                    }
                }
            }
            final IInventory meatCleaverCrafting = null;
            ItemStack stackMeatCleaver = craftedEvent.crafting;
            if (stackMeatCleaver != null && stackCakeKnife.getItem() == ModItems.meatCleaver) {
            } else {
                for (int i = 0; i < craftedEvent.craftMatrix.getSizeInventory(); i++) {
                    ItemStack stack = craftedEvent.craftMatrix.getStackInSlot(i);
                    if (stack != null && stack.getItem() instanceof ItemMeatCleaver) {
                        ItemStack meatCleaver = new ItemStack(ModItems.meatCleaver, 2, stack.getItemDamage() + 1);

                        if (meatCleaver.getItemDamage() >= meatCleaver.getMaxDamage()) {
                            meatCleaver.stackSize--;
                        }
                        craftedEvent.craftMatrix.setInventorySlotContents(i, meatCleaver);
                    }
                }
            }
        }
        @SubscribeEvent
        public void DrumstickCraftedEvent(ItemCraftedEvent craftedEvent) {
            ItemStack stack = craftedEvent.crafting;
            if (stack != null && stack.getItem() == ModItems.meat && stack.getItemDamage() == MeatType.CHICKENNUGGET.getMetaData()) {
                if (!craftedEvent.player.inventory.addItemStackToInventory(new ItemStack(ModItems.meat, 2, MeatType.DRUMSTICK.getMetaData()))) {
                    craftedEvent.player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.meat, 2, MeatType.DRUMSTICK.getMetaData()), false);
                }
            }
        }
    }

    public static class AchievementTriggerEvent {
        @SubscribeEvent
        public void ItemSmeltedEvent(ItemSmeltedEvent smeltedEvent) {
            if (smeltedEvent.smelting.getItem() != null && smeltedEvent.smelting.getItem() instanceof ItemModFishFood) {
                smeltedEvent.player.triggerAchievement(AchievementList.cookFish);
            }
        }
    }
}