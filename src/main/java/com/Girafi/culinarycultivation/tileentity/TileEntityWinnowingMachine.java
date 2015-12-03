package com.Girafi.culinarycultivation.tileentity;

import com.Girafi.culinarycultivation.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;

public class TileEntityWinnowingMachine extends SourceTileEntity implements ITickable, IInventory {

    private boolean isMultiblockFormed;
    private boolean isInvalidBlock;
    private int checkingX, checkingY, checkingZ;

    @Override
    public void update() {
        if (!worldObj.isRemote) {
            checkMultiblock();
            if (isMultiblockFormed) {
            }
        }
    }

    private void checkMultiblock() {
        checkingX++;
        if (checkingX > 1) {
            checkingX = -1;
            checkingY++;
            if (checkingY > 1) {
                checkingY = -1;
                checkingZ++;
                if (checkingZ > 1) {
                    checkingZ = -1;
                    isMultiblockFormed = !isInvalidBlock;
                    isInvalidBlock = false;
                    LogHelper.info("Formed: " + isMultiblockFormed);
                }
            }
        }

        if (checkingX == 0 && checkingY == 0 && checkingZ == 0) return;
        Block block = worldObj.getBlockState(new BlockPos(pos.getX() + checkingX, pos.getY() + checkingY, pos.getZ() + checkingZ)).getBlock();

        boolean x = (checkingX == 1 && checkingY == 0 && checkingZ == 0);
        boolean x_1 = (checkingX == -1 && checkingY == 0 && checkingZ == 0);
        boolean z = (checkingX == 0 && checkingY == 0 && checkingZ == 1);
        boolean z_1 = (checkingX == 0 && checkingY == 0 && checkingZ == -1);

        if (checkingX == 0 && checkingY == 1 && checkingZ == 0) {
            if (block != Blocks.hopper) {
                isInvalidBlock = true;
            }
        } else if (checkingX == 1 && checkingY == 0 && checkingZ == 0) {
            if (!(block instanceof BlockPlanks)) {
                isInvalidBlock = true;
            }
        }
    }

    public void outputSeed(ItemStack stack) {

    }

    public void outputJunk(ItemStack stack) {

    }

    @Override
    public int getSizeInventory() {
        return isMultiblockFormed ? 1 : 0;
    }

    /**
     * Returns the stack in the given slot.
     *
     * @param index The slot to retrieve from.
     */
    @Override
    public ItemStack getStackInSlot(int index) {
        return null;
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     *
     * @param index The slot to remove from.
     * @param count The maximum amount of items to remove.
     */
    @Override
    public ItemStack decrStackSize(int index, int count) {
        return null;
    }

    /**
     * Removes a stack from the given slot and returns it.
     *
     * @param index The slot to remove a stack from.
     */
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return null;
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     *
     * @param index
     * @param stack
     */
    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {

    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
     */
    @Override
    public int getInventoryStackLimit() {
        return 0;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     *
     * @param player
     */
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

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     *
     * @param index
     * @param stack
     */
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
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

    }

    /**
     * Gets the name of this command sender (usually username, but possibly "Rcon")
     */
    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    /**
     * Get the formatted ChatComponent that will be used for the sender's username in chat
     */
    @Override
    public IChatComponent getDisplayName() {
        return null;
    }
}