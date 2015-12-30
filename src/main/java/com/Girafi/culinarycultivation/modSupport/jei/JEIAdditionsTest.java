package com.Girafi.culinarycultivation.modSupport.jei;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class JEIAdditionsTest {
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void tooltipEvent(ItemTooltipEvent event) {
        String itemNameTooltip = event.toolTip.toString().replace("[", "").replace("]", "");
        ItemStack stack = event.itemStack;
        if (event.toolTip.contains(itemNameTooltip)) {
            event.toolTip.clear();
            event.toolTip.add(itemNameTooltip + " " + EnumChatFormatting.GRAY + Item.getIdFromItem(stack.getItem()) + (stack.getItemDamage() != 0 ? ":" + stack.getItemDamage() : ""));
        }
    }
}