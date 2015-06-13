package com.Girafi.culinarycultivation.modSupport.thaumcraft;

import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemModFishFood.*;
import com.Girafi.culinarycultivation.item.ItemModMeatFood.*;
import com.Girafi.culinarycultivation.item.ItemStorageJar.*;
import com.Girafi.culinarycultivation.modSupport.IModSupport;
import com.Girafi.culinarycultivation.reference.Reference;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.WandTriggerRegistry;

import static com.Girafi.culinarycultivation.init.ModItems.cooked_meat;
import static com.Girafi.culinarycultivation.init.ModItems.meat;

public class Thaumcraft implements IModSupport {
    @Override
    public void preInit() {
    }

    @Override
    public void init() {
        FishType[] fish = FishType.values();
        int i = fish.length;
        for (int j = 0; j < i; ++j) {
            FishType fishType = fish[j];
            ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.fish, 1, fishType.getMetaData()), new AspectList().add(Aspect.FLESH, 3).add(Aspect.LIFE, 1).add(Aspect.WATER, 1));
            ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.cooked_fish, 1, fishType.getMetaData()), new AspectList().add(Aspect.FLESH, 4).add(Aspect.HUNGER, 3).add(Aspect.CRAFT, 1));
        }

        StorageJarType[] jar = StorageJarType.values(); //TODO Add Rennet Jar aspects
        int iJar = jar.length;
        for (int j = 0; j < iJar; ++j) {
            StorageJarType jarType = jar[j];
            int meta = jarType.getMetaData();
            if (meta == StorageJarType.WATER.getMetaData()) {
                ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.storageJar, 1, StorageJarType.WATER.getMetaData()), new AspectList().add(Aspect.TREE, 1).add(Aspect.CRYSTAL, 1).add(Aspect.WATER, 1));
            } else if (meta == StorageJarType.MILK.getMetaData()) {
                ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.storageJar, 1, StorageJarType.MILK.getMetaData()), new AspectList().add(Aspect.TREE, 1).add(Aspect.CRYSTAL, 1).add(Aspect.HUNGER, 1).add(Aspect.HEAL, 1).add(Aspect.WATER, 1));
            } else {
                ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.storageJar, 1, jarType.getMetaData()), new AspectList().add(Aspect.TREE, 1).add(Aspect.CRYSTAL, 1));
            }
        }
        /*

        Raw chicken = 3 Corpus, 2 life, 1 bestia
        Cooked chicken = 4 Corpus, 3 fames, 1 fabrico
         */
        MeatType[] ameattype = MeatType.values(); //TODO Figure out to do with squid meat
        int imeat = ameattype.length;
        for (int j = 0; j < imeat; ++j) {
            MeatType meattype = ameattype[j];
            int getMeta = meattype.getMetaData();

            if (getMeta == meattype.LAMB.getMetaData() || getMeta == meattype.LEGSHEEP.getMetaData()) {
                ThaumcraftApi.registerObjectTag(new ItemStack(meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.BEAST, 2).add(Aspect.LIFE, 1).add(Aspect.EARTH, 1));
                ThaumcraftApi.registerObjectTag(new ItemStack(cooked_meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.HUNGER, 2).add(Aspect.CRAFT, 1).add(Aspect.BEAST, 1));
            } else if (getMeta == meattype.VEAL.getMetaData() || getMeta == meattype.ROAST.getMetaData() || getMeta == meattype.RIBSBEEF.getMetaData()) {
                ThaumcraftApi.registerObjectTag(new ItemStack(meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.FLESH, 4).add(Aspect.LIFE, 2).add(Aspect.BEAST, 1));
                ThaumcraftApi.registerObjectTag(new ItemStack(cooked_meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.FLESH, 4).add(Aspect.HUNGER, 2).add(Aspect.CRAFT, 1));
            } else if (getMeta == meattype.RIBS.getMetaData() || getMeta == meattype.HAM.getMetaData() || getMeta == meattype.BACON.getMetaData()) {
                ThaumcraftApi.registerObjectTag(new ItemStack(meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.FLESH, 3).add(Aspect.LIFE, 1).add(Aspect.BEAST, 1));
                ThaumcraftApi.registerObjectTag(new ItemStack(cooked_meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.FLESH, 3).add(Aspect.HUNGER, 3).add(Aspect.CRAFT, 1));
            } else if (getMeta == meattype.CHICKENWING.getMetaData() || getMeta == meattype.DRUMSTICK.getMetaData() || getMeta == meattype.CHICKENNUGGET.getMetaData()) {
                ThaumcraftApi.registerObjectTag(new ItemStack(meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.FLESH, 3).add(Aspect.LIFE, 1).add(Aspect.BEAST, 1));
                ThaumcraftApi.registerObjectTag(new ItemStack(cooked_meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.FLESH, 3).add(Aspect.HUNGER, 3).add(Aspect.CRAFT, 1));
            } else {
                ThaumcraftApi.registerObjectTag(new ItemStack(meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.FLESH, 3).add(Aspect.HUNGER, 1).add(Aspect.LIFE, 1));
                ThaumcraftApi.registerObjectTag(new ItemStack(cooked_meat, 1, meattype.getMetaData()), new AspectList().add(Aspect.FLESH, 3).add(Aspect.LIFE, 1).add(Aspect.CRAFT, 1));
            }
            ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.meat, 1, meattype.PATTY.getMetaData()), new AspectList().add(Aspect.FLESH, 3).add(Aspect.BEAST, 1).add(Aspect.HUNGER, 1));
            ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.cooked_meat, 1, meattype.PATTY.getMetaData()), new AspectList().add(Aspect.FLESH, 3).add(Aspect.LIFE, 1).add(Aspect.CRAFT, 2));
        }
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.mutton), new AspectList().add(Aspect.BEAST, 2).add(Aspect.LIFE, 1).add(Aspect.EARTH, 1));
        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.cookedMutton), new AspectList().add(Aspect.HUNGER, 2).add(Aspect.CRAFT, 1).add(Aspect.BEAST, 1));

        ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.toolHandle), new AspectList().add(Aspect.TREE, 2));
        //TODO Add cheese & crops aspects when done
    }

    @Override
    public void postInit() {
        WandTriggerRegistry.registerWandBlockTrigger(new CrucibleSupport(), 1, ModBlocks.cauldron, -1, Reference.MOD_ID);
    }

    @Override
    public void clientSide() {
    }

    @Override
    public void clientInit() {
    }
}