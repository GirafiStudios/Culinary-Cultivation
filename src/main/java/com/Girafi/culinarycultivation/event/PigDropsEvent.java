package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class PigDropsEvent {
    private static double rand;

    public static class PigRibsPorkDropsEvent {

        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent DropsEvent) {
            rand = Math.random();

            if (DropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) DropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (DropsEvent.entityLiving instanceof EntityPig &! DropsEvent.entityLiving.isChild()) {
                        if (DropsEvent.entityLiving.isBurning()) {
                            DropsEvent.entityLiving.dropItem(ModItems.ribsCooked, 1);
                        } else {
                            DropsEvent.entityLiving.dropItem(ModItems.ribsPorkRaw, 1);
                        }
                        if (rand > 0.85D) {
                            if (DropsEvent.entityLiving.isBurning()) {
                                DropsEvent.entityLiving.dropItem(ModItems.ribsCooked, 1);
                            } else {
                                DropsEvent.entityLiving.dropItem(ModItems.ribsPorkRaw, 1);
                            }
                        }
                    }
                }
            }
        }
    }
}
