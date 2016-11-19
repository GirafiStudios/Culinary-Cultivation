package com.girafi.culinarycultivation.item.equipment.armor.farmer;

import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class ItemFarmerBoots extends ItemFarmerArmor {

    public ItemFarmerBoots() {
        super(EntityEquipmentSlot.FEET, "farmer_armor_boots");
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        super.onArmorTick(world, player, stack);

        Block playerStandingOnBlock = player.world.getBlockState(player.getPosition().down()).getBlock();

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

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format(Reference.MOD_ID + ".armorset.farmer.boots.desc"));
        tooltip.add("");
        super.addInformation(stack, player, tooltip, advanced);
    }
}