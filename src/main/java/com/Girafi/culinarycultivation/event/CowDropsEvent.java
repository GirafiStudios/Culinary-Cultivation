package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import java.util.Random;

public class CowDropsEvent {

    private static Random random = new Random();
    /**
     * Drop 1-3 items of this living's type
     */
    public static class CowRibsBeefDropsEvent {
        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent dropsEvent) {
            if (dropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) dropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (dropsEvent.entityLiving instanceof EntityCow &! dropsEvent.entityLiving.isChild()) {
                        int j = random.nextInt(3) + 1 + random.nextInt(1 + dropsEvent.lootingLevel);
                        for (int k = 0; k < j; ++k) {
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
    /**
     * Drop 0-1 items of this living's type
     */
    public static class CowRoastDropsEvent {
        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent dropsEvent) {
            if (dropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) dropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (dropsEvent.entityLiving instanceof EntityCow &! dropsEvent.entityLiving.isChild()) {
                        int j = random.nextInt(2 + dropsEvent.lootingLevel);
                        for (int k = 0; k < j; ++k) {
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
    /**
     * Drop 1-3 items of this living's type
     */
    public static class BabyCowVealDropsEvent {
        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent dropsEvent) {
            if (dropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) dropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (dropsEvent.entityLiving instanceof EntityCow && dropsEvent.entityLiving.isChild()) {
                        int j = random.nextInt(3) + 1 + random.nextInt(1 + dropsEvent.lootingLevel);
                        for (int k = 0; k < j; ++k) {
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