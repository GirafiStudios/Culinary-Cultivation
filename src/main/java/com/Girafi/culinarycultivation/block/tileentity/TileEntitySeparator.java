package com.Girafi.culinarycultivation.block.tileentity;

import com.Girafi.culinarycultivation.block.BlockFanHousing;
import com.Girafi.culinarycultivation.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class TileEntitySeparator extends TileEntity implements ITickable, IInventory {
    private ItemStack[] separatorContents = new ItemStack[1];
    private boolean isMultiblockFormed;
    private boolean isInvalidBlock;
    private int checkingX, checkingZ;

    @Override
    public void update() {
        if (!worldObj.isRemote) {
            this.checkForFanHousing();
        }
        if (isMultiblockFormed) {
            ArrayList<ItemStack> out = new ArrayList<ItemStack>();
            ItemStack outputStack = null;
            for (int i = 0; i < separatorContents.length; ++i) {
                outputStack = this.getStackInSlot(i);
            }
            if (outputStack != null) {
                out.add(outputStack);
            }
            this.outputItems(out, EnumFacing.getFront(this.getBlockMetadata()));
        }
    }

    private void checkForFanHousing() {
        checkingX++;
        if (checkingX > 1) {
            checkingZ++;
            if (checkingZ > 1) {
                checkingZ = -1;
                isMultiblockFormed = !isInvalidBlock;
                isInvalidBlock = false;
            }
        }

        IBlockState state = worldObj.getBlockState(pos);
        IBlockState stateFront = worldObj.getBlockState(pos.offset(EnumFacing.getFront(this.getBlockMetadata()).rotateY()));
        if (!(stateFront.getBlock() == ModBlocks.fanHousing && stateFront.getValue(BlockFanHousing.FACING) == EnumFacing.getFront(state.getBlock().getMetaFromState(state)))) {
            isInvalidBlock = true;
        }
    }

    @Override
    public int getSizeInventory() {
        if (!isMultiblockFormed) {
            return 0;
        }
        return this.separatorContents.length;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.separatorContents[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.separatorContents, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.separatorContents, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (!isMultiblockFormed) {
            return;
        }
        this.separatorContents[index] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) { //TODO Make only items/blocks that have an output be able to get into the separator (Or just spit it out if it's not valid)
        return isMultiblockFormed;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.separatorContents.length; ++i) {
            this.separatorContents[i] = null;
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }

    private void outputItems(List<ItemStack> stacks, EnumFacing facing) {
        TileEntity tileEntity = this.worldObj.getTileEntity(pos.offset(facing));

        for (int i = 0; i < stacks.size(); i++) {
            ItemStack stack = stacks.get(i);
            if (stack == null) {
                continue;
            }
            if (tileEntity instanceof ISidedInventory && ((ISidedInventory) tileEntity).getSlotsForFace(facing).length > 0 || tileEntity instanceof IInventory && ((IInventory) tileEntity).getSizeInventory() > 0) {
                IInventory inventory = ((IInventory) tileEntity);
                stack = insertStackIntoInventory(inventory, stack, facing);
                ItemStackHelper.getAndRemove(this.separatorContents, i);
            }
            if (stack != null) {
                EntityItem ei = new EntityItem(worldObj, (double) facing.getFrontOffsetX() + pos.getX() + 0.5D, (double) pos.getY() + 0.15D, (double) facing.getFrontOffsetZ() + pos.getZ() + 0.5D, stack.copy());
                ei.motionX = (0.055F * facing.getFrontOffsetX());
                ei.motionY = 0.025D;
                ei.motionZ = (0.055F * facing.getFrontOffsetZ());
                this.worldObj.spawnEntityInWorld(ei);
                ItemStackHelper.getAndRemove(this.separatorContents, i);
            }
        }
    }

    private static ItemStack insertStackIntoInventory(IInventory inventory, ItemStack stack, EnumFacing facing) {
        if (stack == null || inventory == null) {
            return null;
        }
        int stackSize = stack.stackSize;
        if (inventory instanceof ISidedInventory) {
            ISidedInventory sidedInv = (ISidedInventory) inventory;
            int slots[] = sidedInv.getSlotsForFace(facing);
            if (slots == null) {
                return stack;
            }
            for (int i = 0; i < slots.length && stack != null; i++) {
                ItemStack existingStack = inventory.getStackInSlot(slots[i]);
                if (existingStack == null) {
                    continue;
                }
                ItemStack toInsert = copyStackWithAmount(stack, Math.min(existingStack.getMaxStackSize(), inventory.getInventoryStackLimit()) - existingStack.stackSize);
                if (sidedInv.canInsertItem(slots[i], toInsert, facing)) {
                    if (OreDictionary.itemMatches(existingStack, stack, true) && ItemStack.areItemStackTagsEqual(stack, existingStack))
                        stack = addToOccupiedSlot(sidedInv, slots[i], stack, existingStack);
                }
            }
            for (int i = 0; i < slots.length && stack != null; i++)
                if (inventory.getStackInSlot(slots[i]) == null && sidedInv.canInsertItem(slots[i], copyStackWithAmount(stack, inventory.getInventoryStackLimit()), facing)) {
                    stack = addToEmptyInventorySlot(sidedInv, slots[i], stack);
                }
        } else {
            int invSize = inventory.getSizeInventory();
            for (int i = 0; i < invSize && stack != null; i++) {
                ItemStack existingStack = inventory.getStackInSlot(i);
                if (OreDictionary.itemMatches(existingStack, stack, true) && ItemStack.areItemStackTagsEqual(stack, existingStack)) {
                    stack = addToOccupiedSlot(inventory, i, stack, existingStack);
                }
            }
            for (int i = 0; i < invSize && stack != null; i++)
                if (inventory.getStackInSlot(i) == null) {
                    stack = addToEmptyInventorySlot(inventory, i, stack);
                }
        }
        if (stack == null || stack.stackSize != stackSize) {
            inventory.markDirty();
        }
        return stack;
    }

    private static ItemStack addToEmptyInventorySlot(IInventory inventory, int slot, ItemStack stack) {
        if (!inventory.isItemValidForSlot(slot, stack)) {
            return stack;
        }
        int stackLimit = inventory.getInventoryStackLimit();
        inventory.setInventorySlotContents(slot, copyStackWithAmount(stack, Math.min(stack.stackSize, stackLimit)));
        return stackLimit >= stack.stackSize ? null : stack.splitStack(stack.stackSize - stackLimit);
    }

    private static ItemStack addToOccupiedSlot(IInventory inventory, int slot, ItemStack stack, ItemStack existingStack) {
        int stackLimit = Math.min(inventory.getInventoryStackLimit(), stack.getMaxStackSize());
        if (stack.stackSize + existingStack.stackSize > stackLimit) {
            int stackDiff = stackLimit - existingStack.stackSize;
            existingStack.stackSize = stackLimit;
            stack = copyStackWithAmount(stack, stack.stackSize - stackDiff);
            inventory.setInventorySlotContents(slot, existingStack);
            return stack;
        }
        existingStack.stackSize += Math.min(stack.stackSize, stackLimit);
        inventory.setInventorySlotContents(slot, existingStack);
        return null;
    }

    private static ItemStack copyStackWithAmount(ItemStack stack, int amount) {
        if (stack == null) {
            return null;
        }
        ItemStack copyStack = stack.copy();
        copyStack.stackSize = amount;
        return copyStack;
    }
}