package com.girafi.culinarycultivation.event;

import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.util.ConfigurationHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class MobDropEvent {
    private Class<? extends EntityLivingBase> livingClass;
    private boolean isChild;
    private ItemStack drop = ItemStack.EMPTY;
    private ItemStack dropBurning = ItemStack.EMPTY;
    private boolean canDropBurned;
    private Item killTool;
    private int vanillaDropChance, dropMin, dropMax;

    @SubscribeEvent
    public void livingDropsEvent(LivingDropsEvent event) {
        Random random = new Random();
        if (event.getSource().getSourceOfDamage() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getSource().getSourceOfDamage();
            if (player.inventory.getCurrentItem().getItem() == this.killTool) {
                if (event.getEntityLiving().getClass().isAssignableFrom(this.livingClass) && this.isChild == event.getEntityLiving().isChild()) {
                    if (this.vanillaDropChance == -1 ? random.nextInt(100) >= 35 : random.nextInt(100) <= this.vanillaDropChance) {
                        event.getDrops().clear();
                        System.out.println("Clear");
                    }
                    int dropChance = MathHelper.getInt(random, this.dropMin, this.dropMax);
                    for (int k = 0; k < dropChance + event.getLootingLevel(); ++k) {
                        if (event.getEntityLiving().isBurning() && this.canDropBurned) {
                            event.getEntityLiving().entityDropItem(this.dropBurning.copy(), 1F);
                        } else {
                            event.getEntityLiving().entityDropItem(this.drop.copy(), 1F);
                        }
                    }
                }
            }
        }
    }

    private MobDropEvent setDrop(Class<? extends EntityLivingBase> livingClass, boolean isChild, @Nonnull ItemStack drop, @Nonnull ItemStack dropBurning, boolean canDropBurned, Item killTool, int vanillaDropChance, int dropMin, int dropMax) {
        this.livingClass = livingClass;
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

    public static void register(Class<? extends EntityLivingBase> living, @Nonnull ItemStack drop, @Nonnull ItemStack dropBurning, int dropMin, int dropMax) {
        register(living, false, drop, dropBurning, true, ModItems.MEAT_CLEAVER, -1, dropMin, dropMax);
    }

    public static void registerChild(Class<? extends EntityLivingBase> living, @Nonnull ItemStack drop, @Nonnull ItemStack dropBurning, int dropMin, int dropMax) {
        register(living, true, drop, dropBurning, true, ModItems.MEAT_CLEAVER, -1, dropMin, dropMax);
    }

    public static void register(Class<? extends EntityLivingBase> living, boolean child, @Nonnull ItemStack dropStack, @Nonnull ItemStack burningDrop, boolean dropBurned, Item tool, int vanillaChance, int min, int max) {
        Map<String, Class<? extends EntityLivingBase>> animalClasses = new HashMap<>();
        animalClasses.put("Drop " + dropStack.getDisplayName().toLowerCase().replace("raw ", ""), living);
        String subCategoryNames = ConfigurationHandler.CATEGORY_MOB_DROPS + Configuration.CATEGORY_SPLITTER + living.getSimpleName().replace("Entity", "") + (child ? Configuration.CATEGORY_SPLITTER + "baby" : "");
        List<String> subCategories = animalClasses.keySet().stream().filter(modID -> ConfigurationHandler.config.get(subCategoryNames, modID, true).getBoolean()).collect(Collectors.toList());

        ConfigurationHandler.config.save();

        animalClasses.entrySet().stream().filter(entry -> subCategories.contains(entry.getKey())).forEach(entry -> {
            MinecraftForge.EVENT_BUS.register(new MobDropEvent().setDrop(living, child, dropStack, burningDrop, dropBurned, tool, vanillaChance, min, max));
        });
    }
}