package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemCakeKnife;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.BlockCake;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

public class CakeDropsEvent { //Might add more to this later!


    public static class CakePickupEvent {
        @SubscribeEvent
        public void CakeDrop(HarvestDropsEvent DropsEvent) { //TODO Make either cake state return 1 cake slice, when right-clicked with the cake knife
            //TODO Fix that you can eat the cake by clicking, if not full hunger
            if (DropsEvent.harvester instanceof EntityPlayer) {
                EntityPlayer player = DropsEvent.harvester;
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.cakeKnife) {
                    if (DropsEvent.block instanceof BlockCake && DropsEvent.block == Blocks.cake) {
                        if (DropsEvent.blockMetadata == 0) {
                            DropsEvent.drops.add(new ItemStack(Items.cake));
                        }
                        if (DropsEvent.blockMetadata == 1) {
                            DropsEvent.drops.add(new ItemStack(ModItems.pieceOfCake, 5));
                        }
                        if (DropsEvent.blockMetadata == 2) {
                            DropsEvent.drops.add(new ItemStack(ModItems.pieceOfCake, 4));
                        }
                        if (DropsEvent.blockMetadata == 3) {
                            DropsEvent.drops.add(new ItemStack(ModItems.pieceOfCake, 3));
                        }
                        if (DropsEvent.blockMetadata == 4) {
                            DropsEvent.drops.add(new ItemStack(ModItems.pieceOfCake, 2));
                        }
                        if (DropsEvent.blockMetadata == 5) {
                            DropsEvent.drops.add(new ItemStack(ModItems.pieceOfCake));
                        }
                        if (DropsEvent.blockMetadata == 6) { //Will work in 1.8
                            DropsEvent.drops.add(new ItemStack(ModItems.pieceOfCake, 0));
                        }
                    }
                }
            }
        }
    }

    public static class CakeLeftClickEvent {

        @SubscribeEvent
        public void CakeLeftClick(PlayerInteractEvent IEvent) {
            if (IEvent.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
                EntityPlayer player = IEvent.entityPlayer;
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.cakeKnife) {
                    if (IEvent.world.getBlock(IEvent.x, IEvent.y, IEvent.z) instanceof BlockCake && IEvent.world.getBlock(IEvent.x, IEvent.y, IEvent.z) == Blocks.cake) {
                        if (player.getFoodStats().needFood()) {
                            player.getFoodStats().addStats(-2, 0.0F);
                        }
                    }
                }
            }
        }
    }

    public static class CakeRightClickEvent {
        @SubscribeEvent
        public void CakeRightClick(PlayerInteractEvent IEvent) {
            if (IEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                EntityPlayer player = IEvent.entityPlayer;
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.cakeKnife) {
                    if (IEvent.world.getBlock(IEvent.x, IEvent.y, IEvent.z) instanceof BlockCake && IEvent.world.getBlock(IEvent.x, IEvent.y, IEvent.z) == Blocks.cake) {
                        if (player.canEat(false)) {
                            player.getFoodStats().addStats(-2, 0.0F);
                            if (!IEvent.world.isRemote) {
                                IEvent.world.spawnEntityInWorld(new EntityItem(IEvent.world, IEvent.x, IEvent.y, IEvent.z, new ItemStack(ModItems.pieceOfCake)));
                            }
                        } else {
                            if (!IEvent.world.isRemote) {
                                IEvent.world.spawnEntityInWorld(new EntityItem(IEvent.world, IEvent.x, IEvent.y, IEvent.z, new ItemStack(ModItems.pieceOfCake)));
                                int l = IEvent.world.getBlockMetadata(IEvent.x, IEvent.y, IEvent.z) + 1;
                                if (l >= 6) {
                                    IEvent.world.setBlockToAir(IEvent.x, IEvent.y, IEvent.z);
                                } else {
                                    IEvent.world.setBlockMetadataWithNotify(IEvent.x, IEvent.y, IEvent.z, l, 2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}