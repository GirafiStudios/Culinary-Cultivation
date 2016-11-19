package com.girafi.culinarycultivation.item.equipment.tool;

import com.girafi.culinarycultivation.init.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemLargeHoe extends ItemHoe {

    public ItemLargeHoe(ToolMaterial material) {
        super(material);
        this.maxStackSize = 1;
        this.setMaxDamage(material.getMaxUses() * 2 - 39);
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer player, @Nullable World world, BlockPos pos, @Nullable EnumHand hand, @Nullable EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        RayTraceResult rayTraceResult = this.rayTrace(world, player, true);

        if (rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) {
            return EnumActionResult.PASS;
        } else {
            BlockPos rayTracePos = rayTraceResult.getBlockPos();

            if (!world.isBlockModifiable(player, rayTracePos)) {
                return EnumActionResult.FAIL;
            } else {
                if (!player.canPlayerEdit(rayTracePos.offset(rayTraceResult.sideHit), rayTraceResult.sideHit, stack)) {
                    return EnumActionResult.FAIL;
                } else {
                    IBlockState rayTraceState = world.getBlockState(rayTracePos);

                    if (rayTraceState.getMaterial() == Material.WATER && rayTraceState.getValue(BlockLiquid.LEVEL) == 0) {
                        if (this.getMaterialName().equals("GOLD") && rayTraceState.getMaterial() == Material.WATER && rayTraceState.getValue(BlockLiquid.LEVEL) == 0) {
                            for (int x = -4; x <= 4; x++) {
                                for (int z = -4; z <= 4; z++) {
                                    if (facing != EnumFacing.DOWN) {
                                        Block block = world.getBlockState(rayTracePos.add(x, 0, z)).getBlock();
                                        if (block instanceof BlockFarmland) {
                                            this.plantSeed(stack, player, world, rayTracePos.add(x, 0, z), hand, facing);
                                        } else {
                                            this.setBlock(stack, player, world, rayTracePos.add(x, 0, z), Blocks.FARMLAND.getDefaultState());
                                        }
                                    }
                                }
                            }
                        }
                        return EnumActionResult.SUCCESS;
                    }
                }
            }
        }

        if (!player.canPlayerEdit(pos.offset(facing), facing, stack)) {
            return EnumActionResult.FAIL;
        } else {
            int hook = ForgeEventFactory.onHoeUse(stack, player, world, pos);
            if (hook != 0) return hook > 0 ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;

            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            if (facing != EnumFacing.DOWN && world.isAirBlock(pos.up())) {
                if (block == Blocks.GRASS || block == Blocks.GRASS_PATH) {
                    this.useLargeHoe(stack, player, world, pos, Blocks.FARMLAND.getDefaultState());
                    return EnumActionResult.SUCCESS;
                } else if (block instanceof BlockFarmland) {
                    this.useLargeHoe(stack, player, world, pos, null);
                    return EnumActionResult.SUCCESS;
                }

                if (block == Blocks.DIRT) {
                    switch (state.getValue(BlockDirt.VARIANT)) {
                        case DIRT:
                            this.useLargeHoe(stack, player, world, pos, Blocks.FARMLAND.getDefaultState());
                            return EnumActionResult.SUCCESS;
                        case COARSE_DIRT:
                            this.useLargeHoe(stack, player, world, pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                            return EnumActionResult.SUCCESS;
                        case PODZOL:
                            break;
                    }
                }
            }
        }
        return EnumActionResult.PASS;
    }

    private EnumActionResult useLargeHoe(ItemStack stack, EntityPlayer player, World world, BlockPos pos, IBlockState newState) {
        switch (this.getMaterialName()) {
            case "WOOD":
                this.setBlockOrSeed(stack, player, world, pos, newState);
                this.setBlockOrSeed(stack, player, world, pos.offset(player.getHorizontalFacing()), newState);
                break;
            case "STONE":
                this.setBlockOrSeed(stack, player, world, pos, newState);
                this.setBlockOrSeed(stack, player, world, pos.offset(player.getHorizontalFacing()), newState);
                this.setBlockOrSeed(stack, player, world, pos.offset(player.getHorizontalFacing().rotateY()), newState);
                this.setBlockOrSeed(stack, player, world, pos.offset(player.getHorizontalFacing()).offset(player.getHorizontalFacing().rotateY()), newState);
                break;
            case "IRON":
                this.setBlockOrSeed(stack, player, world, pos.offset(player.getHorizontalFacing().rotateY(), -1), newState);
                this.setBlockOrSeed(stack, player, world, pos.offset(player.getHorizontalFacing()).offset(player.getHorizontalFacing().rotateY(), -1), newState);
                this.setBlockOrSeed(stack, player, world, pos, newState);
                this.setBlockOrSeed(stack, player, world, pos.offset(player.getHorizontalFacing()), newState);
                this.setBlockOrSeed(stack, player, world, pos.offset(player.getHorizontalFacing().rotateY()), newState);
                this.setBlockOrSeed(stack, player, world, pos.offset(player.getHorizontalFacing()).offset(player.getHorizontalFacing().rotateY()), newState);
                break;
            case "GOLD":
            case "DIAMOND":
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        this.setBlockOrSeed(stack, player, world, pos.add(x, 0, z), newState);
                    }
                }
                break;
        }
        return EnumActionResult.PASS;
    }

    @Override
    protected void setBlock(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, World world, @Nonnull BlockPos pos, @Nonnull IBlockState newState) {
        IBlockState state = world.getBlockState(pos);
        if (!world.isAirBlock(pos.up())) return;

        if (state.getBlock() instanceof BlockDirt || state.getBlock() instanceof BlockGrass || state.getBlock() instanceof BlockGrassPath) {
            world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

            if (!world.isRemote) {
                world.setBlockState(pos, newState);
                stack.damageItem(1, player);
            }
        }
    }

    private void setBlockOrSeed(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, World world, @Nonnull BlockPos pos, IBlockState newState) {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof BlockFarmland && newState == null) {
            if (!world.isRemote) {
                this.plantSeed(stack, player, world, pos, EnumHand.OFF_HAND, EnumFacing.UP);
            }
        } else if (newState != null) {
            this.setBlock(stack, player, world, pos, newState);
        }
    }

    private void plantSeed(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing) {
        ItemStack heldOff = player.getHeldItemOffhand();
        if (!heldOff.isEmpty() && heldOff.getItem() == ModItems.SEED_BAG) {
            IBlockState state = world.getBlockState(pos);
            if (!world.isAirBlock(pos.up()))
                return;
            if (state.getBlock() instanceof BlockFarmland) {
                if (!world.isRemote) {
                    EnumActionResult result = ItemSeedBag.useSeedBag(player, world, pos, hand, heldOff, facing);
                    if (result == EnumActionResult.SUCCESS) {
                        stack.damageItem(1, player);
                    }
                }
            }
        }
    }
}