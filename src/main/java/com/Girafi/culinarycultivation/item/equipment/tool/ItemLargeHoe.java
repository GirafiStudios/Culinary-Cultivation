package com.Girafi.culinarycultivation.item.equipment.tool;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLargeHoe extends Item {
    protected Item.ToolMaterial theToolMaterial;

    public ItemLargeHoe(Item.ToolMaterial material) {
        this.theToolMaterial = material;
        this.maxStackSize = 1;
        this.setMaxDamage(material.getMaxUses() * 2 - 39);
        this.setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!player.canPlayerEdit(pos.offset(side), side, stack)) {
            return false;
        } else {
            int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(stack, player, world, pos);
            if (hook != 0) return hook > 0;

            IBlockState iBlockState = world.getBlockState(pos);
            Block block = iBlockState.getBlock();

            if (side != EnumFacing.DOWN && world.isAirBlock(pos.up())) {
                if (block == Blocks.grass) {
                    return this.useLargeHoe(stack, player, world, pos, Blocks.farmland.getDefaultState());
                }

                if (block == Blocks.dirt) {
                    switch (SwitchDirtType.TYPE_LOOKUP[((BlockDirt.DirtType) iBlockState.getValue(BlockDirt.VARIANT)).ordinal()]) {
                        case 1:
                            return this.useLargeHoe(stack, player, world, pos, Blocks.farmland.getDefaultState());
                        case 2:
                            return this.useLargeHoe(stack, player, world, pos, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                    }
                }
            }
            return false;
        }
    }

    protected boolean useLargeHoe(ItemStack stack, EntityPlayer player, World world, BlockPos pos, IBlockState newState) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (this.getMaterialName().equals("WOOD")) {
            return     this.useHoe(stack, player, world, pos, newState)
                    && this.useHoe(stack, player, world, pos.offset(player.getHorizontalFacing()), newState);
        }
        if (this.getMaterialName().equals("STONE")) {
            return     this.useHoe(stack, player, world, pos, newState)
                    && this.useHoe(stack, player, world, pos.offset(player.getHorizontalFacing()), newState)
                    && this.useHoe(stack, player, world, pos.offset(player.getHorizontalFacing().rotateY()), newState)
                    && this.useHoe(stack, player, world, pos.offset(player.getHorizontalFacing()).offset(player.getHorizontalFacing().rotateY()), newState);
        }
        if (this.getMaterialName().equals("IRON")) {
            return     this.useHoe(stack, player, world, pos, newState)
                    && this.useHoe(stack, player, world, pos.offset(player.getHorizontalFacing()), newState)
                    && this.useHoe(stack, player, world, pos.offset(player.getHorizontalFacing().rotateY()), newState)
                    && this.useHoe(stack, player, world, pos.offset(player.getHorizontalFacing()).offset(player.getHorizontalFacing().rotateY()), newState)
                    && this.useHoe(stack, player, world, pos.offset(player.getHorizontalFacing().rotateY(), 2), newState)
                    && this.useHoe(stack, player, world, pos.offset(player.getHorizontalFacing()).offset(player.getHorizontalFacing().rotateY(), 2), newState);

        }
        if (this.getMaterialName().equals("GOLD") || this.getMaterialName().equals("EMERALD")) {
            return     this.useHoe(stack, player, world, pos, newState)
                    && this.useHoe(stack, player, world, new BlockPos(x + 1, y, z + 1), newState)
                    && this.useHoe(stack, player, world, new BlockPos(x + 1, y, z - 1), newState)
                    && this.useHoe(stack, player, world, new BlockPos(x + 1, y, z), newState)
                    && this.useHoe(stack, player, world, new BlockPos(x - 1, y, z + 1), newState)
                    && this.useHoe(stack, player, world, new BlockPos(x - 1, y, z - 1), newState)
                    && this.useHoe(stack, player, world, new BlockPos(x - 1, y, z), newState)
                    && this.useHoe(stack, player, world, new BlockPos(x, y, z + 1), newState)
                    && this.useHoe(stack, player, world, new BlockPos(x, y, z - 1), newState);
        }
        return true;
    }

    protected boolean useHoe(ItemStack stack, EntityPlayer player, World world, BlockPos target, IBlockState newState) { //TODO Make water right-clickable, but not replaceable
        if (world.getBlockState(target).getBlock() instanceof BlockDirt || world.getBlockState(target).getBlock() instanceof BlockGrass) {
            world.playSoundEffect((double) ((float) target.getX() + 0.5F), (double) ((float) target.getY() + 0.5F), (double) ((float) target.getZ() + 0.5F), newState.getBlock().stepSound.getStepSound(), (newState.getBlock().stepSound.getVolume() + 1.0F) / 2.0F, newState.getBlock().stepSound.getFrequency() * 0.8F);

            if (world.isRemote) {
                return true;
            } else {
                world.setBlockState(target, newState);
                stack.damageItem(1, player);
                return true;
            }
        }
        return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

    public String getMaterialName()
    {
        return this.theToolMaterial.toString();
    }

    static final class SwitchDirtType {
        static final int[] TYPE_LOOKUP = new int[BlockDirt.DirtType.values().length];

        static {
            try {
                TYPE_LOOKUP[BlockDirt.DirtType.DIRT.ordinal()] = 1;
            } catch (NoSuchFieldError var2) {
                ;
            } try {
                TYPE_LOOKUP[BlockDirt.DirtType.COARSE_DIRT.ordinal()] = 2;
            } catch (NoSuchFieldError var1) {
                ;
            }
        }
    }
}
