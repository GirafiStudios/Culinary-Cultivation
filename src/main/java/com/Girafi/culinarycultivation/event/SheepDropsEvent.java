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
        public void LivingDropsEvent(LivingDropsEvent dropsEvent) {
            rand = Math.random();

            if (dropsEvent.entityLiving instanceof EntitySheep &! dropsEvent.entityLiving.isChild()) {
                if (dropsEvent.entityLiving.isBurning()) {
                    dropsEvent.entityLiving.dropItem(ModItems.cookedMutton, 1);
                }else {
                    dropsEvent.entityLiving.dropItem(ModItems.mutton, 1);

                    if (rand > 0.6D) {
                        if (dropsEvent.entityLiving.isBurning()) {
                            dropsEvent.entityLiving.dropItem(ModItems.cookedMutton, 1);
                        }else {
                            dropsEvent.entityLiving.dropItem(ModItems.mutton, 1);
                        }
                    }
                }
            }
        }
    }

    public static class SheepLegOfSheepDropsEvent {

        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent dropsEvent) {
            rand = Math.random();

            if (dropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) dropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (dropsEvent.entityLiving instanceof EntitySheep & !dropsEvent.entityLiving.isChild()) {
                        if (rand > 0.3D) {
                            if (dropsEvent.entityLiving.isBurning()) {
                                dropsEvent.entityLiving.dropItem(ModItems.legSheepCooked, 1);
                            } else {
                                dropsEvent.entityLiving.dropItem(ModItems.legSheepRaw, 1);
                            }
                        }
                        if (rand > 0.9D) {
                            if (dropsEvent.entityLiving.isBurning()) {
                                dropsEvent.entityLiving.dropItem(ModItems.legSheepCooked, 1);
                            } else {
                                dropsEvent.entityLiving.dropItem(ModItems.legSheepRaw, 1);
                            }
                        }
                    }
                }
            }
        }
    }

    public static class BabySheepLambDropsEvent {

        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent dropsEvent) {
            rand = Math.random();

            if (dropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) dropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (dropsEvent.entityLiving instanceof EntitySheep && dropsEvent.entityLiving.isChild()) {
                        if (dropsEvent.entityLiving.isBurning()) {
                            dropsEvent.entityLiving.dropItem(ModItems.cookedLamb, 1);
                        }else {
                            dropsEvent.entityLiving.dropItem(ModItems.lamb, 1);
                        }
                        if (rand > 0.85D) {
                            if (dropsEvent.entityLiving.isBurning()) {
                                dropsEvent.entityLiving.dropItem(ModItems.cookedLamb, 1);

                            }else {
                                dropsEvent.entityLiving.dropItem(ModItems.lamb, 1);
                            }
                        }
                    }
                }
            }
        }
    }
}