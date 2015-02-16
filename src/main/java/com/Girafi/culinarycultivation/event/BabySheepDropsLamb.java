package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class BabySheepDropsLamb {

    public static double rand;

    @SubscribeEvent
    public void LivingDropsEvent(LivingDropsEvent DropsEvent) { //Try to look if it's possible to make something with how long time the baby sheep have been in the world / age
        rand = Math.random();
        if (DropsEvent.entityLiving instanceof EntitySheep && DropsEvent.entityLiving.isChild()) { //Slaughter age: Under 12 months, between 2 and 8 months (Maybe make 5D?)
            if (rand < 0.08D) {
                if (DropsEvent.entityLiving.isBurning()) {
                    DropsEvent.entityLiving.dropItem(ModItems.cookedLamb, 2);

                } else {
                    DropsEvent.entityLiving.dropItem(ModItems.lamb, 2);
                }
            }
        }
        if (DropsEvent.entityLiving instanceof EntitySheep && DropsEvent.entityLiving.isChild()) {
             if (rand > 0.65D) {
                 if (DropsEvent.entityLiving.isBurning()) {
                     DropsEvent.entityLiving.dropItem(ModItems.cookedLamb, 1);

                 } else {
                     DropsEvent.entityLiving.dropItem(ModItems.lamb, 1);
                        }
                    }
                }
            }
        }