package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.event.BabyCowDropsEvent;
import com.Girafi.culinarycultivation.event.BabySheepDropsEvent;
import com.Girafi.culinarycultivation.event.ChickenDropsEvent;
import com.Girafi.culinarycultivation.event.SheepDropsEvent;
import net.minecraftforge.common.MinecraftForge;

import static com.Girafi.culinarycultivation.event.SquidDropsEvent.*;
import static com.Girafi.culinarycultivation.handler.ConfigurationHandler.*;

public class Events {

    public static void init() {
        if (ShouldBabyCowDropVeal) {MinecraftForge.EVENT_BUS.register(new BabyCowDropsEvent()); }
        if (ShouldBabySheepDropLamb) {MinecraftForge.EVENT_BUS.register(new BabySheepDropsEvent()); }
        if (ShouldSheepDropMutton) {MinecraftForge.EVENT_BUS.register(new SheepDropsEvent()); }
        if (ShouldSquidDropSquidMantle) {MinecraftForge.EVENT_BUS.register(new SquidMantleDropsEvent()); }
        if (ShouldSquidDropSquidTentacle) {MinecraftForge.EVENT_BUS.register(new SquidTentacleDropsEvent()); }
        if (ShouldChickensDropChickenWings) {MinecraftForge.EVENT_BUS.register(new ChickenDropsEvent()); }
    }
}
