package com.Girafi.culinarycultivation.init;

import cpw.mods.fml.common.Loader;
import net.minecraft.init.Items;
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

        //Temporary Patty recipe fix
        OreDictionary.registerOre("foodMincedMeat", Items.beef);
        OreDictionary.registerOre("foodMincedMeat", Items.chicken);
        OreDictionary.registerOre("foodMincedMeat", Items.porkchop);
        OreDictionary.registerOre("foodMincedMeat", ModItems.hamRaw);
        OreDictionary.registerOre("foodMincedMeat", ModItems.lamb);
        OreDictionary.registerOre("foodMincedMeat", ModItems.legSheepRaw);
        OreDictionary.registerOre("foodMincedMeat", ModItems.mutton);
        OreDictionary.registerOre("foodMincedMeat", ModItems.ribsBeefRaw);
        OreDictionary.registerOre("foodMincedMeat", ModItems.ribsPorkRaw);
        OreDictionary.registerOre("foodMincedMeat", ModItems.roastRaw);
        OreDictionary.registerOre("foodMincedMeat", ModItems.veal);

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