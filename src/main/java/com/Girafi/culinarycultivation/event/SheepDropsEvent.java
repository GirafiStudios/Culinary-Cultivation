package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class SheepDropsEvent {

    private static double rand;

    public static class SheepMuttonDropsEvent {

        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent DropsEvent) {
            rand = Math.random();

            if (DropsEvent.entityLiving instanceof EntitySheep &! DropsEvent.entityLiving.isChild()) {
                if (DropsEvent.entityLiving.isBurning()) {
                    DropsEvent.entityLiving.dropItem(ModItems.cookedMutton, 1);
                }else {
                    DropsEvent.entityLiving.dropItem(ModItems.mutton, 1);

                    if (rand > 0.6D) {
                        if (DropsEvent.entityLiving.isBurning()) {
                            DropsEvent.entityLiving.dropItem(ModItems.cookedMutton, 1);
                        }else {
                            DropsEvent.entityLiving.dropItem(ModItems.mutton, 1);
                        }
                    }
                }
            }
        }
    }

    public static class SheepLegOfSheepDropsEvent {

        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent DropsEvent) {
            rand = Math.random();

            if (DropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) DropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (DropsEvent.entityLiving instanceof EntitySheep & !DropsEvent.entityLiving.isChild()) {
                        if (rand > 0.1D) {
                            if (DropsEvent.entityLiving.isBurning()) {
                                DropsEvent.entityLiving.dropItem(ModItems.legSheepCooked, 1);
                            } else {
                                DropsEvent.entityLiving.dropItem(ModItems.legSheepRaw, 1);
                            }
                        }
                        if (rand > 0.6D) {
                            if (DropsEvent.entityLiving.isBurning()) {
                                DropsEvent.entityLiving.dropItem(ModItems.legSheepCooked, 1);
                            } else {
                                DropsEvent.entityLiving.dropItem(ModItems.legSheepRaw, 1);
                            }
                        }
                    }
                }
            }
        }
    }

    public static class BabySheepLambDropsEvent {

        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent DropsEvent) {
            rand = Math.random();

            if (DropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) DropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (DropsEvent.entityLiving instanceof EntitySheep && DropsEvent.entityLiving.isChild()) {
                        if (DropsEvent.entityLiving.isBurning()) {
                            DropsEvent.entityLiving.dropItem(ModItems.cookedLamb, 1);
                        }else {
                            DropsEvent.entityLiving.dropItem(ModItems.lamb, 1);
                        }
                        if (rand > 0.85D) {
                            if (DropsEvent.entityLiving.isBurning()) {
                                DropsEvent.entityLiving.dropItem(ModItems.cookedLamb, 1);

                            }else {
                                DropsEvent.entityLiving.dropItem(ModItems.lamb, 1);
                            }
                        }
                    }
                }
            }
        }
    }
}