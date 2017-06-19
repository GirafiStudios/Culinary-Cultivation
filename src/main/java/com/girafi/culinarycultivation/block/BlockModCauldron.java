package com.girafi.culinarycultivation.block;

import com.girafi.culinarycultivation.block.tileentity.TileEntityCauldron;
import com.girafi.culinarycultivation.block.tileentity.TileFluidTank;
import com.girafi.culinarycultivation.util.NBTHelper;
import com.girafi.culinarycultivation.util.StringUtil;
import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockModCauldron extends SourceBlockTileEntity {
    private static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D);
    private static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
    private static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);

    public BlockModCauldron() {
        super(Material.IRON, MapColor.STONE);
        this.setHardness(2.0F);
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World world, int metadata) {
        return new TileEntityCauldron();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        if (NBTHelper.hasKey(stack, "FluidName")) {
            if (GuiScreen.isShiftKeyDown()) {
                TileFluidTank tank = new TileFluidTank(0);
                tank.readFromNBT(stack.getTagCompound());

                tooltip.add(StringUtil.translateFormatted(Reference.MOD_ID + ".fluid", tank.getFluid().getLocalizedName()));
                tooltip.add(StringUtil.translateFormatted(Reference.MOD_ID + ".fluid_amount", tank.getFluid().amount + " / " + Fluid.BUCKET_VOLUME));
            } else {
                tooltip.add(StringUtil.shiftTooltip());
            }
        }
    }

    //TODO Readd remove dye for cauldron

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldStack = player.getHeldItem(hand);

        if (player.isSneaking() /*&& !stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, facing)*/) { //TODO Force both hands to be empty, and lock the Cauldron in both slots when picked up
            this.onBlockDestroyedByPlayer(world, pos, state);
            ItemStack cauldron = getDrops(world, pos, state, 0).get(0);
            if (!player.inventory.addItemStackToInventory(cauldron)) {
                player.dropItem(cauldron, false);
            }
            world.setBlockToAir(pos);
        }

        //Fluid handling
        FluidUtil.interactWithFluidHandler(player, hand, world, pos, facing);

        return FluidUtil.getFluidHandler(heldStack) != null;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityCauldron && stack != null && stack.hasTagCompound()) {
            ((TileEntityCauldron) te).tank.readFromNBT(stack.getTagCompound());
        }
    }

    @Nonnull
    @Override
    public NonNullList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, @Nonnull IBlockState state, int fortune) {
        NonNullList<ItemStack> ret = NonNullList.create();
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;
        Item item = this.getItemDropped(state, rand, fortune);
        ItemStack stack = ItemStack.EMPTY;
        if (item != Items.AIR) {
            stack = new ItemStack(item, 1, this.damageDropped(state));
            ret.add(stack);
        }

        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileEntityCauldron && !stack.isEmpty()) {
            if (((TileEntityCauldron) tileEntity).tank.getFluid() != null) {
                NBTTagCompound tag = new NBTTagCompound();
                ((TileEntityCauldron) tileEntity).tank.writeToNBT(tag);
                stack.setTagCompound(tag);
            }
        }
        return ret;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull AxisAlignedBB entityBox, @Nonnull List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean b) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LEGS);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
    }

    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (!(tileEntity instanceof TileEntityCauldron)) {
            return 0;
        }
        TileFluidTank tank = ((TileEntityCauldron) tileEntity).tank;
        return 15 * tank.getFluidAmount() / tank.getCapacity();
    }

/*    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) { //TODO Add back feature, but only with cold fluids
        int level = state.getValue(LEVEL);
        float f = (float) pos.getY() + (6.0F + (float) (3 * level)) / 16.0F;

        if (!world.isRemote && entity.isBurning() && level > 0 && level < 13 && entity.getEntityBoundingBox().minY <= (double) f) {
            entity.extinguish();
            world.setBlockState(pos, ModBlocks.CAULDRON.getDefaultState());
        }
    }*/

    @Override
    public boolean blocksMovement(IBlockAccess world, BlockPos pos) {
        return true;
    }
}