package com.girafi.culinarycultivation.event;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.girafi.culinarycultivation.util.reference.Reference.MOD_ID;

public class FishingLootEvent {
    private static final String FISHING = "gameplay/fishing/";
    private static final String[] LOOT_TABLES = new String[] { FISHING + "fish", FISHING + "junk", FISHING + "treasure"};

    @SubscribeEvent
    public void onLootLoading(LootTableLoadEvent event) {
        if (event.getName().getResourceDomain().equals("minecraft")) {
            for (String table: LOOT_TABLES) {
                if (table.equals(event.getName().getResourcePath())) {
                    event.getTable().addPool(getPool(table));
                }
            }
        }
    }

    private LootPool getPool(String entry) {
        return new LootPool(new LootEntry[] { getEntry(entry, 1) }, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), MOD_ID + "_pool");
    }

    private LootEntryTable getEntry(String name, int weight) {
        return new LootEntryTable(new ResourceLocation(MOD_ID, name), weight, 0, new LootCondition[0], MOD_ID + "_entry");
    }
}
