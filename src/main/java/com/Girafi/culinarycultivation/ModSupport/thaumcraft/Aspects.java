package com.Girafi.culinarycultivation.modSupport.thaumcraft;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemModFishFood;
import com.Girafi.culinarycultivation.item.ItemModMeatFood;
import com.Girafi.culinarycultivation.item.ItemStorageJar;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import static com.Girafi.culinarycultivation.init.ModItems.cooked_meat;
import static com.Girafi.culinarycultivation.init.ModItems.meat;

public class Aspects {
    public static void init() {
        ItemModFishFood.FishType[] fish = ItemModFishFood.FishType.values();
        int i = fish.length;
        for (int j = 0; j < i; ++j) {
            ItemModFishFood.FishType fishType = fish[j];
            ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.fish, 1, fishType.getMetaData()), new AspectList().add(Aspect.LIFE, 1).add(Aspect.WATER, 1));
            ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.cooked_fish, 1, fishType.getMetaData()), new AspectList().add(Aspect.HUNGER, 3).add(Aspect.CRAFT, 1));
        }

        ItemStorageJar.StorageJarType[] jar = ItemStorageJar.StorageJarType.values(); //TODO Add Rennet Jar aspects
        int iJar = jar.length;
        for (int j = 0; j < iJar; ++j) {
            ItemStorageJar.StorageJarType jarType = jar[j];
            int meta = jarType.getMetaData();
            if (meta == ItemStorageJar.StorageJarType.WATER.getMetaData()) {
                ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.storageJar, 1, ItemStorageJar.StorageJarType.WATER.getMetaData()), new AspectList().add(Aspect.CRYSTAL, 1).add(Aspect.WATER, 1));
            } else if (meta == ItemStorageJar.StorageJarType.MILK.getMetaData()) {
                ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.storageJar, 1, ItemStorageJar.StorageJarType.MILK.getMetaData()), new AspectList().add(Aspect.CRYSTAL, 1).add(Aspect.HUNGER, 1).add(Aspect.WATER, 1));
            } else {
                ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.storageJar, 1, jarType.getMetaData()), new AspectList().add(Aspect.CRYSTAL, 1));
            }
        }

        ItemModMeatFood.MeatType[] ameattype = ItemModMeatFood.MeatType.values(); //TODO Figure out to do with squid meat
        int imeat = ameattype.length;
        for (int j = 0; j < imeat; ++j) {
            ItemModMeatFood.MeatType meattype = ameattype[j];
            int getMeta = meattype.getMetaData();

            if (getMeta == meattype.LAMB.getMetaData() || getMeta == meattype.LEGSHEEP.getMetaData()) {
                ThaumcraftApi.registerObjectTag(new ItemStack(meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.BEAST, 2).add(Aspect.LIFE, 1).add(Aspect.EARTH, 1));
                ThaumcraftApi.registerObjectTag(new ItemStack(cooked_meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.HUNGER, 2).add(Aspect.CRAFT, 1).add(Aspect.BEAST, 1));
            } else if (getMeta == meattype.VEAL.getMetaData() || getMeta == meattype.ROAST.getMetaData() || getMeta == meattype.RIBSBEEF.getMetaData()) {
                ThaumcraftApi.registerObjectTag(new ItemStack(meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.LIFE, 2).add(Aspect.BEAST, 1));
                ThaumcraftApi.registerObjectTag(new ItemStack(cooked_meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.HUNGER, 2).add(Aspect.CRAFT, 1));
            } else if (getMeta == meattype.RIBS.getMetaData() || getMeta == meattype.HAM.getMetaData() || getMeta == meattype.BACON.getMetaData()) {
                ThaumcraftApi.registerObjectTag(new ItemStack(meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.LIFE, 1).add(Aspect.BEAST, 1));
                ThaumcraftApi.registerObjectTag(new ItemStack(cooked_meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.HUNGER, 3).add(Aspect.CRAFT, 1));
            } else if (getMeta == meattype.CHICKENWING.getMetaData() || getMeta == meattype.DRUMSTICK.getMetaData() || getMeta == meattype.CHICKENNUGGET.getMetaData()) {
                ThaumcraftApi.registerObjectTag(new ItemStack(meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.LIFE, 1).add(Aspect.BEAST, 1));
                ThaumcraftApi.registerObjectTag(new ItemStack(cooked_meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.HUNGER, 3).add(Aspect.CRAFT, 1));
            } else {
                ThaumcraftApi.registerObjectTag(new ItemStack(meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.HUNGER, 1).add(Aspect.LIFE, 1));
                ThaumcraftApi.registerObjectTag(new ItemStack(cooked_meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.LIFE, 1).add(Aspect.CRAFT, 1));
            }
            ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.meat, 1, meattype.PATTY.getMetaData()), new AspectList().add(Aspect.BEAST, 1).add(Aspect.HUNGER, 1));
            ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.cooked_meat, 1, meattype.PATTY.getMetaData()), new AspectList().add(Aspect.LIFE, 1).add(Aspect.CRAFT, 2));
        }

        //ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.toolHandle), new AspectList().add(Aspect.TREE, 2));
        //TODO Add cheese & crops aspects when done
    }
}