package com.girafi.culinarycultivation.item.equipment.armor.farmer;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import java.util.Random;

public class ItemFarmerBoots extends ItemFarmerArmor {

    public ItemFarmerBoots() {
        super(EntityEquipmentSlot.FEET, "farmerArmorBoots");
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        Block playerStandingOnBlock = player.worldObj.getBlockState(player.getPosition().down()).getBlock();

        if (player.onGround) {
            if (player.isSneaking()) {
                if (playerStandingOnBlock == Blocks.FARMLAND) {
                    world.playSound(player, (player.posX + 0.5F), (player.posY + 0.5F), (player.posZ + 0.5F), SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    if (!world.isRemote) {
                        world.setBlockState(player.getPosition().down(), Blocks.DIRT.getDefaultState());
                    }
                    stack.attemptDamageItem(1, new Random(5));
                }
            }
        }
    }
}