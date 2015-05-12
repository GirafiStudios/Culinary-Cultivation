package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.item.ItemModFishFood;
import com.Girafi.culinarycultivation.item.ItemModMeatFood;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.text.WordUtils;

public class OreDictionaryRegistration {

    public static void init() {

        ItemModFishFood.FishType[] fish = ItemModFishFood.FishType.values();
        int i = fish.length;
        for (int j = 0; j < i; ++j) {
            ItemModFishFood.FishType fishtype = fish[j];
                OreDictionary.registerOre("food" + WordUtils.capitalize(fishtype.getTextureName()) + "Raw", new ItemStack(ModItems.fish, 1, fishtype.getMetaData()));
                OreDictionary.registerOre("food" + WordUtils.capitalize(fishtype.getTextureName()) + "Cooked", new ItemStack(ModItems.cooked_fish, 1, fishtype.getMetaData()));
        }

        OreDictionary.registerOre("foodSquidRingCooked", ModItems.squidRingCooked);
        OreDictionary.registerOre("foodSquidRingRaw", ModItems.squidRing);
        OreDictionary.registerOre("foodSquidTentacleCooked", ModItems.squidTentacleCooked);
        OreDictionary.registerOre("foodSquidTentacleRaw", ModItems.squidTentacle);
        OreDictionary.registerOre("foodVealCooked", ModItems.cookedVeal);
        OreDictionary.registerOre("foodVealRaw", ModItems.veal);

        //Minced Meat recipe
        OreDictionary.registerOre("foodMincedMeat", Items.beef);
        OreDictionary.registerOre("foodMincedMeat", Items.porkchop);
        OreDictionary.registerOre("foodMincedMeat", ModItems.hamRaw);
        OreDictionary.registerOre("foodMincedMeat", ModItems.mutton);
        OreDictionary.registerOre("foodMincedMeat", ModItems.roastRaw);
        OreDictionary.registerOre("foodMincedMeat", ModItems.veal);
        OreDictionary.registerOre("foodMincedMeat", new ItemStack(ModItems.meat, ItemModMeatFood.MeatType.LAMB.getMetaData()));
    }
}