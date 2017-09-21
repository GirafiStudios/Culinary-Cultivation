package com.girafi.culinarycultivation.modsupport.forestry;

import com.girafi.culinarycultivation.block.BlockCrop;
import com.girafi.culinarycultivation.block.BlockDoubleCrop;
import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.item.ItemCropProduct;
import com.girafi.culinarycultivation.item.ItemGeneral;
import com.girafi.culinarycultivation.item.ItemModMeatFood;
import com.girafi.culinarycultivation.modsupport.IModSupport;
import forestry.api.core.ForestryAPI;
import forestry.api.farming.IFarmRegistry;
import forestry.api.recipes.RecipeManagers;
import forestry.api.storage.BackpackManager;
import forestry.core.fluids.Fluids;
import forestry.farming.FarmRegistry;
import forestry.farming.logic.FarmableAgingCrop;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class Forestry implements IModSupport {

    @Override
    public void preInit() {
        IFarmRegistry farmRegistry = FarmRegistry.getInstance();

        for (ItemCropProduct.ProductType productType : ItemCropProduct.ProductType.values()) {
            ItemStack seed = new ItemStack(ModItems.CROP_SEEDS, 1, productType.getMetadata());
            ItemStack crop = new ItemStack(ModItems.CROP_FOOD, 1, productType.getMetadata());
            ItemStack stack = productType.canPlantCrop() ? crop : seed;
            String identifier = productType.canPlantCrop() ? "farmVegetables" : "farmWheat";

            if (productType.getCropBlock() instanceof BlockDoubleCrop) {
                IBlockState planted = productType.getCropBlock().getDefaultState();
                IBlockState mature = planted.withProperty(BlockDoubleCrop.AGE, 14);
                farmRegistry.registerFarmables(identifier, new FarmableDoubleAgingCrop(seed, planted, mature)); //Make sure the seed can always be planted
                farmRegistry.registerFarmables(identifier, new FarmableDoubleAgingCrop(stack, planted, mature));
            } else {
                farmRegistry.registerFarmables(identifier, new FarmableAgingCrop(seed, productType.getCropBlock(), BlockCrop.AGE, 7)); //Make sure the seed can always be planted
                farmRegistry.registerFarmables(identifier, new FarmableAgingCrop(stack, productType.getCropBlock(), BlockCrop.AGE, 7));
            }
        }
    }

    @Override
    public void init() {
        for (ItemCropProduct.ProductType product : ItemCropProduct.ProductType.values()) {
            int amount = ForestryAPI.activeMode.getIntegerSetting("squeezer.liquid.seed");
            RecipeManagers.squeezerManager.addRecipe(10, new ItemStack(ModItems.CROP_SEEDS, 1, product.getMetadata()), Fluids.SEED_OIL.getFluid(amount));
        }

        for (ItemModMeatFood.MeatType type : ItemModMeatFood.MeatType.values()) {
            addToHunterBackpack(new ItemStack(ModItems.MEAT, 1, type.getMetadata()));
            addToHunterBackpack(new ItemStack(ModItems.COOKED_MEAT, 1, type.getMetadata()));
        }
        addToHunterBackpack(new ItemStack(ModItems.GENERAL, 1, ItemGeneral.Type.CALF_BELLY.getMetadata()));
    }

    private void addToHunterBackpack(ItemStack stack) {
        addToBackpack(BackpackManager.HUNTER_UID, stack);
    }

    private void addToBackpack(String backpackUid, ItemStack stack) {
        BackpackManager.backpackInterface.addItemToForestryBackpack(backpackUid, stack);
    }
}