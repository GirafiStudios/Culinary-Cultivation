package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.crops.TestingCrop;
import com.Girafi.culinarycultivation.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {

    //Testing crop stuff
    public static Block cropTesting = new TestingCrop().setBlockName("cropTesting").setBlockTextureName("emptyStorageJar");
    //Figure out a good way to add a lot of crops


    public static void init() //Will show up in this order in NEI and Creative Tab
    {
        //GameRegistry.registerBlock(cropTesting, "cropTesting");
    }
}
