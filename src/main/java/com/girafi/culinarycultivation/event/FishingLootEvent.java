package com.girafi.culinarycultivation.event;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import static com.girafi.culinarycultivation.util.reference.Reference.MOD_ID;

@EventBusSubscriber
public class FishingLootEvent {
    private static final String FISHING = "gameplay/fishing/";
    private static final String[] LOOT_TABLES = new String[]{FISHING + "fish", FISHING + "junk", FISHING + "treasure"};

    @SubscribeEvent
    public static void onLootLoading(LootTableLoadEvent event) {
        if (event.getName().toString().equals("minecraft:gameplay/fishing")) {
            LootPool pool = event.getTable().getPool("main");
            if (pool != null) {
                for (String name : LOOT_TABLES) {
                    LootEntry entry = pool.getEntry("minecraft:" + name);
                    pool.addEntry(getEntry(MOD_ID + "_" + name.replace(FISHING, ""), name, getVanillaQuality(entry), getVanillaWeight(entry)));
                }
            }
        }
    }

    private static int getVanillaQuality(LootEntry entry) {
        return ReflectionHelper.getPrivateValue(LootEntry.class, entry, "quality", "field_186365_d");
    }

    private static int getVanillaWeight(LootEntry entry) {
        return ReflectionHelper.getPrivateValue(LootEntry.class, entry, "weight", "field_186364_c");
    }

    private static LootEntryTable getEntry(String unique, String name, int quality, int weight) {
        return new LootEntryTable(new ResourceLocation(MOD_ID, name), weight, quality, new LootCondition[0], unique);
    }
}