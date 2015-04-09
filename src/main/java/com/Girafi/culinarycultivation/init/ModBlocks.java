package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

import static com.Girafi.culinarycultivation.block.Crops.*;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {

    //Testing crop stuff
    public static Block beet = new BlockBeet().setBlockName(Reference.MOD_ID.toLowerCase() + ":" + "beets").setBlockTextureName("beet");
    //Figure out a good way to add a lot of crops


    public static void init() //Will show up in this order in NEI and Creative Tab
    {
        GameRegistry.registerBlock(beet, "beet");
    }
}
