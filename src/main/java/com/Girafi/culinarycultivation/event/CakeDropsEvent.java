package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.network.NetworkHandler;
import com.Girafi.culinarycultivation.network.packet.PacketUpdateFoodOnClient;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCake;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

public class CakeDropsEvent {


    public static class CakePickupEvent {
        @SubscribeEvent
        public void CakeDrop(BlockEvent.HarvestDropsEvent dropsEvent) {
            if (dropsEvent.harvester instanceof EntityPlayer) {
                int meta = dropsEvent.blockMetadata;
                EntityPlayer player = dropsEvent.harvester;
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.cakeKnife) {
                    if (dropsEvent.block instanceof BlockCake && dropsEvent.block == Blocks.cake) {
                        if (meta == 0) {
                            dropsEvent.drops.add(new ItemStack(Items.cake));
                        }
                        if (meta == 1) {
                            dropsEvent.drops.add(new ItemStack(ModItems.pieceOfCake, 5));
                        }
                        if (meta == 2) {
                            dropsEvent.drops.add(new ItemStack(ModItems.pieceOfCake, 4));
                        }
                        if (meta == 3) {
                            dropsEvent.drops.add(new ItemStack(ModItems.pieceOfCake, 3));
                        }
                        if (meta == 4) {
                            dropsEvent.drops.add(new ItemStack(ModItems.pieceOfCake, 2));
                        }
                        if (meta == 5) {
                            dropsEvent.drops.add(new ItemStack(ModItems.pieceOfCake));
                        }
                    }
                }
            }
        }
    }

    public static class CakeLeftClickEvent {
        @SubscribeEvent
        public void CakeLeftClick(PlayerInteractEvent iEvent) {
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

    public static class CakeRightClickEvent {
        @SubscribeEvent
        public void CakeRightClick(PlayerInteractEvent iEvent) {
            if (iEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                EntityPlayer player = iEvent.entityPlayer;
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.cakeKnife) {
                    if (iEvent.world.getBlock(iEvent.x, iEvent.y, iEvent.z) instanceof BlockCake && iEvent.world.getBlock(iEvent.x, iEvent.y, iEvent.z) == Blocks.cake) {
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