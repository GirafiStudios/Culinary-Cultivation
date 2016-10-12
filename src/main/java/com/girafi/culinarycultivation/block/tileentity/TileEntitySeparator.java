package com.girafi.culinarycultivation.block.tileentity;

import com.girafi.culinarycultivation.block.BlockFanHousing;
import com.girafi.culinarycultivation.block.BlockSeparator;
import com.girafi.culinarycultivation.init.ModBlocks;
import com.girafi.culinarycultivation.init.recipes.WinnowingMachineRecipe;
import com.girafi.culinarycultivation.init.recipes.WinnowingMachineRecipes;
import com.girafi.culinarycultivation.util.InventoryHandlerHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class TileEntitySeparator extends TileEntityInventory implements ITickable, ISidedInventory {
    private boolean isMultiblockFormed;
    private int timer = 0;

    public TileEntitySeparator() {
        super(1);
    }

    @Override
    public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack) {
        return isMultiblockFormed && WinnowingMachineRecipes.instance().getProcessingResult(stack) != null;
    }

    @Override
    @Nonnull
    public int[] getSlotsForFace(@Nonnull EnumFacing side) {
        return new int[]{0};
    }

    @Override
    public boolean canInsertItem(int index, @Nonnull ItemStack stack, @Nonnull EnumFacing direction) {
        return direction == EnumFacing.UP && isItemValidForSlot(index, stack);
    }

    @Override
    public boolean canExtractItem(int index, @Nonnull ItemStack stack, @Nonnull EnumFacing direction) {
        return false;
    }

    public void checkForFanHousing() {
        //Reset
        isMultiblockFormed = false;

        IBlockState state = worldObj.getBlockState(pos);
        BlockPos left = pos.offset(state.getValue(BlockSeparator.FACING).rotateAround(Axis.Y));
        IBlockState leftState = worldObj.getBlockState(left);
        if (leftState.getBlock() == ModBlocks.FAN_HOUSING) {
            EnumFacing leftFacing = leftState.getValue(BlockFanHousing.FACING);
            EnumFacing thisFacing = state.getValue(BlockSeparator.FACING);
            if (leftFacing == thisFacing) {
                isMultiblockFormed = true;
            }
        }
        this.markDirty();
    }

    @Override
    public void update() {
        if (!worldObj.isRemote) {
            if (isMultiblockFormed) {
                if (worldObj.getTotalWorldTime() % 8 == 0) {
                    //Pull from Chest
                    if (!inputItems()) { //If there is a block above or it is full, don't try to pickup items
                        pickupItems();
                    }
                }
            }
        }
        /*if (worldObj.isRemote) { //TODO Particle
            EnumFacing facing = worldObj.getBlockState(pos).getValue(BlockSeparator.FACING).rotateY().getOpposite();
            worldObj.playEvent(2000, pos, facing.getFrontOffsetX() + 1 + (facing.getFrontOffsetZ() + 1) * 3);
        }*/
    }

    private boolean inputItems() {
        if (inventory[0] != null && inventory[0].stackSize >= 64) return true;
        IInventory inventory = getInventoryAbove();
        if (inventory != null && inventory.getSizeInventory() > 0) {
            if (inventory instanceof ISidedInventory) {
                int[] slots = ((ISidedInventory) inventory).getSlotsForFace(EnumFacing.DOWN);
                for (int i : slots) {
                    if (((ISidedInventory) inventory).canExtractItem(i, inventory.getStackInSlot(i), EnumFacing.DOWN)) {
                        if (removeFromAbove(inventory, i)) {
                            return true;
                        }
                    }
                }
            } else {
                for (int i = 0; i < inventory.getSizeInventory(); i++) {
                    if (removeFromAbove(inventory, i)) {
                        return true;
                    }
                }
            }
        }
        return inventory != null;
    }

    private void pickupItems() {
        for (EntityItem entityItem : getCaptureItems(worldObj, pos)) {
            ItemStack stack = entityItem.getEntityItem().copy();
            if (WinnowingMachineRecipes.instance().getProcessingResult(stack) != null) {
                if (inventory[0] == null) {
                    inventory[0] = stack;
                    entityItem.setDead();
                } else {
                    ItemStack ret = InventoryHandlerHelper.insertStackIntoInventory(this, stack, EnumFacing.UP);
                    if (ret == null || ret.stackSize <= 0) {
                        entityItem.setDead();
                    } else {
                        entityItem.setEntityItemStack(ret);
                    }
                }
            }
        }
    }

    public void onPowered() {
        ItemStack input = inventory[0];
        if (input != null) {
            if (timer >= 20) {
                timer = 0;

                WinnowingMachineRecipe recipe = WinnowingMachineRecipes.instance().getProcessingResult(input);
                if (recipe != null) {
                    IBlockState state = worldObj.getBlockState(getPos());
                    ItemStack output = recipe.getOutput().get(worldObj);
                    ItemStack junk = recipe.getJunk().get(worldObj);
                    EnumFacing facing = state.getValue(BlockFanHousing.FACING);
                    if (output != null) outputItems(output, getPos(), facing);
                    if (junk != null) outputItems(junk, getPos(), facing.rotateAround(Axis.Y).getOpposite());
                    ItemStackHelper.getAndSplit(inventory, 0, 1); //Decrease by 1
                }
            } else timer++;
        }
    }

    private void outputItems(ItemStack stack, BlockPos pos, EnumFacing facing) {
        TileEntity tileEntity = worldObj.getTileEntity(pos.offset(facing));
        if (tileEntity instanceof ISidedInventory && ((ISidedInventory) tileEntity).getSlotsForFace(facing).length > 0 || tileEntity instanceof IInventory && ((IInventory) tileEntity).getSizeInventory() > 0) {
            IInventory inventory = ((IInventory) tileEntity);
            stack = InventoryHandlerHelper.insertStackIntoInventory(inventory, stack, facing);
        }

        if (stack != null) {
            EntityItem ei = new EntityItem(worldObj, (double) facing.getFrontOffsetX() + pos.getX() + 0.5D, (double) pos.getY() + 0.15D, (double) facing.getFrontOffsetZ() + pos.getZ() + 0.5D, stack.copy());
            ei.motionX = (0.055F * facing.getFrontOffsetX());
            ei.motionY = 0.025D;
            ei.motionZ = (0.055F * facing.getFrontOffsetZ());
            worldObj.spawnEntityInWorld(ei);
        }
    }

    @Nullable
    private IInventory getInventoryAbove() {
        TileEntity tileEntity = worldObj.getTileEntity(pos.up());
        if (tileEntity instanceof IInventory) {
            Block block = tileEntity.getBlockType();
            if (tileEntity instanceof TileEntityChest && block instanceof BlockChest) {
                return ((BlockChest) block).getLockableContainer(worldObj, tileEntity.getPos());
            }
            return ((IInventory) tileEntity);
        }
        return null;
    }

    private boolean removeFromAbove(IInventory inventory, int slot) {
        ItemStack stack = inventory.getStackInSlot(slot);
        if (stack != null && (this.inventory[0] == null || stack.isItemEqual(this.inventory[0]))) {
            if (WinnowingMachineRecipes.instance().getProcessingResult(stack) != null) {
                ItemStack clone = stack.copy();
                clone.stackSize = stack.stackSize - 1;
                if (clone.stackSize <= 0) clone = null;
                inventory.setInventorySlotContents(slot, clone);
                int stackSize = this.inventory[0] == null ? 1 : this.inventory[0].stackSize + 1;
                setInventorySlotContents(0, new ItemStack(stack.getItem(), stackSize, stack.getItemDamage()));
                return true;
            }
        }
        return false;
    }

    private List<EntityItem> getCaptureItems(World world, BlockPos pos) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        return world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(x, y + 0.5D, z, x + 1.0D, y + 2.0D, z + 1.0D), EntitySelectors.IS_ALIVE);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        isMultiblockFormed = compound.getBoolean("IsMultiBlockFormed");
        timer = compound.getInteger("Timer");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setBoolean("IsMultiBlockFormed", isMultiblockFormed);
        compound.setInteger("Timer", timer);

        return compound;
    }
}