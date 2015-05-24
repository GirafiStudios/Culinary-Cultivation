package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemStorageJar.*;
import com.Girafi.culinarycultivation.network.NetworkHandler;
import com.Girafi.culinarycultivation.network.packet.PacketUpdateFoodOnClient;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.BlockCake;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class InteractEvents {

    public static class CakeLeftClickEvent {
        @SubscribeEvent
        public void CakeLeftClick(PlayerInteractEvent iEvent) {
            if (iEvent.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
                EntityPlayer player = iEvent.entityPlayer;
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.cakeKnife) {
                    if (iEvent.world.getBlock(iEvent.x, iEvent.y, iEvent.z) instanceof BlockCake && iEvent.world.getBlock(iEvent.x, iEvent.y, iEvent.z) == Blocks.cake) {
                        int meta = iEvent.world.getBlockMetadata(iEvent.x, iEvent.y, iEvent.z);
                        boolean b = player.onGround && player.isSneaking();
                        if (b && iEvent.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
                            if (player.getFoodStats().needFood()) {
                                NetworkHandler.instance.sendTo(new PacketUpdateFoodOnClient(-2, -0.1F), (EntityPlayerMP) player);
                            }
                            iEvent.world.setBlockToAir(iEvent.x, iEvent.y, iEvent.z);
                            if (meta == 0) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, iEvent.x, iEvent.y, iEvent.z, new ItemStack(Items.cake)));
                            }
                            if (meta == 1) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, iEvent.x, iEvent.y, iEvent.z, new ItemStack(ModItems.pieceOfCake, 5)));
                            }
                            if (meta == 2) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, iEvent.x, iEvent.y, iEvent.z, new ItemStack(ModItems.pieceOfCake, 4)));
                            }
                            if (meta == 3) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, iEvent.x, iEvent.y, iEvent.z, new ItemStack(ModItems.pieceOfCake, 3)));
                            }
                            if (meta == 4) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, iEvent.x, iEvent.y, iEvent.z, new ItemStack(ModItems.pieceOfCake, 2)));
                            }
                            if (meta == 5) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, iEvent.x, iEvent.y, iEvent.z, new ItemStack(ModItems.pieceOfCake, 1)));
                            }
                        }
                    }
                }
            }
        }
    }

    public static class CakeRightClickEvent {
        @SubscribeEvent
        public void CakeRightClick(PlayerInteractEvent iEvent) {
            if (iEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                EntityPlayer player = iEvent.entityPlayer;
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.cakeKnife) {
                    if (iEvent.world.getBlock(iEvent.x, iEvent.y, iEvent.z) instanceof BlockCake && iEvent.world.getBlock(iEvent.x, iEvent.y, iEvent.z) == Blocks.cake) {
                        boolean b = player.onGround && player.isSneaking();
                        if (!b && iEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                            if (player.canEat(false)) {
                                player.getFoodStats().addStats(-2, 0.0F);
                                if (!iEvent.world.isRemote) {
                                    iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, iEvent.x, iEvent.y, iEvent.z, new ItemStack(ModItems.pieceOfCake)));
                                }
                            } else {
                                if (!iEvent.world.isRemote) {
                                    iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, iEvent.x, iEvent.y, iEvent.z, new ItemStack(ModItems.pieceOfCake)));
                                    int l = iEvent.world.getBlockMetadata(iEvent.x, iEvent.y, iEvent.z) + 1;
                                    if (l >= 6) {
                                        iEvent.world.setBlockToAir(iEvent.x, iEvent.y, iEvent.z);
                                    } else {
                                        iEvent.world.setBlockMetadataWithNotify(iEvent.x, iEvent.y, iEvent.z, l, 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static class CauldronTransformation { //Works for now?
        @SubscribeEvent
        public void CauldronTransformation(PlayerInteractEvent iEvent) {
            if (iEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                EntityPlayer player = iEvent.entityPlayer;
                int meta = iEvent.world.getBlockMetadata(iEvent.x, iEvent.y, iEvent.z);
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.milk_bucket) //Or Rennet bucket! {
                    if (iEvent.world.getBlock(iEvent.x, iEvent.y, iEvent.z) == Blocks.cauldron && meta <= 0) {
                        iEvent.world.setBlock(iEvent.x, iEvent.y, iEvent.z, ModBlocks.cauldron);
                    }
            }
        }
    }

    public static class StorageJarMilkFill {
        @SubscribeEvent
        public void StorageJarMiliFillEvent(EntityInteractEvent iEvent) {
            ItemStack stack = iEvent.entityPlayer.inventory.getCurrentItem();
            if (iEvent.target instanceof EntityCow & !iEvent.entityLiving.isChild()) {
                if (stack != null && stack.getItem() == ModItems.storageJar && stack.getItemDamage() == StorageJarType.EMPTY.getMetaData() && !iEvent.entityPlayer.capabilities.isCreativeMode) {
                    if (stack.stackSize-- == 1) {
                        iEvent.entityPlayer.inventory.setInventorySlotContents(iEvent.entityPlayer.inventory.currentItem, new ItemStack(ModItems.storageJar, 1, StorageJarType.MILK.getMetaData()));
                    } else if (!iEvent.entityPlayer.inventory.addItemStackToInventory(new ItemStack(ModItems.storageJar, 1, StorageJarType.MILK.getMetaData()))) {
                        iEvent.entityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.storageJar, 1, StorageJarType.MILK.getMetaData()), false);
                    }
                }
            }
        }
    }
}