package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class SquidDropsEvent {

    private static double rand;

        public static class SquidMantleDropsEvent {

            @SubscribeEvent
            public void LivingDropsEvent(LivingDropsEvent dropsEvent) {
                rand = Math.random();

                if (dropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) dropsEvent.source.getSourceOfDamage();
                    if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                        if (dropsEvent.entityLiving instanceof EntitySquid) {
                            if (rand < 0.1D) {
                                dropsEvent.entityLiving.dropItem(ModItems.squidMantle, 1);
                            }
                                if (rand > 0.05D) {
                                    dropsEvent.entityLiving.dropItem(ModItems.squidMantle, 1);
                            }
                        }
                    }
                }
            }
        }

    public static class SquidTentacleDropsEvent {
        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent dropsEvent) {
            if (dropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) dropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (dropsEvent.entityLiving instanceof EntitySquid) {
                        if (rand > 0.5D) {
                            dropsEvent.entityLiving.dropItem(ModItems.squidTentacle, 1);
                        }
                    }
                }
            }
        }
    }
}