package com.Girafi.culinarycultivation.init.recipes;

import com.Girafi.culinarycultivation.api.CulinaryCultivationAPI;
import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemCropSeeds.SeedType;
import com.Girafi.culinarycultivation.item.ItemModFishFood.FishType;
import com.Girafi.culinarycultivation.item.ItemModMeatFood.MeatType;
import com.Girafi.culinarycultivation.item.ItemStorageJar.StorageJarType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import static com.Girafi.culinarycultivation.init.ModItems.*;

public class Recipes {
    public static void initHandlers() {
        CulinaryCultivationAPI.winnowing = WinnowingMachineRecipes.instance();
    }

    public static void init() {
        GameRegistry.addRecipe(new RecipesFarmerArmorDyes());
        //Tools, armor and other stuff
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CAKE_KNIFE), true, "  H", "II ", 'H', TOOL_HANDLE, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CANE_KNIFE), true, " II", " I ", " H ", 'H', TOOL_HANDLE, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(DIAMOND_HOE_LARGE), "###", " H ", "H  ", 'H', TOOL_HANDLE, '#', "gemDiamond"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FARMER_BOOTS), " B ", "LLL", 'B', Items.LEATHER_BOOTS, 'L', Items.LEATHER));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FARMER_OVERALLS), "S S", "#L#", 'S', Items.STRING, 'L', Items.LEATHER_LEGGINGS, '#', new ItemStack(Items.DYE, 1, 12)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FARMER_SHIRT), "# #", "###", "###", '#', new ItemStack(Blocks.WOOL, 1, 14)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(FARMER_STRAWHAT), "SHS", "W W", 'W', Items.WHEAT, 'H', Blocks.HAY_BLOCK, 'S', Items.STRING));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GOLDEN_HOE_LARGE), "###", " H ", "H  ", 'H', TOOL_HANDLE, '#', "ingotGold"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(IRON_HOE_LARGE), "###", " H ", "H  ", 'H', TOOL_HANDLE, '#', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(KITCHEN_KNIFE), true, "  I", " I ", "H  ", 'H', TOOL_HANDLE, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(MEAT_CLEAVER), true, " II", " II", "H  ", 'H', TOOL_HANDLE, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.STORAGE_JAR, 1, StorageJarType.EMPTY.getMetaData()), " S ", "P P", " P ", 'P', "paneGlass", 'S', "slabWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(STONE_HOE_LARGE), "###", " H ", "H  ", 'H', TOOL_HANDLE, '#', "cobblestone"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(TOOL_HANDLE), "S", "S", 'S', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(WOODEN_HOE_LARGE), "###", " H ", "H  ", 'H', TOOL_HANDLE, '#', "plankWood"));

        //Food
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.CAKE), " C ", "CCC", "CCC", 'C', ModItems.CAKE_PIECE));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(FISH, 1, FishType.FILLET.getMetaData()), new ItemStack(KITCHEN_KNIFE, 1, OreDictionary.WILDCARD_VALUE), "filletFish"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(MEAT, 1, MeatType.PATTY.getMetaData()), new ItemStack(MEAT_CLEAVER, 1, OreDictionary.WILDCARD_VALUE), "foodMincedMeat"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(MEAT, 3, MeatType.BACON.getMetaData()), new ItemStack(KITCHEN_KNIFE, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.PORKCHOP)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(MEAT, 2, MeatType.CHICKENNUGGET.getMetaData()), new ItemStack(KITCHEN_KNIFE, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.CHICKEN)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(MEAT, 3, MeatType.SQUIDRING.getMetaData()), new ItemStack(KITCHEN_KNIFE, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(MEAT, 1, MeatType.SQUIDMANTLE.getMetaData())));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(MEAT, 2, MeatType.SQUIDRING.getMetaData()), new ItemStack(KITCHEN_KNIFE, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(FISH, 1, FishType.SMALLSQUID.getMetaData())));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(CAKE_PIECE, 7), new ItemStack(CAKE_KNIFE, 1, OreDictionary.WILDCARD_VALUE), Items.CAKE));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(CHEESE_SLICE, 7), new ItemStack(KITCHEN_KNIFE, 1, OreDictionary.WILDCARD_VALUE), ModBlocks.CHEESE));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(STORAGE_JAR, 3, StorageJarType.RENNET.getMetaData()), new ItemStack(KITCHEN_KNIFE, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(CALF_BELLY), STORAGE_JAR, STORAGE_JAR, STORAGE_JAR, Items.WATER_BUCKET));

        //Winnowing recipes
        CulinaryCultivationAPI.winnowing.addRecipe(new ItemStack(Blocks.TALLGRASS), new ItemStack(ModItems.CROP_SEEDS, 1, SeedType.BLACKPEPPERDRUPE.getMetadata()), 100);

        //Furnace recipes
        GameRegistry.addSmelting(new ItemStack(Items.FISH, 1, 2), new ItemStack(COOKED_FISH, 1, FishType.CLOWNFISH.getMetaData()), 0.35F);
        GameRegistry.addSmelting(new ItemStack(MEAT, 1, MeatType.RIBSBEEF.getMetaData()), new ItemStack(COOKED_MEAT, 1, MeatType.RIBS.getMetaData()), 0.35F);

        for (FishType fishtype : FishType.values()) {
            if (fishtype.isHaveCookedFish() && fishtype.isHaveRawFish()) {
                GameRegistry.addSmelting(new ItemStack(FISH, 1, fishtype.getMetaData()), new ItemStack(COOKED_FISH, 1, fishtype.getMetaData()), 0.35F);
            }
        }

        for (MeatType meattype : MeatType.values()) {
            if (meattype.isHaveCookedMeat()) {
                GameRegistry.addSmelting(new ItemStack(MEAT, 1, meattype.getMetaData()), new ItemStack(COOKED_MEAT, 1, meattype.getMetaData()), 0.35F);
            }
        }
    }
}