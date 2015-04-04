package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class ChickenDropsEvent {

    private static double rand;

    @SubscribeEvent
    public void LivingDropsEvent(LivingDropsEvent DropsEvent) {

        rand = Math.random();

        if (DropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) DropsEvent.source.getSourceOfDamage();
            if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                if (DropsEvent.entityLiving instanceof EntityChicken) {
                    if (rand > 0.1D) {
                        if (DropsEvent.entityLiving.isBurning()) {
                            DropsEvent.entityLiving.dropItem(ModItems.chickenWingCooked, 1);
                        } else {
                            DropsEvent.entityLiving.dropItem(ModItems.chickenWingRaw, 1);
                        }
                    }
                    if (rand > 0.5D) {
                        if (DropsEvent.entityLiving.isBurning()) {
                            DropsEvent.entityLiving.dropItem(ModItems.chickenWingCooked, 1);
                        } else {
                            DropsEvent.entityLiving.dropItem(ModItems.chickenWingRaw, 1);
                        }
                    }
                }
            }
        }
    }
}

