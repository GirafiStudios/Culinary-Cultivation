package com.girafi.culinarycultivation.init;

import com.girafi.culinarycultivation.util.StringUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryRegistration { //TODO Clean up this shitty code

    public static final String MINCED_MEAT = "foodMincedMeat";

    public static void init() {
        /*for (ItemCropProduct.ProductType product : ItemCropProduct.ProductType.values()) {
            register("crop" + product.getCropName(), new ItemStack(ModItems.CROP_FOOD, 1, product.getMetadata()));
            register("seed" + product.getCropName(), new ItemStack(ModItems.CROP_SEEDS, 1, product.getMetadata()));
        }
        for (FishType fishtype : FishType.values()) {
            if (fishtype.isHaveRawFish() && fishtype.getMetadata() != FishType.FILLET.getMetadata() && fishtype.getMetadata() != FishType.SMALL_SQUID.getMetadata()) {
                register("filletFish", new ItemStack(ModItems.FISH, 1, fishtype.getMetadata()));
                register("fish", new ItemStack(ModItems.FISH, 1, fishtype.getMetadata()));
            }
            register("food" + fishtype.getFishName() + "Raw", new ItemStack(ModItems.FISH, 1, fishtype.getMetadata()));
            register("food" + fishtype.getFishName() + "Cooked", new ItemStack(ModItems.COOKED_FISH, 1, fishtype.getMetadata()));
            register("fish" + fishtype.getFishName(), new ItemStack(ModItems.FISH, 1, fishtype.getMetadata()));
            register("fish" + fishtype.getFishName() + "Cooked", new ItemStack(ModItems.COOKED_FISH, 1, fishtype.getMetadata()));
        }
        register("filletFish", new ItemStack(Items.FISH, 1, ItemFishFood.FishType.COD.getMetadata()));
        register("filletFish", new ItemStack(Items.FISH, 1, ItemFishFood.FishType.SALMON.getMetadata()));
        register("filletFish", new ItemStack(Items.FISH, 1, ItemFishFood.FishType.CLOWNFISH.getMetadata()));

        for (MeatType meatType : MeatType.values()) {
            register("food" + meatType.getMeatName() + "Raw", new ItemStack(ModItems.MEAT, 1, meatType.getMetadata()));
            register("food" + meatType.getMeatName() + "Cooked", new ItemStack(ModItems.COOKED_MEAT, 1, meatType.getMetadata()));
        }

        register("foodRibsRaw", new ItemStack(ModItems.MEAT, 1, MeatType.RIBS_BEEF.getMetadata()));
        register("foodRibsPorkRaw", new ItemStack(ModItems.MEAT, 1, MeatType.RIBS.getMetadata()));

        //Minced Meat recipe
        register(MINCED_MEAT, new ItemStack(Items.BEEF));
        register(MINCED_MEAT, new ItemStack(Items.MUTTON));
        register(MINCED_MEAT, new ItemStack(Items.PORKCHOP));
        register(MINCED_MEAT, new ItemStack(Items.RABBIT));
        register(MINCED_MEAT, new ItemStack(ModItems.MEAT, 1, MeatType.HAM.getMetadata()));
        register(MINCED_MEAT, new ItemStack(ModItems.MEAT, 1, MeatType.LAMB.getMetadata()));
        register(MINCED_MEAT, new ItemStack(ModItems.MEAT, 1, MeatType.ROAST.getMetadata()));
        register(MINCED_MEAT, new ItemStack(ModItems.MEAT, 1, MeatType.VEAL.getMetadata()));*/
    }

    private static void register(String oreName, ItemStack stack) {
        OreDictionary.registerOre(StringUtils.toCamelCase(oreName), stack);
    }
}