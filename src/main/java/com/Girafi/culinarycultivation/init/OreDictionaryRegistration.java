package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.item.ItemModFishFood.FishType;
import com.Girafi.culinarycultivation.item.ItemModMeatFood.MeatType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.text.WordUtils;

public class OreDictionaryRegistration {
    public static void init() {

        FishType[] fish = FishType.values();
        int iFish = fish.length;
        for (int j = 0; j < iFish; ++j) {
            FishType fishtype = fish[j];
            OreDictionary.registerOre("food" + WordUtils.capitalize(fishtype.getUnlocalizedName()) + "Raw", new ItemStack(ModItems.fish, 1, fishtype.getMetaData()));
            OreDictionary.registerOre("food" + WordUtils.capitalize(fishtype.getUnlocalizedName()) + "Cooked", new ItemStack(ModItems.cooked_fish, 1, fishtype.getMetaData()));
            if (fishtype.isHaveRawFish() && fishtype.getMetaData() != FishType.FILLET.getMetaData() && fishtype.getMetaData() != FishType.SMALLSQUID.getMetaData()) {
                OreDictionary.registerOre("filletFish", new ItemStack(ModItems.fish, 1, fishtype.getMetaData()));
                OreDictionary.registerOre("fish", new ItemStack(ModItems.fish, 1, fishtype.getMetaData()));
            }
        }
        OreDictionary.registerOre("filletFish", new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.getMetadata()));
        OreDictionary.registerOre("filletFish", new ItemStack(Items.fish, 1, ItemFishFood.FishType.SALMON.getMetadata()));
        OreDictionary.registerOre("filletFish", new ItemStack(Items.fish, 1, ItemFishFood.FishType.CLOWNFISH.getMetadata()));

        MeatType[] meat = MeatType.values();
        int iMeat = meat.length;
        for (int j = 0; j < iMeat; ++j) {
            MeatType meatType = meat[j];
            OreDictionary.registerOre("food" + WordUtils.capitalize(meatType.getUnlocalizedName()) + "Raw", new ItemStack(ModItems.meat, 1, meatType.getMetaData()));
            OreDictionary.registerOre("food" + WordUtils.capitalize(meatType.getUnlocalizedName()) + "Cooked", new ItemStack(ModItems.cooked_meat, 1, meatType.getMetaData()));
        }

        OreDictionary.registerOre("foodRibsRaw", new ItemStack(ModItems.meat, 1, MeatType.RIBSBEEF.getMetaData()));
        OreDictionary.registerOre("foodRibsPorkRaw", new ItemStack(ModItems.meat, 1, MeatType.RIBS.getMetaData()));

        //Minced Meat recipe
        OreDictionary.registerOre("foodMincedMeat", Items.beef);
        OreDictionary.registerOre("foodMincedMeat", Items.mutton);
        OreDictionary.registerOre("foodMincedMeat", Items.porkchop);
        OreDictionary.registerOre("foodMincedMeat", new ItemStack(ModItems.meat, 1, MeatType.HAM.getMetaData()));
        OreDictionary.registerOre("foodMincedMeat", new ItemStack(ModItems.meat, 1, MeatType.LAMB.getMetaData()));
        OreDictionary.registerOre("foodMincedMeat", new ItemStack(ModItems.meat, 1, MeatType.ROAST.getMetaData()));
        OreDictionary.registerOre("foodMincedMeat", new ItemStack(ModItems.meat, 1, MeatType.VEAL.getMetaData()));
    }
}