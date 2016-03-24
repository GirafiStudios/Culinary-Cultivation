package com.Girafi.culinarycultivation.block.tileentity;

import com.Girafi.culinarycultivation.block.BlockFanHousing;
import com.Girafi.culinarycultivation.block.BlockSeparator;
import com.Girafi.culinarycultivation.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.List;

public class TileEntitySeparator extends TileEntity implements IHopper, ITickable {
    public static boolean isMultiblockFormed;
    private boolean isInvalidBlock;
    private int checkingX, checkingZ;
    private ItemStack[] inventory = new ItemStack[1];
    private String customName;
    private int transferCooldown = -1;

    @Override
    public void update() {
        if (worldObj != null && !worldObj.isRemote) {
            checkForFanHousing();
            --this.transferCooldown;
            if (isMultiblockFormed) {
                if (!this.isOnTransferCooldown()) {
                    this.setTransferCooldown(0);
                    this.updateHopper();
                }
            }
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

        if (checkingX == 0 && checkingZ == 0) return;

        IBlockState state = worldObj.getBlockState(pos);
        IBlockState stateFront = worldObj.getBlockState(pos.offset(EnumFacing.getFront(this.getBlockMetadata()).rotateY()));

        if (!(stateFront.getBlock() == ModBlocks.fanHousing && stateFront.getValue(BlockFanHousing.FACING) == EnumFacing.getFront(state.getBlock().getMetaFromState(state)))) {
            isInvalidBlock = true;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];

        this.transferCooldown = compound.getInteger("TransferCooldown");

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot");

            if (j >= 0 && j < this.inventory.length) {
                this.inventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.inventory.length; ++i) {
            if (this.inventory[i] != null) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) i);
                this.inventory[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        compound.setTag("Items", nbttaglist);
        compound.setInteger("TransferCooldown", this.transferCooldown);
    }

    @Override
    public void markDirty() {
        super.markDirty();
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.inventory[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (this.inventory[index] != null) {
            if (this.inventory[index].stackSize <= count) {
                ItemStack itemstack1 = this.inventory[index];
                this.inventory[index] = null;
                return itemstack1;
            } else {
                ItemStack itemstack = this.inventory[index].splitStack(count);

                if (this.inventory[index].stackSize == 0) {
                    this.inventory[index] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        if (this.inventory[index] != null) {
            ItemStack itemstack = this.inventory[index];
            this.inventory[index] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.inventory[index] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : "container.hopper";
    }

    @Override
    public boolean hasCustomName() {
        return this.customName != null && this.customName.length() > 0;
    }

    @Override
    public ITextComponent getDisplayName() {
        return (this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), 0));
    }

    public void setCustomName(String customNameIn) {
        this.customName = customNameIn;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    private boolean updateHopper() {
        if (this.worldObj != null && !this.worldObj.isRemote) {
            if (!this.isOnTransferCooldown() && BlockSeparator.isEnabled(this.getBlockMetadata())) {
                boolean flag = false;

                if (!this.isEmpty()) {
                    flag = this.transferItemsOut();
                }

                if (!this.isFull()) {
                    flag = captureDroppedItems(this) || flag;
                }

                if (flag) {
                    this.setTransferCooldown(8);
                    this.markDirty();
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    private boolean isEmpty() {
        for (ItemStack itemstack : this.inventory) {
            if (itemstack != null) {
                return false;
            }
        }

        return true;
    }

    private boolean isFull() {
        for (ItemStack itemstack : this.inventory) {
            if (itemstack == null || itemstack.stackSize != itemstack.getMaxStackSize()) {
                return false;
            }
        }

        return true;
    }

    private boolean transferItemsOut() {
        IInventory iinventory = this.getInventoryForHopperTransfer();

        if (iinventory == null) {
            return false;
        } else {
            EnumFacing enumfacing = EnumFacing.getFront(this.getBlockMetadata()).getOpposite();

            if (this.isInventoryFull(iinventory, enumfacing)) {
                return false;
            } else {
                for (int i = 0; i < this.getSizeInventory(); ++i) {
                    if (this.getStackInSlot(i) != null) {
                        ItemStack itemstack = this.getStackInSlot(i).copy();
                        ItemStack itemstack1 = putStackInInventoryAllSlots(iinventory, this.decrStackSize(i, 1), enumfacing);

                        if (itemstack1 == null || itemstack1.stackSize == 0) {
                            iinventory.markDirty();
                            return true;
                        }

                        this.setInventorySlotContents(i, itemstack);
                    }
                }

                return false;
            }
        }
    }

    private boolean isInventoryFull(IInventory inventoryIn, EnumFacing side) {
        if (inventoryIn instanceof ISidedInventory) {
            ISidedInventory isidedinventory = (ISidedInventory) inventoryIn;

            for (int anAint : isidedinventory.getSlotsForFace(side)) {
                ItemStack itemstack1 = isidedinventory.getStackInSlot(anAint);

                if (itemstack1 == null || itemstack1.stackSize != itemstack1.getMaxStackSize()) {
                    return false;
                }
            }
        } else {
            int i = inventoryIn.getSizeInventory();

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = inventoryIn.getStackInSlot(j);

                if (itemstack == null || itemstack.stackSize != itemstack.getMaxStackSize()) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean isInventoryEmpty(IInventory inventoryIn, EnumFacing side) {
        if (inventoryIn instanceof ISidedInventory) {
            ISidedInventory isidedinventory = (ISidedInventory) inventoryIn;

            for (int anAint : isidedinventory.getSlotsForFace(side)) {
                if (isidedinventory.getStackInSlot(anAint) != null) {
                    return false;
                }
            }
        } else {
            int j = inventoryIn.getSizeInventory();

            for (int k = 0; k < j; ++k) {
                if (inventoryIn.getStackInSlot(k) != null) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean captureDroppedItems(IHopper p_145891_0_) {
        if (net.minecraftforge.items.VanillaInventoryCodeHooks.extractHook(p_145891_0_)) {
            return true;
        }
        IInventory iinventory = getHopperInventory(p_145891_0_);

        if (iinventory != null) {
            EnumFacing enumfacing = EnumFacing.DOWN;

            if (isInventoryEmpty(iinventory, enumfacing)) {
                return false;
            }

            if (iinventory instanceof ISidedInventory) {
                ISidedInventory isidedinventory = (ISidedInventory) iinventory;

                for (int anAint : isidedinventory.getSlotsForFace(enumfacing)) {
                    if (pullItemFromSlot(p_145891_0_, iinventory, anAint, enumfacing)) {
                        return true;
                    }
                }
            } else {
                int j = iinventory.getSizeInventory();

                for (int k = 0; k < j; ++k) {
                    if (pullItemFromSlot(p_145891_0_, iinventory, k, enumfacing)) {
                        return true;
                    }
                }
            }
        } else {
            for (EntityItem entityitem : getCaptureItems(p_145891_0_.getWorld(), p_145891_0_.getXPos(), p_145891_0_.getYPos() + 1.0D, p_145891_0_.getZPos())) {
                if (putDropInInventoryAllSlots(p_145891_0_, entityitem)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean pullItemFromSlot(IHopper hopper, IInventory inventoryIn, int index, EnumFacing direction) {
        ItemStack itemstack = inventoryIn.getStackInSlot(index);

        if (itemstack != null && canExtractItemFromSlot(inventoryIn, itemstack, index, direction)) {
            ItemStack itemstack1 = itemstack.copy();
            ItemStack itemstack2 = putStackInInventoryAllSlots(hopper, inventoryIn.decrStackSize(index, 1), null);

            if (itemstack2 == null || itemstack2.stackSize == 0) {
                inventoryIn.markDirty();
                return true;
            }

            inventoryIn.setInventorySlotContents(index, itemstack1);
        }

        return false;
    }

    private static boolean putDropInInventoryAllSlots(IInventory p_145898_0_, EntityItem itemIn) {
        boolean flag = false;

        if (itemIn == null) {
            return false;
        } else {
            ItemStack itemstack = itemIn.getEntityItem().copy();
            ItemStack itemstack1 = putStackInInventoryAllSlots(p_145898_0_, itemstack, null);

            if (itemstack1 != null && itemstack1.stackSize != 0) {
                itemIn.setEntityItemStack(itemstack1);
            } else {
                flag = true;
                itemIn.setDead();
            }

            return flag;
        }
    }

    private static ItemStack putStackInInventoryAllSlots(IInventory inventoryIn, ItemStack stack, EnumFacing side) {
        if (inventoryIn instanceof ISidedInventory && side != null) {
            ISidedInventory isidedinventory = (ISidedInventory) inventoryIn;
            int[] aint = isidedinventory.getSlotsForFace(side);

            for (int k = 0; k < aint.length && stack != null && stack.stackSize > 0; ++k) {
                stack = insertStack(inventoryIn, stack, aint[k], side);
            }
        } else {
            int i = inventoryIn.getSizeInventory();

            for (int j = 0; j < i && stack != null && stack.stackSize > 0; ++j) {
                stack = insertStack(inventoryIn, stack, j, side);
            }
        }

        if (stack != null && stack.stackSize == 0) {
            stack = null;
        }

        return stack;
    }

    private static boolean canInsertItemInSlot(IInventory inventoryIn, ItemStack stack, int index, EnumFacing side) {
        return inventoryIn.isItemValidForSlot(index, stack) && (!(inventoryIn instanceof ISidedInventory) || ((ISidedInventory) inventoryIn).canInsertItem(index, stack, side));
    }

    private static boolean canExtractItemFromSlot(IInventory inventoryIn, ItemStack stack, int index, EnumFacing side) {
        return !(inventoryIn instanceof ISidedInventory) || ((ISidedInventory) inventoryIn).canExtractItem(index, stack, side);
    }

    private static ItemStack insertStack(IInventory inventoryIn, ItemStack stack, int index, EnumFacing side) {
        ItemStack itemstack = inventoryIn.getStackInSlot(index);

        if (canInsertItemInSlot(inventoryIn, stack, index, side)) {
            boolean flag = false;

            if (itemstack == null) {
                //Forge: BUGFIX: Again, make things respect max stack sizes.
                int max = Math.min(stack.getMaxStackSize(), inventoryIn.getInventoryStackLimit());
                if (max >= stack.stackSize) {
                    inventoryIn.setInventorySlotContents(index, stack);
                    stack = null;
                } else {
                    inventoryIn.setInventorySlotContents(index, stack.splitStack(max));
                }
                flag = true;
            } else if (canCombine(itemstack, stack)) {
                //Forge: BUGFIX: Again, make things respect max stack sizes.
                int max = Math.min(stack.getMaxStackSize(), inventoryIn.getInventoryStackLimit());
                if (max > itemstack.stackSize) {
                    int i = max - itemstack.stackSize;
                    int j = Math.min(stack.stackSize, i);
                    stack.stackSize -= j;
                    itemstack.stackSize += j;
                    flag = j > 0;
                }
            }

            if (flag) {
                if (inventoryIn instanceof TileEntitySeparator) {
                    TileEntitySeparator tileEntitySeparator = (TileEntitySeparator) inventoryIn;

                    if (tileEntitySeparator.mayTransfer()) {
                        tileEntitySeparator.setTransferCooldown(8);
                    }

                    inventoryIn.markDirty();
                }

                inventoryIn.markDirty();
            }
        }

        return stack;
    }

    private IInventory getInventoryForHopperTransfer() {
        EnumFacing enumfacing = EnumFacing.getFront(this.getBlockMetadata());
        /**
         * Returns the IInventory (if applicable) of the TileEntity at the specified position
         */
        return getInventoryAtPosition(this.getWorld(), (double) (this.pos.getX() + enumfacing.getFrontOffsetX()), (double) (this.pos.getY() + enumfacing.getFrontOffsetY()), (double) (this.pos.getZ() + enumfacing.getFrontOffsetZ()));
    }

    private static IInventory getHopperInventory(IHopper hopper) {
        return getInventoryAtPosition(hopper.getWorld(), hopper.getXPos(), hopper.getYPos() + 1.0D, hopper.getZPos());
    }

    private static List<EntityItem> getCaptureItems(World world, double x, double y, double z) {
        return world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(x - 0.5D, y - 0.5D, z - 0.5D, x + 0.5D, y + 0.5D, z + 0.5D), EntitySelectors.IS_ALIVE);
    }

    private static IInventory getInventoryAtPosition(World worldIn, double x, double y, double z) {
        IInventory iinventory = null;
        int i = MathHelper.floor_double(x);
        int j = MathHelper.floor_double(y);
        int k = MathHelper.floor_double(z);
        BlockPos blockpos = new BlockPos(i, j, k);
        Block block = worldIn.getBlockState(blockpos).getBlock();

        if (block.hasTileEntity(block.getDefaultState())) {
            TileEntity tileentity = worldIn.getTileEntity(blockpos);

            if (tileentity instanceof IInventory) {
                iinventory = (IInventory) tileentity;

                if (iinventory instanceof TileEntityChest && block instanceof BlockChest) {
                    iinventory = ((BlockChest) block).getLockableContainer(worldIn, blockpos);
                }
            }
        }

        if (iinventory == null) {
            List<Entity> list = worldIn.getEntitiesInAABBexcluding(null, new AxisAlignedBB(x - 0.5D, y - 0.5D, z - 0.5D, x + 0.5D, y + 0.5D, z + 0.5D), EntitySelectors.HAS_INVENTORY);

            if (list.size() > 0) {
                iinventory = (IInventory) list.get(worldIn.rand.nextInt(list.size()));
            }
        }

        return iinventory;
    }

    private static boolean canCombine(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem() == stack2.getItem() && (stack1.getMetadata() == stack2.getMetadata() && (stack1.stackSize <= stack1.getMaxStackSize() && ItemStack.areItemStackTagsEqual(stack1, stack2)));
    }

    public double getXPos() {
        return (double) this.pos.getX() + 0.5D;
    }

    public double getYPos() {
        return (double) this.pos.getY() + 0.5D;
    }

    public double getZPos() {
        return (double) this.pos.getZ() + 0.5D;
    }

    private void setTransferCooldown(int ticks) {
        this.transferCooldown = ticks;
    }

    private boolean isOnTransferCooldown() {
        return this.transferCooldown > 0;
    }

    private boolean mayTransfer() {
        return this.transferCooldown <= 1;
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
        for (int i = 0; i < this.inventory.length; ++i) {
            this.inventory[i] = null;
        }
    }
}