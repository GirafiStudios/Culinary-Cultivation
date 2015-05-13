package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.item.*;
import com.Girafi.culinarycultivation.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.MinecraftForge;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {
    public static final Item debugItem = new ItemDebugItem();
    //public static final Item emptyStorageJar = new ItemStorageJar().setUnlocalizedName("emptyStorageJar").setTextureName("emptyStorageJar");
    public  static final Item toolHandle = new SourceItem().setUnlocalizedName("toolHandle").setTextureName("toolHandle");
    public static final Item cakeKnife = new ItemCakeKnife(Item.ToolMaterial.IRON);
    public static final Item knife = new ItemKnife(Item.ToolMaterial.IRON);
    public static final Item meatCleaver = new ItemMeatCleaver(Item.ToolMaterial.IRON);
    //public static final Item rennet_bucket = new ItemBucket(Blocks.piston).setContainerItem(Items.bucket).setUnlocalizedName(Reference.MOD_ID.toLowerCase() + ":" + "bucketRennet").setTextureName(Reference.MOD_ID.toLowerCase() + ":" + "bucketRennet");

    ////Food
    //HealAmount, Saturation, isWolfFood
    public static final Item beetRaw = new SourceSeedFood(1, 0.3F, ModBlocks.beet, Blocks.farmland).setUnlocalizedName("beet").setTextureName("beet");
    public static final Item beetSoup = new SourceSoup(8).setUnlocalizedName("beetSoup").setTextureName("beetSoup");
    //public static final Item chickenWingHot = new SourceFood(5, 0.8F, true).setPotionEffect(Potion.fireResistance.id, 15, 0, 0.25F).setUnlocalizedName("chickenWingHot").setTextureName("chickenWingHot");
    public static final Item cooked_fish = new ItemModFishFood(true).setUnlocalizedName("fish").setTextureName("fish_cooked").setHasSubtypes(true);
    public static final Item cooked_meat = new ItemModMeatFood(true).setHasSubtypes(true);
    public static final Item cookedMutton = new SourceFood(6, 0.8F, true).setUnlocalizedName("muttonCooked").setTextureName("muttonCooked");
    public static final Item fish = new ItemModFishFood(false).setUnlocalizedName("fish").setTextureName("fish_raw").setHasSubtypes(true);
    public static final Item meat = new ItemModMeatFood(false).setHasSubtypes(true);
    public static final Item mutton = new SourceFood(2, 0.3F, true).setUnlocalizedName("muttonRaw").setTextureName("muttonRaw");
    public static final Item pieceOfCake = new SourceFood(2, 0.1F, false).setUnlocalizedName("pieceOfCake").setTextureName("pieceOfCake"); //TODO (MAYBE) Make diffrent cake "states", each with a piece less on, when used the kitchen knife on it. (Make them not show up in NEI)

    //public static final Item sausage = new SourceFood(0, 0.0F, true).setUnlocalizedName("sausage").setTextureName("sausage");
    //public static final Item hotdog = new SourceFood(0, 0.0F, false).setUnlocalizedName("hotdog").setTextureName("hotdog");

    //public static final Item hoeBoots = new ItemHoeBoots(ItemArmor.ArmorMaterial.CLOTH, 0, 3).setUnlocalizedName("hoeBoots").setTextureName("hoeBoots");

    //Seeds
    public static final Item blackPepperDrupe = new SourceSeeds(ModBlocks.blackPepper, Blocks.farmland).setUnlocalizedName("blackPepperDrupe").setTextureName("blackPepperDrupe");

    public static void init() { //Will show up in this order in NEI and Creative Tab
        //GameRegistry.registerItem(emptyStorageJar, "emptyStorageJar");
        GameRegistry.registerItem(mutton, "mutton");
        GameRegistry.registerItem(cookedMutton, "cookedMutton");
        GameRegistry.registerItem(meat, "meat");
        GameRegistry.registerItem(cooked_meat, "cooked_meat");
        //GameRegistry.registerItem(sausage, "sausage");
        //GameRegistry.registerItem(hotdog, "hotdog");
        //GameRegistry.registerItem(chickenWingHot, "chickenWingHot");
        GameRegistry.registerItem(fish, "fish");
        GameRegistry.registerItem(cooked_fish, "cooked_fish");
        GameRegistry.registerItem(beetRaw, "beetRaw");
        GameRegistry.registerItem(beetSoup, "beetSoup");
        GameRegistry.registerItem(blackPepperDrupe, "blackPepperDrupe");
        GameRegistry.registerItem(pieceOfCake, "pieceOfCake");
        GameRegistry.registerItem(toolHandle, "toolHandle");
        GameRegistry.registerItem(knife, "knife");
        GameRegistry.registerItem(meatCleaver, "meatCleaver");
        GameRegistry.registerItem(cakeKnife, "cakeKnife");
        //GameRegistry.registerItem(hoeBoots, "hoeBoots");
        //GameRegistry.registerItem(rennet_bucket, "rennet_bucket");
        GameRegistry.registerItem(debugItem, "debugItem");
        MinecraftForge.EVENT_BUS.register(new ItemDebugItem());
    }
}