package com.Girafi.culinarycultivation;

import com.Girafi.culinarycultivation.event.SheepsDropsMutton;
import com.Girafi.culinarycultivation.handler.ConfigurationHandler;
import com.Girafi.culinarycultivation.handler.CraftingHandler;
import com.Girafi.culinarycultivation.init.*;
import com.Girafi.culinarycultivation.proxy.CommonProxy;
import com.Girafi.culinarycultivation.reference.Reference;
import com.Girafi.culinarycultivation.utility.LogHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
public class CulinaryCultivation
{
    @Mod.Instance(Reference.MOD_ID)
    public static CulinaryCultivation instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_ClASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        //Network handling
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
        ModItems.init();
        proxy.registerRenders();
        MinecraftForge.EVENT_BUS.register(new SheepsDropsMutton());
        LogHelper.info("CulinaryCultivation Pre Initialization Complete.");
    }

    @Mod.EventHandler
    public void init (FMLInitializationEvent event)
    {
        //Register GUIs, TileEntities, Crafting Recipes
        FMLCommonHandler.instance().bus().register(new CraftingHandler());
        OreDictionaryRegistration.init();
        Recipes.init();
        LogHelper.info("CulinaryCultivation Initialization Complete.");
    }

    @Mod.EventHandler
    public void init (FMLPostInitializationEvent event)
    {
        LogHelper.info("CulinaryCultivation Post Initialization Complete.");
    }
}