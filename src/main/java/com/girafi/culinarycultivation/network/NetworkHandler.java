package com.girafi.culinarycultivation.network;

import com.girafi.culinarycultivation.network.packet.PacketDebugItemMode;
import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
    public static final NetworkHandler INSTANCE = new NetworkHandler(Reference.MOD_ID);
    private final SimpleNetworkWrapper networkWrapper;

    private int lastDiscriminator = 0;

    public static NetworkHandler instance() {
        return INSTANCE;
    }

    private NetworkHandler(String modId) {
        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(modId);
    }

    public static void init() {
        INSTANCE.registerPacket(PacketDebugItemMode.class, Side.SERVER);
    }

    public <REQ extends IMessage, REPLY extends IMessage> void registerPacket(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side) {
        networkWrapper.registerMessage(messageHandler, requestMessageType, lastDiscriminator++, side);
    }

    private <REQ extends IMessage, REPLY extends IMessage> void registerPacket(Class packetType, Side side) {
        networkWrapper.registerMessage(packetType, packetType, lastDiscriminator++, side);
    }

    public void sendToServer(IMessage packet) {
        networkWrapper.sendToServer(packet);
    }

    public void sendTo(IMessage packet, EntityPlayerMP player) {
        networkWrapper.sendTo(packet, player);
    }

    public void sendToAllAround(IMessage packet, NetworkRegistry.TargetPoint point) {
        networkWrapper.sendToAllAround(packet, point);
    }

    public void sendToAll(IMessage packet) {
        networkWrapper.sendToAll(packet);
    }

    public void sendToDimension(IMessage packet, int dimensionId) {
        networkWrapper.sendToDimension(packet, dimensionId);
    }
}