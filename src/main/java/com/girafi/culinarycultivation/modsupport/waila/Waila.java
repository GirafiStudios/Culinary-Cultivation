package com.girafi.culinarycultivation.modsupport.waila;

import com.girafi.culinarycultivation.block.BlockDoubleCrop;
import com.girafi.culinarycultivation.modsupport.IModSupport;
import com.girafi.culinarycultivation.util.reference.SupportedModIDs;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class Waila implements IModSupport {
    @Override
    public void preInit() {
    }

    @Override
    public void init() {
        FMLInterModComms.sendMessage(SupportedModIDs.WAILA, "register", "com.girafi.culinarycultivation.modsupport.waila.Waila.callbackRegister");
    }

    @Override
    public void postInit() {
    }

    @Override
    public void clientSide() {
    }

    public static void callbackRegister(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(new WailaDoubleCropHandler(), BlockDoubleCrop.class);
    }
}