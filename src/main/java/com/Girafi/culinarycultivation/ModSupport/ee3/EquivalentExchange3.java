package com.Girafi.culinarycultivation.modSupport.ee3;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemModFishFood;
import com.Girafi.culinarycultivation.item.ItemModMeatFood;
import com.Girafi.culinarycultivation.modSupport.IModSupport;
import com.pahimar.ee3.api.exchange.EnergyValueRegistryProxy;
import net.minecraft.item.ItemStack;

public class EquivalentExchange3 implements IModSupport {

    private static final int BASIC_FOOD_VALUE = 24;

    @Override
    public void preInit() {
        ItemModFishFood.FishType[] fish = ItemModFishFood.FishType.values();
        int i = fish.length;
        for (int j = 0; j < i; ++j) {
            ItemModFishFood.FishType fishType = fish[j];
            EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.fish, 1, fishType.getMetaData()), BASIC_FOOD_VALUE);
            EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.cooked_fish, 1, fishType.getMetaData()), BASIC_FOOD_VALUE);
        }
        ItemModMeatFood.MeatType[] meat = ItemModMeatFood.MeatType.values();
        int imeat = meat.length;
        for (int j = 0; j < imeat; ++j) {
            ItemModMeatFood.MeatType meatType = meat[j];
            EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.meat, 1, meatType.getMetaData()), BASIC_FOOD_VALUE);
            EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.cooked_meat, 1, meatType.getMetaData()), BASIC_FOOD_VALUE);
        }

        EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.pieceOfCake), 61);
        EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.beetRaw), BASIC_FOOD_VALUE);
        EnergyValueRegistryProxy.addPreAssignedEnergyValue(new ItemStack(ModItems.blackPepperDrupe), BASIC_FOOD_VALUE);
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