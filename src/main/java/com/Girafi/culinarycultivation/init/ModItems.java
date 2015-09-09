package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.item.*;
import com.Girafi.culinarycultivation.item.equipment.armor.farmer.ItemFarmerBoots;
import com.Girafi.culinarycultivation.item.equipment.armor.farmer.ItemFarmerOveralls;
import com.Girafi.culinarycultivation.item.equipment.armor.farmer.ItemFarmerStrawhat;
import com.Girafi.culinarycultivation.item.equipment.tool.*;
import com.Girafi.culinarycultivation.reference.Paths;
import com.Girafi.culinarycultivation.reference.Reference;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {
    public static final Item cakeKnife = new ItemCakeKnife(Item.ToolMaterial.IRON);
    public static final Item debugItem = new ItemDebugItem();
    public static final Item farmerStrawhat = new ItemFarmerStrawhat();
    public static final Item farmerBoots = new ItemFarmerBoots();
    public static final Item farmerOveralls = new ItemFarmerOveralls();
    public static final Item knife = new ItemKnife(Item.ToolMaterial.IRON);
    public static final Item meatCleaver = new ItemMeatCleaver(Item.ToolMaterial.IRON);
    public static final Item storageJar = new ItemStorageJar();
    public static final Item caneKnife = new ItemCaneKnife(Item.ToolMaterial.STONE);
    public static final Item toolHandle = new SourceItem().setUnlocalizedName("toolHandle");

    ////Food
    //HealAmount, Saturation, isWolfFood
    public static final Item beetroot = new SourceFood(1, 0.1F, false).setUnlocalizedName("beetroot");
    public static final Item beetrootSoup = new SourceSoup(8).setUnlocalizedName("beetrootSoup");
    public static final Item cheeseSlice = new SourceFood(2, 0.4F, false).setUnlocalizedName("cheeseSlice");
    public static final Item cooked_fish = new ItemModFishFood(true).setHasSubtypes(true);
    public static final Item cooked_meat = new ItemModMeatFood(true).setHasSubtypes(true);
    public static final Item fish = new ItemModFishFood(false).setHasSubtypes(true);
    public static final Item meat = new ItemModMeatFood(false).setHasSubtypes(true);
    //public static final Item seasoned_meat = new ItemModMeatFood(true, true).setHasSubtypes(true);
    public static final Item pieceOfCake = new SourceFood(2, 0.1F, false).setUnlocalizedName("pieceOfCake");
    public static final Item calfBelly = new SourceItem().setUnlocalizedName("calfBelly").setMaxStackSize(1);

    //public static final Item chickenWingHot = new SourceFood(5, 0.8F, true).setPotionEffect(Potion.fireResistance.id, 15, 0, 0.25F).setUnlocalizedName("chickenWingHot");
    //public static final Item sausage = new SourceFood(0, 0.0F, true).setUnlocalizedName("sausage"));

    //Seeds
    public static final Item blackPepperDrupe = new ItemSeeds(ModBlocks.blackPepper, Blocks.farmland).setUnlocalizedName(Paths.ModAssets + "blackPepperDrupe");
    public static final Item beetrootSeeds = new ItemSeeds(ModBlocks.beetroots, Blocks.farmland).setUnlocalizedName(Paths.ModAssets + "beetrootSeeds");

    public static void init() { //Will show up in this order in NEI and Creative Tab
        GameRegistry.registerItem(cheeseSlice, "cheeseSlice");
        GameRegistry.registerItem(pieceOfCake, "pieceOfCake");
        GameRegistry.registerItem(calfBelly, "calfBelly");
        GameRegistry.registerItem(meat, "meat");
        GameRegistry.registerItem(cooked_meat, "cooked_meat");
        //GameRegistry.registerItem(seasoned_meat, "seasoned_meat");
        GameRegistry.registerItem(fish, "fish");
        GameRegistry.registerItem(cooked_fish, "cooked_fish");
        GameRegistry.registerItem(beetrootSeeds, "beetRootSeeds");
        GameRegistry.registerItem(beetroot, "beetroot");
        GameRegistry.registerItem(beetrootSoup, "beetRootSoup");
        GameRegistry.registerItem(blackPepperDrupe, "blackPepperDrupe");
        GameRegistry.registerItem(storageJar, "storageJar");
        GameRegistry.registerItem(toolHandle, "toolHandle");
        GameRegistry.registerItem(knife, "knife");
        GameRegistry.registerItem(cakeKnife, "cakeKnife");
        GameRegistry.registerItem(meatCleaver, "meatCleaver");
        GameRegistry.registerItem(caneKnife, "caneKnife");
        GameRegistry.registerItem(debugItem, "debugItem");
        GameRegistry.registerItem(farmerStrawhat, "farmerStrawhat");
        GameRegistry.registerItem(farmerOveralls, "farmerOveralls");
        GameRegistry.registerItem(farmerBoots, "farmerBoots");
        MinecraftForge.EVENT_BUS.register(new ItemDebugItem());
    }
}