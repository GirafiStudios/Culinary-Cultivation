package com.girafi.culinarycultivation.init.recipes;

import com.girafi.culinarycultivation.api.CulinaryCultivationAPI;
import com.girafi.culinarycultivation.init.ModBlocks;
import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.item.ItemModFishFood.FishType;
import com.girafi.culinarycultivation.item.ItemModMeatFood.MeatType;
import com.girafi.culinarycultivation.item.ItemStorageJar.StorageJarType;
import com.girafi.culinarycultivation.util.FuelHandler;
import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

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
        //Machine recipes
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.FAN_HOUSING), "PPP", "PRD", "I  ", 'P', "plankWood", 'R', "blockRedstone", 'D', Blocks.DISPENSER, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.SEPARATOR), "PHP", "PBF", "  I", 'H', Blocks.HOPPER, 'P', "plankWood", 'B', Items.BOWL, 'F', Blocks.IRON_BARS, 'I', "ingotIron"));
        //Tools, armor and other stuff
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CAKE_KNIFE), "  H", "II ", 'H', TOOL_HANDLE, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CANE_KNIFE), " II", " I ", " H ", 'H', TOOL_HANDLE, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(DIAMOND_HOE_LARGE), "###", " H ", "H  ", 'H', TOOL_HANDLE, '#', "gemDiamond"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FARMER_BOOTS), " B ", "LLL", 'B', Items.LEATHER_BOOTS, 'L', Items.LEATHER));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FARMER_OVERALLS), "S S", "#L#", 'S', Items.STRING, 'L', Items.LEATHER_LEGGINGS, '#', "dyeLightBlue"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FARMER_SHIRT), "# #", "###", "###", '#', new ItemStack(Blocks.WOOL, 1, 14)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FARMER_STRAWHAT), "SHS", "W W", 'W', Items.WHEAT, 'H', Blocks.HAY_BLOCK, 'S', Items.STRING));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOLDEN_HOE_LARGE), "###", " H ", "H  ", 'H', TOOL_HANDLE, '#', "ingotGold"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(IRON_HOE_LARGE), "###", " H ", "H  ", 'H', TOOL_HANDLE, '#', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(KITCHEN_KNIFE), "  I", " I ", "H  ", 'H', TOOL_HANDLE, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MEAT_CLEAVER), " II", " II", "H  ", 'H', TOOL_HANDLE, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.STORAGE_JAR, 1, StorageJarType.EMPTY.getMetaData()), " S ", "P P", " P ", 'P', "paneGlass", 'S', "slabWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(STONE_HOE_LARGE), "###", " H ", "H  ", 'H', TOOL_HANDLE, '#', "cobblestone"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TOOL_HANDLE), "S", "S", 'S', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(WOODEN_HOE_LARGE), "###", " H ", "H  ", 'H', TOOL_HANDLE, '#', "plankWood"));

        //Food
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.CAKE), " C ", "CCC", "CCC", 'C', CAKE_PIECE));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(FISH, 1, FishType.FILLET.getMetadata()), new ItemStack(KITCHEN_KNIFE, 1, OreDictionary.WILDCARD_VALUE), "filletFish"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(MEAT, 1, MeatType.PATTY.getMetadata()), new ItemStack(MEAT_CLEAVER, 1, OreDictionary.WILDCARD_VALUE), "foodMincedMeat"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(MEAT, 3, MeatType.BACON.getMetadata()), new ItemStack(KITCHEN_KNIFE, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.PORKCHOP)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(MEAT, 2, MeatType.CHICKEN_NUGGET.getMetadata()), new ItemStack(KITCHEN_KNIFE, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.CHICKEN)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(MEAT, 3, MeatType.SQUID_RING.getMetadata()), new ItemStack(KITCHEN_KNIFE, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(MEAT, 1, MeatType.SQUID_MANTLE.getMetadata())));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(MEAT, 2, MeatType.SQUID_RING.getMetadata()), new ItemStack(KITCHEN_KNIFE, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(FISH, 1, FishType.SMALL_SQUID.getMetadata())));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(CAKE_PIECE, 7), new ItemStack(CAKE_KNIFE, 1, OreDictionary.WILDCARD_VALUE), Items.CAKE));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(CHEESE_SLICE, 7), new ItemStack(KITCHEN_KNIFE, 1, OreDictionary.WILDCARD_VALUE), ModBlocks.CHEESE));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(STORAGE_JAR, 3, StorageJarType.RENNET.getMetaData()), new ItemStack(KITCHEN_KNIFE, 1, OreDictionary.WILDCARD_VALUE), CALF_BELLY, STORAGE_JAR, STORAGE_JAR, STORAGE_JAR, Items.WATER_BUCKET));

        //Winnowing recipes
        CulinaryCultivationAPI.winnowing.addRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, BlockDoublePlant.EnumPlantType.GRASS.getMeta()), new ItemStack(ModItems.CROP_SEEDS, 1, ProductType.BLACK_PEPPER_DRUPE.getMetadata()), 25);

        CulinaryCultivationAPI.winnowing.addRecipe(new ItemStack(Blocks.TALLGRASS, 1, BlockTallGrass.EnumType.GRASS.getMeta()), new ItemStack(Items.WHEAT_SEEDS), 10);
        CulinaryCultivationAPI.winnowing.addRecipe(new ItemStack(Blocks.TALLGRASS, 1, BlockTallGrass.EnumType.GRASS.getMeta()), new ItemStack(Items.BEETROOT_SEEDS), 2);
        CulinaryCultivationAPI.winnowing.addRecipe(new ItemStack(Blocks.TALLGRASS, 1, BlockTallGrass.EnumType.GRASS.getMeta()), new ItemStack(Items.PUMPKIN_SEEDS), 1);
        CulinaryCultivationAPI.winnowing.addRecipe(new ItemStack(Blocks.SAPLING, 1, BlockPlanks.EnumType.JUNGLE.getMetadata()), new ItemStack(Items.MELON_SEEDS), 1, new ItemStack(Blocks.DEADBUSH), 10);
        CulinaryCultivationAPI.winnowing.addRecipe(new ItemStack(Items.WHEAT), new ItemStack(Items.WHEAT_SEEDS), 15, new ItemStack(ModItems.CHAFF_PILE), 90);

        //Furnace recipes
        GameRegistry.addSmelting(new ItemStack(Items.FISH, 1, 2), new ItemStack(COOKED_FISH, 1, FishType.CLOWNFISH.getMetadata()), 0.35F);
        GameRegistry.addSmelting(new ItemStack(MEAT, 1, MeatType.RIBS_BEEF.getMetadata()), new ItemStack(COOKED_MEAT, 1, MeatType.RIBS.getMetadata()), 0.35F);

        for (FishType fishtype : FishType.values()) {
            if (fishtype.isHaveCookedFish() && fishtype.isHaveRawFish()) {
                GameRegistry.addSmelting(new ItemStack(FISH, 1, fishtype.getMetadata()), new ItemStack(COOKED_FISH, 1, fishtype.getMetadata()), 0.35F);
            }
        }

        for (MeatType meattype : MeatType.values()) {
            if (meattype.isHaveCookedMeat()) {
                GameRegistry.addSmelting(new ItemStack(MEAT, 1, meattype.getMetadata()), new ItemStack(COOKED_MEAT, 1, meattype.getMetadata()), 0.35F);
            }
        }
    }
}