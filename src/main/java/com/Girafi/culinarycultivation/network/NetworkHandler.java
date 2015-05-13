package com.Girafi.culinarycultivation.network;

import com.Girafi.culinarycultivation.network.packet.PacketDebugItemMode;
import com.Girafi.culinarycultivation.network.packet.PacketUpdateFoodOnClient;
import com.Girafi.culinarycultivation.reference.Reference;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class NetworkHandler {
    public static final NetworkHandler instance = new NetworkHandler(Reference.MOD_ID);
    public final SimpleNetworkWrapper networkWrapper;

    private int lastDiscriminator = 0;

    public static NetworkHandler instance() {return instance;}

    public NetworkHandler(String modid) {networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(modid);}

    public static void init() {
        instance.registerPacket(PacketDebugItemMode.class, Side.SERVER);
        instance.registerPacket(PacketUpdateFoodOnClient.class, Side.CLIENT);
    }

    public void registerPacket(Class packetHandler, Class packetType, Side side) {networkWrapper.registerMessage(packetHandler, packetType, lastDiscriminator++, side);}

    public void registerPacket(Class packetType, Side side) {networkWrapper.registerMessage(packetType, packetType, lastDiscriminator++, side); }

    public void sendToServer(IMessage packet) {networkWrapper.sendToServer(packet);}

    public void sendTo(IMessage packet, EntityPlayerMP player) {networkWrapper.sendTo(packet, player);}

    public void sendToAllAround(IMessage packet, NetworkRegistry.TargetPoint point) {networkWrapper.sendToAllAround(packet, point); }

    public void sendToAll(IMessage packet) {networkWrapper.sendToAll(packet);}

    public void sendToDimension(IMessage packet, int dimensionId) {networkWrapper.sendToDimension(packet, dimensionId);}
}