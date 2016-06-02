package com.girafi.culinarycultivation.init;

import com.girafi.culinarycultivation.item.ItemModFishFood.FishType;
import com.girafi.culinarycultivation.item.ItemModMeatFood.MeatType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.text.WordUtils;

public class OreDictionaryRegistration {
    public static void preInit() {
        for (FishType fishtype : FishType.values()) {
            if (fishtype.isHaveRawFish() && fishtype.getMetaData() != FishType.FILLET.getMetaData() && fishtype.getMetaData() != FishType.SMALL_SQUID.getMetaData()) {
                OreDictionary.registerOre("filletFish", new ItemStack(ModItems.FISH, 1, fishtype.getMetaData()));
                OreDictionary.registerOre("fish", new ItemStack(ModItems.FISH, 1, fishtype.getMetaData()));
            }
            OreDictionary.registerOre("food" + toCamelCase(fishtype.getFishName()) + "Raw", new ItemStack(ModItems.FISH, 1, fishtype.getMetaData()));
            OreDictionary.registerOre("food" + toCamelCase(fishtype.getFishName()) + "Cooked", new ItemStack(ModItems.COOKED_FISH, 1, fishtype.getMetaData()));
            OreDictionary.registerOre("fish" + toCamelCase(fishtype.getFishName()), new ItemStack(ModItems.FISH, 1, fishtype.getMetaData()));
            OreDictionary.registerOre("fish" + toCamelCase(fishtype.getFishName()) + "Cooked", new ItemStack(ModItems.COOKED_FISH, 1, fishtype.getMetaData()));
        }
        OreDictionary.registerOre("filletFish", new ItemStack(Items.FISH, 1, ItemFishFood.FishType.COD.getMetadata()));
        OreDictionary.registerOre("filletFish", new ItemStack(Items.FISH, 1, ItemFishFood.FishType.SALMON.getMetadata()));
        OreDictionary.registerOre("filletFish", new ItemStack(Items.FISH, 1, ItemFishFood.FishType.CLOWNFISH.getMetadata()));

        for (MeatType meatType : MeatType.values()) {
            OreDictionary.registerOre("food" + toCamelCase(meatType.getMeatName()) + "Raw", new ItemStack(ModItems.MEAT, 1, meatType.getMetaData()));
            OreDictionary.registerOre("food" + toCamelCase(meatType.getMeatName()) + "Cooked", new ItemStack(ModItems.COOKED_MEAT, 1, meatType.getMetaData()));
        }

        OreDictionary.registerOre("foodRibsRaw", new ItemStack(ModItems.MEAT, 1, MeatType.RIBS_BEEF.getMetaData()));
        OreDictionary.registerOre("foodRibsPorkRaw", new ItemStack(ModItems.MEAT, 1, MeatType.RIBS.getMetaData()));

        //Minced Meat recipe
        OreDictionary.registerOre("foodMincedMeat", Items.BEEF);
        OreDictionary.registerOre("foodMincedMeat", Items.MUTTON);
        OreDictionary.registerOre("foodMincedMeat", Items.PORKCHOP);
        OreDictionary.registerOre("foodMincedMeat", Items.RABBIT);
        OreDictionary.registerOre("foodMincedMeat", new ItemStack(ModItems.MEAT, 1, MeatType.HAM.getMetaData()));
        OreDictionary.registerOre("foodMincedMeat", new ItemStack(ModItems.MEAT, 1, MeatType.LAMB.getMetaData()));
        OreDictionary.registerOre("foodMincedMeat", new ItemStack(ModItems.MEAT, 1, MeatType.ROAST.getMetaData()));
        OreDictionary.registerOre("foodMincedMeat", new ItemStack(ModItems.MEAT, 1, MeatType.VEAL.getMetaData()));
    }

    private static String toCamelCase(String string) {
        return WordUtils.capitalizeFully(string, '_').replace("_", "");
    }
}