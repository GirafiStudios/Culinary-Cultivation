package com.Girafi.culinarycultivation.item.equipment.armor.farmer;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class ItemFarmerBoots extends ItemFarmerArmor {

    public ItemFarmerBoots() {
        super(3, "farmerBoots");
    }

    public String armorModelFile() {
        return "farmerArmorBoots.png";
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        Block playerStandingOnBlock = player.worldObj.getBlockState(player.getPosition().down()).getBlock();

        if (player.onGround) {
            if (player.isSneaking()) {
                if (playerStandingOnBlock == Blocks.farmland) {
                    world.playSoundEffect((player.posX + 0.5F), (player.posY + 0.5F), (player.posZ + 0.5F), Blocks.dirt.stepSound.getStepSound(), (Blocks.dirt.stepSound.getVolume() + 1.0F) / 2.0F, Blocks.dirt.stepSound.getFrequency() * 0.8F);
                    if (!world.isRemote) {
                        world.setBlockState(player.getPosition().down(), Blocks.dirt.getDefaultState());
                    }
                    stack.attemptDamageItem(1, new Random(5));
                }
            }
        }
    }
}