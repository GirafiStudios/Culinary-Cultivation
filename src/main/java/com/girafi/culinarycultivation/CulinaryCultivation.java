package com.girafi.culinarycultivation;

import com.girafi.culinarycultivation.init.*;
import com.girafi.culinarycultivation.init.recipes.Recipes;
import com.girafi.culinarycultivation.modsupport.ModSupport;
import com.girafi.culinarycultivation.network.NetworkHandler;
import com.girafi.culinarycultivation.proxy.CommonProxy;
import com.girafi.culinarycultivation.util.ConfigurationHandler;
import com.girafi.culinarycultivation.util.LogHelper;
import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = Reference.DEPENDENCIES, guiFactory = Reference.GUI_FACTORY_CLASS)
public class CulinaryCultivation {
    @Mod.Instance(Reference.MOD_ID)
    public static CulinaryCultivation instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_ClASS)
    public static CommonProxy proxy;


    public static final CreativeTabs TAB = new CreativeTabs(Reference.MOD_ID) {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return ModItems.MEAT_CLEAVER;
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigurationHandler.init(new File(event.getModConfigurationDirectory(), "CulinaryCultivation.cfg"));
        Events.register(new ConfigurationHandler());
        ModSupport.INSTANCE.modSupportIndex();
        ModItems.init();
        ModBlocks.init();
        ModBlocks.setup();
        Events.init();
        NetworkHandler.init();
        proxy.preInit();
        Recipes.initHandlers();
        OreDictionaryRegistration.preInit();
        ModSupport.INSTANCE.preInit();
        LogHelper.debug(Reference.MOD_NAME + " Pre Initialization Complete.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ModTileEntities.init();
        Recipes.init();
        ModSupport.INSTANCE.init();
        LogHelper.debug(Reference.MOD_NAME + " Initialization Complete.");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
        ModSupport.INSTANCE.postInit();
        LogHelper.debug(Reference.MOD_NAME + " Post Initialization Complete.");
    }
}