package com.girafi.culinarycultivation.util;

import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

@EventBusSubscriber
public class ConfigurationHandler {
    public static Configuration config;
    public static final String CATEGORY_MOB_DROPS = "mob drops";
    public static final String CATEGORY_MOD_SUPPORT = "mod support";
    public static final String CATEGORY_RIGHT_CLICK_HARVESTING = "right click harvesting";
    public static boolean canRightClickHarvestAllCulinaryCultivationCrops;
    public static boolean canRightClickHarvestCulinaryCultivationCrops;
    public static boolean canRightClickHarvestVanillaCrops;

    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {
        config.addCustomCategoryComment(CATEGORY_MOD_SUPPORT, "Whether support for certain mods should be enabled or disabled. It's recommended to not mess with these settings, unless you're experiencing issues with one of the mods.");
        config.addCustomCategoryComment(CATEGORY_MOB_DROPS, "Toggle if certain mobs should drop certain items or not.");
        config.addCustomCategoryComment(CATEGORY_RIGHT_CLICK_HARVESTING, "Toggle if the right click harvest ability should work on certain crops or not.");
        canRightClickHarvestAllCulinaryCultivationCrops = config.getBoolean("Enable right click harvesting for all Culinary Cultivation crops", CATEGORY_RIGHT_CLICK_HARVESTING, false, "Set to true to enable right click harvesting for all Culinary Cultivation crops including two tall crops, even the ones that are not by default.");
        canRightClickHarvestCulinaryCultivationCrops = config.getBoolean("Enable right click harvesting for Culinary Cultivation crops", CATEGORY_RIGHT_CLICK_HARVESTING, true, "Set to false to disable Culinary Cultivation crops being harvestable via right click.");
        canRightClickHarvestVanillaCrops = config.getBoolean("Enable right click harvesting for vanilla Minecraft crops", CATEGORY_RIGHT_CLICK_HARVESTING, false, "Set to true to enable right click harvesting for Minecraft vanilla crops (And all crops added by other mods, which extends BlockCrop)");

        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public static void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equalsIgnoreCase(Reference.MOD_ID)) {
            loadConfiguration();
        }
    }
}