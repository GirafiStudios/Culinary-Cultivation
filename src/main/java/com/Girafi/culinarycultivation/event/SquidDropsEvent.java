package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import java.util.Random;

public class SquidDropsEvent {
    private static Random random = new Random();

    /**
     * Drop 1-2 items of this living's type
     */
    public static class SquidMantleDropsEvent {
        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent dropsEvent) {
            if (dropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) dropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (dropsEvent.entityLiving instanceof EntitySquid) {
                        int j = random.nextInt(2) + 1 + random.nextInt(1 + dropsEvent.lootingLevel);
                        for (int k = 0; k < j; ++k) {
                            dropsEvent.entityLiving.dropItem(ModItems.squidMantle, 1);
                        }
                    }
                }
            }
        }
    }

    /**
     * Drop 0-1 items of this living's type
     */
    public static class SquidTentacleDropsEvent {
        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent dropsEvent) {
            if (dropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) dropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (dropsEvent.entityLiving instanceof EntitySquid) {
                        int j = random.nextInt(2 + dropsEvent.lootingLevel);
                        for (int k = 0; k < j; ++k) {
                            dropsEvent.entityLiving.dropItem(ModItems.squidTentacle, 1);
                        }
                    }
                }
            }
        }
    }
}