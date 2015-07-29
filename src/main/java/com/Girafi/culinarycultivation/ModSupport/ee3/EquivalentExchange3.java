package com.Girafi.culinarycultivation.modSupport.ee3;

import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemModFishFood.*;
import com.Girafi.culinarycultivation.item.ItemModMeatFood.*;
import com.Girafi.culinarycultivation.item.ItemStorageJar.*;
import com.Girafi.culinarycultivation.modSupport.IModSupport;
import com.pahimar.ee3.api.exchange.EnergyValueRegistryProxy;
import net.minecraft.item.ItemStack;

public class EquivalentExchange3 implements IModSupport {

    private static final int BASIC_FOOD_VALUE = 24;
    private static final int SLICE_VALUE = 61;

    @Override
    public void preInit() {
        FishType[] fish = FishType.values();
        int i = fish.length;
        for (int j = 0; j < i; ++j) {
            FishType fishType = fish[j];
            EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.fish, 1, fishType.getMetaData()), BASIC_FOOD_VALUE);
            EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.cooked_fish, 1, fishType.getMetaData()), BASIC_FOOD_VALUE);
        }
        MeatType[] meat = MeatType.values();
        int imeat = meat.length;
        for (int j = 0; j < imeat; ++j) {
            MeatType meatType = meat[j];
            int meta = meatType.getMetaData();
            if (meta == MeatType.CHICKENNUGGET.getMetaData() || meta == MeatType.DRUMSTICK.getMetaData()) {
                EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.meat, 1, MeatType.CHICKENNUGGET.getMetaData()), 4.8F);
                EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.cooked_meat, 1, MeatType.CHICKENNUGGET.getMetaData()), 4.8F);
                EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.meat, 1, MeatType.DRUMSTICK.getMetaData()), 4.8F);
                EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.cooked_meat, 1, MeatType.DRUMSTICK.getMetaData()), 4.8F);
            } else {
                EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.meat, 1, meatType.getMetaData()), BASIC_FOOD_VALUE);
                EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.cooked_meat, 1, meatType.getMetaData()), BASIC_FOOD_VALUE);
            }
        }
        StorageJarType[] jar = StorageJarType.values();
        int ijar = jar.length;
        for (int j = 0; j < ijar; ++j) {
            StorageJarType jarType = jar[j];
            if (jarType.getMetaData() == StorageJarType.MILK.getMetaData()) {
                EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.storageJar, 1, jarType.getMetaData()), 1);
            } else {
                EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.storageJar, 1, jarType.getMetaData()), 2);
            }
        }
        EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.pieceOfCake), SLICE_VALUE);
        EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModBlocks.cheese), 360);
        EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.cheeseSlice), SLICE_VALUE);
        EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.mutton), BASIC_FOOD_VALUE);
        EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.cookedMutton), BASIC_FOOD_VALUE);
    }


    @Override
    public void init(){ }

    @Override
    public void postInit(){ }

    @Override
    public void clientSide(){ }

    @Override
    public void clientInit(){ }
}