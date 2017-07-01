package com.girafi.culinarycultivation.event;

import com.girafi.culinarycultivation.api.item.ICraftingTool;
import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.item.ItemModFishFood;
import com.girafi.culinarycultivation.item.ItemModMeatFood.MeatType;
import com.girafi.culinarycultivation.util.InventoryHandlerHelper;
import com.girafi.culinarycultivation.util.NBTHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;

import java.util.Random;

public class ItemCraftingEvent {

    @EventBusSubscriber
    public static class CraftedEvent {
        @SubscribeEvent
        public static void craftingHandler(ItemCraftedEvent craftedEvent) {
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
        public static void drumstickCraftedEvent(ItemCraftedEvent event) {
            ItemStack stack = event.crafting;
            if (stack.getItem() == ModItems.MEAT && stack.getItemDamage() == MeatType.CHICKEN_NUGGET.getMetadata()) {
                Random rand = event.player.getRNG();
                InventoryHandlerHelper.giveItem(event.player, EnumHand.MAIN_HAND, new ItemStack(ModItems.MEAT, rand.nextInt(5) == 1 ? 2 : 1, MeatType.DRUMSTICK.getMetadata()));
            }
        }
    }

    @EventBusSubscriber
    public static class AchievementTriggerEvent {
        @SubscribeEvent
        public static void smeltedEvent(ItemSmeltedEvent event) {
            if (event.smelting.getItem() instanceof ItemModFishFood) {
                //event.player.addStat(AdvancementList.COOK_FISH); //TODO
            }
        }
    }
}