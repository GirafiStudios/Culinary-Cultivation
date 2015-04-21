package com.Girafi.culinarycultivation.utility;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Helper {
    public static void addInInventory(EntityPlayer playerIn, ItemStack stack) {
        if (!playerIn.inventory.addItemStackToInventory(stack)) {
            if (!playerIn.worldObj.isRemote){
                spawnItem(playerIn.worldObj, playerIn.posX, playerIn.posY, playerIn.posZ, stack);
            }
        }
    }
    public static void spawnItem(World world, double x, double y, double z, ItemStack stack) {
        spawnItem(world, x, y, z, stack);
    }
}
