package com.girafi.culinarycultivation.item.equipment.tool;

import com.girafi.culinarycultivation.CulinaryCultivation;
import com.girafi.culinarycultivation.client.gui.GuiHandler;
import com.girafi.culinarycultivation.inventory.SeedBagInventory;
import com.girafi.culinarycultivation.util.InventoryHandlerHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemSeedBag extends Item {

    public ItemSeedBag() {
        this.maxStackSize = 1;
        SeedBagInventory seedBagInventory = new SeedBagInventory(new ItemStack(this));
        this.setMaxDamage(seedBagInventory.getInventoryStackLimit());
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        if (!world.isRemote && player.isSneaking() && hand == EnumHand.MAIN_HAND) {
            player.openGui(CulinaryCultivation.instance, GuiHandler.GuiID.SEED_BAG.ordinal(), world, 0, 0, 0);
        }
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        if (!getSeedStack(stack).isEmpty()) {
            tooltip.add(TextFormatting.GREEN + getSeedStack(stack).getDisplayName());
            if (advanced) tooltip.add("");
        }

        if (advanced) {
            int maxDamage = this.getMaxDamage(stack);
            tooltip.add("Seeds: " + this.getSeedAmount(stack) + " / " + maxDamage);
        }
    }

    @Override
    public boolean showDurabilityBar(@Nonnull ItemStack stack) {
        return stack.getTagCompound() != null && this.getSeedAmount(stack) != 0;
    }

    @Override
    public double getDurabilityForDisplay(@Nonnull ItemStack stack) {
        return 1.0D - (double) this.getSeedAmount(stack) / (double) stack.getMaxDamage();
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        useSeedBag(player, world, pos, hand, player.getHeldItem(hand), facing);

        return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
    }

    public static EnumActionResult useSeedBag(EntityPlayer player, World world, BlockPos pos, EnumHand hand, @Nonnull ItemStack heldStack, EnumFacing facing) {
        if (player.isSneaking()) return EnumActionResult.PASS;

        SeedBagInventory seedBagInventory = new SeedBagInventory(heldStack);
        ItemStack seedStack = getSeedStack(player.getHeldItem(hand));

        if (!seedStack.isEmpty() && seedStack.getItem() instanceof IPlantable) {
            IPlantable plantable = (IPlantable) seedStack.getItem();
            IBlockState state = world.getBlockState(pos);
            if (state.getBlock().canSustainPlant(state, world, pos, EnumFacing.UP, plantable) && world.isAirBlock(pos.up())) {
                for (int i = 0; i < seedBagInventory.getSizeInventory(); i++) {
                    ItemStack slotStack = seedBagInventory.getStackInSlot(i);
                    if (!slotStack.isEmpty()) {
                        slotStack.onItemUse(player, world, pos, hand, facing, 0, 0, 0);
                        seedBagInventory.decrStackSize(i, 0);
                        return EnumActionResult.SUCCESS;
                    }
                }
            }
        }
        return EnumActionResult.PASS;
    }

    @SubscribeEvent
    public void pickupSeeds(EntityItemPickupEvent event) {
        boolean handled = false;
        EntityItem entityItem = event.getItem();
        if (entityItem != null) {
            ItemStack leftover = entityItem.getEntityItem();
            InventoryPlayer playerInv = event.getEntityPlayer().inventory;
            for (int i = 0; i < playerInv.getSizeInventory(); i++) {
                ItemStack playerSlotStack = playerInv.getStackInSlot(i);
                if (playerSlotStack.getItem() instanceof ItemSeedBag) {
                    SeedBagInventory seedBagInventory = new SeedBagInventory(playerSlotStack);
                    ItemStack original = leftover.copy();
                    leftover = InventoryHandlerHelper.insertStackIntoInventory(seedBagInventory, leftover, EnumFacing.DOWN, true);
                    if (!leftover.isEmpty()) {
                        if (leftover.isEmpty()) finishSeeds(event, entityItem, event.getEntityPlayer(), leftover);
                    } else return;

                    //We had seed bags
                    if (!handled) handled = original.getCount() != leftover.getCount();
                }
            }
            if (handled) finishSeeds(event, entityItem, event.getEntityPlayer(), leftover);
        }
    }

    private void finishSeeds(EntityItemPickupEvent event, EntityItem entity, EntityPlayer player, @Nullable ItemStack leftover) {
        entity.setDead();
        event.setCanceled(true);
        player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((player.world.rand.nextFloat() - player.world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
        if (leftover != null && !leftover.isEmpty()) {
            ItemHandlerHelper.giveItemToPlayer(player, leftover);
        }
    }

    private int getSeedAmount(@Nonnull ItemStack stack) {
        int amount = 0;
        SeedBagInventory seedBagInventory = new SeedBagInventory(stack);
        for (int i = 0; i < seedBagInventory.getSizeInventory(); i++) {
            ItemStack slotStack = seedBagInventory.getStackInSlot(i);
            if (!slotStack.isEmpty()) {
                amount += slotStack.getCount();
            }
        }
        return amount;
    }

    @Nonnull
    private static ItemStack getSeedStack(@Nonnull ItemStack stack) {
        ItemStack seedStack = ItemStack.EMPTY;
        SeedBagInventory seedBagInventory = new SeedBagInventory(stack);
        for (int i = 0; i < seedBagInventory.getSizeInventory(); i++) {
            seedStack = seedBagInventory.getStackInSlot(i);
        }
        return seedStack;
    }
}