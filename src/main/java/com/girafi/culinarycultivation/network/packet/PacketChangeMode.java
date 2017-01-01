package com.girafi.culinarycultivation.network.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;
import java.io.IOException;

public class PacketChangeMode extends Packet<PacketChangeMode> {
    private int damage;
    @Nonnull
    private ItemStack stack = ItemStack.EMPTY;
    private int slot;
    private boolean direction;

    public PacketChangeMode() {
    }

    public PacketChangeMode(int damage, @Nonnull ItemStack stack, int slot, boolean direction) {
        this.damage = damage + 1;
        this.stack = stack;
        this.slot = slot;
        this.direction = direction;
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
    }

    @Override
    public void handleServerSide(EntityPlayer player) {
        ItemStack slotStack = player.inventory.getStackInSlot(slot);
        if (!slotStack.isEmpty() && slotStack.getItem() == this.stack.getItem()) {
            int damage = (slotStack.getItemDamage() + (direction ? 1 : -1)) % this.damage;
            if (damage < 0) {
                damage += this.damage;
            }
            slotStack.setItemDamage(damage);
        }
    }

    @Override
    public void toBytes(PacketBuffer buffer) {
        buffer.writeInt(damage);
        buffer.writeItemStack(stack);
        buffer.writeInt(slot);
        buffer.writeBoolean(direction);
    }

    @Override
    public void fromBytes(PacketBuffer buffer) throws IOException {
        damage = buffer.readInt();
        stack = buffer.readItemStack();
        slot = buffer.readInt();
        direction = buffer.readBoolean();
    }
}