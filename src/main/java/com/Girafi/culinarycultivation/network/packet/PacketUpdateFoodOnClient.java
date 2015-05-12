package com.Girafi.culinarycultivation.network.packet;

import net.minecraft.entity.player.EntityPlayer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class PacketUpdateFoodOnClient extends Packet<PacketUpdateFoodOnClient> {

    private int amount;
    private float saturation;

    public PacketUpdateFoodOnClient(int amount, float saturation) {
        this.amount = amount;
        this.saturation = saturation;
    }

    public PacketUpdateFoodOnClient() {
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
        player.getFoodStats().addStats(amount, saturation);
    }

    @Override
    public void handleServerSide(EntityPlayer player) {
    }

    @Override
    public void read(DataInput buffer) throws IOException {
        amount = buffer.readInt();
        saturation = buffer.readFloat();
    }

    @Override
    public void write(DataOutput buffer) throws IOException {
        buffer.writeInt(amount);
        buffer.writeFloat(saturation);
    }
}