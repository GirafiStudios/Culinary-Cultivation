package com.girafi.culinarycultivation.modsupport.forestry;

import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.item.ItemModMeatFood;
import com.girafi.culinarycultivation.modsupport.IModSupport;
import forestry.api.storage.BackpackManager;
import net.minecraft.item.ItemStack;

public class Forestry implements IModSupport {
    @Override
    public void preInit() {
    }

    @Override
    public void init() {
        for (ItemModMeatFood.MeatType meattype : ItemModMeatFood.MeatType.values()) {
            addToHunterBackpack(new ItemStack(ModItems.MEAT, 1, meattype.getMetaData()));
            addToHunterBackpack(new ItemStack(ModItems.COOKED_MEAT, 1, meattype.getMetaData()));
        }
        addToHunterBackpack(new ItemStack(ModItems.CALF_BELLY));
    }

    @Override
    public void postInit() {
    }

    @Override
    public void clientSide() {
    }

    private void addToHunterBackpack(ItemStack stack) {
        addToBackpack(BackpackManager.HUNTER_UID, stack);
    }

    private void addToBackpack(String backpackUID, ItemStack stack) {
        BackpackManager.backpackInterface.getBackpack(backpackUID).addValidItem(stack);
    }
}