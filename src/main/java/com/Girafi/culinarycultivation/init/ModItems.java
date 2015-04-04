package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.item.*;
import com.Girafi.culinarycultivation.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems
{

    public static final Item cakeKnife = new ItemCakeKnife(Item.ToolMaterial.IRON);
    public static final Item knife = new ItemKnife(Item.ToolMaterial.IRON);
    public static final Item meatCleaver = new ItemMeatCleaver(Item.ToolMaterial.IRON);
    //public static final Item emptyStorageJar = new ItemStorageJar().setUnlocalizedName("emptyStorageJar").setTextureName("emptyStorageJar");
    public  static final Item toolHandle = new SourceItem().setUnlocalizedName("toolHandle").setTextureName("toolHandle");

    ////Food
    //HealAmount, Saturation, isWolfFood
    //public static final Item debugFood = new SourceFood(-19, 0.0F, false).setUnlocalizedName("debugFood"); //TODO Make texture
    public static final Item cookedClownfish = new SourceFood(3, 0.2F, false).setPotionEffect(Potion.poison.id, 3, 0, 0.001F).setPotionEffect(Potion.confusion.id, 8, 0, 0.08F).setUnlocalizedName("clownfishCooked").setTextureName("clownfishCooked"); //TODO Make you get the Fish achievement, when cooking this
    public static final Item cookedLamb = new SourceFood(5, 1.1F, true).setUnlocalizedName("lambCooked").setTextureName("lambCooked");
    public static final Item cookedMutton = new SourceFood(6, 0.8F, true).setUnlocalizedName("muttonCooked").setTextureName("muttonCooked");
    public static final Item cookedVeal = new SourceFood(7, 1.0F, true).setUnlocalizedName("vealCooked").setTextureName("vealCooked");
    public static final Item lamb = new SourceFood(2, 0.3F, true).setUnlocalizedName("lambRaw").setTextureName("lambRaw");
    public static final Item mutton = new SourceFood(2, 0.3F, true).setUnlocalizedName("muttonRaw").setTextureName("muttonRaw");
    public static final Item pieceOfCake = new SourceFood(2, 0.1F, false).setUnlocalizedName("pieceOfCake").setTextureName("pieceOfCake"); //TODO Make diffrent cake "states", each with a piece less on, when used a knife on it. (Make them not show up in NEI) //TODO Add Cake Knife, which can cut the cake pieces all at once (6 pieces)
    public static final Item squidMantle = new SourceFood(3, 0.3F, false).setUnlocalizedName("squidMantleRaw").setTextureName("squidMantleRaw");
    public static final Item squidMantleCooked = new SourceFood(5, 0.4F, false).setUnlocalizedName("squidMantleCooked").setTextureName("squidMantleCooked");
    public static final Item squidRingCooked = new SourceFood(2, 0.2F, false).setUnlocalizedName("squidRingCooked").setTextureName("squidRingCooked");
    public static final Item squidRing = new SourceFood(1, 0.1F, false).setUnlocalizedName("squidRingRaw").setTextureName("squidRingRaw");
    public static final Item squidTentacle = new SourceFood(2, 0.2F, false).setUnlocalizedName("squidTentacleRaw").setTextureName("squidTentacleRaw");
    public static final Item squidTentacleCooked = new SourceFood(4, 0.4F, false).setUnlocalizedName("squidTentacleCooked").setTextureName("squidTentacleCooked");
    public static final Item veal = new SourceFood(2, 0.4F, true).setUnlocalizedName("vealRaw").setTextureName("vealRaw");

    public static final Item chickenNuggetRaw = new SourceFood(0, 0.0F, true).setUnlocalizedName("chickenNuggetRaw").setTextureName("chickenNuggetRaw");
    public static final Item chickenNuggetCooked = new SourceFood(0, 0.0F, true).setUnlocalizedName("chickenNuggetCooked").setTextureName("chickenNuggetCooked");
    public static final Item chickenWingHot = new SourceFood(0, 0.0F, true).setUnlocalizedName("chickenWingHot").setTextureName("chickenWingHot");
    public static final Item chickenWingCooked = new SourceFood(0, 0.0F, true).setUnlocalizedName("chickenWingCooked").setTextureName("chickenWingCooked");
    public static final Item chickenWingRaw = new SourceFood(0, 0.0F, true).setUnlocalizedName("chickenWingRaw").setTextureName("chickenWingRaw");

    public static final Item drumstickRaw = new SourceFood(0, 0.0F, true).setUnlocalizedName("drumstickRaw").setTextureName("drumstickRaw");
    public static final Item drumstickCooked = new SourceFood(0, 0.0F, true).setUnlocalizedName("drumstickCooked").setTextureName("drumstickCooked");

    public static final Item legSheepRaw = new SourceFood(0, 0.0F, true).setUnlocalizedName("legSheepRaw").setTextureName("legSheepRaw");
    public static final Item legSheepCooked = new SourceFood(0, 0.0F, true).setUnlocalizedName("legSheepCooked").setTextureName("legSheepCooked");

    public static final Item pattyRaw = new SourceFood(0, 0.0F, true).setUnlocalizedName("pattyRaw").setTextureName("pattyRaw");
    public static final Item pattyCooked = new SourceFood(0, 0.0F, true).setUnlocalizedName("pattyCooked").setTextureName("pattyCooked");

    public static final Item ribsBeefRaw = new SourceFood(0, 0.0F, true).setUnlocalizedName("ribsBeefRaw").setTextureName("ribsBeefRaw");
    public static final Item ribsPorkRaw = new SourceFood(0, 0.0F, true).setUnlocalizedName("ribsPorkRaw").setTextureName("ribsPorkRaw");
    public static final Item ribsCooked = new SourceFood(0, 0.0F, true).setUnlocalizedName("ribsCooked").setTextureName("ribsCooked");

    public static final Item sausage = new SourceFood(0, 0.0F, true).setUnlocalizedName("sausage").setTextureName("sausage");

    public static final Item hotdog = new SourceFood(0, 0.0F, false).setUnlocalizedName("hotdog").setTextureName("hotdog");

    public static final Item beetRaw = new SourceFood(0, 0.0F, false).setUnlocalizedName("beet").setTextureName("beet");
    public static final Item beetSeed = new SourceFood(0, 0.0F, false).setUnlocalizedName("beetSeed").setTextureName("beetSeed");
    public static final Item beetSoup = new SourceFood(0, 0.0F, false).setUnlocalizedName("beetSoup").setTextureName("beetSoup");

    public static void init() //Will show up in this order in NEI and Creative Tab
    {
        //GameRegistry.registerItem(debugFood, "debugFood");
        //GameRegistry.registerItem(emptyStorageJar, "emptyStorageJar");
        GameRegistry.registerItem(mutton, "mutton");
        GameRegistry.registerItem(cookedMutton, "cookedMutton");
        GameRegistry.registerItem(lamb , "lamb");
        GameRegistry.registerItem(cookedLamb, "cookedLamb");
        GameRegistry.registerItem(veal, "veal");
        GameRegistry.registerItem(cookedVeal, "cookedVeal");
        GameRegistry.registerItem(legSheepRaw, "legSheepRaw");
        GameRegistry.registerItem(legSheepCooked, "legSheepCooked");
        GameRegistry.registerItem(ribsBeefRaw, "ribsBeefRaw");
        GameRegistry.registerItem(ribsPorkRaw, "ribsPorkRaw");
        GameRegistry.registerItem(ribsCooked, "ribsCooked");
        GameRegistry.registerItem(pattyRaw, "pattyRaw");
        GameRegistry.registerItem(pattyCooked, "pattyCooked");
        GameRegistry.registerItem(sausage, "sausage");
        GameRegistry.registerItem(hotdog, "hotdog");
        GameRegistry.registerItem(chickenWingRaw, "chickenWingRaw");
        GameRegistry.registerItem(chickenWingCooked, "chickenWingCooked");
        GameRegistry.registerItem(chickenWingHot, "chickenWingHot");
        GameRegistry.registerItem(drumstickRaw, "drumstickRaw");
        GameRegistry.registerItem(drumstickCooked, "drumstickCooked");
        GameRegistry.registerItem(chickenNuggetRaw, "chickenNuggetRaw");
        GameRegistry.registerItem(chickenNuggetCooked, "chickenNuggetCooked");
        GameRegistry.registerItem(squidMantle, "squidMantle");
        GameRegistry.registerItem(squidMantleCooked, "squidMantleCooked");
        GameRegistry.registerItem(squidTentacle, "squidTentacle");
        GameRegistry.registerItem(squidTentacleCooked, "squidTentacleCooked");
        GameRegistry.registerItem(squidRing, "squidRing");
        GameRegistry.registerItem(squidRingCooked, "squidRingCooked");
        GameRegistry.registerItem(cookedClownfish, "cookedClownfish");
        GameRegistry.registerItem(beetSeed, "beetSeed");
        GameRegistry.registerItem(beetRaw, "beetRaw");
        GameRegistry.registerItem(beetSoup, "beetSoup");
        GameRegistry.registerItem(pieceOfCake, "pieceOfCake");
        GameRegistry.registerItem(toolHandle, "toolHandle");
        GameRegistry.registerItem(knife, "knife");
        GameRegistry.registerItem(meatCleaver, "meatCleaver");
        GameRegistry.registerItem(cakeKnife, "cakeKnife");
    }
}