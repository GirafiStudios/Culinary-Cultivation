package com.Girafi.culinarycultivation.init;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;

import static com.Girafi.culinarycultivation.event.CakeDropsEvent.*;
import static com.Girafi.culinarycultivation.event.ChickenDropsEvent.*;
import static com.Girafi.culinarycultivation.event.CowDropsEvent.*;
import static com.Girafi.culinarycultivation.event.ItemCraftingEvent.*;
import static com.Girafi.culinarycultivation.event.PigDropsEvent.*;
import static com.Girafi.culinarycultivation.event.SheepDropsEvent.*;
import static com.Girafi.culinarycultivation.event.SquidDropsEvent.*;
import static com.Girafi.culinarycultivation.handler.ConfigurationHandler.*;

public class Events {
    public static void init() {
        if (ShouldBabyCowDropVeal) {MinecraftForge.EVENT_BUS.register(new BabyCowVealDropsEvent()); }
        if (ShouldBabySheepDropLamb) {MinecraftForge.EVENT_BUS.register(new BabySheepLambDropsEvent()); }
        if (ShouldChickensDropChickenWings) {MinecraftForge.EVENT_BUS.register(new ChickenWingDropsEvent()); }
        if (ShouldCowsDropBeefRibs) {MinecraftForge.EVENT_BUS.register(new CowRibsBeefDropsEvent()); }
        if (ShouldCowsDropRoast) {MinecraftForge.EVENT_BUS.register(new CowRoastDropsEvent()); }
        if (ShouldPigsDropHam) {MinecraftForge.EVENT_BUS.register(new PigHamDropsEvent()); }
        if (ShouldPigsDropPorkRibs) {MinecraftForge.EVENT_BUS.register(new PigRibsPorkDropsEvent()); }
        if (ShouldSheepDropLegOfSheep) {MinecraftForge.EVENT_BUS.register(new SheepLegOfSheepDropsEvent()); }
        if (ShouldSheepDropMutton) {MinecraftForge.EVENT_BUS.register(new SheepMuttonDropsEvent()); }
        if (ShouldSquidDropSquidMantle) {MinecraftForge.EVENT_BUS.register(new SquidMantleDropsEvent()); }
        if (ShouldSquidDropSquidTentacle) {MinecraftForge.EVENT_BUS.register(new SquidTentacleDropsEvent()); }
        MinecraftForge.EVENT_BUS.register(new CakeLeftClickEvent());
        MinecraftForge.EVENT_BUS.register(new CakePickupEvent());
        MinecraftForge.EVENT_BUS.register(new CakeRightClickEvent());
        FMLCommonHandler.instance().bus().register(new DrumstickCraftingEvent());
        FMLCommonHandler.instance().bus().register(new AchievementTriggerEvent());
    }
}