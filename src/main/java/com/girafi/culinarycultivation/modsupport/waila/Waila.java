package com.girafi.culinarycultivation.modsupport.waila;

import com.girafi.culinarycultivation.modsupport.IModSupport;

public class Waila implements IModSupport {
    @Override
    public void preInit() {
    }

    @Override
    public void init() {
        //FMLInterModComms.sendMessage(SupportedModIDs.WAILA, "register", "com.girafi.culinarycultivation.modsupport.waila.Waila.callbackRegister");
    }

    @Override
    public void postInit() {
    }

    @Override
    public void clientSide() {
    }

    /*public static void callbackRegister(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(new WailaDoubleCropHandler(), BlockDoubleCrop.class);
    }*/
}