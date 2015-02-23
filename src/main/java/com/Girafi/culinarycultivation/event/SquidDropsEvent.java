package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class SquidDropsEvent {

    private static double rand;

    @SubscribeEvent
    public void LivingDropsEvent(LivingDropsEvent DropsEvent) {

        rand = Math.random();

        if (DropsEvent.entityLiving instanceof EntitySquid) {
            if (rand > 0.90D) {
                DropsEvent.entityLiving.dropItem(ModItems.squidMantle, 1);
            }
        }
        if (DropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) DropsEvent.source.getSourceOfDamage();
            if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                if (DropsEvent.entityLiving instanceof EntitySquid) {
                    if (rand > 0.05D) {
                        DropsEvent.entityLiving.dropItem(ModItems.squidMantle, 1);
                    }
                }
                if (DropsEvent.entityLiving instanceof EntitySquid) {
                    if (rand > 0.5D) {
                        DropsEvent.entityLiving.dropItem(ModItems.squidTentacle, 1);
                    }
                }
            }
        }
    }
}