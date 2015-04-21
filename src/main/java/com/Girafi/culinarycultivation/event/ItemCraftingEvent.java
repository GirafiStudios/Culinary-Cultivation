package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.utility.Helper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.*;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;

public class ItemCraftingEvent {

    public static class DrumstickCraftingEvent {
        @SubscribeEvent
        public void ItemCraftedEvent(ItemCraftedEvent craftedEvent) {
                ItemStack stack = craftedEvent.crafting;
                    if (stack != null && stack.getItem() == ModItems.chickenNuggetRaw) {
                        craftedEvent.player.inventory.addItemStackToInventory(new ItemStack(ModItems.drumstickRaw, 2));
            }
        }
    }

    public static class AchievementTriggerEvent {
        @SubscribeEvent
        public void ClownFishTriggersFishAchievement(ItemSmeltedEvent smeltedEvent) {
            if (smeltedEvent.smelting.getItem() != null && smeltedEvent.smelting.getItem() == ModItems.cookedClownfish) {
                smeltedEvent.player.triggerAchievement(AchievementList.cookFish);
            }
        }
    }
}