package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.block.BlockCheese;
import com.Girafi.culinarycultivation.block.BlockModCauldron;
import com.Girafi.culinarycultivation.block.Crops;
import com.Girafi.culinarycultivation.reference.Paths;
import com.Girafi.culinarycultivation.reference.Reference;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {

    //Testing crop stuff
    //Figure out a good way to add a lot of crops
    public static Block blackPepper = new Crops.BlockBlackPepper().setUnlocalizedName(Paths.ModAssets + "blackPepper");
    //public static Block blackPepper2 = new BlockBlackPepperStep2().setUnlocalizedName(Paths.ModAssets + "blackPepperStep2");

    public static Block cheese = new BlockCheese();
    public static Block cauldron = new BlockModCauldron();

    public static void init() //Will show up in this order in NEI and Creative Tab
    {
        GameRegistry.registerBlock(blackPepper, "blackPepper");
        //GameRegistry.registerBlock(blackPepper2, "blackPepper2");
        GameRegistry.registerBlock(cauldron, "cauldron");
        GameRegistry.registerBlock(cheese, "cheese");
    }
}