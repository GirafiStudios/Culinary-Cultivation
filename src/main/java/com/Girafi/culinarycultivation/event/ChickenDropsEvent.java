package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class ChickenDropsEvent {

    private static double rand;

    public static class ChickenWingDropsEvent {
        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent dropsEvent) {

            rand = Math.random();

            if (dropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) dropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (dropsEvent.entityLiving instanceof EntityChicken &! dropsEvent.entityLiving.isChild()) {
                        if (rand > 0.1D) {
                            if (dropsEvent.entityLiving.isBurning()) {
                                dropsEvent.entityLiving.dropItem(ModItems.chickenWingCooked, 1);
                            } else {
                                dropsEvent.entityLiving.dropItem(ModItems.chickenWingRaw, 1);
                            }
                        }
                        if (rand > 0.7D) {
                            if (dropsEvent.entityLiving.isBurning()) {
                                dropsEvent.entityLiving.dropItem(ModItems.chickenWingCooked, 1);
                            } else {
                                dropsEvent.entityLiving.dropItem(ModItems.chickenWingRaw, 1);
                            }
                        }
                    }
                }
            }
        }
    }
}

