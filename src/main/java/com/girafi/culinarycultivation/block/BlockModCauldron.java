package com.girafi.culinarycultivation.block;

import com.girafi.culinarycultivation.api.CulinaryCultivationAPI;
import com.girafi.culinarycultivation.block.tileentity.TileEntityCauldron;
import com.girafi.culinarycultivation.init.ModBlocks;
import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.item.ItemStorageJar.StorageJarType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockModCauldron extends SourceBlockTileEntity {
    public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 13);
    private static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D);
    private static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
    private static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);

    public BlockModCauldron() {
        super(Material.IRON, MapColor.STONE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, 0));
        this.setHardness(2.0F);
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World world, int metadata) {
        return new TileEntityCauldron();
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull AxisAlignedBB entityBox, @Nonnull List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity) {
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
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        int level = state.getValue(LEVEL);
        float f = (float) pos.getY() + (6.0F + (float) (3 * level)) / 16.0F;

        if (!world.isRemote && entity.isBurning() && level > 0 && level < 13 && entity.getEntityBoundingBox().minY <= (double) f) {
            entity.extinguish();
            world.setBlockState(pos, ModBlocks.CAULDRON.getDefaultState());
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        int level = state.getValue(LEVEL);

        if (level == 13) {
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ModBlocks.CHEESE));
            this.setLevel(world, pos, 13, 13, level - 1);
            return true;
        }
        if (world.isRemote) {
            return true;
        } else {
            if (heldItem == null) {
                return true;
            } else {
                Item item = heldItem.getItem();

                if (item == Items.WATER_BUCKET) {
                    if (level < 3) {
                        if (!player.capabilities.isCreativeMode) {
                            player.setHeldItem(hand, new ItemStack(Items.BUCKET));
                        }
                        player.addStat(StatList.CAULDRON_FILLED);
                        this.setLevel(world, pos, 0, 3, 3);
                    }
                    return true;
                }
                if (item == Items.BUCKET) {
                    if (level == 3) {
                        if (!player.capabilities.isCreativeMode) {
                            player.setHeldItem(hand, new ItemStack(Items.WATER_BUCKET));
                        }
                        player.addStat(StatList.CAULDRON_USED);
                        this.setLevel(world, pos, 0, 3, -3);
                    }
                    if (level == 6) {
                        if (!player.capabilities.isCreativeMode) {
                            player.setHeldItem(hand, new ItemStack(Items.MILK_BUCKET));
                        }
                        this.setLevel(world, pos, 4, 6, -6);
                    }
                    return true;
                }
                if (item == Items.MILK_BUCKET) {
                    if (level == 0) {
                        if (!player.capabilities.isCreativeMode) {
                            player.setHeldItem(hand, new ItemStack(Items.BUCKET));
                        }
                        this.setLevel(world, pos, 4, 6, 6);
                    }
                    return true;
                } else {
                    if (item == Items.GLASS_BOTTLE) {
                        if (level > 0 && level <= 3) {
                            this.useJar(player, heldItem, new ItemStack(Items.POTIONITEM, 1, 0));
                            this.setLevel(world, pos, 0, 3, level - 1);
                        }
                    }
                }
                if (item == ModItems.STORAGE_JAR) {
                    int damage = heldItem.getItemDamage();
                    if (damage == StorageJarType.EMPTY.getMetaData()) {
                        ItemStack jarItem = null;

                        if (level > 0 && level <= 3) {
                            jarItem = new ItemStack(ModItems.STORAGE_JAR, 1, StorageJarType.WATER.getMetaData());
                            this.setLevel(world, pos, 0, 3, level - 1);
                        }
                        if (level >= 4 && level <= 6) {
                            jarItem = new ItemStack(ModItems.STORAGE_JAR, 1, StorageJarType.MILK.getMetaData());
                            this.setLevel(world, pos, 4, 6, level - 1);
                        }

                        if (level >= 7 && level <= 9) {
                            jarItem = new ItemStack(ModItems.STORAGE_JAR, 1, StorageJarType.RENNET.getMetaData());
                            this.setLevel(world, pos, 7, 9, level - 1);
                        }

                        if (level > 0) {
                            this.useJar(player, heldItem, jarItem);
                        }
                    }
                    if (damage == StorageJarType.WATER.getMetaData() && level <= 3) {
                        this.useJar(player, heldItem);
                        this.setLevel(world, pos, 0, 3, level + 1);
                    }
                    if (damage == StorageJarType.MILK.getMetaData() && (level == 0 || level >= 4 && level <= 6)) {
                        this.useJar(player, heldItem);

                        if (level == 0) {
                            this.setLevel(world, pos, 4, 6, 4);
                        } else {
                            this.setLevel(world, pos, 4, 6, level + 1);
                        }
                    }
                    if (damage == StorageJarType.RENNET.getMetaData() && (level == 0 || level >= 7 && level <= 9)) {
                        this.useJar(player, heldItem);

                        if (level == 0) {
                            this.setLevel(world, pos, 7, 9, 7);
                        } else {
                            this.setLevel(world, pos, 7, 9, level + 1);
                        }
                    }
                    /* CheeseMass starts here */
                    if (damage == StorageJarType.RENNET.getMetaData() && (level == 4 || level == 5)) {
                        this.useJar(player, heldItem);

                        if (level == 4) {
                            this.setLevel(world, pos, 10, 12, 11);
                        }
                        if (level == 5) {
                            this.setLevel(world, pos, 10, 12, 12);
                        }
                    }
                    if (damage == StorageJarType.MILK.getMetaData() && (level == 7 || level == 11)) {
                        this.useJar(player, heldItem);

                        if (level == 7) {
                            this.setLevel(world, pos, 10, 12, 11);
                        }
                        if (level == 11) {
                            this.setLevel(world, pos, 10, 12, 12);
                        }
                    }
                } else {
                    if (level > 0 && level <= 3 && item instanceof ItemArmor) {
                        ItemArmor armor = (ItemArmor) item;
                        if (armor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER && armor.hasColor(heldItem) || armor.getArmorMaterial() == CulinaryCultivationAPI.FARMER_ARMOR_MATERIAL && armor.hasColor(heldItem)) {
                            armor.removeColor(heldItem);
                            player.addStat(StatList.ARMOR_CLEANED);
                            this.setLevel(world, pos, 0, 3, level - 1);
                            return true;
                        }
                    }
                    if (level > 0 && level <= 3 && item instanceof ItemBanner) {
                        if (TileEntityBanner.getPatterns(heldItem) > 0) {
                            ItemStack bannerStack = heldItem.copy();
                            bannerStack.stackSize = 1;
                            TileEntityBanner.removeBannerData(bannerStack);
                            player.addStat(StatList.BANNER_CLEANED);

                            if (!player.capabilities.isCreativeMode) {
                                --heldItem.stackSize;
                            }

                            if (heldItem.stackSize == 0) {
                                player.setHeldItem(hand, bannerStack);
                            } else {
                                ItemHandlerHelper.giveItemToPlayer(player, bannerStack);
                            }

                            if (!player.capabilities.isCreativeMode) {
                                this.setLevel(world, pos, 0, 3, level - 1);
                            }
                        }
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    private void useJar(EntityPlayer player, ItemStack heldItem) {
        this.useJar(player, heldItem, new ItemStack(ModItems.STORAGE_JAR, 1, StorageJarType.EMPTY.getMetaData()));
    }

    private void useJar(EntityPlayer player, ItemStack heldItem, ItemStack givenStack) {
        if (!player.capabilities.isCreativeMode) {
            ItemHandlerHelper.giveItemToPlayer(player, givenStack);

            player.addStat(StatList.CAULDRON_USED);
            --heldItem.stackSize;
            if (heldItem.stackSize <= 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }
        }
    }

    private void setLevel(World world, BlockPos pos, int minLevel, int maxLevel, int level) {
        IBlockState state = world.getBlockState(pos);
        if (level < minLevel) {
            world.setBlockState(pos, state.getBlock().getDefaultState(), 2);
        } else {
            world.setBlockState(pos, state.withProperty(LEVEL, MathHelper.clamp_int(level, minLevel, maxLevel)), 2);
        }
        world.updateComparatorOutputLevel(pos, this);
    }

    public void fillWithRain(World world, BlockPos pos) {
        if (world.rand.nextInt(20) == 1) {
            float f = world.getBiome(pos).getFloatTemperature(pos);

            if (world.getBiomeProvider().getTemperatureAtHeight(f, pos.getY()) >= 0.15F) {
                IBlockState state = world.getBlockState(pos);

                if (state.getValue(LEVEL) < 3) {
                    world.setBlockState(pos, state.cycleProperty(LEVEL), 2);
                }
            }
        }
    }

    @Override
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.CAULDRON;
    }

    @Override
    @Nonnull
    public ItemStack getPickBlock(@Nonnull IBlockState state, RayTraceResult target, @Nonnull World world, @Nonnull BlockPos pos, EntityPlayer player) {
        return new ItemStack(Items.CAULDRON);
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos) {
        return world.getBlockState(pos).getValue(LEVEL);
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(LEVEL, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(LEVEL);
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LEVEL);
    }

    @Override
    public boolean isPassable(IBlockAccess world, BlockPos pos) {
        return true;
    }
}