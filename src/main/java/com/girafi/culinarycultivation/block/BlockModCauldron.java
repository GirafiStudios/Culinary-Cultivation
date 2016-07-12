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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityCauldron();
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LEGS);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
    }

    @Override
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
        int i = state.getValue(LEVEL);
        float f = (float) pos.getY() + (6.0F + (float) (3 * i)) / 16.0F;

        if (!world.isRemote && entity.isBurning() && i > 0 && i < 13 && entity.getEntityBoundingBox().minY <= (double) f) {
            entity.extinguish();
            world.setBlockState(pos, ModBlocks.CAULDRON.getDefaultState());
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (state.getValue(LEVEL) == 13) {
            ItemStack cheese = new ItemStack(ModBlocks.CHEESE);
            if (!player.inventory.addItemStackToInventory(cheese)) {
                world.spawnEntityInWorld(new EntityItem(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, cheese));
            } else if (player instanceof EntityPlayerMP) {
                ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
            }
            world.setBlockState(pos, state.getBlock().getDefaultState());
            return true;
        }
        if (world.isRemote) {
            return true;
        } else {

            if (heldItem == null) {
                return true;
            } else {
                int level = state.getValue(LEVEL);
                Item item = heldItem.getItem();

                if (item == Items.WATER_BUCKET) {
                    if (level < 3 && level <= 3) {
                        if (!player.capabilities.isCreativeMode) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.BUCKET));
                        }
                        player.addStat(StatList.CAULDRON_FILLED);
                        this.setWaterLevel(world, pos, state, 3);
                    }
                    return true;
                }
                if (item == Items.BUCKET) {
                    if (level > 0 && level == 3) {
                        if (!player.capabilities.isCreativeMode) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.WATER_BUCKET));
                        }
                        player.addStat(StatList.CAULDRON_USED);
                        this.setWaterLevel(world, pos, state, -3);
                    }
                    if (level > 0 && level == 6) {
                        if (!player.capabilities.isCreativeMode) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.MILK_BUCKET));
                        }
                        world.setBlockState(pos, world.getBlockState(pos).getBlock().getDefaultState(), 2);
                    }
                    return true;
                }
                if (item == Items.MILK_BUCKET) {
                    if (level == 0) {
                        if (!player.capabilities.isCreativeMode) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.BUCKET));
                        }
                        world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(6));
                    }
                    return true;
                } else {
                    if (item == Items.GLASS_BOTTLE) {
                        if (level > 0 && level <= 3) {
                            if (!player.capabilities.isCreativeMode) {
                                ItemStack itemStack1 = new ItemStack(Items.POTIONITEM, 1, 0);

                                if (!player.inventory.addItemStackToInventory(itemStack1)) {
                                    world.spawnEntityInWorld(new EntityItem(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, itemStack1));
                                } else if (player instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                                }
                                player.addStat(StatList.CAULDRON_USED);
                                --heldItem.stackSize;
                                if (heldItem.stackSize <= 0) {
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                                }
                            }
                            setWaterLevel(world, pos, state, level - 1);
                        }
                    }
                }
                if (item == ModItems.STORAGE_JAR && heldItem.getItemDamage() == StorageJarType.EMPTY.getMetaData()) {
                    if (level > 0 && level <= 3) {
                        if (!player.capabilities.isCreativeMode) {
                            ItemStack stackStorageJar = new ItemStack(ModItems.STORAGE_JAR, 1, StorageJarType.WATER.getMetaData());

                            if (!player.inventory.addItemStackToInventory(stackStorageJar)) {
                                world.spawnEntityInWorld(new EntityItem(world, (double) pos.getX() + 0.5D, (double) pos.getZ() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJar));
                            } else if (player instanceof EntityPlayerMP) {
                                ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                            }
                            player.addStat(StatList.CAULDRON_USED);
                            --heldItem.stackSize;
                            if (heldItem.stackSize <= 0) {
                                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                            }
                        }
                        setWaterLevel(world, pos, state, level - 1);
                    }
                    if (level > 0 && level >= 4 && level <= 6) {
                        if (!player.capabilities.isCreativeMode) {
                            ItemStack stackStorageJarMilk = new ItemStack(ModItems.STORAGE_JAR, 1, StorageJarType.MILK.getMetaData());

                            if (!player.inventory.addItemStackToInventory(stackStorageJarMilk)) {
                                world.spawnEntityInWorld(new EntityItem(world, (double) pos.getX() + 0.5D, (double) pos.getZ() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJarMilk));
                            } else if (player instanceof EntityPlayerMP) {
                                ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                            }
                            --heldItem.stackSize;
                            if (heldItem.stackSize <= 0) {
                                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                            }
                        }
                        if (level == 4) {
                            world.setBlockState(pos, world.getBlockState(pos).getBlock().getDefaultState(), 2);
                        }
                        if (level == 5) {
                            world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(6 - 2));
                        }
                        if (level == 6) {
                            world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(6 - 1));

                        }
                    }
                    if (level > 0 && level >= 7 && level <= 9) {
                        if (!player.capabilities.isCreativeMode) {
                            ItemStack stackStorageJarRennet = new ItemStack(ModItems.STORAGE_JAR, 1, StorageJarType.RENNET.getMetaData());

                            if (!player.inventory.addItemStackToInventory(stackStorageJarRennet)) {
                                world.spawnEntityInWorld(new EntityItem(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJarRennet));
                            } else if (player instanceof EntityPlayerMP) {
                                ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                            }
                            --heldItem.stackSize;
                            if (heldItem.stackSize <= 0) {
                                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                            }
                        }
                        if (level == 7) {
                            world.setBlockState(pos, world.getBlockState(pos).getBlock().getDefaultState(), 2);
                        }
                        if (level == 8) {
                            world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(9 - 2));
                        }
                        if (level == 9) {
                            world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(9 - 1));
                        }
                    }
                }
                if (item == ModItems.STORAGE_JAR && heldItem.getItemDamage() == StorageJarType.WATER.getMetaData()) {
                    if (level > 0 && level <= 3 || level == 0) {
                        if (!player.capabilities.isCreativeMode) {
                            ItemStack stackStorageJar = new ItemStack(ModItems.STORAGE_JAR, 1, StorageJarType.EMPTY.getMetaData());

                            if (!player.inventory.addItemStackToInventory(stackStorageJar)) {
                                world.spawnEntityInWorld(new EntityItem(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJar));
                            } else if (player instanceof EntityPlayerMP) {
                                ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                            }
                            --heldItem.stackSize;
                            if (heldItem.stackSize <= 0) {
                                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                            }
                        }
                        if (level == 0) {
                            setWaterLevel(world, pos, state, level + 1);
                        }
                        if (level == 1 || level == 2) {
                            setWaterLevel(world, pos, state, level + 1);
                        }
                        if (level == 3) {
                            setWaterLevel(world, pos, state, level);
                        }
                    }
                }
                if (item == ModItems.STORAGE_JAR && heldItem.getItemDamage() == StorageJarType.MILK.getMetaData()) {
                    if (level > 0 && level >= 4 && level <= 6 || level == 0) {
                        if (!player.capabilities.isCreativeMode) {
                            ItemStack stackStorageJar = new ItemStack(ModItems.STORAGE_JAR, 1, StorageJarType.EMPTY.getMetaData());

                            if (!player.inventory.addItemStackToInventory(stackStorageJar)) {
                                world.spawnEntityInWorld(new EntityItem(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJar));
                            } else if (player instanceof EntityPlayerMP) {
                                ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                            }
                            --heldItem.stackSize;
                            if (heldItem.stackSize <= 0) {
                                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                            }
                        }
                        if (level == 0) {
                            world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(4));
                        }
                        if (level == 4) {
                            world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(4 + 1));
                        }
                        if (level == 5 || level == 6) {
                            world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(4 + 2));
                        }
                    }
                }
                if (item == ModItems.STORAGE_JAR && heldItem.getItemDamage() == StorageJarType.RENNET.getMetaData()) {
                    if (level > 0 && level >= 7 && level <= 9 || level == 0) {
                        if (!player.capabilities.isCreativeMode) {
                            ItemStack stackStorageJar = new ItemStack(ModItems.STORAGE_JAR, 1, StorageJarType.EMPTY.getMetaData());

                            if (!player.inventory.addItemStackToInventory(stackStorageJar)) {
                                world.spawnEntityInWorld(new EntityItem(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJar));
                            } else if (player instanceof EntityPlayerMP) {
                                ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                            }
                            --heldItem.stackSize;
                            if (heldItem.stackSize <= 0) {
                                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                            }
                        }
                        if (level == 0) {
                            world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(7));
                        }
                        if (level == 7) {
                            world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(7 + 1));
                        }
                        if (level == 8 || level == 9) {
                            world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(7 + 2));
                        }
                    }
                }
                    /* CheeseMass starts here */
                if (item == ModItems.STORAGE_JAR && heldItem.getItemDamage() == StorageJarType.RENNET.getMetaData()) {
                    if (level > 0 && level == 4 || level == 5) {
                        if (!player.capabilities.isCreativeMode) {
                            ItemStack stackStorageJar = new ItemStack(ModItems.STORAGE_JAR, 1, StorageJarType.EMPTY.getMetaData());

                            if (!player.inventory.addItemStackToInventory(stackStorageJar)) {
                                world.spawnEntityInWorld(new EntityItem(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJar));
                            } else if (player instanceof EntityPlayerMP) {
                                ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                            }
                            --heldItem.stackSize;
                            if (heldItem.stackSize <= 0) {
                                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                            }
                        }
                        if (level == 4) {
                            world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(11));
                        }
                        if (level == 5) {
                            world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(12));
                        }
                    }
                }
                if (item == ModItems.STORAGE_JAR && heldItem.getItemDamage() == StorageJarType.MILK.getMetaData()) {
                    if (level > 0 && level == 7 || level == 11) {
                        if (!player.capabilities.isCreativeMode) {
                            ItemStack stackStorageJar = new ItemStack(ModItems.STORAGE_JAR, 1, StorageJarType.EMPTY.getMetaData());

                            if (!player.inventory.addItemStackToInventory(stackStorageJar)) {
                                world.spawnEntityInWorld(new EntityItem(world, (double) pos.getZ() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJar));
                            } else if (player instanceof EntityPlayerMP) {
                                ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                            }
                            --heldItem.stackSize;
                            if (heldItem.stackSize <= 0)
                                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                        }
                        if (level == 7) {
                            world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(11));
                        }
                        if (level == 11) {
                            world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(12));
                        }
                    }
                } else {
                    if (level > 0 && level <= 3 && item instanceof ItemArmor) {
                        ItemArmor armor = (ItemArmor) item;
                        if (armor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER && armor.hasColor(heldItem) || armor.getArmorMaterial() == CulinaryCultivationAPI.FARMER_ARMOR_MATERIAL && armor.hasColor(heldItem)) {
                            armor.removeColor(heldItem);
                            player.addStat(StatList.ARMOR_CLEANED);
                            setWaterLevel(world, pos, state, level - 1);
                            return true;
                        }
                    }
                    if (level > 0 && item instanceof ItemBanner) {
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
                            } else if (!player.inventory.addItemStackToInventory(bannerStack)) {
                                player.dropItem(bannerStack, false);
                            } else if (player instanceof EntityPlayerMP) {
                                ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                            }

                            if (!player.capabilities.isCreativeMode) {
                                this.setWaterLevel(world, pos, state, level - 1);
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

    private void setWaterLevel(World world, BlockPos pos, IBlockState state, int level) {
        world.setBlockState(pos, state.withProperty(LEVEL, MathHelper.clamp_int(level, 0, 3)), 2);
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
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
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
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(LEVEL, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(LEVEL);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LEVEL);
    }

    @Override
    public boolean isPassable(IBlockAccess world, BlockPos pos) {
        return true;
    }
}