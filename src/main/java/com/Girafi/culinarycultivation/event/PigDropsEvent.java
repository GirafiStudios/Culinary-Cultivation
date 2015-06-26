package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemModMeatFood.MeatType;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class PigDropsEvent {
    private static Random random = new Random();
    /**
     * Drop 1-2 items of this living's type
     */
    public static class PigRibsPorkDropsEvent {
        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent dropsEvent) {
            if (dropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) dropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (dropsEvent.entityLiving instanceof EntityPig & !dropsEvent.entityLiving.isChild()) {
                        int j = random.nextInt(2) + 1 + random.nextInt(1 + dropsEvent.lootingLevel);
                        for (int k = 0; k < j; ++k) {
                            if (dropsEvent.entityLiving.isBurning()) {
                                dropsEvent.entityLiving.entityDropItem(new ItemStack(ModItems.cooked_meat, 1, MeatType.RIBS.getMetaData()), 1F);
                            } else {
                                dropsEvent.entityLiving.entityDropItem(new ItemStack(ModItems.meat, 1, MeatType.RIBS.getMetaData()), 1F);
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
    public static class PigHamDropsEvent {
        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent dropsEvent) {
            if (dropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) dropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (dropsEvent.entityLiving instanceof EntityPig & !dropsEvent.entityLiving.isChild()) {
                        int j = random.nextInt(2 + dropsEvent.lootingLevel);
                        for (int k = 0; k < j; ++k) {
                            if (dropsEvent.entityLiving.isBurning()) {
                                dropsEvent.entityLiving.entityDropItem(new ItemStack(ModItems.cooked_meat, 1, MeatType.HAM.getMetaData()), 1F);
                            } else {
                                dropsEvent.entityLiving.entityDropItem(new ItemStack(ModItems.meat, 1, MeatType.HAM.getMetaData()), 1F);
                            }
                        }
                    }
                }
            }
        }
    }
}
