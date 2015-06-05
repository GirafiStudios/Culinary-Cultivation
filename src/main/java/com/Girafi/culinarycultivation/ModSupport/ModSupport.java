package com.Girafi.culinarycultivation.modSupport;

import com.Girafi.culinarycultivation.handler.ConfigurationHandler;
import com.Girafi.culinarycultivation.modSupport.ee3.EquivalentExchange3;
import com.Girafi.culinarycultivation.modSupport.thaumcraft.Thaumcraft;
import com.Girafi.culinarycultivation.reference.Reference;
import com.Girafi.culinarycultivation.reference.SupportedModIDs;
import com.Girafi.culinarycultivation.utility.LogHelper;
import cpw.mods.fml.common.Loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * This class is highly inspired from PneumaticCrafts ThirdPartyManager. Credits to MineMaarten for letting me use this. PneumaticCraft repo: https://github.com/MineMaarten/PneumaticCraft
 */
public class ModSupport {

    private static ModSupport INSTANCE = new ModSupport();
    private final List<IModSupport> modSupportMods = new ArrayList<IModSupport>();
    private final List<IModSupport> modSupportModsNoConfig = new ArrayList<IModSupport>();

    public static ModSupport instance() { return INSTANCE; }

    public void modSupportIndex() {
        Map<String, Class<? extends IModSupport>> modSupportClasses = new HashMap<String, Class<? extends IModSupport>>();
        Map<String, Class<? extends IModSupport>> modSupportClassesNoConfig = new HashMap<String, Class<? extends IModSupport>>();
        modSupportClasses.put(SupportedModIDs.EE3, EquivalentExchange3.class);
        modSupportClasses.put(SupportedModIDs.TC, Thaumcraft.class);


        List<String> enabledModSupport = new ArrayList<String>();
        for (String modid : modSupportClasses.keySet()) {
            if (ConfigurationHandler.config.get(ConfigurationHandler.CATEGORY_MODSUPPORT_ENABLING, modid, true).getBoolean()) {
                enabledModSupport.add(modid);
            }
        }
        ConfigurationHandler.config.save();

        for (Map.Entry<String, Class<? extends IModSupport>> entry : modSupportClasses.entrySet()) {
            if (enabledModSupport.contains(entry.getKey()) && Loader.isModLoaded(entry.getKey())) {
                try {
                    modSupportMods.add(entry.getValue().newInstance());
                } catch (Exception e) {
                    LogHelper.error("Failed to load mod support handler");
                    e.printStackTrace();
                }
            }
        }

        for (Map.Entry<String, Class<? extends IModSupport>> entryNoConfig : modSupportClassesNoConfig.entrySet()) {
            if (enabledModSupport.contains(entryNoConfig.getKey()) && Loader.isModLoaded(entryNoConfig.getKey())) {
                try {
                    modSupportMods.add(entryNoConfig.getValue().newInstance());
                } catch (Exception e) {
                    LogHelper.error("Failed to load mod support handler");
                    e.printStackTrace();
                }
            }
        }
    }

    public void preInit() {
        for (IModSupport modSupport : modSupportMods) {
            try {
                modSupport.preInit();
            } catch (Exception e) {
                LogHelper.error(Reference.MOD_NAME_ + "could not load mod support content from " + modSupport.getClass() + " in PreInit");
                e.printStackTrace();
            }
        }
    }

    public void init() {
        for (IModSupport modSupport : modSupportMods) {
            try {
                modSupport.init();
            } catch (Exception e) {
                LogHelper.error(Reference.MOD_NAME_ + "could not load mod support content from " + modSupport.getClass() + " in Init");
                e.printStackTrace();
            }
        }
    }

    public void postInit() {
        for (IModSupport modSupport : modSupportMods) {
            try {
                modSupport.postInit();
            } catch (Exception e) {
                LogHelper.error(Reference.MOD_NAME_ + "could not load mod support content from " + modSupport.getClass() + " in PostInit");
                e.printStackTrace();
            }
        }
    }

    public void clientSide() {
        for (IModSupport modSupport : modSupportMods) {
            try {
                modSupport.clientSide();
            } catch (Exception e) {
                LogHelper.error(Reference.MOD_NAME_ + "could not load mod support content from " + modSupport.getClass() + " on client side!");
                e.printStackTrace();
            }
        }
    }

    public void clientInit() {
        for (IModSupport modSupport : modSupportMods) {
            try {
                modSupport.clientInit();
            } catch (Exception e) {
                LogHelper.error(Reference.MOD_NAME_ + "could not load mod support content from " + modSupport.getClass() + " on client side under Initialization");
                e.printStackTrace();
            }
        }
    }
}