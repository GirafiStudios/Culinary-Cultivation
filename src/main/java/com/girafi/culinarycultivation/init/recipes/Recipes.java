package com.girafi.culinarycultivation.init.recipes;

import com.girafi.culinarycultivation.api.CulinaryCultivationAPI;
import com.girafi.culinarycultivation.api.crafting.IWinnowingMachineHandler;
import com.girafi.culinarycultivation.item.ItemModFishFood.FishType;
import com.girafi.culinarycultivation.item.ItemModMeatFood.MeatType;
import com.girafi.culinarycultivation.util.FuelHandler;
import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nonnull;

import static com.girafi.culinarycultivation.init.ModBlocks.*;
import static com.girafi.culinarycultivation.init.ModItems.*;
import static com.girafi.culinarycultivation.item.ItemCropProduct.ProductType;
import static net.minecraftforge.oredict.RecipeSorter.Category.SHAPELESS;

public class Recipes {
    public static void initHandlers() {
        CulinaryCultivationAPI.winnowing = WinnowingMachineRecipes.instance();
        GameRegistry.addRecipe(new RecipesFarmerArmorDyes());
        RecipeSorter.register(Reference.MOD_ID + ":farmerarmordyes", RecipesFarmerArmorDyes.class, SHAPELESS, "after:minecraft:shapeless");
        GameRegistry.registerFuelHandler(new FuelHandler());
    }

    public static void init() {
        addRecipes();
        addFurnaceRecipes();
        addWinnowingRecipes();
    }

    private static void addRecipes() {
        //Machines
        addShaped(FAN_HOUSING, "PPP", "PRD", "I  ", 'P', "plankWood", 'R', "blockRedstone", 'D', Blocks.DISPENSER, 'I', "ingotIron");
        addShaped(SEPARATOR, "PHP", "PBF", "  I", 'H', Blocks.HOPPER, 'P', "plankWood", 'B', Items.BOWL, 'F', Blocks.IRON_BARS, 'I', "ingotIron");
        addShaped(CAULDRON, "   ", "ICI", " I ", 'C', Items.CAULDRON, 'I', "nuggetIron");
        addShaped(SEED_BAG, " S ", "CXC", " C ", 'C', new ItemStack(Blocks.CARPET, 1, OreDictionary.WILDCARD_VALUE), 'S', "string", 'X', new ItemStack(CROP_SEEDS, 1, OreDictionary.WILDCARD_VALUE));

        //Tools, armor and other stuff
        addShaped(CAKE_KNIFE, "  H", "II ", 'H', TOOL_HANDLE, 'I', "ingotIron");
        addShaped(CANE_KNIFE, " II", " I ", " H ", 'H', TOOL_HANDLE, 'I', "ingotIron");
        addShaped(FARMER_BOOTS, " B ", "LLL", 'B', Items.LEATHER_BOOTS, 'L', Items.LEATHER);
        addShaped(FARMER_OVERALLS, "S S", "#L#", 'S', "string", 'L', Items.LEATHER_LEGGINGS, '#', "dyeLightBlue");
        addShaped(FARMER_SHIRT, "# #", "###", "###", '#', new ItemStack(Blocks.WOOL, 1, 14));
        addShaped(FARMER_STRAWHAT, "SHS", "W W", 'W', "cropWheat", 'H', Blocks.HAY_BLOCK, 'S', "string");
        addShaped(HOE_LARGE_DIAMOND, "###", " H ", "H  ", 'H', TOOL_HANDLE, '#', "gemDiamond");
        addShaped(HOE_LARGE_GOLDEN, "###", " H ", "H  ", 'H', TOOL_HANDLE, '#', "ingotGold");
        addShaped(HOE_LARGE_IRON, "###", " H ", "H  ", 'H', TOOL_HANDLE, '#', "ingotIron");
        addShaped(HOE_LARGE_STONE, "###", " H ", "H  ", 'H', TOOL_HANDLE, '#', "cobblestone");
        addShaped(HOE_LARGE_WOODEN, "###", " H ", "H  ", 'H', TOOL_HANDLE, '#', "plankWood");
        addShaped(KITCHEN_KNIFE, "  I", " I ", "H  ", 'H', TOOL_HANDLE, 'I', "ingotIron");
        addShaped(MEAT_CLEAVER, " II", " II", "H  ", 'H', TOOL_HANDLE, 'I', "ingotIron");
        addShaped(STORAGE_JAR, " S ", "P P", " P ", 'P', "paneGlass", 'S', "slabWood");
        addShaped(TOOL_HANDLE, "S", "S", 'S', "stickWood");

        //Food
        addShaped(Items.CAKE, " C ", "CCC", "CCC", 'C', CAKE_PIECE);
        addShapeless(new ItemStack(FISH, 1, FishType.FILLET.getMetadata()), new ItemStack(KITCHEN_KNIFE, 1, OreDictionary.WILDCARD_VALUE), "filletFish");
        addShapeless(new ItemStack(MEAT, 1, MeatType.PATTY.getMetadata()), new ItemStack(MEAT_CLEAVER, 1, OreDictionary.WILDCARD_VALUE), "foodMincedMeat");
        addShapeless(new ItemStack(MEAT, 3, MeatType.BACON.getMetadata()), new ItemStack(KITCHEN_KNIFE, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.PORKCHOP));
        addShapeless(new ItemStack(MEAT, 2, MeatType.CHICKEN_NUGGET.getMetadata()), new ItemStack(KITCHEN_KNIFE, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.CHICKEN));
        addShapeless(new ItemStack(MEAT, 3, MeatType.SQUID_RING.getMetadata()), new ItemStack(KITCHEN_KNIFE, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(MEAT, 1, MeatType.SQUID_MANTLE.getMetadata()));
        addShapeless(new ItemStack(MEAT, 2, MeatType.SQUID_RING.getMetadata()), new ItemStack(KITCHEN_KNIFE, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(FISH, 1, FishType.SMALL_SQUID.getMetadata()));
        addShapeless(new ItemStack(CAKE_PIECE, 7), new ItemStack(CAKE_KNIFE, 1, OreDictionary.WILDCARD_VALUE), Items.CAKE);
        addShapeless(new ItemStack(CHEESE_SLICE, 7), new ItemStack(KITCHEN_KNIFE, 1, OreDictionary.WILDCARD_VALUE), CHEESE);
        //addShapeless(STORAGE_JAR, 3, StorageJarType.RENNET.getMetaData()), new ItemStack(KITCHEN_KNIFE, 1, OreDictionary.WILDCARD_VALUE), CALF_BELLY, STORAGE_JAR, STORAGE_JAR, STORAGE_JAR, Items.WATER_BUCKET));

        //Crop -> Seed recipes
        for (ProductType productType : ProductType.values()) {
            if (productType.hasCrop()) {
                addShapeless(new ItemStack(CROP_SEEDS, 1, productType.getMetadata()), new ItemStack(CROP_FOOD, 1, productType.getMetadata()));
            }
        }
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
        IWinnowingMachineHandler winnowing = CulinaryCultivationAPI.winnowing;

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
        winnowing.addRecipe(new ItemStack(Items.WHEAT), new ItemStack(Items.WHEAT_SEEDS), 15, new ItemStack(CHAFF_PILE), 90);
    }

    private static void addShaped(Block result, Object... recipe) {
        addShaped(new ItemStack(result), recipe);
    }

    private static void addShaped(Item result, Object... recipe) {
        addShaped(new ItemStack(result), recipe);
    }

    private static void addShaped(@Nonnull ItemStack result, Object... recipe) {
        GameRegistry.addRecipe(new ShapedOreRecipe(result, recipe));
    }

    private static void addShapeless(@Nonnull ItemStack result, Object... recipe) {
        GameRegistry.addRecipe(new ShapelessOreRecipe(result, recipe));
    }
}