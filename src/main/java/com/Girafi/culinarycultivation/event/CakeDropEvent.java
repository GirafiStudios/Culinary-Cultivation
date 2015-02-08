package com.Girafi.culinarycultivation.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

public class CakeDropEvent { //TODO Fix this!

    @SubscribeEvent
    public void CakeDrop(HarvestDropsEvent dropsEvent){
            if (dropsEvent.harvester.canHarvestBlock(Blocks.cake)) {
                    dropsEvent.drops.add(new ItemStack(Items.cake));
            }
        }
    }