package com.girafi.culinarycultivation.event;

import com.girafi.culinarycultivation.api.annotations.RegisterEvent;
import com.girafi.culinarycultivation.api.item.ICraftingTool;
import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.item.ItemModFishFood;
import com.girafi.culinarycultivation.item.ItemModMeatFood.MeatType;
import com.girafi.culinarycultivation.util.NBTHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

public class ItemCraftingEvent {

    @RegisterEvent
    public static class CraftedEvent {
        @SubscribeEvent
        public void craftingHandler(ItemCraftedEvent craftedEvent) {
            for (int i = 0; i < craftedEvent.craftMatrix.getSizeInventory(); i++) {
                ItemStack stack = craftedEvent.craftMatrix.getStackInSlot(i);
                if (!stack.isEmpty() && stack.getItem() instanceof ICraftingTool && !(craftedEvent.crafting.getItem() instanceof ICraftingTool)) {
                    ItemStack craftingTool = new ItemStack(stack.getItem(), 2, stack.getItemDamage() + 1);

                    if (NBTHelper.hasTag(stack) && stack.getTagCompound() != null) {
                        craftingTool.setTagCompound(stack.getTagCompound());
                    }

                    if (craftingTool.getItemDamage() >= craftingTool.getMaxDamage()) {
                        craftingTool.shrink(1);
                    }
                    craftedEvent.craftMatrix.setInventorySlotContents(i, craftingTool);
                }
            }
        }


        @SubscribeEvent
        public void drumstickCraftedEvent(ItemCraftedEvent craftedEvent) {
            ItemStack stack = craftedEvent.crafting;
            if (!stack.isEmpty() && stack.getItem() == ModItems.MEAT && stack.getItemDamage() == MeatType.CHICKEN_NUGGET.getMetadata()) {
                if (!craftedEvent.player.inventory.addItemStackToInventory(new ItemStack(ModItems.MEAT, 1, MeatType.DRUMSTICK.getMetadata()))) {
                    craftedEvent.player.dropItem(new ItemStack(ModItems.MEAT, 1, MeatType.DRUMSTICK.getMetadata()), false);
                }
            }
        }
    }

    @RegisterEvent
    public static class AchievementTriggerEvent {
        @SubscribeEvent
        public void smeltedEvent(ItemSmeltedEvent smeltedEvent) {
            if (smeltedEvent.smelting.getItem() instanceof ItemModFishFood) {
                smeltedEvent.player.addStat(AchievementList.COOK_FISH);
            }
        }
    }
}