package com.Girafi.culinarycultivation.modSupport.thaumcraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.wands.IWandTriggerManager;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.items.wands.ItemWandCasting;

public class CrucibleSupport implements IWandTriggerManager {

    @Override
    public boolean performTrigger(World world, ItemStack stack, EntityPlayer player, int x, int y, int z, int side, int event) {
            ItemWandCasting wand = (ItemWandCasting)stack.getItem();
            if(!world.isRemote) {
                world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "thaumcraft:wand", 1.0F, 1.0F);
                world.setBlockToAir(x, y, z);
                world.setBlock(x, y, z, ConfigBlocks.blockMetalDevice, 0, 3);
                world.notifyBlocksOfNeighborChange(x, y, z, world.getBlock(x, y, z));
                world.markBlockForUpdate(x, y, z);
                world.addBlockEvent(x, y, z, ConfigBlocks.blockMetalDevice, 1, 1);
                return true;
            } else {
                return false;
        }
    }
}
