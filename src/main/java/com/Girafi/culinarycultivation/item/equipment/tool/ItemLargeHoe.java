package com.Girafi.culinarycultivation.item.equipment.tool;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemLargeHoe extends ItemHoe {

    public ItemLargeHoe(ToolMaterial material) {
        super(material);
        this.theToolMaterial = material;
        this.maxStackSize = 1;
        this.setMaxDamage(material.getMaxUses() * 2 - 39);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!player.canPlayerEdit(pos.offset(facing), facing, stack)) {
            return EnumActionResult.FAIL;
        } else {
            int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(stack, player, world, pos);
            if (hook != 0) return hook > 0 ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;

            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            if (facing != EnumFacing.DOWN && world.isAirBlock(pos.up())) {
                if (block == Blocks.grass || block == Blocks.grass_path) {
                    this.useLargeHoe(stack, player, world, pos, Blocks.farmland.getDefaultState());
                    return EnumActionResult.SUCCESS;
                }

                if (block == Blocks.dirt) {
                    switch (state.getValue(BlockDirt.VARIANT)) {
                        case DIRT:
                            this.useLargeHoe(stack, player, world, pos, Blocks.farmland.getDefaultState());
                            return EnumActionResult.SUCCESS;
                        case COARSE_DIRT:
                            this.useLargeHoe(stack, player, world, pos, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                            return EnumActionResult.SUCCESS;
                        case PODZOL:
                            break;
                    }
                }
            }
            return EnumActionResult.PASS;
        }
    }

    private void useLargeHoe(ItemStack stack, EntityPlayer player, World world, BlockPos pos, IBlockState newState) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (this.getMaterialName().equals("WOOD")) {
            this.useHoe(stack, player, world, pos, newState);
            this.useHoe(stack, player, world, pos.offset(player.getHorizontalFacing()), newState);
        }
        if (this.getMaterialName().equals("STONE")) {
            this.useHoe(stack, player, world, pos, newState);
            this.useHoe(stack, player, world, pos.offset(player.getHorizontalFacing()), newState);
            this.useHoe(stack, player, world, pos.offset(player.getHorizontalFacing().rotateY()), newState);
            this.useHoe(stack, player, world, pos.offset(player.getHorizontalFacing()).offset(player.getHorizontalFacing().rotateY()), newState);
        }
        if (this.getMaterialName().equals("IRON")) {
            this.useHoe(stack, player, world, pos, newState);
            this.useHoe(stack, player, world, pos.offset(player.getHorizontalFacing()), newState);
            this.useHoe(stack, player, world, pos.offset(player.getHorizontalFacing().rotateY()), newState);
            this.useHoe(stack, player, world, pos.offset(player.getHorizontalFacing()).offset(player.getHorizontalFacing().rotateY()), newState);
            this.useHoe(stack, player, world, pos.offset(player.getHorizontalFacing().rotateY(), 2), newState);
            this.useHoe(stack, player, world, pos.offset(player.getHorizontalFacing()).offset(player.getHorizontalFacing().rotateY(), 2), newState);

        }
        if (this.getMaterialName().equals("GOLD") || this.getMaterialName().equals("DIAMOND")) {
            this.useHoe(stack, player, world, pos, newState);
            this.useHoe(stack, player, world, new BlockPos(x + 1, y, z + 1), newState);
            this.useHoe(stack, player, world, new BlockPos(x + 1, y, z - 1), newState);
            this.useHoe(stack, player, world, new BlockPos(x + 1, y, z), newState);
            this.useHoe(stack, player, world, new BlockPos(x - 1, y, z + 1), newState);
            this.useHoe(stack, player, world, new BlockPos(x - 1, y, z - 1), newState);
            this.useHoe(stack, player, world, new BlockPos(x - 1, y, z), newState);
            this.useHoe(stack, player, world, new BlockPos(x, y, z + 1), newState);
            this.useHoe(stack, player, world, new BlockPos(x, y, z - 1), newState);
        }
    }

    private void useHoe(ItemStack stack, EntityPlayer player, World world, BlockPos pos, IBlockState newState) {
        this.func_185071_a(stack, player, world, pos, newState);
    }

    @Override
    protected void func_185071_a(ItemStack stack, EntityPlayer player, World world, BlockPos pos, IBlockState newState) { //TODO Make water right-clickable, but not replaceable
        if (world.getBlockState(pos).getBlock() instanceof BlockDirt || world.getBlockState(pos).getBlock() instanceof BlockGrass) {
            world.playSound(player, pos, SoundEvents.item_hoe_till, SoundCategory.BLOCKS, 1.0F, 1.0F);

            if (!world.isRemote) {
                world.setBlockState(pos, newState);
                stack.damageItem(1, player);
            }
        }
    }
}