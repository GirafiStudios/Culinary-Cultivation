package com.girafi.culinarycultivation;

import com.girafi.culinarycultivation.init.*;
import com.girafi.culinarycultivation.init.recipes.Recipes;
import com.girafi.culinarycultivation.modsupport.ModSupport;
import com.girafi.culinarycultivation.network.NetworkHandler;
import com.girafi.culinarycultivation.proxy.CommonProxy;
import com.girafi.culinarycultivation.util.ConfigurationHandler;
import com.girafi.culinarycultivation.util.LogHelper;
import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = Reference.DEPENDENCIES, guiFactory = Reference.GUI_FACTORY_CLASS)
public class CulinaryCultivation {
    @Mod.Instance(Reference.MOD_ID)
    public static CulinaryCultivation instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_ClASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        Events.register(new ConfigurationHandler());
        ModSupport.instance().modSupportIndex();
        ModItems.init();
        ModBlocks.init();
        ModBlocks.setup();
        Events.init();
        NetworkHandler.init();
        proxy.preInit();
        Recipes.initHandlers();
        ModSupport.instance().preInit();
        LogHelper.info(Reference.MOD_NAME + " Pre Initialization Complete.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ModTileEntities.init();
        Recipes.init();
        OreDictionaryRegistration.init();
        proxy.postInit();
        ModSupport.instance().init();
        LogHelper.info(Reference.MOD_NAME + " Initialization Complete.");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ModSupport.instance().postInit();
        LogHelper.info(Reference.MOD_NAME + " Post Initialization Complete.");
    }
}