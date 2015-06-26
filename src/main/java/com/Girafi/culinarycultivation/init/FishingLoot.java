package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.item.ItemModFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraftforge.common.FishingHooks;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Random;

public class FishingLoot {

    public static void init() { //TODO Change fish caught chances!
        FishingHooks.addFish(new WeightedRandomFishable(new ItemStack(ModItems.fish, 1, ItemModFishFood.FishType.MACKEREL.getMetaData()), 55));
        FishingHooks.addFish(new WeightedRandomFishable(new ItemStack(ModItems.fish, 1, ItemModFishFood.FishType.TUNA.getMetaData()), 40));
        FishingHooks.addFish(new WeightedRandomFishable(new ItemStack(ModItems.fish, 1, ItemModFishFood.FishType.TROUT.getMetaData()), 25));
        FishingHooks.addFish(new WeightedRandomFishable(new ItemStack(ModItems.fish, 1, ItemModFishFood.FishType.HERRING.getMetaData()), 30));
        FishingHooks.addFish(new WeightedRandomFishable(new ItemStack(ModItems.fish, 1, ItemModFishFood.FishType.PLAICE.getMetaData()), 18));
        FishingHooks.addFish(new WeightedRandomFishable(new ItemStack(ModItems.fish, 1, ItemModFishFood.FishType.SMALLSQUID.getMetaData()), 8));
        FishingHooks.addJunk(new WeightedRandomFishable(new ItemStack(ModItems.meatCleaver, 1), 8).setMaxDamagePercent(0.25F));
    }
}