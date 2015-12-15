package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class MobDropEvent {
    private Random random = new Random();
    private Class<? extends EntityLivingBase> entityLivingClass;
    private boolean isChild;
    private ItemStack drop;
    private ItemStack dropBurning;
    private boolean canDropBurned;
    private Item killTool;
    private int vanillaDropChance;
    private int dropMin;
    private int dropMax;

    @SubscribeEvent
    public void livingDropEvent(LivingDropsEvent dropsEvent) {
        if (dropsEvent.source.getSourceOfDamage() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) dropsEvent.source.getSourceOfDamage();
            if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == this.killTool) {
                if (dropsEvent.entityLiving.getClass().isAssignableFrom(this.entityLivingClass) && (isChild ? dropsEvent.entityLiving.isChild() : !dropsEvent.entityLiving.isChild())) {
                    if (this.vanillaDropChance == -1 ? random.nextInt(100) <= 50 : random.nextInt(100) <= this.vanillaDropChance) {
                        dropsEvent.drops.clear();
                    }
                    int drop = MathHelper.getRandomIntegerInRange(random, dropMin, dropMax);
                    for (int k = 0; k < drop + dropsEvent.lootingLevel; ++k) {
                        if (dropsEvent.entityLiving.isBurning() && canDropBurned) {
                            dropsEvent.entityLiving.entityDropItem(this.dropBurning.copy(), 1F);
                        } else {
                            dropsEvent.entityLiving.entityDropItem(this.drop.copy(), 1F);
                        }
                    }
                }
            }
        }
    }

    public MobDropEvent setDrop(Class<? extends EntityLivingBase> entityLivingClass, ItemStack drop, ItemStack dropBurning, int dropMin, int dropMax) {
        this.setFullDrop(entityLivingClass, false, drop, dropBurning, true, ModItems.meatCleaver, -1, dropMin, dropMax);
        return this;
    }

    public MobDropEvent setModifiedDrop(Class<? extends EntityLivingBase> entityLivingClass, ItemStack drop, ItemStack dropBurning, int vanillaDropChance, int dropMin, int dropMax) {
        this.setFullDrop(entityLivingClass, false, drop, dropBurning, true, ModItems.meatCleaver, vanillaDropChance, dropMin, dropMax);
        return this;
    }

    public MobDropEvent setChildDrop(Class<? extends EntityLivingBase> entityLivingClass, ItemStack drop, ItemStack dropBurning, int dropMin, int dropMax) {
        this.setFullDrop(entityLivingClass, true, drop, dropBurning, true, ModItems.meatCleaver, -1, dropMin, dropMax);
        return this;
    }

    public MobDropEvent setWaterDrop(Class<? extends EntityLivingBase> entityLivingClass, ItemStack drop, int dropMin, int dropMax) {
        this.setFullDrop(entityLivingClass, false, drop, null, false, ModItems.meatCleaver, 25, dropMin, dropMax);
        return this;
    }

    public MobDropEvent setFullDrop(Class<? extends EntityLivingBase> entityLivingClass, boolean isChild, ItemStack drop, ItemStack dropBurning, boolean canDropBurned, Item killTool, int vanillaDropChance, int dropMin, int dropMax) {
        this.entityLivingClass = entityLivingClass;
        this.isChild = isChild;
        this.drop = drop;
        this.dropBurning = dropBurning;
        this.canDropBurned = canDropBurned;
        this.killTool = killTool;
        this.vanillaDropChance = vanillaDropChance;
        this.dropMin = dropMin;
        this.dropMax = dropMax;
        return this;
    }
}