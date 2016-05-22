package com.girafi.culinarycultivation.network.packet;

import com.girafi.culinarycultivation.item.equipment.tool.ItemDebugItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

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
            if (damage < 0) {
                damage += 5;
            }
            item.setItemDamage(damage);
        }
    }

    @Override
    public void toBytes(PacketBuffer buffer) {
        buffer.writeInt(slot);
        buffer.writeBoolean(direction);
    }

    @Override
    public void fromBytes(PacketBuffer buffer) {
        slot = buffer.readInt();
        direction = buffer.readBoolean();
    }
}