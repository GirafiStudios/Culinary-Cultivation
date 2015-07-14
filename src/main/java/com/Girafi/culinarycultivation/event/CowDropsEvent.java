package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemModMeatFood.MeatType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
                    if (dropsEvent.entityLiving instanceof EntityCow & !dropsEvent.entityLiving.isChild()) {
                        int j = random.nextInt(3) + 1 + random.nextInt(1 + dropsEvent.lootingLevel);
                        for (int k = 0; k < j; ++k) {
                            if (dropsEvent.entityLiving.isBurning()) {
                                dropsEvent.entityLiving.entityDropItem(new ItemStack(ModItems.cooked_meat, 1, MeatType.RIBS.getMetaData()), 1F);
                            } else {
                                dropsEvent.entityLiving.entityDropItem(new ItemStack(ModItems.meat, 1, MeatType.RIBSBEEF.getMetaData()), 1F);
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
                    if (dropsEvent.entityLiving instanceof EntityCow & !dropsEvent.entityLiving.isChild()) {
                        int j = random.nextInt(2 + dropsEvent.lootingLevel);
                        for (int k = 0; k < j; ++k) {
                            if (dropsEvent.entityLiving.isBurning()) {
                                dropsEvent.entityLiving.entityDropItem(new ItemStack(ModItems.cooked_meat, 1, MeatType.ROAST.getMetaData()), 1F);
                            } else {
                                dropsEvent.entityLiving.entityDropItem(new ItemStack(ModItems.meat, 1, MeatType.ROAST.getMetaData()), 1F);
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
                                dropsEvent.entityLiving.entityDropItem(new ItemStack(ModItems.cooked_meat, 1, MeatType.VEAL.getMetaData()), 1F);
                            } else {
                                dropsEvent.entityLiving.entityDropItem(new ItemStack(ModItems.meat, 1, MeatType.VEAL.getMetaData()), 1F);
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
    public static class BabyCowCalfBellyDropsEvent {
        @SubscribeEvent
        public void LivingDropsEvent(LivingDropsEvent dropsEvent) {
            if (dropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) dropsEvent.source.getSourceOfDamage();
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.meatCleaver) {
                    if (dropsEvent.entityLiving instanceof EntityCow && dropsEvent.entityLiving.isChild()) {
                        int j = random.nextInt(2 + dropsEvent.lootingLevel);
                        for (int k = 0; k < j; ++k) {
                            dropsEvent.entityLiving.entityDropItem(new ItemStack(ModItems.calfBelly, 1, 200), 1F);
                        }
                    }
                }
            }
        }
    }
}