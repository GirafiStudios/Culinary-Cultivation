package com.Girafi.culinarycultivation.block.tileentity;

import com.Girafi.culinarycultivation.block.BlockFanHousing;
import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.util.InventoryHandlerHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.ArrayList;
import java.util.List;

public class TileEntitySeparator extends TileEntity implements ITickable, IInventory {
    private ItemStack[] separatorContents = new ItemStack[1];
    private List<ItemStack> input = new ArrayList<ItemStack>();
    private boolean isMultiblockFormed;
    private boolean isInvalidBlock;
    private int checkingX, checkingZ;

    @Override
    public void update() {
        if (!worldObj.isRemote) {
            this.checkForFanHousing();
        }
        if (isMultiblockFormed) {
            for (EntityItem object : getCaptureItems(worldObj, pos)) {
                if (object instanceof EntityItem) {
                    EntityItem entityItem = object;
                    ItemStack inputStack = entityItem.getEntityItem();
                    if (input != null) {
                        entityItem.setDead();
                        //update = true;
                        continue;
                    }
                    addStackToInput(inputStack);
                    //update = true;
                    entityItem.setDead();
                }
            }

            ArrayList<ItemStack> out = new ArrayList<ItemStack>();
            ItemStack outputStack = null;
            for (int i = 0; i < separatorContents.length; ++i) {
                outputStack = this.getStackInSlot(i);
            }
            if (outputStack != null) {
                out.add(outputStack);
            }
            this.outputItems(out, EnumFacing.getFront(this.getBlockMetadata()));
            this.inputItems(out);
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
        if (!(stateFront.getBlock() == ModBlocks.FAN_HOUSING && stateFront.getValue(BlockFanHousing.FACING) == EnumFacing.getFront(state.getBlock().getMetaFromState(state)))) {
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
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.separatorContents = new ItemStack[this.getSizeInventory()];

        NBTTagList nbttaglist = compound.getTagList("Items", 10);

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound tagCompound = nbttaglist.getCompoundTagAt(i);
            int j = tagCompound.getByte("Slot") & 255;

            if (j >= 0 && j < this.separatorContents.length) {
                this.separatorContents[j] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }

        NBTTagList inventoryList = compound.getTagList("input", 10);
        input.clear();
        for (int i = 0; i < inventoryList.tagCount(); i++) {
            input.add(ItemStack.loadItemStackFromNBT(inventoryList.getCompoundTagAt(i)));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.separatorContents.length; ++i) {
            if (this.separatorContents[i] != null) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) i);
                this.separatorContents[i].writeToNBT(tagCompound);
                nbttaglist.appendTag(tagCompound);
            }
        }
        compound.setTag("Items", nbttaglist);

        NBTTagList inventoryList = new NBTTagList();
        for (ItemStack s : input) {
            inventoryList.appendTag(s.writeToNBT(new NBTTagCompound()));
        }
        compound.setTag("input", inventoryList);
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }


    private void inputItems(List<ItemStack> stacks) {
        TileEntity tileEntity = worldObj.getTileEntity(pos.up());

        for (int i = 0; i < stacks.size(); i++) {
            ItemStack stack = stacks.get(i);
            if (tileEntity instanceof ISidedInventory && ((ISidedInventory) tileEntity).getSlotsForFace(null).length > 0 || tileEntity instanceof IInventory && ((IInventory) tileEntity).getSizeInventory() > 0) {
                IInventory inventory = ((IInventory) tileEntity);
                InventoryHandlerHelper.insertStackIntoInventory(inventory, stack, null);
                ItemStackHelper.getAndRemove(this.separatorContents, i);
            }
        }
    }

    private boolean addStackToInput(ItemStack stack) {
        for (ItemStack inputStack : input)
            if (input != null && inputStack.isItemEqual(stack) && (inputStack.stackSize + stack.stackSize <= stack.getMaxStackSize())) {
                inputStack.stackSize += stack.stackSize;
                return true;
            }
        this.input.add(stack);

        return true;
    }

    private void outputItems(List<ItemStack> stacks, EnumFacing facing) {
        TileEntity tileEntity = worldObj.getTileEntity(pos.offset(facing));

        for (int i = 0; i < stacks.size(); i++) {
            ItemStack stack = stacks.get(i);
            if (stack == null) {
                continue;
            }
            if (tileEntity instanceof ISidedInventory && ((ISidedInventory) tileEntity).getSlotsForFace(facing).length > 0 || tileEntity instanceof IInventory && ((IInventory) tileEntity).getSizeInventory() > 0) {
                IInventory inventory = ((IInventory) tileEntity);
                stack = InventoryHandlerHelper.insertStackIntoInventory(inventory, stack, facing);
                ItemStackHelper.getAndRemove(this.separatorContents, i);
            }
            if (stack != null) {
                EntityItem ei = new EntityItem(worldObj, (double) facing.getFrontOffsetX() + pos.getX() + 0.5D, (double) pos.getY() + 0.15D, (double) facing.getFrontOffsetZ() + pos.getZ() + 0.5D, stack.copy());
                ei.motionX = (0.055F * facing.getFrontOffsetX());
                ei.motionY = 0.025D;
                ei.motionZ = (0.055F * facing.getFrontOffsetZ());
                worldObj.spawnEntityInWorld(ei);
                ItemStackHelper.getAndRemove(this.separatorContents, i);
            }
        }
    }

    private static List<EntityItem> getCaptureItems(World world, BlockPos pos) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        return world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(x, y + 0.5D, z, x + 1.0D, y + 2.0D, z + 1.0D), EntitySelectors.IS_ALIVE);
    }

    private IItemHandler itemHandler;

    private IItemHandler createUnSidedHandler() {
        return new InvWrapper(this);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T) (itemHandler == null ? (itemHandler = createUnSidedHandler()) : itemHandler);
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }
}