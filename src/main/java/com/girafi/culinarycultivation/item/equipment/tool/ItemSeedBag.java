package com.girafi.culinarycultivation.item.equipment.tool;

import com.girafi.culinarycultivation.CulinaryCultivation;
import com.girafi.culinarycultivation.client.gui.GuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemSeedBag extends Item {

    public ItemSeedBag() {
        this.maxStackSize = 1;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote && player.isSneaking()) {
            player.openGui(CulinaryCultivation.instance, GuiHandler.GuiID.SEED_BAG.ordinal(), world, 0, 0, 0); //TODO Coords?
        }

        return new ActionResult<>(EnumActionResult.PASS, stack);
    }
}