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

import javax.annotation.Nonnull;

public class ItemSeedBag extends Item {

    public ItemSeedBag() {
        this.maxStackSize = 1;
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote && player.isSneaking()) {
            player.openGui(CulinaryCultivation.instance, GuiHandler.GuiID.SEED_BAG.ordinal(), world, 0, 0, 0);
        }
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }
}