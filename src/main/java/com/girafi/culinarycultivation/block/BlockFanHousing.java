package com.girafi.culinarycultivation.block;

import com.girafi.culinarycultivation.block.tileentity.TileEntitySeparator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLever;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockFanHousing extends Block {
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockFanHousing() {
        super(Material.IRON);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        setSoundType(SoundType.METAL);
        setHardness(3.5F);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean causesSuffocation(IBlockState state) {
        return false;
    }

    @Override
    public boolean isSideSolid(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, EnumFacing side) {
        return side == EnumFacing.getFront(state.getBlock().getMetaFromState(state));
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighborPos) {
        if (!world.isRemote) {
            if (neighborBlock instanceof BlockLever) {
                if (isFrontPowered(world, pos, state)) {
                    BlockPos right = pos.offset(state.getValue(FACING).rotateAround(Axis.Y).getOpposite());
                    if (world.getTileEntity(right) instanceof TileEntitySeparator) {
                        ((TileEntitySeparator) world.getTileEntity(right)).onPowered();
                        world.playSound(null, pos, SoundEvents.ENTITY_MINECART_RIDING, SoundCategory.BLOCKS, 0.4F, 0.6F);
                    }
                }
            }
        }
    }

    private boolean isFrontPowered(World world, BlockPos pos, IBlockState state) {
        return world.getRedstonePower(pos, EnumFacing.getFront(state.getBlock().getMetaFromState(state))) > 0;
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        this.setDefaultFacing(world, pos, state);
        //Check for a block on the world
        BlockPos right = pos.offset(state.getValue(FACING).rotateAround(Axis.Y).getOpposite());
        if (world.getTileEntity(right) instanceof TileEntitySeparator) {
            ((TileEntitySeparator) world.getTileEntity(right)).checkForFanHousing();
        }
    }

    @Override
    public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        //Check for a block on the world
        BlockPos right = pos.offset(state.getValue(FACING).rotateAround(Axis.Y).getOpposite());
        if (world.getTileEntity(right) instanceof TileEntitySeparator) {
            ((TileEntitySeparator) world.getTileEntity(right)).checkForFanHousing();
        }

        super.breakBlock(world, pos, state);
    }

    @Override
    public void onBlockDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        //Check for a block on the world
        if (!world.isAirBlock(pos)) {
            BlockPos right = pos.offset(world.getBlockState(pos).getValue(FACING).rotateAround(Axis.Y).getOpposite());
            if (world.getTileEntity(right) instanceof TileEntitySeparator) {
                ((TileEntitySeparator) world.getTileEntity(right)).checkForFanHousing();
            }
        }
        super.onBlockDestroyedByExplosion(world, pos, explosion);
    }

    private void setDefaultFacing(World world, BlockPos pos, IBlockState state) {
        if (!world.isRemote) {
            IBlockState stateNorth = world.getBlockState(pos.north());
            IBlockState stateSouth = world.getBlockState(pos.south());
            IBlockState stateWest = world.getBlockState(pos.west());
            IBlockState stateEast = world.getBlockState(pos.east());
            EnumFacing facing = state.getValue(FACING);

            if (facing == EnumFacing.NORTH && stateNorth.isFullBlock() && !stateSouth.isFullBlock()) {
                facing = EnumFacing.SOUTH;
            } else if (facing == EnumFacing.SOUTH && stateSouth.isFullBlock() && !stateNorth.isFullBlock()) {
                facing = EnumFacing.NORTH;
            } else if (facing == EnumFacing.WEST && stateWest.isFullBlock() && !stateEast.isFullBlock()) {
                facing = EnumFacing.EAST;
            } else if (facing == EnumFacing.EAST && stateEast.isFullBlock() && !stateWest.isFullBlock()) {
                facing = EnumFacing.WEST;
            }

            world.setBlockState(pos, state.withProperty(FACING, facing), 2);
        }
    }

    @Override
    @Nonnull
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, @Nonnull ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    @Override
    @Nonnull
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }
}