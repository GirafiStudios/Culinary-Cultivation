package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemModMeatFood;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import java.util.Random;

public class SheepDropsEvent {
    private static Random random = new Random();
    /**
     * Drop 1-2 items of this living's type
     */
    public static class SheepMuttonDropsEvent {
        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent dropsEvent) {
            if (dropsEvent.entityLiving instanceof EntitySheep & !dropsEvent.entityLiving.isChild()) {
                int j = random.nextInt(2) + 1 + random.nextInt(1 + dropsEvent.lootingLevel);
                for (int k = 0; k < j; ++k) {
                    if (dropsEvent.entityLiving.isBurning()) {
                        dropsEvent.entityLiving.dropItem(ModItems.cookedMutton, 1);
                    } else {
                        dropsEvent.entityLiving.dropItem(ModItems.mutton, 1);
                    }
                }
            }
        }
    }

    /**
     * Drop 0-2 items of this living's type
     */
    public static class SheepLegOfSheepDropsEvent {
        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent dropsEvent) {
            if (dropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) dropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (dropsEvent.entityLiving instanceof EntitySheep & !dropsEvent.entityLiving.isChild()) {
                        int j = random.nextInt(3 + dropsEvent.lootingLevel);
                        for (int k = 0; k < j; ++k) {
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
    /**
     * Drop 1-2 items of this living's type
     */
    public static class BabySheepLambDropsEvent {
        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent dropsEvent) {
            if (dropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) dropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (dropsEvent.entityLiving instanceof EntitySheep && dropsEvent.entityLiving.isChild()) {
                        int j = random.nextInt(2) + 1 + random.nextInt(1 + dropsEvent.lootingLevel);
                        for (int k = 0; k < j; ++k) {
                            if (dropsEvent.entityLiving.isBurning()) {
                                dropsEvent.entityLiving.entityDropItem(new ItemStack(ModItems.meat_cooked, 1, ItemModMeatFood.MeatType.LAMB.getMetaData()), 1F);
                            } else {
                                dropsEvent.entityLiving.entityDropItem(new ItemStack(ModItems.meat, 1, ItemModMeatFood.MeatType.LAMB.getMetaData()), 1F);
                            }
                        }
                    }
                }
            }
        }
    }
}