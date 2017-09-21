package com.girafi.culinarycultivation.event;

import com.girafi.culinarycultivation.api.annotations.EventRegister;
import com.girafi.culinarycultivation.api.item.ICraftingTool;
import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.item.ItemGeneral;
import com.girafi.culinarycultivation.item.ItemModMeatFood.MeatType;
import com.girafi.culinarycultivation.util.InventoryHandlerHelper;
import com.girafi.culinarycultivation.util.NBTHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

import java.util.Random;

public class ItemCraftingEvent {

    @EventRegister
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
        public void drumstickCraftedEvent(ItemCraftedEvent event) {
            ItemStack stack = event.crafting;
            if (stack.getItem() == ModItems.MEAT && stack.getItemDamage() == MeatType.CHICKEN_NUGGET.getMetadata()) {
                Random rand = event.player.getRNG();
                InventoryHandlerHelper.giveItem(event.player, EnumHand.MAIN_HAND, new ItemStack(ModItems.MEAT, rand.nextInt(5) == 1 ? 2 : 1, MeatType.DRUMSTICK.getMetadata()));
            }
        }
    }

    @EventRegister
    public static class FuelHandler {
        @SubscribeEvent
        public void getBurnTime(FurnaceFuelBurnTimeEvent event) {
            ItemStack fuel = event.getItemStack();
            if (fuel.equals(new ItemStack(ModItems.GENERAL, 1, ItemGeneral.Type.TOOL_HANDLE.getMetadata()))) {
                event.setBurnTime(200);
            } else if (fuel.equals(new ItemStack(ModItems.GENERAL, 1, ItemGeneral.Type.CHAFF_PILE.getMetadata()))) {
                event.setBurnTime(50);
            }
        }
    }
}