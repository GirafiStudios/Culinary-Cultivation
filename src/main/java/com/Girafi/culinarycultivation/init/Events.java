package com.Girafi.culinarycultivation.init;

import net.minecraftforge.common.MinecraftForge;

import static com.Girafi.culinarycultivation.event.ChickenDropsEvent.ChickenWingDropsEvent;
import static com.Girafi.culinarycultivation.event.CowDropsEvent.*;
import static com.Girafi.culinarycultivation.event.InteractEvents.*;
import static com.Girafi.culinarycultivation.event.ItemCraftingEvent.AchievementTriggerEvent;
import static com.Girafi.culinarycultivation.event.ItemCraftingEvent.CraftedEvent;
import static com.Girafi.culinarycultivation.event.PigDropsEvent.PigHamDropsEvent;
import static com.Girafi.culinarycultivation.event.PigDropsEvent.PigRibsPorkDropsEvent;
import static com.Girafi.culinarycultivation.event.SheepDropsEvent.BabySheepLambDropsEvent;
import static com.Girafi.culinarycultivation.event.SheepDropsEvent.SheepLegOfSheepDropsEvent;
import static com.Girafi.culinarycultivation.event.SquidDropsEvent.SquidMantleDropsEvent;
import static com.Girafi.culinarycultivation.event.SquidDropsEvent.SquidTentacleDropsEvent;
import static com.Girafi.culinarycultivation.handler.ConfigurationHandler.*;

public class Events {
    public static void init() {
        if (CanRightClickHarvestVanillaCrops) { registerEvent(new VanillaCrops()); }
        if (ShouldBabyCowDropVeal) { registerEvent(new BabyCowVealDropsEvent()); }
        if (ShouldBabySheepDropLamb) { registerEvent(new BabySheepLambDropsEvent()); }
        if (ShouldChickensDropChickenWings) { registerEvent(new ChickenWingDropsEvent()); }
        if (ShouldCowsDropBeefRibs) { registerEvent(new CowRibsBeefDropsEvent()); }
        if (ShouldCowsDropRoast) { registerEvent(new CowRoastDropsEvent()); }
        if (ShouldPigsDropHam) { registerEvent(new PigHamDropsEvent()); }
        if (ShouldPigsDropPorkRibs) { registerEvent(new PigRibsPorkDropsEvent()); }
        if (ShouldSheepDropLegOfSheep) { registerEvent(new SheepLegOfSheepDropsEvent()); }
        if (ShouldSquidDropSquidMantle) { registerEvent(new SquidMantleDropsEvent()); }
        if (ShouldSquidDropSquidTentacle) { registerEvent(new SquidTentacleDropsEvent()); }
        registerEvent(new BabyCowCalfBellyDropsEvent());
        registerEvent(new AchievementTriggerEvent());
        registerEvent(new CraftedEvent());
        registerEvent(new CakeInteractionEvent());
        registerEvent(new CaneKnife());
        registerEvent(new CauldronTransformation());
        registerEvent(new CheeseInteractionEvent());
        registerEvent(new DebugItemEvent());
        registerEvent(new StorageJarMilkFill());
    }

    public static void registerEvent(Object eventClass) {
        MinecraftForge.EVENT_BUS.register(eventClass);
    }
}