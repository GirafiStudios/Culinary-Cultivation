package com.girafi.culinarycultivation.block;

import com.girafi.culinarycultivation.block.tileentity.TileEntityCauldron;
import com.girafi.culinarycultivation.block.tileentity.TileFluidTank;
import com.girafi.culinarycultivation.util.InventoryHandlerHelper;
import com.girafi.culinarycultivation.util.NBTHelper;
import com.girafi.culinarycultivation.util.StringUtil;
import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
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
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World world, int metadata) {
        return new TileEntityCauldron();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        if (NBTHelper.hasKey(stack, "FluidName")) {
            if (GuiScreen.isShiftKeyDown()) {
                TileFluidTank tank = new TileFluidTank(0);
                tank.readFromNBT(NBTHelper.getTag(stack));

                tooltip.add(I18n.format(Reference.MOD_ID + ".fluid", tank.getFluid().getLocalizedName()));
                tooltip.add(I18n.format(Reference.MOD_ID + ".fluid_amount", tank.getFluid().amount + " / " + Fluid.BUCKET_VOLUME));
            } else {
                tooltip.add(StringUtil.shiftTooltip());
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldStack = player.getHeldItem(hand);
        ItemStack cauldron = getDrops(world, pos, state, 0).get(0);

        if (player.isSneaking() && (player.getHeldItemMainhand().isEmpty() && player.getHeldItemOffhand().isEmpty())) {
            this.onPlayerDestroy(world, pos, state);
            InventoryHandlerHelper.giveItem(player, hand, cauldron);
            world.setBlockToAir(pos);
        }
        TileFluidTank tank = new TileFluidTank(0);
        if (cauldron.getTagCompound() != null) {
            tank.readFromNBT(cauldron.getTagCompound());
        }

        if (removeDye(world, pos, player, hand, heldStack, tank)) {
            return true;
        }

        //Fluid handling
        FluidUtil.interactWithFluidHandler(player, hand, world, pos, facing);

        return FluidUtil.getFluidHandler(heldStack) != null;
    }

    private boolean removeDye(World world, BlockPos pos, EntityPlayer player, EnumHand hand, @Nonnull ItemStack stack, TileFluidTank tank) {
        IFluidHandler handler = FluidUtil.getFluidHandler(world, pos, null);
        if (handler == null) return false;
        if (tank.getFluid() != null && tank.getFluid().getFluid() == FluidRegistry.WATER && tank.getFluidAmount() >= 250) {
            if (stack.getItem() instanceof ItemArmor && ((ItemArmor) stack.getItem()).hasColor(stack)) {
                if (!world.isRemote) {
                    ((ItemArmor) stack.getItem()).removeColor(stack);
                    player.addStat(StatList.ARMOR_CLEANED);
                    handler.drain(250, true);
                }
                player.playSound(SoundEvents.ENTITY_BOBBER_SPLASH, 0.16F, 0.66F);
                return true;
            }
            if (stack.getItem() instanceof ItemBanner) {
                if (TileEntityBanner.getPatterns(stack) > 0) {
                    if (!world.isRemote) {
                        ItemStack banner = stack.copy();
                        banner.setCount(1);
                        TileEntityBanner.removeBannerData(banner);
                        player.addStat(StatList.BANNER_CLEANED);

                        if (!player.capabilities.isCreativeMode) {
                            stack.shrink(1);
                            handler.drain(250, true);
                        }
                        InventoryHandlerHelper.giveItem(player, hand, banner);
                    }
                    player.playSound(SoundEvents.ENTITY_BOBBER_SPLASH, 0.16F, 0.66F);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, @Nonnull ItemStack stack) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityCauldron && !stack.isEmpty() && stack.hasTagCompound()) {
            ((TileEntityCauldron) te).tank.readFromNBT(NBTHelper.getTag(stack));
        }
    }

    @Override
    public void getDrops(@Nonnull NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, @Nonnull IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;
        Item item = this.getItemDropped(state, rand, fortune);
        ItemStack stack = ItemStack.EMPTY;
        if (item != Items.AIR) {
            stack = new ItemStack(item, 1, this.damageDropped(state));
            drops.add(stack);
        }

        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileEntityCauldron && !stack.isEmpty()) {
            if (((TileEntityCauldron) tileEntity).tank.getFluid() != null) {
                NBTTagCompound tag = new NBTTagCompound();
                ((TileEntityCauldron) tileEntity).tank.writeToNBT(tag);
                stack.setTagCompound(tag);
            }
        }
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        IFluidHandler handler = FluidUtil.getFluidHandler(world, pos, null);
        TileFluidTank tank = new TileFluidTank(0);
        tank.readFromNBT(NBTHelper.getTag(getDrops(world, pos, state, 0).get(0)));
        FluidStack fluidStack = tank.getFluid();
        if (handler == null || fluidStack == null) return;

        float f = (float) pos.getY() + (6.0F + (float) 3) / 16.0F;

        if (entity.getEntityBoundingBox().minY <= (double) f) {
            int temperature = fluidStack.getFluid().getTemperature(world, pos);
            if (entity.isBurning() && temperature < 1000) {
                if (!world.isRemote) {
                    entity.extinguish();
                    handler.drain(750, true);
                }
                entity.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 0.7F, 1.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.4F);
            } else if (!entity.isImmuneToFire()) {
                if (temperature >= 340 && temperature < 1000) {
                    entity.attackEntityFrom(DamageSource.HOT_FLOOR, 1.0F);
                } else if (temperature > 1000) {
                    entity.attackEntityFrom(DamageSource.LAVA, 2.5F);
                    entity.setFire(7);
                    if (!(entity instanceof EntityLivingBase)) {
                        entity.setDead();
                        world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
                    }
                }
            }
        }
    }

    @Override
    public void fillWithRain(World world, BlockPos pos) {
        if (world.rand.nextInt(10) == 1) {
            IFluidHandler handler = FluidUtil.getFluidHandler(world, pos, null);
            ItemStack cauldron = getDrops(world, pos, world.getBlockState(pos), 0).get(0);
            TileFluidTank tank = new TileFluidTank(0);
            tank.readFromNBT(NBTHelper.getTag(cauldron));

            float temperature = world.getBiome(pos).getTemperature(pos);
            if (world.getBiomeProvider().getTemperatureAtHeight(temperature, pos.getY()) >= 0.15F) {

                if ((tank.getFluidAmount() == 0 || tank.getFluid() != null && tank.getFluid().getFluid() == FluidRegistry.WATER) && tank.getFluidAmount() < Fluid.BUCKET_VOLUME) {
                    if (handler != null) {
                        handler.fill(new FluidStack(FluidRegistry.WATER, 250), true);
                    }
                }
            }
        }
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

    @Override
    public boolean isPassable(IBlockAccess world, BlockPos pos) {
        return true;
    }
}