package com.girafi.culinarycultivation.init;

import com.girafi.culinarycultivation.item.ItemCropProduct;
import com.girafi.culinarycultivation.item.ItemModFishFood.FishType;
import com.girafi.culinarycultivation.item.ItemModMeatFood.MeatType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.text.WordUtils;

public class OreDictionaryRegistration { //TODO Clean up this shitty code

    public static void init() {
        for (ItemCropProduct.ProductType product : ItemCropProduct.ProductType.values()) {
            OreDictionary.registerOre("crop" + toCamelCase(product.getCropName()), new ItemStack(ModItems.CROP_FOOD, 1, product.getMetadata()));
            OreDictionary.registerOre("seed" + toCamelCase(product.getCropName()), new ItemStack(ModItems.CROP_SEEDS, 1, product.getMetadata()));
        }
        for (FishType fishtype : FishType.values()) {
            if (fishtype.isHaveRawFish() && fishtype.getMetadata() != FishType.FILLET.getMetadata() && fishtype.getMetadata() != FishType.SMALL_SQUID.getMetadata()) {
                OreDictionary.registerOre("filletFish", new ItemStack(ModItems.FISH, 1, fishtype.getMetadata()));
                OreDictionary.registerOre("fish", new ItemStack(ModItems.FISH, 1, fishtype.getMetadata()));
            }
            OreDictionary.registerOre("food" + toCamelCase(fishtype.getFishName()) + "Raw", new ItemStack(ModItems.FISH, 1, fishtype.getMetadata()));
            OreDictionary.registerOre("food" + toCamelCase(fishtype.getFishName()) + "Cooked", new ItemStack(ModItems.COOKED_FISH, 1, fishtype.getMetadata()));
            OreDictionary.registerOre("fish" + toCamelCase(fishtype.getFishName()), new ItemStack(ModItems.FISH, 1, fishtype.getMetadata()));
            OreDictionary.registerOre("fish" + toCamelCase(fishtype.getFishName()) + "Cooked", new ItemStack(ModItems.COOKED_FISH, 1, fishtype.getMetadata()));
        }
        OreDictionary.registerOre("filletFish", new ItemStack(Items.FISH, 1, ItemFishFood.FishType.COD.getMetadata()));
        OreDictionary.registerOre("filletFish", new ItemStack(Items.FISH, 1, ItemFishFood.FishType.SALMON.getMetadata()));
        OreDictionary.registerOre("filletFish", new ItemStack(Items.FISH, 1, ItemFishFood.FishType.CLOWNFISH.getMetadata()));

        for (MeatType meatType : MeatType.values()) {
            OreDictionary.registerOre("food" + toCamelCase(meatType.getMeatName()) + "Raw", new ItemStack(ModItems.MEAT, 1, meatType.getMetadata()));
            OreDictionary.registerOre("food" + toCamelCase(meatType.getMeatName()) + "Cooked", new ItemStack(ModItems.COOKED_MEAT, 1, meatType.getMetadata()));
        }

        OreDictionary.registerOre("foodRibsRaw", new ItemStack(ModItems.MEAT, 1, MeatType.RIBS_BEEF.getMetadata()));
        OreDictionary.registerOre("foodRibsPorkRaw", new ItemStack(ModItems.MEAT, 1, MeatType.RIBS.getMetadata()));

        //Minced Meat recipe
        OreDictionary.registerOre("foodMincedMeat", Items.BEEF);
        OreDictionary.registerOre("foodMincedMeat", Items.MUTTON);
        OreDictionary.registerOre("foodMincedMeat", Items.PORKCHOP);
        OreDictionary.registerOre("foodMincedMeat", Items.RABBIT);
        OreDictionary.registerOre("foodMincedMeat", new ItemStack(ModItems.MEAT, 1, MeatType.HAM.getMetadata()));
        OreDictionary.registerOre("foodMincedMeat", new ItemStack(ModItems.MEAT, 1, MeatType.LAMB.getMetadata()));
        OreDictionary.registerOre("foodMincedMeat", new ItemStack(ModItems.MEAT, 1, MeatType.ROAST.getMetadata()));
        OreDictionary.registerOre("foodMincedMeat", new ItemStack(ModItems.MEAT, 1, MeatType.VEAL.getMetadata()));
    }

    private static String toCamelCase(String string) {
        return WordUtils.capitalizeFully(string, '_').replace("_", "");
    }
}