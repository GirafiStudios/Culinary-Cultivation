package com.girafi.culinarycultivation.init;

import com.girafi.culinarycultivation.crafting.RecipesFarmerArmorDyes;
import com.girafi.culinarycultivation.item.ItemModFishFood.FishType;
import com.girafi.culinarycultivation.item.ItemModMeatFood.MeatType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static com.girafi.culinarycultivation.init.ModItems.*;
import static com.girafi.culinarycultivation.item.ItemCropProduct.ProductType;

public class Recipes {
    public static void initHandlers() {
        ForgeRegistries.RECIPES.register(new RecipesFarmerArmorDyes());
    }

    public static void init() {
        addFurnaceRecipes();
        //addWinnowingRecipes();
    }

    private static void addFurnaceRecipes() {
        GameRegistry.addSmelting(new ItemStack(Items.FISH, 1, 2), new ItemStack(COOKED_FISH, 1, FishType.CLOWNFISH.getMetadata()), 0.35F);
        GameRegistry.addSmelting(new ItemStack(MEAT, 1, MeatType.RIBS_BEEF.getMetadata()), new ItemStack(COOKED_MEAT, 1, MeatType.RIBS.getMetadata()), 0.35F);

        for (FishType fishType : FishType.values()) {
            if (fishType.isHaveCookedFish() && fishType.isHaveRawFish()) {
                GameRegistry.addSmelting(new ItemStack(FISH, 1, fishType.getMetadata()), new ItemStack(COOKED_FISH, 1, fishType.getMetadata()), 0.35F);
            }
        }

        for (MeatType meatType : MeatType.values()) {
            if (meatType.isHaveCookedMeat()) {
                GameRegistry.addSmelting(new ItemStack(MEAT, 1, meatType.getMetadata()), new ItemStack(COOKED_MEAT, 1, meatType.getMetadata()), 0.35F);
            }
        }

        for (ProductType productType : ProductType.values()) {
            if (productType.hasCookedCrop()) {
                GameRegistry.addSmelting(new ItemStack(CROP_FOOD, 1, productType.getMetadata()), new ItemStack(CROP_COOKED, 1, productType.getMetadata()), 0.35F);
            }
        }
    }

    private static void addWinnowingRecipes() {
        /*IWinnowingMachineHandler winnowing = CulinaryCultivationAPI.winnowing;

        ItemStack tallGrass = new ItemStack(Blocks.TALLGRASS, 1, BlockTallGrass.EnumType.GRASS.getMeta());
        ItemStack doubleTallGrass = new ItemStack(Blocks.DOUBLE_PLANT, 1, BlockDoublePlant.EnumPlantType.GRASS.getMeta());

        //Culinary Cultivation outputs
        winnowing.addOutput(tallGrass, new ItemStack(CROP_SEEDS, 1, ProductType.CUCUMBER.getMetadata()), 10);
        winnowing.addOutput(tallGrass, new ItemStack(CROP_SEEDS, 1, ProductType.TOMATO.getMetadata()), 8);
        winnowing.addJunk(tallGrass, new ItemStack(CHAFF_PILE), 10);
        winnowing.addRecipe(doubleTallGrass, new ItemStack(CROP_SEEDS, 1, ProductType.BLACK_PEPPER_DRUPE.getMetadata()), 18);
        winnowing.addRecipe(doubleTallGrass, new ItemStack(CROP_SEEDS, 1, ProductType.CORN.getMetadata()), 8);

        //Vanilla outputs
        winnowing.addOutput(tallGrass, new ItemStack(Items.WHEAT_SEEDS), 10);
        winnowing.addOutput(tallGrass, new ItemStack(Items.BEETROOT_SEEDS), 2);
        winnowing.addOutput(tallGrass, new ItemStack(Items.PUMPKIN_SEEDS), 1);
        winnowing.addRecipe(new ItemStack(Blocks.SAPLING, 1, BlockPlanks.EnumType.JUNGLE.getMetadata()), new ItemStack(Items.MELON_SEEDS), 1, new ItemStack(Blocks.DEADBUSH), 10);
        winnowing.addRecipe(new ItemStack(Items.WHEAT), new ItemStack(Items.WHEAT_SEEDS), 15, new ItemStack(CHAFF_PILE), 90);*/
    }
}