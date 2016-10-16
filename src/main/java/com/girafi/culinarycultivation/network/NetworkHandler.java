package com.girafi.culinarycultivation.network;

import com.girafi.culinarycultivation.network.packet.PacketChangeMode;
import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
    public static SimpleNetworkWrapper WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
    private static int lastDiscriminator = 0;

    private NetworkHandler(String modId) {
        WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(modId);
    }

    public static void init() {
        registerPacket(PacketChangeMode.class, Side.SERVER);
    }

    private static void registerPacket(Class packetType, Side side) {
        WRAPPER.registerMessage(packetType, packetType, lastDiscriminator++, side);
    }
}