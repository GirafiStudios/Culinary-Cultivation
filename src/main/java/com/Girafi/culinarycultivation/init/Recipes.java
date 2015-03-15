package com.Girafi.culinarycultivation.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import static com.Girafi.culinarycultivation.init.ModItems.*;

public class Recipes {

    public static void init() {
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.emptyStorageJar), "   ", "PSP", " P ", 'P', new ItemStack(Blocks.glass_pane), 'S', "slabWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cakeKnife), true, "   ", "  H", "II ", 'H', toolHandle, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(knife), true, "  I", " I ", "H  ", 'H', toolHandle, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(meatCleaver), true, " II", " II", "H  ", 'H', toolHandle, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(pieceOfCake, 6), new ItemStack(cakeKnife, 1, OreDictionary.WILDCARD_VALUE), Items.cake));  //TODO Make different cake states. Temp. recipe   //TODO 1.8 : Change cake slices from 6 to 7
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(squidRing, 3), new ItemStack(knife, 1,OreDictionary.WILDCARD_VALUE), new ItemStack(squidMantle)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(toolHandle), "stickWood", "stickWood"));


        //Furnace recipes
        GameRegistry.addSmelting(lamb, new ItemStack(cookedLamb), 0.35F);
        GameRegistry.addSmelting(mutton, new ItemStack(cookedMutton), 0.35F);
        GameRegistry.addSmelting(squidMantle, new ItemStack(squidMantleCooked), 0.35F);
        GameRegistry.addSmelting(squidRing, new ItemStack(squidRingCooked), 0.35F);
        GameRegistry.addSmelting(squidTentacle, new ItemStack(squidTentacleCooked), 0.35F);
        GameRegistry.addSmelting(veal, new ItemStack(cookedVeal), 0.35F);
        GameRegistry.addSmelting(new ItemStack(Items.fish, 1, 2), new ItemStack(cookedClownfish), 0.35F);
    }
}