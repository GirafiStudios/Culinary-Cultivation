package com.Girafi.culinarycultivation.handler;

import com.Girafi.culinarycultivation.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigurationHandler
{
    public static Configuration config;
    public static String CATEGORY_MODSUPPORT_ENABLING = "mod support";
    public static String CATEGORY_MOB_DROPS = "mob drops";
    public static boolean ShouldBabyCowDropVeal;
    public static boolean ShouldBabySheepDropLamb;
    public static boolean ShouldChickensDropChickenWings;
    public static boolean ShouldCowsDropBeefRibs;
    public static boolean ShouldCowsDropRoast;
    public static boolean ShouldPigsDropHam;
    public static boolean ShouldPigsDropPorkRibs;
    public static boolean ShouldSheepDropLegOfSheep;
    public static boolean ShouldSquidDropSquidMantle;
    public static boolean ShouldSquidDropSquidTentacle;

    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {
        config.addCustomCategoryComment(CATEGORY_MODSUPPORT_ENABLING, "Whether support for certain mods should be enabled or disabled. It's recommended to not mess with these settings, unless you're experiencing issues with one of the mods.");
        config.addCustomCategoryComment(CATEGORY_MOB_DROPS, "Toggle if certain mobs should drop certain items or not.");
        ShouldBabyCowDropVeal = config.get(CATEGORY_MOB_DROPS, "Should baby cows drop veal?", true).getBoolean(true);
        ShouldBabySheepDropLamb = config.get(CATEGORY_MOB_DROPS, "Should baby sheep drop lamb?", true).getBoolean(true);
        ShouldChickensDropChickenWings = config.get(CATEGORY_MOB_DROPS, "Should chickens drop chicken wings?", true).getBoolean(true);
        ShouldCowsDropBeefRibs = config.get(CATEGORY_MOB_DROPS, "Should cows drop beef ribs?", true).getBoolean(true);
        ShouldCowsDropRoast = config.get(CATEGORY_MOB_DROPS, "Should cows drop roast?", true).getBoolean(true);
        ShouldPigsDropHam = config.get(CATEGORY_MOB_DROPS, "Should pigs drop ham?", true).getBoolean(true);
        ShouldPigsDropPorkRibs = config.get(CATEGORY_MOB_DROPS, "Should pigs drop pork ribs?", true).getBoolean(true);
        ShouldSheepDropLegOfSheep = config.get(CATEGORY_MOB_DROPS, "Should sheep drop leg of sheep?", true).getBoolean(true);
        ShouldSquidDropSquidMantle = config.get(CATEGORY_MOB_DROPS, "Should squid drop squid mantle?", true).getBoolean(true);
        ShouldSquidDropSquidTentacle = config.get(CATEGORY_MOB_DROPS, "Should squid drop squid tentacle?", true).getBoolean(true);

        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
            loadConfiguration();
        }
    }
}