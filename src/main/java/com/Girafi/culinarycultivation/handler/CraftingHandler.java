package com.Girafi.culinarycultivation.handler;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemKnife;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class CraftingHandler {

    @SubscribeEvent
    public void onCrafting(PlayerEvent.ItemCraftedEvent event) {

        final IInventory crafting = null;
        for(int i = 0; i < event.craftMatrix.getSizeInventory(); i++){
            ItemStack stack = event.craftMatrix.getStackInSlot(i);
            if(stack != null && stack.getItem() instanceof ItemKnife){
                ItemStack itemKnife = new ItemStack(ModItems.knife, 2, stack.getItemDamage() + 1);

                if(itemKnife.getItemDamage() >= itemKnife.getMaxDamage()){
                    itemKnife.stackSize--;
                }
                event.craftMatrix.setInventorySlotContents(i, itemKnife);
            }
        }
    }
}