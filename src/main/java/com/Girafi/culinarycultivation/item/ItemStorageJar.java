package com.Girafi.culinarycultivation.item;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemStorageJar extends SourceItem {

    public ItemStorageJar() {
        this.setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
    }

    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(worldIn, playerIn, true);

        if (movingobjectposition == null) {
            return itemStackIn;
        } else {
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                int i = movingobjectposition.blockX;
                int j = movingobjectposition.blockY;
                int k = movingobjectposition.blockZ;

                if (!worldIn.canMineBlock(playerIn, i, j, k)) {
                    return itemStackIn;
                }

                if (!playerIn.canPlayerEdit(i, j, k, movingobjectposition.sideHit, itemStackIn)) {
                    return itemStackIn;
                }

                if (worldIn.getBlock(i, j, k).getMaterial() == Material.water) {
                    --itemStackIn.stackSize;

                    if (itemStackIn.stackSize <= 0) {
                        return new ItemStack(Items.potionitem);
                    }

                    if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.potionitem))) {
                        playerIn.dropPlayerItemWithRandomChoice(new ItemStack(Items.potionitem, 1, 0), false);
                    }
                }
            }

            return itemStackIn;
        }
    }
}