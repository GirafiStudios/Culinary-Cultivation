package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntityCow;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class BabyCowDropsVeal  {

    public static double rand;

    @SubscribeEvent
    public void LivingDropsEvent(LivingDropsEvent DropsEvent) { //Try to look if it's possible to make something with how long time the baby cow have been in the world / age
        rand = Math.random();
        if (DropsEvent.entityLiving instanceof EntityCow && DropsEvent.entityLiving.isChild()) { //Slaughter age: Between 4 and 6 months old
            if (rand < 0.13D) {
                if (DropsEvent.entityLiving.isBurning()) {
                    DropsEvent.entityLiving.dropItem(ModItems.cookedVeal, 2);

                } else {
                    DropsEvent.entityLiving.dropItem(ModItems.veal, 2);
                }
            }
        }
        if (DropsEvent.entityLiving instanceof EntityCow && DropsEvent.entityLiving.isChild()) {
            if (rand > 0.7D) {
                if (DropsEvent.entityLiving.isBurning()) {
                    DropsEvent.entityLiving.dropItem(ModItems.cookedVeal, 1);

                } else {
                    DropsEvent.entityLiving.dropItem(ModItems.veal, 1);
                }
            }
        }
    }
}