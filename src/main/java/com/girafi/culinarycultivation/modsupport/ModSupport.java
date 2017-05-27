package com.girafi.culinarycultivation.modsupport;

import com.girafi.culinarycultivation.modsupport.forestry.Forestry;
import com.girafi.culinarycultivation.modsupport.waila.Waila;
import com.girafi.culinarycultivation.util.ConfigurationHandler;
import com.girafi.culinarycultivation.util.LogHelper;
import com.girafi.culinarycultivation.util.reference.Reference;
import com.girafi.culinarycultivation.util.reference.SupportedModIDs;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * This class is highly inspired from PneumaticCrafts ThirdPartyManager. Credits to MineMaarten for letting me use this. PneumaticCraft repo: https://github.com/MineMaarten/PneumaticCraft
 */
public class ModSupport {
    public static final ModSupport INSTANCE = new ModSupport();
    private final List<IModSupport> modSupportMods = new ArrayList<>();

    public void initIndex() {
        Map<String, Class<? extends IModSupport>> modSupportClasses = new HashMap<>();
        modSupportClasses.put(SupportedModIDs.FORESTRY, Forestry.class);
        //modSupportClasses.put(SupportedModIDs.TC, Thaumcraft.class);
        modSupportClasses.put(SupportedModIDs.WAILA, Waila.class);


        List<String> enabledModSupport = modSupportClasses.keySet().stream().filter(modid -> ConfigurationHandler.config.get(ConfigurationHandler.CATEGORY_MOD_SUPPORT, modid, true).getBoolean()).collect(Collectors.toList());

        ConfigurationHandler.config.save();

        modSupportClasses.entrySet().stream().filter(entry -> enabledModSupport.contains(entry.getKey()) && Loader.isModLoaded(entry.getKey())).forEach(entry -> {
            try {
                modSupportMods.add(entry.getValue().newInstance());
            } catch (Exception e) {
                LogHelper.error("Failed to load mod support handler");
                e.printStackTrace();
            }
        });
    }

    public void preInit() {
        for (IModSupport modSupport : modSupportMods) {
            try {
                modSupport.preInit();
            } catch (Exception e) {
                LogHelper.error(Reference.MOD_NAME + " could not load mod support content from " + modSupport.getClass() + " in PreInit");
                e.printStackTrace();
            }
        }
    }

    public void init() {
        for (IModSupport modSupport : modSupportMods) {
            try {
                modSupport.init();
            } catch (Exception e) {
                LogHelper.error(Reference.MOD_NAME + " could not load mod support content from " + modSupport.getClass() + " in Init");
                e.printStackTrace();
            }
        }
    }

    public void postInit() {
        for (IModSupport modSupport : modSupportMods) {
            try {
                modSupport.postInit();
            } catch (Exception e) {
                LogHelper.error(Reference.MOD_NAME + " could not load mod support content from " + modSupport.getClass() + " in PostInit");
                e.printStackTrace();
            }
        }
    }

    public void clientSide() {
        for (IModSupport modSupport : modSupportMods) {
            try {
                modSupport.clientSide();
            } catch (Exception e) {
                LogHelper.error(Reference.MOD_NAME + " could not load mod support content from " + modSupport.getClass() + " on client side!");
                e.printStackTrace();
            }
        }
    }
}