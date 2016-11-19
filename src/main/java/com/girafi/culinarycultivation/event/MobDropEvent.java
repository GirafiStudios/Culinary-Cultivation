package com.girafi.culinarycultivation.event;

import com.girafi.culinarycultivation.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class MobDropEvent {
    private static final Random RANDOM = new Random();
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
    public void livingDropsEvent(LivingDropsEvent dropsEvent) {
        if (dropsEvent.getSource().getSourceOfDamage() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) dropsEvent.getSource().getSourceOfDamage();
            if (player.inventory.getCurrentItem().getItem() == this.killTool) {
                if (dropsEvent.getEntityLiving().getClass().isAssignableFrom(this.entityLivingClass) && isChild == dropsEvent.getEntityLiving().isChild()) {
                    if (this.vanillaDropChance == -1 ? RANDOM.nextInt(100) <= 35 : RANDOM.nextInt(100) <= this.vanillaDropChance) {
                        dropsEvent.getDrops().clear();
                    }
                    int drop = MathHelper.getInt(RANDOM, dropMin, dropMax);
                    for (int k = 0; k < drop + dropsEvent.getLootingLevel(); ++k) {
                        if (dropsEvent.getEntityLiving().isBurning() && canDropBurned) {
                            dropsEvent.getEntityLiving().entityDropItem(this.dropBurning.copy(), 1F);
                        } else {
                            dropsEvent.getEntityLiving().entityDropItem(this.drop.copy(), 1F);
                        }
                    }
                }
            }
        }
    }

    public MobDropEvent setDrop(Class<? extends EntityLivingBase> entityLivingClass, ItemStack drop, ItemStack dropBurning, int dropMin, int dropMax) {
        this.setFullDrop(entityLivingClass, false, drop, dropBurning, true, ModItems.MEAT_CLEAVER, -1, dropMin, dropMax);
        return this;
    }

    public MobDropEvent setModifiedDrop(Class<? extends EntityLivingBase> entityLivingClass, ItemStack drop, ItemStack dropBurning, int vanillaDropChance, int dropMin, int dropMax) {
        this.setFullDrop(entityLivingClass, false, drop, dropBurning, true, ModItems.MEAT_CLEAVER, vanillaDropChance, dropMin, dropMax);
        return this;
    }

    public MobDropEvent setChildDrop(Class<? extends EntityLivingBase> entityLivingClass, ItemStack drop, ItemStack dropBurning, int dropMin, int dropMax) {
        this.setFullDrop(entityLivingClass, true, drop, dropBurning, true, ModItems.MEAT_CLEAVER, -1, dropMin, dropMax);
        return this;
    }

    public MobDropEvent setWaterDrop(Class<? extends EntityLivingBase> entityLivingClass, ItemStack drop, int dropMin, int dropMax) {
        this.setFullDrop(entityLivingClass, false, drop, null, false, ModItems.MEAT_CLEAVER, 25, dropMin, dropMax);
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