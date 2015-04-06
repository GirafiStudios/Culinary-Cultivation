package com.Girafi.culinarycultivation.init;

import net.minecraftforge.common.MinecraftForge;

import static com.Girafi.culinarycultivation.event.ChickenDropsEvent.*;
import static com.Girafi.culinarycultivation.event.CowDropsEvent.*;
import static com.Girafi.culinarycultivation.event.SheepDropsEvent.*;
import static com.Girafi.culinarycultivation.event.SquidDropsEvent.*;
import static com.Girafi.culinarycultivation.handler.ConfigurationHandler.*;

public class Events {

    public static void init() {
        if (ShouldBabyCowDropVeal) {MinecraftForge.EVENT_BUS.register(new BabyCowVealDropsEvent()); }
        if (ShouldBabySheepDropLamb) {MinecraftForge.EVENT_BUS.register(new BabySheepLambDropsEvent()); }
        if (ShouldSheepDropMutton) {MinecraftForge.EVENT_BUS.register(new SheepMuttonDropsEvent()); }
        if (ShouldSquidDropSquidMantle) {MinecraftForge.EVENT_BUS.register(new SquidMantleDropsEvent()); }
        if (ShouldSquidDropSquidTentacle) {MinecraftForge.EVENT_BUS.register(new SquidTentacleDropsEvent()); }
        if (ShouldChickensDropChickenWings) {MinecraftForge.EVENT_BUS.register(new ChickenWingDropsEvent()); }
    }
}