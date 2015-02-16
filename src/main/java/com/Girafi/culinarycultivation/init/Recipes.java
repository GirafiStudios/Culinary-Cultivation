package com.Girafi.culinarycultivation.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Recipes {
    public static void init() {
        //Recipes. Most of them is still Work in Progress
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.emptyStorageJar), "   ", "PSP", " P ", 'P', new ItemStack(Blocks.glass_pane), 'S', "slabWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.cakeKnife), true, "   ", "  H", "II ", 'H', ModItems.toolHandle, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.knife), true, "  I", " I ", "H  ", 'H', ModItems.toolHandle, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.pieceOfCake, 6), new ItemStack(ModItems.cakeKnife, 1, OreDictionary.WILDCARD_VALUE), Items.cake));  //TODO Make diffrent cake states. Temp. recipe   //TODO 1.8 : Change cake slices from 6 to 7
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.toolHandle), "stickWood", "stickWood"));

        //Furnace recipes
        GameRegistry.addSmelting(ModItems.lamb, new ItemStack(ModItems.cookedLamb), 0.35F);
        GameRegistry.addSmelting(ModItems.mutton, new ItemStack(ModItems.cookedMutton), 0.35F);
        GameRegistry.addSmelting(ModItems.veal, new ItemStack(ModItems.cookedVeal), 0.35F);
        GameRegistry.addSmelting(new ItemStack(Items.fish, 1, 2), new ItemStack(ModItems.cookedClownfish), 0.35F);
    }
}