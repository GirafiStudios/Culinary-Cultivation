package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.item.ItemModFishFood;
import com.Girafi.culinarycultivation.item.ItemModMeatFood;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.HashMap;
import java.util.Map;

import static com.Girafi.culinarycultivation.init.ModItems.*;

public class Recipes {

    public static void init() {
        //Tools and other stuff
        //GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.emptyStorageJar), "   ", "PSP", " P ", 'P', new ItemStack(Blocks.glass_pane), 'S', "slabWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cakeKnife), true, "   ", "  H", "II ", 'H', toolHandle, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(knife), true, "  I", " I ", "H  ", 'H', toolHandle, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(meatCleaver), true, " II", " II", "H  ", 'H', toolHandle, 'I', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(toolHandle), true, "   ", " S ", " S ", 'S', "stickWood"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.dye, 1, 1), new ItemStack(beetRaw)));

        //Food
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(pieceOfCake, 6), new ItemStack(cakeKnife, 1, OreDictionary.WILDCARD_VALUE), Items.cake));  //TODO Minecraft 1.8 : Change cake slices from 6 to 7
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.cake), "   ", "CCC", "CCC", 'C', ModItems.pieceOfCake));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(squidRing, 3), new ItemStack(knife, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(squidMantle)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(beetSoup), "BBB", "BBB", " C ", 'B', beetRaw, 'C', Items.bowl));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(baconRaw, 3), new ItemStack(knife, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.porkchop)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(pattyRaw), new ItemStack(meatCleaver, 1,OreDictionary.WILDCARD_VALUE), "foodMincedMeat"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(chickenNuggetRaw, 3), new ItemStack(knife, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.chicken)));

        //Temporary recipes
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(chickenWingHot), new ItemStack(chickenWingCooked), new ItemStack(Items.blaze_powder)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(beetRaw), new ItemStack(Blocks.tallgrass, 1, 1)));

        //Furnace recipes
        GameRegistry.addSmelting(baconRaw, new ItemStack(baconCooked), 0.35F);
        GameRegistry.addSmelting(chickenNuggetRaw, new ItemStack(chickenNuggetCooked), 0.35F);
        GameRegistry.addSmelting(chickenWingRaw, new ItemStack(chickenWingCooked), 0.35F);
        GameRegistry.addSmelting(drumstickRaw, new ItemStack(drumstickCooked), 0.35F);
        GameRegistry.addSmelting(hamRaw, new ItemStack(hamCooked), 0.35F);
        GameRegistry.addSmelting(new ItemStack(ModItems.meat, ItemModMeatFood.MeatType.LAMB.getMetaData()), new ItemStack(ModItems.meat_cooked, ItemModMeatFood.MeatType.LAMB.getMetaData()), 0.35F);
        GameRegistry.addSmelting(legSheepRaw, new ItemStack(legSheepCooked), 0.35F);
        GameRegistry.addSmelting(mutton, new ItemStack(cookedMutton), 0.35F);
        GameRegistry.addSmelting(new ItemStack(Items.fish, 1, 2), new ItemStack(cooked_fish, 1 ,ItemModFishFood.FishType.CLOWNFISH.getMetaData()), 0.35F);
        GameRegistry.addSmelting(pattyRaw, new ItemStack(pattyCooked), 0.35F);
        GameRegistry.addSmelting(ribsBeefRaw, new ItemStack(ribsCooked), 0.35F);
        GameRegistry.addSmelting(ribsPorkRaw, new ItemStack(ribsCooked), 0.35F);
        GameRegistry.addSmelting(roastRaw, new ItemStack(roastCooked), 0.35F);
        GameRegistry.addSmelting(squidMantle, new ItemStack(squidMantleCooked), 0.35F);
        GameRegistry.addSmelting(squidRing, new ItemStack(squidRingCooked), 0.35F);
        GameRegistry.addSmelting(squidTentacle, new ItemStack(squidTentacleCooked), 0.35F);
        GameRegistry.addSmelting(veal, new ItemStack(cookedVeal), 0.35F);

        ItemModFishFood.FishType[] afishtype = ItemModFishFood.FishType.values();
        int i = afishtype.length;
        for (int j = 0; j < i; ++j) {
            ItemModFishFood.FishType fishtype = afishtype[j];
            if (fishtype.isHaveCookedFish() && fishtype.isHaveRawFish()) {
                GameRegistry.addSmelting(new ItemStack(ModItems.fish, 1, fishtype.getMetaData()), new ItemStack(ModItems.cooked_fish, 1, fishtype.getMetaData()), 0.35F);
            }
        }
    }
}