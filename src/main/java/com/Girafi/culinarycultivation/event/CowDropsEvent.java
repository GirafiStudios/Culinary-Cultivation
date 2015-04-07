package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class CowDropsEvent {

    private static double rand;

    public static class CowRibsBeefDropsEvent {

        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent DropsEvent) {
            rand = Math.random();

            if (DropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) DropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (DropsEvent.entityLiving instanceof EntityCow &! DropsEvent.entityLiving.isChild()) {
                        if (DropsEvent.entityLiving.isBurning()) {
                            DropsEvent.entityLiving.dropItem(ModItems.ribsCooked, 1);
                        } else {
                            DropsEvent.entityLiving.dropItem(ModItems.ribsBeefRaw, 1);
                        }
                        if (rand > 0.7D) {
                            if (DropsEvent.entityLiving.isBurning()) {
                                DropsEvent.entityLiving.dropItem(ModItems.ribsCooked, 1);
                            } else {
                                DropsEvent.entityLiving.dropItem(ModItems.ribsBeefRaw, 1);
                            }
                        }
                        if (rand > 0.98D) {
                            if (DropsEvent.entityLiving.isBurning()) {
                                DropsEvent.entityLiving.dropItem(ModItems.ribsCooked, 1);
                            } else {
                                DropsEvent.entityLiving.dropItem(ModItems.ribsBeefRaw, 1);
                            }
                        }
                    }
                }
            }
        }
    }

    public static class CowRoastDropsEvent {

        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent DropsEvent) {
            rand = Math.random();

            if (DropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) DropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (DropsEvent.entityLiving instanceof EntityCow &! DropsEvent.entityLiving.isChild()) {
                        if (rand < 0.1D) {
                            if (DropsEvent.entityLiving.isBurning()) {
                                DropsEvent.entityLiving.dropItem(ModItems.ribsCooked, 1);
                            } else {
                                DropsEvent.entityLiving.dropItem(ModItems.ribsBeefRaw, 1);
                            }
                        }
                    }
                }
            }
        }
    }

    public static class BabyCowVealDropsEvent {

        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent DropsEvent) {
            rand = Math.random();

            if (DropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) DropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (DropsEvent.entityLiving instanceof EntityCow && DropsEvent.entityLiving.isChild()) {
                        if (DropsEvent.entityLiving.isBurning()) {
                            DropsEvent.entityLiving.dropItem(ModItems.cookedVeal, 1);
                        } else {
                            DropsEvent.entityLiving.dropItem(ModItems.veal, 1);
                        }
                        if (rand > 0.85D) {
                            if (DropsEvent.entityLiving.isBurning()) {
                                DropsEvent.entityLiving.dropItem(ModItems.cookedVeal, 1);
                            } else {
                                DropsEvent.entityLiving.dropItem(ModItems.veal, 1);
                            }
                        }
                        if (rand > 0.98D) {
                            if (DropsEvent.entityLiving.isBurning()) {
                                DropsEvent.entityLiving.dropItem(ModItems.cookedVeal, 1);
                            } else {
                                DropsEvent.entityLiving.dropItem(ModItems.veal, 1);
                            }
                        }
                    }
                }
            }
        }
    }
}