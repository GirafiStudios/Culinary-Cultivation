package com.Girafi.culinarycultivation.network.packet;

import com.Girafi.culinarycultivation.item.equipment.tool.ItemDebugItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class PacketDebugItemMode extends Packet<PacketDebugItemMode> {

    private int slot;
    private boolean direction;

    public PacketDebugItemMode(int slot, boolean direction) {
        this.slot = slot;
        this.direction = direction;
    }

    public PacketDebugItemMode() {
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
    }

    @Override
    public void handleServerSide(EntityPlayer player) {
        ItemStack item = player.inventory.getStackInSlot(slot);
        if (item != null && item.getItem() instanceof ItemDebugItem) {
            int damage = (item.getItemDamage() + (direction ? 1 : -1)) % 5;
            if (damage < 0)
                damage += 5;
            item.setItemDamage(damage);
        }
    }

    @Override
    public void read(DataInput buffer) throws IOException {
        slot = buffer.readInt();
        direction = buffer.readBoolean();
    }

    @Override
    public void write(DataOutput buffer) throws IOException {
        buffer.writeInt(slot);
        buffer.writeBoolean(direction);
    }
}