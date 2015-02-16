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
    //public static final Item emptyStorageJar = new ItemStorageJar().setUnlocalizedName("emptyStorageJar").setTextureName("emptyStorageJar");
    public  static final Item toolHandle = new SourceItem().setUnlocalizedName("toolHandle").setTextureName("toolHandle");

    ////Food
    //HealAmount, Saturation, isWolfFood
    //public static final Item debugFood = new SourceFood(-19, 0.0F, false).setUnlocalizedName("debugFood"); //TODO Make texture
    public static final Item cookedClownfish = new SourceFood(3, 0.2F, false).setPotionEffect(Potion.poison.id, 3, 0, 0.001F).setPotionEffect(Potion.confusion.id, 8, 0, 0.08F).setUnlocalizedName("clownfishCooked").setTextureName("clownfishCooked");
    public static final Item cookedLamb = new SourceFood(5, 1.1F, true).setUnlocalizedName("lambCooked").setTextureName("lambCooked");
    public static final Item cookedMutton = new SourceFood(6, 0.8F, true).setUnlocalizedName("muttonCooked").setTextureName("muttonCooked");
    public static final Item cookedVeal = new SourceFood(7, 1.0F, true).setUnlocalizedName("vealCooked").setTextureName("vealCooked");
    public static final Item lamb = new SourceFood(2, 0.3F, true).setUnlocalizedName("lambRaw").setTextureName("lambRaw");
    public static final Item mutton = new SourceFood(2, 0.3F, true).setUnlocalizedName("muttonRaw").setTextureName("muttonRaw");
    public static final Item pieceOfCake = new SourceFood(2, 0.1F, false).setUnlocalizedName("pieceOfCake").setTextureName("pieceOfCake"); //TODO Make diffrent cake "states", each with a piece less on, when used a knife on it. (Make them not show up in NEI) //TODO Add Cake Knife, which can cut the cake pieces all at once (6 pieces)
    public static final Item veal = new SourceFood(2, 0.4F, true).setUnlocalizedName("vealRaw").setTextureName("vealRaw");

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
        GameRegistry.registerItem(cookedClownfish, "cookedClownfish");
        GameRegistry.registerItem(pieceOfCake, "pieceOfCake");
        GameRegistry.registerItem(toolHandle, "toolHandle");
        GameRegistry.registerItem(cakeKnife, "cakeKnife");
        GameRegistry.registerItem(knife, "knife");
    }
}