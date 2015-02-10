package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.item.*;
import com.Girafi.culinarycultivation.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems
{

    public static final Item cakeKnife = new ItemCakeKnife(Item.ToolMaterial.IRON);
    public static final Item knife = new ItemKnife(Item.ToolMaterial.IRON);
    //public static final Item emptyStorageJar = new ItemStorageJar().setUnlocalizedName("emptyStorageJar").setTextureName("emptyStorageJar");

    ////Food
    //HealAmount, Saturation, isWolfFood
    public static final Item cookedClownfish = new SourceFood(3, 0.2F, false).setUnlocalizedName("clownfishCooked").setTextureName("clownfishCooked"); //Add very tiny chance for poison, and tiny chance for nausea
    public static final Item cookedMutton = new SourceFood(6, 0.8F, true).setUnlocalizedName("muttonCooked").setTextureName("muttonCooked");
    //public static final Item debugFood = new SourceFood(-19, 0.0F, false).setUnlocalizedName("debugFood");
    public static final Item mutton = new SourceFood(2, 0.3F, true).setUnlocalizedName("muttonRaw").setTextureName("muttonRaw");
    public static final Item pieceOfCake = new SourceFood(2, 0.1F, false).setUnlocalizedName("pieceOfCake").setTextureName("pieceOfCake"); //TODO Make diffrent cake "states", each with a piece less on, when used a knife on it. (Make them not show up in NEI) //TODO Add Cake Knife, which can cut the cake pieces all at once (6 pieces)

    public static void init()
    {
        //GameRegistry.registerItem(debugFood, "debugFood");
        //GameRegistry.registerItem(emptyStorageJar, "emptyStorageJar");
        GameRegistry.registerItem(mutton, "mutton");
        GameRegistry.registerItem(cookedMutton, "cookedMutton");
        GameRegistry.registerItem(cookedClownfish, "cookedClownfish");
        GameRegistry.registerItem(pieceOfCake, "pieceOfCake");
        GameRegistry.registerItem(knife, "knife");
        GameRegistry.registerItem(cakeKnife, "cakeKnife");
    }
}