package com.Girafi.culinarycultivation.ModSupport.ee3;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemModFishFood;
import com.Girafi.culinarycultivation.item.ItemModMeatFood;
import com.Girafi.culinarycultivation.ModSupport.IModSupport;
import com.pahimar.ee3.api.exchange.EnergyValueRegistryProxy;
import net.minecraft.item.ItemStack;

public class EE3 implements IModSupport {

    private static final int BASIC_FOOD_VALUE = 24;

    @Override
    public void preInit() {
        ItemModFishFood.FishType[] fish = ItemModFishFood.FishType.values();
        int i = fish.length;
        for (int j = 0; j < i; ++j) {
            ItemModFishFood.FishType fishType = fish[j];
            EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.fish, 1, fishType.getMetaData()), BASIC_FOOD_VALUE);
            EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.cooked_fish, 1, fishType.getMetaData()), BASIC_FOOD_VALUE);
            EnergyValueRegistryProxy.addPreAssignedEnergyValue(ModItems.cookedClownfish, BASIC_FOOD_VALUE);
        }
        ItemModMeatFood.MeatType[] meat = ItemModMeatFood.MeatType.values();
        int imeat = fish.length;
        for (int j = 0; j < imeat; ++j) {
            ItemModMeatFood.MeatType meatType = meat[j];
            EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.meat, 1, meatType.getMetaData()), BASIC_FOOD_VALUE);
            EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.meat_cooked, 1, meatType.getMetaData()), BASIC_FOOD_VALUE);
        }
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