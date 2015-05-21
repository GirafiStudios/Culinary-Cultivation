package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemModFishFood;
import com.Girafi.culinarycultivation.item.ItemModMeatFood.MeatType;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.*;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;

public class ItemCraftingEvent {

    public static class DrumstickCraftingEvent {
        @SubscribeEvent
        public void ItemCraftedEvent(ItemCraftedEvent craftedEvent) {
            ItemStack stack = craftedEvent.crafting;
            if (stack != null && stack.getItem() == ModItems.meat && stack.getItemDamage() == MeatType.CHICKENNUGGET.getMetaData()) {
                craftedEvent.player.inventory.addItemStackToInventory(new ItemStack(ModItems.meat, 2, MeatType.DRUMSTICK.getMetaData()));
                if (!craftedEvent.player.inventory.addItemStackToInventory(new ItemStack(ModItems.meat, 2, MeatType.DRUMSTICK.getMetaData()))) {
                    craftedEvent.player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.meat, 2, MeatType.DRUMSTICK.getMetaData()), false);
                }
            }
        }
    }

    public static class AchievementTriggerEvent {
        @SubscribeEvent
        public void ModFishTriggersFishAchievement(ItemSmeltedEvent smeltedEvent) {
            if (smeltedEvent.smelting.getItem() != null && smeltedEvent.smelting.getItem() instanceof ItemModFishFood) {
                smeltedEvent.player.triggerAchievement(AchievementList.cookFish);
            }
        }
    }
}