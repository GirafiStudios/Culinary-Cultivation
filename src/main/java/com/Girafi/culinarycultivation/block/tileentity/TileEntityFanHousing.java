package com.Girafi.culinarycultivation.block.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class TileEntityFanHousing extends TileInventoryBase implements ITickable {

    public static final String[] AGE = {"age", "field_70292_b", "c"};

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return true;
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack == null || stack.stackSize == 0) {
            return null;
        }

        ItemStack existing = this.inventory[slot];

        int limit = stack.getMaxStackSize();

        if (existing != null) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing)) {
                return stack;
            }
            limit -= existing.stackSize;
        }

        if (limit <= 0) {
            return stack;
        }

        boolean reachedLimit = stack.stackSize > limit;

        if (existing == null) {
            this.inventory[slot] = reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack;
        } else {
            existing.stackSize += reachedLimit ? limit : stack.stackSize;
        }
        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.stackSize - limit) : null;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0) {
            return null;
        }

        ItemStack existing = this.inventory[slot];

        if (existing == null) {
            return null;
        }

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.stackSize <= toExtract) {
            return existing;
        }
        return ItemHandlerHelper.copyStackWithSize(existing, existing.stackSize - toExtract);

    }

    @Override
    public void update() {
        if (worldObj.isRemote)
            return;

        boolean redstone = false;
        for (EnumFacing dir : EnumFacing.VALUES) {
            int redstoneSide = worldObj.getRedstonePower(pos.offset(dir), dir);
            if (redstoneSide > 0) {
                redstone = true;
                break;
            }
        }

        if (canEject()) {
            ItemStack stack = getStackInSlot(0);
            if (stack != null)
                eject(stack, redstone);
        }
    }

    public boolean canEject() {
        Block blockBelow = worldObj.getBlockState(pos.down()).getBlock();
        return blockBelow.isAir(worldObj, pos.down()) || blockBelow.getCollisionBoundingBox(worldObj, pos.down(), worldObj.getBlockState(pos.down())) == null;
    }

    public void eject(ItemStack stack, boolean redstone) {
        EntityItem item = new EntityItem(worldObj, pos.getX() + 0.5, pos.getY() - 0.5, pos.getZ() + 0.5, stack);
        item.motionX = 0;
        item.motionY = 0;
        item.motionZ = 0;

        if (redstone)
            ObfuscationReflectionHelper.setPrivateValue(EntityItem.class, item, -200, AGE);

        insertItem(0, null, false);
        worldObj.spawnEntityInWorld(item);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) this;
        }
        return super.getCapability(capability, facing);
    }
}