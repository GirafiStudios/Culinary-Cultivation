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
        public void LivingDropsEvent(LivingDropsEvent dropsEvent) {
            rand = Math.random();

            if (dropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) dropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (dropsEvent.entityLiving instanceof EntityPig &! dropsEvent.entityLiving.isChild()) {
                        if (rand > 0.05D) {
                            if (dropsEvent.entityLiving.isBurning()) {
                                dropsEvent.entityLiving.dropItem(ModItems.ribsCooked, 1);
                            } else {
                                dropsEvent.entityLiving.dropItem(ModItems.ribsPorkRaw, 1);
                            }
                        }
                        if (rand > 0.85D) {
                            if (dropsEvent.entityLiving.isBurning()) {
                                dropsEvent.entityLiving.dropItem(ModItems.ribsCooked, 1);
                            } else {
                                dropsEvent.entityLiving.dropItem(ModItems.ribsPorkRaw, 1);
                            }
                        }
                    }
                }
            }
        }
    }
    public static class PigHamDropsEvent {
        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent dropsEvent) {
            rand = Math.random();

            if (dropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) dropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (dropsEvent.entityLiving instanceof EntityPig &! dropsEvent.entityLiving.isChild()) {
                        if (rand < 0.1D) {
                            if (dropsEvent.entityLiving.isBurning()) {
                                dropsEvent.entityLiving.dropItem(ModItems.hamCooked, 1);
                            } else {
                                dropsEvent.entityLiving.dropItem(ModItems.hamRaw, 1);
                            }
                        }
                    }
                }
            }
        }
    }
}
