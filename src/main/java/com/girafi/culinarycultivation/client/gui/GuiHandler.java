package com.girafi.culinarycultivation.client.gui;

import com.girafi.culinarycultivation.client.gui.inventory.GuiSeedBag;
import com.girafi.culinarycultivation.inventory.ContainerSeedBag;
import com.girafi.culinarycultivation.inventory.InventoryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        switch (GuiID.values()[ID]) {
            case SEED_BAG:
                return new ContainerSeedBag(player.inventory, new InventoryItem(player.getHeldItemMainhand(), "seed_bag", 1, 1280), player);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        switch (GuiID.values()[ID]) {
            case SEED_BAG:
                return new GuiSeedBag(player.inventory, new InventoryItem(player.getHeldItemMainhand(), "seed_bag", 1, 1280));
        }

        return null;
    }

    public enum GuiID {
        SEED_BAG
    }
}