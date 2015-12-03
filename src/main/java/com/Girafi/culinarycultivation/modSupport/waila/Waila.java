package com.Girafi.culinarycultivation.modSupport.waila;

import com.Girafi.culinarycultivation.block.BlockDoubleCrop;
import com.Girafi.culinarycultivation.modSupport.IModSupport;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class Waila implements IModSupport {
    @Override
    public void preInit() {
    }

    @Override
    public void init() {
        FMLInterModComms.sendMessage("Waila", "register", "com.Girafi.culinarycultivation.modSupport.waila.Waila.callbackRegister");
    }

    @Override
    public void postInit() {
    }

    @Override
    public void clientSide() {
    }

    @Override
    public void clientInit() {
    }

    /*public static void callbackRegister(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(new WailaDoubleCropHandler(), BlockDoubleCrop.class);
    }*/
}