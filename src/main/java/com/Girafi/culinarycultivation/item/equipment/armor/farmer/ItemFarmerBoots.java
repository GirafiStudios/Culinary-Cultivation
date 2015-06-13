package com.Girafi.culinarycultivation.item.equipment.armor.farmer;

import codechicken.lib.math.MathHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFarmerBoots extends ItemFarmerArmor { //TODO Figure out what do on sneak, and what not to do (Maybe switch mode on sneak?)

    public ItemFarmerBoots() {
        super(3, "farmerBoots");
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        Block playerStandingOnBlock = player.worldObj.getBlock(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.boundingBox.minY) - 1, MathHelper.floor_double(player.posZ));
        int x = (int) player.posX;
        int y = (int) player.posY;
        int z = (int) player.posZ;

        if (player.onGround) {
            if (player.isSneaking()) {
                if (playerStandingOnBlock == Blocks.dirt || playerStandingOnBlock == Blocks.grass) {
                    world.playSoundEffect((double) (x + 0.5F), (double) (y + 0.5F), (double) (z + 0.5F), Blocks.farmland.stepSound.getStepResourcePath(), (Blocks.farmland.stepSound.getVolume() + 1.0F) / 2.0F, Blocks.farmland.stepSound.getPitch() * 0.8F);
                    if (!world.isRemote) {
                        world.setBlock(x, y - 1, z, Blocks.farmland);
                    }
                }
            }
            if (!player.isSneaking()) {
                if (playerStandingOnBlock == Blocks.farmland) {
                    if (!world.isRemote) {
                        world.setBlock(x, y - 1, z, Blocks.dirt);
                    }
                }
            }
        }
    }
}