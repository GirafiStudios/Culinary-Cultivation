package com.girafi.culinarycultivation.event;

import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.item.ItemModFishFood;
import com.girafi.culinarycultivation.item.ItemModMeatFood.MeatType;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

public class ItemCraftingEvent {

    public static class CraftedEvent {
        @SubscribeEvent
        public void DrumstickCraftedEvent(ItemCraftedEvent craftedEvent) {
            ItemStack stack = craftedEvent.crafting;
            if (stack != null && stack.getItem() == ModItems.MEAT && stack.getItemDamage() == MeatType.CHICKEN_NUGGET.getMetadata()) {
                if (!craftedEvent.player.inventory.addItemStackToInventory(new ItemStack(ModItems.MEAT, 1, MeatType.DRUMSTICK.getMetadata()))) {
                    craftedEvent.player.dropItem(new ItemStack(ModItems.MEAT, 1, MeatType.DRUMSTICK.getMetadata()), false);
                }
            }
        }
    }

    public static class AchievementTriggerEvent {
        @SubscribeEvent
        public void ItemSmeltedEvent(ItemSmeltedEvent smeltedEvent) {
            if (smeltedEvent.smelting.getItem() instanceof ItemModFishFood) {
                smeltedEvent.player.addStat(AchievementList.COOK_FISH);
            }
        }
    }
}