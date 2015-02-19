package com.Girafi.culinarycultivation.init;

import cpw.mods.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryRegistration {

    public static void init() {

        OreDictionary.registerOre("foodLambCooked", ModItems.cookedLamb);
        OreDictionary.registerOre("foodLambRaw", ModItems.lamb);
        OreDictionary.registerOre("foodVealCooked", ModItems.cookedVeal);
        OreDictionary.registerOre("foodVealRaw", ModItems.veal);

        //Mutton
        if (Loader.isModLoaded("harvestcraft")) {
            OreDictionary.registerOre("foodMuttonraw", ModItems.mutton);
            OreDictionary.registerOre("foodMuttoncooked", ModItems.cookedMutton);
            OreDictionary.registerOre("listAllmuttonraw", ModItems.mutton);
            OreDictionary.registerOre("listAllmuttoncooked", ModItems.cookedMutton);
        }
        else {
            OreDictionary.registerOre("foodMuttonraw", ModItems.mutton);
            OreDictionary.registerOre("foodMuttoncooked", ModItems.cookedMutton);
        }

    }
}