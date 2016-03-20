package com.Girafi.culinarycultivation.modSupport.thaumcraft;

import com.Girafi.culinarycultivation.init.ModBlocks;
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
        for (ItemModFishFood.FishType fishType : ItemModFishFood.FishType.values()) {
            ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.fish, 1, fishType.getMetaData()), new AspectList().add(Aspect.BEAST, 2).add(Aspect.LIFE, 1).add(Aspect.WATER, 1));
            ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.cooked_fish, 1, fishType.getMetaData()), new AspectList().add(Aspect.LIFE, 2).add(Aspect.CRAFT, 1).add(Aspect.BEAST, 1));
        }

        for (ItemStorageJar.StorageJarType jarType : ItemStorageJar.StorageJarType.values()) {
            int meta = jarType.getMetaData();
            if (meta == ItemStorageJar.StorageJarType.WATER.getMetaData()) {
                ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.storageJar, 1, ItemStorageJar.StorageJarType.WATER.getMetaData()), new AspectList().add(Aspect.CRYSTAL, 1).add(Aspect.WATER, 1));
            } else if (meta == ItemStorageJar.StorageJarType.MILK.getMetaData()) {
                ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.storageJar, 1, ItemStorageJar.StorageJarType.MILK.getMetaData()), new AspectList().add(Aspect.CRYSTAL, 1).add(Aspect.LIFE, 1).add(Aspect.WATER, 1).add(Aspect.BEAST, 1));
            } else if (meta == ItemStorageJar.StorageJarType.RENNET.getMetaData()) {
                ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.storageJar, 1, ItemStorageJar.StorageJarType.RENNET.getMetaData()), new AspectList().add(Aspect.CRYSTAL, 1).add(Aspect.LIFE, 1).add(Aspect.WATER, 1).add(Aspect.BEAST, 1).add(Aspect.MOTION, 2));
            } else {
                ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.storageJar, 1, jarType.getMetaData()), new AspectList().add(Aspect.CRYSTAL, 1));
            }
        }

        for (ItemModMeatFood.MeatType meattype : ItemModMeatFood.MeatType.values()) {
            int getMeta = meattype.getMetaData();

            if (getMeta == ItemModMeatFood.MeatType.LAMB.getMetaData() || getMeta == ItemModMeatFood.MeatType.LEGSHEEP.getMetaData()) {
                ThaumcraftApi.registerObjectTag(new ItemStack(meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.BEAST, 2).add(Aspect.LIFE, 1).add(Aspect.EARTH, 1));
                ThaumcraftApi.registerObjectTag(new ItemStack(cooked_meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.LIFE, 2).add(Aspect.CRAFT, 1).add(Aspect.BEAST, 1));
            } else if (getMeta == ItemModMeatFood.MeatType.VEAL.getMetaData() || getMeta == ItemModMeatFood.MeatType.ROAST.getMetaData() || getMeta == ItemModMeatFood.MeatType.RIBSBEEF.getMetaData() || getMeta == ItemModMeatFood.MeatType.RIBS.getMetaData() || getMeta == ItemModMeatFood.MeatType.HAM.getMetaData() || getMeta == ItemModMeatFood.MeatType.BACON.getMetaData()) {
                ThaumcraftApi.registerObjectTag(new ItemStack(meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.BEAST, 2).add(Aspect.LIFE, 1).add(Aspect.EARTH, 1));
                ThaumcraftApi.registerObjectTag(new ItemStack(cooked_meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.LIFE, 2).add(Aspect.CRAFT, 1).add(Aspect.BEAST, 1));
            } else if (getMeta == ItemModMeatFood.MeatType.CHICKENWING.getMetaData() || getMeta == ItemModMeatFood.MeatType.DRUMSTICK.getMetaData() || getMeta == ItemModMeatFood.MeatType.CHICKENNUGGET.getMetaData()) {
                ThaumcraftApi.registerObjectTag(new ItemStack(meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.LIFE, 1).add(Aspect.BEAST, 1).add(Aspect.AIR, 1));
                ThaumcraftApi.registerObjectTag(new ItemStack(cooked_meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.LIFE, 3).add(Aspect.CRAFT, 1).add(Aspect.BEAST, 1));
            } else if (getMeta == ItemModMeatFood.MeatType.SQUIDTENTACLE.getMetaData() || getMeta == ItemModMeatFood.MeatType.SQUIDMANTLE.getMetaData() || getMeta == ItemModMeatFood.MeatType.SQUIDRING.getMetaData()) {
                ThaumcraftApi.registerObjectTag(new ItemStack(meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.LIFE, 2).add(Aspect.BEAST, 1).add(Aspect.WATER, 2));
                ThaumcraftApi.registerObjectTag(new ItemStack(cooked_meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.LIFE, 1).add(Aspect.CRAFT, 1).add(Aspect.WATER, 1));
            } else {
                ThaumcraftApi.registerObjectTag(new ItemStack(meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.LIFE, 2));
                ThaumcraftApi.registerObjectTag(new ItemStack(cooked_meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.LIFE, 1).add(Aspect.CRAFT, 1));
            }
            ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.meat, 1, ItemModMeatFood.MeatType.PATTY.getMetaData()), new AspectList().add(Aspect.BEAST, 1).add(Aspect.LIFE, 1));
            ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.cooked_meat, 1, ItemModMeatFood.MeatType.PATTY.getMetaData()), new AspectList().add(Aspect.LIFE, 1).add(Aspect.CRAFT, 2));
        }
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.calfBelly), new AspectList().add(Aspect.BEAST, 2).add(Aspect.LIFE, 1).add(Aspect.MOTION, 1));
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.toolHandle), new AspectList().add(Aspect.PLANT, 2));
        ThaumcraftApi.registerObjectTag(new ItemStack(ModBlocks.cheese), new AspectList().add(Aspect.LIFE, 7));
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.cheeseSlice), new AspectList().add(Aspect.LIFE, 1));
        //TODO Add aspects for crop related stuff, when done.
    }
}