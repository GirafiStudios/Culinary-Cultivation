package com.girafi.culinarycultivation.util;

import com.girafi.culinarycultivation.api.annotations.RegisterEvent;
import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

@RegisterEvent
public class ConfigurationHandler {
    public static Configuration config;
    public static final String CATEGORY_MOB_DROPS = "mob drops";
    public static final String CATEGORY_MOD_SUPPORT_ENABLING = "mod support";
    public static final String CATEGORY_RIGHT_CLICK_HARVESTING = "right click harvesting";
    public static boolean canRightClickHarvestAllCulinaryCultivationCrops;
    public static boolean canRightClickHarvestAllCulinaryCultivationDoubleCrops;
    public static boolean canRightClickHarvestCulinaryCultivationCrops;
    public static boolean canRightClickHarvestVanillaCrops;
    public static boolean shouldBabyCowDropVeal;
    public static boolean shouldBabySheepDropLamb;
    public static boolean shouldChickensDropChickenWings;
    public static boolean shouldCowsDropBeefRibs;
    public static boolean shouldCowsDropRoast;
    public static boolean shouldPigsDropHam;
    public static boolean shouldPigsDropPorkRibs;
    public static boolean shouldSheepDropLegOfSheep;
    public static boolean shouldSquidDropSquidMantle;
    public static boolean shouldSquidDropSquidTentacle;


    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {
        config.addCustomCategoryComment(CATEGORY_MOD_SUPPORT_ENABLING, "Whether support for certain mods should be enabled or disabled. It's recommended to not mess with these settings, unless you're experiencing issues with one of the mods.");
        config.addCustomCategoryComment(CATEGORY_MOB_DROPS, "Toggle if certain mobs should drop certain items or not.");
        config.addCustomCategoryComment(CATEGORY_RIGHT_CLICK_HARVESTING, "Toggle if the right click harvest ability should work on certain crops or not.");
        canRightClickHarvestAllCulinaryCultivationCrops = config.getBoolean("Enable right click harvesting for all Culinary Cultivation crops", CATEGORY_RIGHT_CLICK_HARVESTING, false, "Set to true to enable right click harvesting for all Culinary Cultivation crops including two tall crops, even the ones that are not by default.");
        canRightClickHarvestAllCulinaryCultivationDoubleCrops = config.getBoolean("Enable right click harvesting for Culinary Cultivation two tall crops", CATEGORY_RIGHT_CLICK_HARVESTING, true, "Set to false to disable Culinary Cultivation two tall crops being harvestable via right click.");
        canRightClickHarvestCulinaryCultivationCrops = config.getBoolean("Enable right click harvesting for Culinary Cultivation crops", CATEGORY_RIGHT_CLICK_HARVESTING, true, "Set to false to disable Culinary Cultivation crops being harvestable via right click.");
        canRightClickHarvestVanillaCrops = config.getBoolean("Enable right click harvesting for vanilla Minecraft crops", CATEGORY_RIGHT_CLICK_HARVESTING, false, "Set to true to enable right click harvesting for Minecraft vanilla crops (And all crops added by other mods, which extends BlockCrop)");
        shouldBabyCowDropVeal = config.get(CATEGORY_MOB_DROPS, "Should baby cows drop veal?", true).getBoolean(true);
        shouldBabySheepDropLamb = config.get(CATEGORY_MOB_DROPS, "Should baby sheep drop lamb?", true).getBoolean(true);
        shouldChickensDropChickenWings = config.get(CATEGORY_MOB_DROPS, "Should chickens drop chicken wings?", true).getBoolean(true);
        shouldCowsDropBeefRibs = config.get(CATEGORY_MOB_DROPS, "Should cows drop beef ribs?", true).getBoolean(true);
        shouldCowsDropRoast = config.get(CATEGORY_MOB_DROPS, "Should cows drop roast?", true).getBoolean(true);
        shouldPigsDropHam = config.get(CATEGORY_MOB_DROPS, "Should pigs drop ham?", true).getBoolean(true);
        shouldPigsDropPorkRibs = config.get(CATEGORY_MOB_DROPS, "Should pigs drop pork ribs?", true).getBoolean(true);
        shouldSheepDropLegOfSheep = config.get(CATEGORY_MOB_DROPS, "Should sheep drop leg of sheep?", true).getBoolean(true);
        shouldSquidDropSquidMantle = config.get(CATEGORY_MOB_DROPS, "Should squid drop squid mantle?", true).getBoolean(true);
        shouldSquidDropSquidTentacle = config.get(CATEGORY_MOB_DROPS, "Should squid drop squid tentacle?", true).getBoolean(true);

        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equalsIgnoreCase(Reference.MOD_ID)) {
            loadConfiguration();
        }
    }
}