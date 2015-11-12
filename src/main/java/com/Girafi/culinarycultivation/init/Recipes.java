package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.item.ItemModFishFood.FishType;
import com.Girafi.culinarycultivation.item.ItemModMeatFood.MeatType;
import com.Girafi.culinarycultivation.item.ItemStorageJar;
import com.Girafi.culinarycultivation.item.ItemStorageJar.StorageJarType;
import com.Girafi.culinarycultivation.item.equipment.armor.farmer.RecipesFarmerArmorDyes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.VanillaBrewingRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import static com.Girafi.culinarycultivation.init.ModItems.*;

public class Recipes {

    public static void init() {
        GameRegistry.addRecipe(new RecipesFarmerArmorDyes());
        //Tools and other stuff
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cakeKnife), true, "   ", "  H", "II ", 'H', toolHandle, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(caneKnife), true, " II", " I ", " H ", 'H', toolHandle, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(kitchenKnife), true, "  I", " I ", "H  ", 'H', toolHandle, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(meatCleaver), true, " II", " II", "H  ", 'H', toolHandle, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.storageJar, 1, StorageJarType.EMPTY.getMetaData()), " S ", "P P", " P ", 'P', "paneGlass", 'S', "slabWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(toolHandle), true, "   ", " S ", " S ", 'S', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wooden_hoeLarge), true, "###", " H ", "H  ", 'H', toolHandle, '#', "plankWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stone_hoeLarge), true, "###", " H ", "H  ", 'H', toolHandle, '#', "cobblestone"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(iron_hoeLarge), true, "###", " H ", "H  ", 'H', toolHandle, '#', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(golden_hoeLarge), true, "###", " H ", "H  ", 'H', toolHandle, '#', "ingotGold"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(diamond_hoeLarge), true, "###", " H ", "H  ", 'H', toolHandle, '#', "gemDiamond"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.dye, 1, 1), new ItemStack(beetroot)));

        //Food
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(beetrootSoup), "BBB", "BBB", " C ", 'B', beetroot, 'C', Items.bowl));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.cake), " C ", "CCC", "CCC", 'C', ModItems.pieceOfCake));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(fish, 1, FishType.FILLET.getMetaData()), new ItemStack(kitchenKnife, 1, OreDictionary.WILDCARD_VALUE), "fish"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(meat, 1, MeatType.PATTY.getMetaData()), new ItemStack(meatCleaver, 1, OreDictionary.WILDCARD_VALUE), "foodMincedMeat"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(meat, 3, MeatType.BACON.getMetaData()), new ItemStack(kitchenKnife, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.porkchop)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(meat, 3, MeatType.CHICKENNUGGET.getMetaData()), new ItemStack(kitchenKnife, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.chicken)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(meat, 3, MeatType.SQUIDRING.getMetaData()), new ItemStack(kitchenKnife, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(meat, 1, MeatType.SQUIDMANTLE.getMetaData())));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(meat, 2, MeatType.SQUIDRING.getMetaData()), new ItemStack(kitchenKnife, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(fish, 1, FishType.SMALLSQUID.getMetaData())));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(pieceOfCake, 7), new ItemStack(cakeKnife, 1, OreDictionary.WILDCARD_VALUE), Items.cake));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(cheeseSlice, 7), new ItemStack(kitchenKnife, 1, OreDictionary.WILDCARD_VALUE), ModBlocks.cheese));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(storageJar, 3, StorageJarType.RENNET.getMetaData()), new ItemStack(kitchenKnife, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(calfBelly), storageJar, storageJar, storageJar, Items.water_bucket));
        //BrewingRecipeRegistry.addRecipe(new ItemStack(ModItems.storageJar, 1, StorageJarType.WATER.getMetaData()), new ItemStack(ModItems.calfBelly), new ItemStack(ModItems.storageJar, 1, StorageJarType.RENNET.getMetaData()));

        //Furnace recipes
        GameRegistry.addSmelting(new ItemStack(Items.fish, 1, 2), new ItemStack(cooked_fish, 1, FishType.CLOWNFISH.getMetaData()), 0.35F);
        GameRegistry.addSmelting(new ItemStack(meat, 1, MeatType.RIBSBEEF.getMetaData()), new ItemStack(cooked_meat, 1, MeatType.RIBS.getMetaData()), 0.35F);

        FishType[] afishtype = FishType.values();
        int i = afishtype.length;
        for (int j = 0; j < i; ++j) {
            FishType fishtype = afishtype[j];
            if (fishtype.isHaveCookedFish() && fishtype.isHaveRawFish()) {
                GameRegistry.addSmelting(new ItemStack(fish, 1, fishtype.getMetaData()), new ItemStack(cooked_fish, 1, fishtype.getMetaData()), 0.35F);
            }
        }
        MeatType[] ameattype = MeatType.values();
        int iMeat = ameattype.length;
        for (int j = 0; j < iMeat; ++j) {
            MeatType meattype = ameattype[j];
            if (meattype.isHaveCookedMeat()) {
                GameRegistry.addSmelting(new ItemStack(meat, 1, meattype.getMetaData()), new ItemStack(cooked_meat, 1, meattype.getMetaData()), 0.35F);
            }
        }
    }
}