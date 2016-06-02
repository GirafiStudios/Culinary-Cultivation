package com.girafi.culinarycultivation.modsupport.forestry;

import com.girafi.culinarycultivation.block.BlockCrop;
import com.girafi.culinarycultivation.block.BlockDoubleCrop;
import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.item.ItemCropFood;
import com.girafi.culinarycultivation.item.ItemCropSeeds;
import com.girafi.culinarycultivation.item.ItemModMeatFood;
import com.girafi.culinarycultivation.modsupport.IModSupport;
import forestry.api.farming.Farmables;
import forestry.api.storage.BackpackManager;
import forestry.farming.logic.FarmableAgingCrop;
import net.minecraft.item.ItemStack;

public class Forestry implements IModSupport {
    @Override
    public void preInit() {
        for (ItemCropFood.CropType cropType : ItemCropFood.CropType.values()) {
            if (cropType.getCropBlock() instanceof BlockDoubleCrop) {
                Farmables.farmables.put("farmVegetables", new FarmableDoubleAgingCrop(new ItemStack(ModItems.CROP_FOOD, 1, cropType.getMetadata()), cropType.getCropBlock(), BlockDoubleCrop.AGE, 7));
            } else {
                Farmables.farmables.put("farmVegetables", new FarmableAgingCrop(new ItemStack(ModItems.CROP_FOOD, 1, cropType.getMetadata()), cropType.getCropBlock(), BlockCrop.AGE, 7));
            }
        }
        for (ItemCropSeeds.SeedType seedType : ItemCropSeeds.SeedType.values()) {
            if (seedType.getCropBlock() instanceof BlockDoubleCrop) {
                Farmables.farmables.put("farmVegetables", new FarmableDoubleAgingCrop(new ItemStack(ModItems.CROP_SEEDS, 1, seedType.getMetadata()), seedType.getCropBlock(), BlockDoubleCrop.AGE, 7));
            } else {
                Farmables.farmables.put("farmVegetables", new FarmableAgingCrop(new ItemStack(ModItems.CROP_SEEDS, 1, seedType.getMetadata()), seedType.getCropBlock(), BlockCrop.AGE, 7));
            }
        }
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