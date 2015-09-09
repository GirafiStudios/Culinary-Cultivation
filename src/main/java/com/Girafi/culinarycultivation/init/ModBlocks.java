package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.block.BlockBlackPepper;
import com.Girafi.culinarycultivation.block.BlockCheese;
import com.Girafi.culinarycultivation.block.BlockCrop;
import com.Girafi.culinarycultivation.block.BlockModCauldron;
import com.Girafi.culinarycultivation.reference.Paths;
import com.Girafi.culinarycultivation.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {

    public static Block blackPepper = new BlockBlackPepper().setUnlocalizedName(Paths.ModAssets + "blackPepper");
    public static BlockCrop cucumber = new BlockCrop();
    public static BlockCrop beetRoots = new BlockCrop();

    public static Block cheese = new BlockCheese();
    public static Block cauldron = new BlockModCauldron();

    public static void init() { //Will show up in this order in NEI and Creative Tab
        GameRegistry.registerBlock(beetRoots, "beetRoots");
        GameRegistry.registerBlock(cucumber, "cucumber");
        GameRegistry.registerBlock(blackPepper, "blackPepper");
        GameRegistry.registerBlock(cauldron, "cauldron");
        GameRegistry.registerBlock(cheese, "cheese");
    }

    public static void setup() {
        cucumber.setModCrop(new ItemStack(ModItems.cheeseSlice), 0, 2);
        beetRoots.setModCrop(new ItemStack(ModItems.beetRoot), 1, 1).setModSeed(new ItemStack(ModItems.beetRootSeeds), 0, 1); //On 45 crops, 44 beets, 35 beetroots seeds
    }
}