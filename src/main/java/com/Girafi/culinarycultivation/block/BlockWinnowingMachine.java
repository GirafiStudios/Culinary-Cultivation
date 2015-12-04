package com.Girafi.culinarycultivation.block;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import com.Girafi.culinarycultivation.reference.Paths;
import com.Girafi.culinarycultivation.tileentity.TileEntityWinnowingMachine;
import com.google.common.collect.Lists;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.TRSRTransformation;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockWinnowingMachine extends SourceBlockTileEntity {
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockWinnowingMachine() {
        super(Material.iron);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        setUnlocalizedName(Paths.ModAssets + "winnowingMachine");
        setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityWinnowingMachine();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public boolean isVisuallyOpaque() {
        return false;
    }

    @Override
    public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, getFacingFromEntity(world, pos, placer));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing) state.getValue(FACING)).getIndex();
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        //Only return an IExtendedBlockState from this method and createState(), otherwise block placement will break!
        EnumFacing facing = (EnumFacing) state.getValue(FACING);
        TRSRTransformation transform = new TRSRTransformation(facing);
        OBJModel.OBJState newState = new OBJModel.OBJState(Lists.newArrayList(OBJModel.Group.ALL), true, transform);
        return ((IExtendedBlockState) state).withProperty(OBJModel.OBJProperty.instance, newState);
    }

    @Override
    public BlockState createBlockState() {
        return new ExtendedBlockState(this, new IProperty[]{FACING}, new IUnlistedProperty[]{OBJModel.OBJProperty.instance});
    }

    public static EnumFacing getFacingFromEntity(World world, BlockPos clickedBlock, EntityLivingBase livingBase) {
        if (MathHelper.abs((float) livingBase.posX - (float) clickedBlock.getX()) < 2.0F && MathHelper.abs((float) livingBase.posZ - (float) clickedBlock.getZ()) < 2.0F) {
            double d0 = livingBase.posY + (double) livingBase.getEyeHeight();

            if (d0 - (double) clickedBlock.getY() > 2.0D) {
                return EnumFacing.UP;
            }

            if ((double) clickedBlock.getY() - d0 > 0.0D) {
                return EnumFacing.DOWN;
            }
        }
        return livingBase.getHorizontalFacing().getOpposite();
    }
}