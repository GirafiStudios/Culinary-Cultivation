package com.Girafi.culinarycultivation;

import com.Girafi.culinarycultivation.handler.ConfigurationHandler;
import com.Girafi.culinarycultivation.init.*;
import com.Girafi.culinarycultivation.modSupport.ModSupport;
import com.Girafi.culinarycultivation.network.NetworkHandler;
import com.Girafi.culinarycultivation.proxy.CommonProxy;
import com.Girafi.culinarycultivation.reference.Reference;
import com.Girafi.culinarycultivation.utility.LogHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = Reference.DEPENDENCIES)
public class CulinaryCultivation {
    @Mod.Instance(Reference.MOD_ID)
    public static CulinaryCultivation instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_ClASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
        ModSupport.instance().modSupportIndex();
        ModBlocks.init();
        ModItems.init();
        FishingLoot.init();
        Events.init();
        NetworkHandler.init();
        ModSupport.instance().preInit();
        LogHelper.info(Reference.MOD_NAME_ + "Pre Initialization Complete.");
    }

    @Mod.EventHandler
    public void init (FMLInitializationEvent event) {
        //Register GUIs
        ModTileEntities.init();
        Recipes.init();
        OreDictionaryRegistration.init();
        proxy.registerRenders();
        ModSupport.instance().init();
        LogHelper.info(Reference.MOD_NAME_ + "Initialization Complete.");
    }

    @Mod.EventHandler
    public void postInit (FMLPostInitializationEvent event) {
        ModSupport.instance().postInit();
        LogHelper.info(Reference.MOD_NAME_ + "Post Initialization Complete.");
    }
}