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
        public void LivingDropsEvent(LivingDropsEvent dropsEvent) {
            rand = Math.random();

            if (dropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) dropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (dropsEvent.entityLiving instanceof EntityCow &! dropsEvent.entityLiving.isChild()) {
                        if (dropsEvent.entityLiving.isBurning()) {
                            dropsEvent.entityLiving.dropItem(ModItems.ribsCooked, 1);
                        } else {
                            dropsEvent.entityLiving.dropItem(ModItems.ribsBeefRaw, 1);
                        }
                        if (rand > 0.7D) {
                            if (dropsEvent.entityLiving.isBurning()) {
                                dropsEvent.entityLiving.dropItem(ModItems.ribsCooked, 1);
                            } else {
                                dropsEvent.entityLiving.dropItem(ModItems.ribsBeefRaw, 1);
                            }
                        }
                        if (rand > 0.98D) {
                            if (dropsEvent.entityLiving.isBurning()) {
                                dropsEvent.entityLiving.dropItem(ModItems.ribsCooked, 1);
                            } else {
                                dropsEvent.entityLiving.dropItem(ModItems.ribsBeefRaw, 1);
                            }
                        }
                    }
                }
            }
        }
    }

    public static class CowRoastDropsEvent {

        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent dropsEvent) {
            rand = Math.random();

            if (dropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) dropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (dropsEvent.entityLiving instanceof EntityCow &! dropsEvent.entityLiving.isChild()) {
                        if (rand < 0.1D) {
                            if (dropsEvent.entityLiving.isBurning()) {
                                dropsEvent.entityLiving.dropItem(ModItems.roastCooked, 1);
                            } else {
                                dropsEvent.entityLiving.dropItem(ModItems.roastRaw, 1);
                            }
                        }
                    }
                }
            }
        }
    }

    public static class BabyCowVealDropsEvent {

        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent dropsEvent) {
            rand = Math.random();

            if (dropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) dropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (dropsEvent.entityLiving instanceof EntityCow && dropsEvent.entityLiving.isChild()) {
                        if (dropsEvent.entityLiving.isBurning()) {
                            dropsEvent.entityLiving.dropItem(ModItems.cookedVeal, 1);
                        } else {
                            dropsEvent.entityLiving.dropItem(ModItems.veal, 1);
                        }
                        if (rand > 0.85D) {
                            if (dropsEvent.entityLiving.isBurning()) {
                                dropsEvent.entityLiving.dropItem(ModItems.cookedVeal, 1);
                            } else {
                                dropsEvent.entityLiving.dropItem(ModItems.veal, 1);
                            }
                        }
                        if (rand > 0.98D) {
                            if (dropsEvent.entityLiving.isBurning()) {
                                dropsEvent.entityLiving.dropItem(ModItems.cookedVeal, 1);
                            } else {
                                dropsEvent.entityLiving.dropItem(ModItems.veal, 1);
                            }
                        }
                    }
                }
            }
        }
    }
}