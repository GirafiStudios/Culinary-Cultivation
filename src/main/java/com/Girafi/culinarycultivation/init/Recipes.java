package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.item.ItemModFishFood.FishType;
import com.Girafi.culinarycultivation.item.ItemModMeatFood.MeatType;
import com.Girafi.culinarycultivation.item.ItemStorageJar.StorageJarType;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import static com.Girafi.culinarycultivation.init.ModItems.*;

public class Recipes {

    public static void init() {
        //Tools and other stuff
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.storageJar, 1, StorageJarType.EMPTY.getMetaData()), " S ", "P P", " P ", 'P', "paneGlass", 'S', "slabWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cakeKnife), true, "   ", "  H", "II ", 'H', toolHandle, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(knife), true, "  I", " I ", "H  ", 'H', toolHandle, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(meatCleaver), true, " II", " II", "H  ", 'H', toolHandle, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(toolHandle), true, "   ", " S ", " S ", 'S', "stickWood"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.dye, 1, 1), new ItemStack(beetRaw)));

        //Food
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(pieceOfCake, 6), new ItemStack(cakeKnife, 1, OreDictionary.WILDCARD_VALUE), Items.cake));  //TODO Minecraft 1.8 : Change cake slices from 6 to 7
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.cake), "   ", "CCC", "CCC", 'C', ModItems.pieceOfCake));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(meat, 3 , MeatType.SQUIDRING.getMetaData()), new ItemStack(knife, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(meat, 1 , MeatType.SQUIDMANTLE.getMetaData())));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(beetSoup), "BBB", "BBB", " C ", 'B', beetRaw, 'C', Items.bowl));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(meat, 3 , MeatType.BACON.getMetaData()), new ItemStack(knife, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.porkchop)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(meat, 1 , MeatType.PATTY.getMetaData()), new ItemStack(meatCleaver, 1,OreDictionary.WILDCARD_VALUE), "foodMincedMeat"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(meat, 3 , MeatType.CHICKENNUGGET.getMetaData()), new ItemStack(knife, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.chicken)));

        //Temporary recipes
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(beetRaw), new ItemStack(Blocks.tallgrass, 1, 1)));

        //Furnace recipes
        GameRegistry.addSmelting(mutton, new ItemStack(cookedMutton), 0.35F);
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
        int imeat = ameattype.length;
        for (int j = 0; j < imeat; ++j) {
            MeatType meattype = ameattype[j];
            if (meattype.isHaveCookedMeat()) {
                GameRegistry.addSmelting(new ItemStack(meat, 1, meattype.getMetaData()), new ItemStack(cooked_meat, 1, meattype.getMetaData()), 0.35F);
            }
        }
    }
}