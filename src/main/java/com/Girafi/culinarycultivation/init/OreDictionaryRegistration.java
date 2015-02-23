package com.Girafi.culinarycultivation.init;

import cpw.mods.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryRegistration {

    public static void init() {

        OreDictionary.registerOre("foodClownfishCooked", ModItems.cookedClownfish);
        OreDictionary.registerOre("foodLambCooked", ModItems.cookedLamb);
        OreDictionary.registerOre("foodLambRaw", ModItems.lamb);
        OreDictionary.registerOre("foodSquidRingCooked", ModItems.squidRingCooked);
        OreDictionary.registerOre("foodSquidRingRaw", ModItems.squidRing);
        OreDictionary.registerOre("foodSquidTentacleCooked", ModItems.squidTentacleCooked);
        OreDictionary.registerOre("foodSquidTentacleRaw", ModItems.squidTentacle);
        OreDictionary.registerOre("foodVealCooked", ModItems.cookedVeal);
        OreDictionary.registerOre("foodVealRaw", ModItems.veal);

        //Harvestcraft Support
        if (Loader.isModLoaded("harvestcraft")) {
            OreDictionary.registerOre("foodMuttonraw", ModItems.mutton);
            OreDictionary.registerOre("foodMuttoncooked", ModItems.cookedMutton);
            OreDictionary.registerOre("listAllmuttonraw", ModItems.mutton);
            OreDictionary.registerOre("listAllmuttoncooked", ModItems.cookedMutton);
            OreDictionary.registerOre("foodCalamariraw", ModItems.squidMantle);
            OreDictionary.registerOre("foodCalamaricooked", ModItems.squidMantleCooked);
        }
        else {
            OreDictionary.registerOre("foodMuttonRaw", ModItems.mutton);
            OreDictionary.registerOre("foodMuttonCooked", ModItems.cookedMutton);
            OreDictionary.registerOre("foodSquidMantleRaw", ModItems.squidMantle);
            OreDictionary.registerOre("foodSquidMantleCooked", ModItems.squidMantleCooked);
        }

    }
}