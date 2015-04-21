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
        public void CakeDrop(HarvestDropsEvent dropsEvent) { //TODO Fix that you can eat the cake by clicking, if not full hunger
            if (dropsEvent.harvester instanceof EntityPlayer) {
                EntityPlayer player = dropsEvent.harvester;
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.cakeKnife) {
                    if (dropsEvent.block instanceof BlockCake && dropsEvent.block == Blocks.cake) {
                        if (dropsEvent.blockMetadata == 0) {
                            dropsEvent.drops.add(new ItemStack(Items.cake));
                        }
                        if (dropsEvent.blockMetadata == 1) {
                            dropsEvent.drops.add(new ItemStack(ModItems.pieceOfCake, 5));
                        }
                        if (dropsEvent.blockMetadata == 2) {
                            dropsEvent.drops.add(new ItemStack(ModItems.pieceOfCake, 4));
                        }
                        if (dropsEvent.blockMetadata == 3) {
                            dropsEvent.drops.add(new ItemStack(ModItems.pieceOfCake, 3));
                        }
                        if (dropsEvent.blockMetadata == 4) {
                            dropsEvent.drops.add(new ItemStack(ModItems.pieceOfCake, 2));
                        }
                        if (dropsEvent.blockMetadata == 5) {
                            dropsEvent.drops.add(new ItemStack(ModItems.pieceOfCake));
                        }
                        if (dropsEvent.blockMetadata == 6) { //Will work in 1.8
                            dropsEvent.drops.add(new ItemStack(ModItems.pieceOfCake, 0));
                        }
                    }
                }
            }
        }
    }

    public static class CakeLeftClickEvent {
        @SubscribeEvent
        public void CakeLeftClick(PlayerInteractEvent iEvent) {
            if (iEvent.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
                EntityPlayer player = iEvent.entityPlayer;
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.cakeKnife) {
                    if (iEvent.world.getBlock(iEvent.x, iEvent.y, iEvent.z) instanceof BlockCake && iEvent.world.getBlock(iEvent.x, iEvent.y, iEvent.z) == Blocks.cake) {
                        if (player.getFoodStats().needFood()) {
                            int prevFood = player.getFoodStats().getFoodLevel()- 2;
                            player.getFoodStats().setFoodLevel(prevFood);
                            player.getFoodStats().onUpdate(player);
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