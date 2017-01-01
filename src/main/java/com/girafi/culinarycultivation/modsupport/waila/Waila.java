package com.girafi.culinarycultivation.modsupport.waila;

import com.girafi.culinarycultivation.modsupport.IModSupport;
import com.girafi.culinarycultivation.util.reference.SupportedModIDs;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class Waila implements IModSupport {

    @Override
    public void clientSide() {
        FMLInterModComms.sendMessage(SupportedModIDs.WAILA, "register", "com.girafi.culinarycultivation.modsupport.waila.Waila.callbackRegister");
    }

    public static void callbackRegister(IWailaRegistrar registrar) {
        WailaHUDHandler.register();
    }
}