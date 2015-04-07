package com.Girafi.culinarycultivation.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import static com.Girafi.culinarycultivation.init.ModItems.*;

public class Recipes {

    public static void init() {
        //Tools and other stuff
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.emptyStorageJar), "   ", "PSP", " P ", 'P', new ItemStack(Blocks.glass_pane), 'S', "slabWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cakeKnife), true, "   ", "  H", "II ", 'H', toolHandle, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(knife), true, "  I", " I ", "H  ", 'H', toolHandle, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(meatCleaver), true, " II", " II", "H  ", 'H', toolHandle, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.dye, 1, 1), new ItemStack(beetRaw)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(toolHandle), "stickWood", "stickWood"));

        //Food
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(pieceOfCake, 6), new ItemStack(cakeKnife, 1, OreDictionary.WILDCARD_VALUE), Items.cake));  //TODO Make different cake states. Temp. recipe   //TODO 1.8 : Change cake slices from 6 to 7
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(squidRing, 3), new ItemStack(knife, 1,OreDictionary.WILDCARD_VALUE), new ItemStack(squidMantle)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(beetSoup), "BBB", "BBB", " C ", 'B', beetRaw, 'C', Items.bowl));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(baconRaw, 3), new ItemStack(knife, 1,OreDictionary.WILDCARD_VALUE), new ItemStack(Items.porkchop)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(pattyRaw), new ItemStack(meatCleaver, 1,OreDictionary.WILDCARD_VALUE), "foodMincedMeat"));

        //Temporary recipes
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(chickenWingHot), new ItemStack(chickenWingCooked), new ItemStack(Items.blaze_powder)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(beetRaw), new ItemStack(Blocks.tallgrass, 1, 1)));

        //Furnace recipes
        GameRegistry.addSmelting(baconRaw, new ItemStack(baconCooked), 0.35F);
        GameRegistry.addSmelting(chickenNuggetRaw, new ItemStack(chickenNuggetCooked), 0.35F);
        GameRegistry.addSmelting(chickenWingRaw, new ItemStack(chickenWingCooked), 0.35F);
        GameRegistry.addSmelting(drumstickRaw, new ItemStack(drumstickCooked), 0.35F);
        GameRegistry.addSmelting(hamRaw, new ItemStack(hamCooked), 0.35F);
        GameRegistry.addSmelting(lamb, new ItemStack(cookedLamb), 0.35F);
        GameRegistry.addSmelting(legSheepRaw, new ItemStack(legSheepCooked), 0.35F);
        GameRegistry.addSmelting(mutton, new ItemStack(cookedMutton), 0.35F);
        GameRegistry.addSmelting(new ItemStack(Items.fish, 1, 2), new ItemStack(cookedClownfish), 0.35F);
        GameRegistry.addSmelting(pattyRaw, new ItemStack(pattyCooked), 0.35F);
        GameRegistry.addSmelting(ribsBeefRaw, new ItemStack(ribsCooked), 0.35F);
        GameRegistry.addSmelting(ribsPorkRaw, new ItemStack(ribsCooked), 0.35F);
        GameRegistry.addSmelting(roastRaw, new ItemStack(roastCooked), 0.35F);
        GameRegistry.addSmelting(squidMantle, new ItemStack(squidMantleCooked), 0.35F);
        GameRegistry.addSmelting(squidRing, new ItemStack(squidRingCooked), 0.35F);
        GameRegistry.addSmelting(squidTentacle, new ItemStack(squidTentacleCooked), 0.35F);
        GameRegistry.addSmelting(veal, new ItemStack(cookedVeal), 0.35F);
    }
}