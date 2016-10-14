package com.girafi.culinarycultivation.item.equipment.tool;

import com.girafi.culinarycultivation.CulinaryCultivation;
import com.girafi.culinarycultivation.client.gui.GuiHandler;
import com.girafi.culinarycultivation.inventory.SeedBagInventory;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemSeedBag extends Item {

    public ItemSeedBag() {
        this.maxStackSize = 1;
        SeedBagInventory seedBagInventory = new SeedBagInventory(new ItemStack(this));
        this.setMaxDamage(seedBagInventory.getInventoryStackLimit());
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote && player.isSneaking()) {
            player.openGui(CulinaryCultivation.instance, GuiHandler.GuiID.SEED_BAG.ordinal(), world, 0, 0, 0);
        }
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        if (this.getSeedStack(stack) != null) {
            tooltip.add(TextFormatting.GREEN + this.getSeedStack(stack).getDisplayName());
            if (advanced) tooltip.add("");
        }

        if (advanced) {
            int maxDamage = this.getMaxDamage(stack);
            tooltip.add("Seeds: " + this.getSeedAmount(stack) + " / " + maxDamage);
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return stack.getTagCompound() != null && this.getSeedAmount(stack) != 0;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1.0D - (double) this.getSeedAmount(stack) / (double) stack.getMaxDamage();
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (player.isSneaking()) return EnumActionResult.PASS;

        SeedBagInventory seedBagInventory = new SeedBagInventory(player.getHeldItemMainhand());
        ItemStack seedStack = this.getSeedStack(stack);

        if (seedStack != null && seedStack.getItem() instanceof IPlantable) {
            IPlantable plantable = (IPlantable) seedStack.getItem();
            IBlockState state = world.getBlockState(pos);
            if (state.getBlock().canSustainPlant(state, world, pos, EnumFacing.UP, plantable) && world.isAirBlock(pos.up())) {
                for (int i = 0; i < seedBagInventory.getSizeInventory(); i++) {
                    ItemStack slotStack = seedBagInventory.getStackInSlot(i);
                    if (slotStack != null) {
                        slotStack.getItem().onItemUse(slotStack, player, world, pos, hand, facing, hitX, hitY, hitZ);
                        seedBagInventory.decrStackSize(i, 0);
                        return EnumActionResult.SUCCESS;
                    }
                }
            }
        }
        return EnumActionResult.PASS;
    }

    @SubscribeEvent
    public void pickupSeeds(EntityItemPickupEvent event) { //TODO Fix stuff about when it is almost full
        EntityItem entityItem = event.getItem();

        if (entityItem != null) {
            ItemStack entityStack = entityItem.getEntityItem();
            InventoryPlayer playerInv = event.getEntityPlayer().inventory;

            for (int i = 0; i < playerInv.getSizeInventory(); i++) {
                ItemStack playerSlotStack = playerInv.getStackInSlot(i);

                if (playerSlotStack != null && playerSlotStack.getItem() instanceof ItemSeedBag) {
                    SeedBagInventory seedBagInventory = new SeedBagInventory(playerSlotStack);
                    ItemStack slotStack = seedBagInventory.getStackInSlot(0);

                    if (seedBagInventory.isItemValidForSlot(0, entityStack) && slotStack != null && entityStack.getItem() == slotStack.getItem() && entityStack.getItemDamage() == slotStack.getItemDamage()) {
                        int stackSize = entityStack.stackSize;

                        if (!(slotStack.stackSize >= seedBagInventory.getInventoryStackLimit())) {
                            entityStack.stackSize = 0;
                            seedBagInventory.setInventorySlotContents(0, new ItemStack(slotStack.getItem(), slotStack.stackSize + stackSize, slotStack.getItemDamage()));
                        }
                    }
                }
            }
        }
    }

    private int getSeedAmount(ItemStack stack) {
        int amount = 0;
        SeedBagInventory seedBagInventory = new SeedBagInventory(stack);
        for (int i = 0; i < seedBagInventory.getSizeInventory(); i++) {
            ItemStack slotStack = seedBagInventory.getStackInSlot(i);
            if (slotStack != null) {
                amount += slotStack.stackSize;
            }
        }
        return amount;
    }

    private ItemStack getSeedStack(ItemStack stack) {
        ItemStack seedStack = null;
        SeedBagInventory seedBagInventory = new SeedBagInventory(stack);
        for (int i = 0; i < seedBagInventory.getSizeInventory(); i++) {
            seedStack = seedBagInventory.getStackInSlot(i);
        }
        return seedStack;
    }
}