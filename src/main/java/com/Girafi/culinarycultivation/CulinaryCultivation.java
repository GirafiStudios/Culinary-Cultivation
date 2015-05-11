package com.Girafi.culinarycultivation;

import com.Girafi.culinarycultivation.handler.ConfigurationHandler;
import com.Girafi.culinarycultivation.handler.CraftingHandler;
import com.Girafi.culinarycultivation.init.*;
import com.Girafi.culinarycultivation.network.NetworkHandler;
import com.Girafi.culinarycultivation.ModSupport.ModSupport;
import com.Girafi.culinarycultivation.proxy.CommonProxy;
import com.Girafi.culinarycultivation.reference.Reference;
import com.Girafi.culinarycultivation.utility.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = Reference.DEPENDENCIES)
public class CulinaryCultivation {
    @Mod.Instance(Reference.MOD_ID)
    public static CulinaryCultivation instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_ClASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        Events.init();
        FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
        ModBlocks.init();
        ModItems.init();
        ModSupport.instance().modSupportIndex();
        FishingLoot.init();
        NetworkHandler.init();
        proxy.registerRenders();
        ModSupport.instance().preInit();
        LogHelper.info("Culinary Cultivation Pre Initialization Complete.");
    }

    @Mod.EventHandler
    public void init (FMLInitializationEvent event) {
        //Register GUIs, TileEntities
        FMLCommonHandler.instance().bus().register(new CraftingHandler());
        Recipes.init();
        OreDictionaryRegistration.init();
        ModSupport.instance().init();
        LogHelper.info("Culinary Cultivation Initialization Complete.");
    }

    @Mod.EventHandler
    public void init (FMLPostInitializationEvent event) {
        ModSupport.instance().postInit();
        LogHelper.info("Culinary Cultivation Post Initialization Complete.");
    }
}