package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.event.BabyCowDropsVeal;
import com.Girafi.culinarycultivation.event.BabySheepDropsLamb;
import com.Girafi.culinarycultivation.event.SheepDropsMutton;
import com.Girafi.culinarycultivation.event.SquidDropsEvent;
import net.minecraftforge.common.MinecraftForge;

import static com.Girafi.culinarycultivation.handler.ConfigurationHandler.ShouldSheepDropMutton;

public class Events {

    public static void init() {
        if (ShouldSheepDropMutton == true) { MinecraftForge.EVENT_BUS.register(new SheepDropsMutton()); }
        MinecraftForge.EVENT_BUS.register(new BabySheepDropsLamb());
        MinecraftForge.EVENT_BUS.register(new BabyCowDropsVeal());
        MinecraftForge.EVENT_BUS.register(new SquidDropsEvent());
    }
}
