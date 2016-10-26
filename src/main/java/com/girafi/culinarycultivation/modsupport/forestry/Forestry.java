package com.girafi.culinarycultivation.modsupport.forestry;

import com.girafi.culinarycultivation.block.BlockCrop;
import com.girafi.culinarycultivation.block.BlockDoubleCrop;
import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.item.ItemCropProduct;
import com.girafi.culinarycultivation.item.ItemModMeatFood;
import com.girafi.culinarycultivation.modsupport.IModSupport;
import forestry.api.farming.Farmables;
import forestry.api.storage.BackpackManager;
import forestry.farming.logic.FarmableAgingCrop;
import net.minecraft.item.ItemStack;

public class Forestry implements IModSupport {
    //TODO Add seed oil recipes for all types of seeds

    @Override
    public void preInit() {
        for (ItemCropProduct.ProductType productType : ItemCropProduct.ProductType.values()) { //TODO Check if this still works after moving to new crop drop/seed system
            if (productType.getCropBlock() instanceof BlockDoubleCrop) {
                Farmables.farmables.put("farmVegetables", new FarmableDoubleAgingCrop(new ItemStack(ModItems.CROP_FOOD, 1, productType.getMetadata()), productType.getCropBlock(), BlockDoubleCrop.AGE, 7));
            } else {
                Farmables.farmables.put("farmVegetables", new FarmableAgingCrop(new ItemStack(ModItems.CROP_FOOD, 1, productType.getMetadata()), productType.getCropBlock(), BlockCrop.AGE, 7));
            }
        }
    }

    @Override
    public void init() {
        for (ItemModMeatFood.MeatType meattype : ItemModMeatFood.MeatType.values()) {
            addToHunterBackpack(new ItemStack(ModItems.MEAT, 1, meattype.getMetadata()));
            addToHunterBackpack(new ItemStack(ModItems.COOKED_MEAT, 1, meattype.getMetadata()));
        }
        addToHunterBackpack(new ItemStack(ModItems.CALF_BELLY));
    }

    private void addToHunterBackpack(ItemStack stack) {
        addToBackpack(BackpackManager.HUNTER_UID, stack);
    }

    private void addToBackpack(String backpackUid, ItemStack stack) {
        BackpackManager.backpackInterface.addItemToForestryBackpack(backpackUid, stack);
    }
}