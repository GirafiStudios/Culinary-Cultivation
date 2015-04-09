package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.BlockCake;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

public class CakeDropEvent { //Might add more to this later!

    @SubscribeEvent
    public void CakeDrop(HarvestDropsEvent DropsEvent) {
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
                        DropsEvent.drops.add(new ItemStack(ModItems.pieceOfCake, 6));
                    }
                }
            }
        }
    }
}