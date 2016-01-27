package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.block.*;
import com.Girafi.culinarycultivation.item.ItemCropFood;
import com.Girafi.culinarycultivation.item.ItemCropSeeds;
import com.Girafi.culinarycultivation.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {

    public static BlockDoubleCrop blackPepper = new BlockDoubleCrop();
    public static BlockCrop cucumber = new BlockCrop();
    public static BlockCrop beetroots = new BlockCrop();
    public static BlockCrop tomato = new BlockCrop();

    public static Block cheese = new BlockCheese();
    public static Block cauldron = new BlockModCauldron();
    public static Block fanHousing = new BlockFanHousing();
    public static Block separator = new BlockSeparator();

    public static void init() {
        //Crops
        GameRegistry.registerBlock(beetroots, "beetroots");
        GameRegistry.registerBlock(blackPepper, "blackPepper");
        GameRegistry.registerBlock(cucumber, "cucumber");
        GameRegistry.registerBlock(tomato, "tomato");
        GameRegistry.registerBlock(cauldron, "cauldron");
        GameRegistry.registerBlock(fanHousing, "fanHousing");
        GameRegistry.registerBlock(separator, "separator");
        GameRegistry.registerBlock(cheese, "cheese");
    }

    public static void setup() {
        cucumber.setModCrop(new ItemStack(ModItems.cropFood, 1, ItemCropFood.CropType.CUCUMBER.getMetadata()), 0, 4).setRightClickHarvest();
        beetroots.setModCrop(new ItemStack(ModItems.beetroot), 1, 1).setModSeed(new ItemStack(ModItems.beetrootSeeds), 0, 1);
        blackPepper.setModCrop(new ItemStack(ModItems.cropSeeds, 1, ItemCropSeeds.SeedType.BLACKPEPPERDRUPE.getMetadata()), 1, 5).setRightClickHarvest();
        tomato.setModCrop(new ItemStack(ModItems.cropFood, 1, ItemCropFood.CropType.TOMATO.getMetadata()), 1, 4).setRightClickHarvest();
    }
}