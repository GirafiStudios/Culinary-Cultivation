package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemModMeatFood.MeatType;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
                            dropsEvent.entityLiving.entityDropItem(new ItemStack(ModItems.meat, 1, MeatType.SQUIDMANTLE.getMetaData()), 1F);
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
                            dropsEvent.entityLiving.entityDropItem(new ItemStack(ModItems.meat, 1, MeatType.SQUIDTENTACLE.getMetaData()), 1F);
                        }
                    }
                }
            }
        }
    }
}