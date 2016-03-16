package com.Girafi.culinarycultivation.block;

import com.Girafi.culinarycultivation.block.tileentity.TileEntityCauldron;
import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemStorageJar.StorageJarType;
import com.Girafi.culinarycultivation.item.equipment.armor.farmer.ItemFarmerArmor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
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
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockModCauldron extends SourceBlockTileEntity {

    public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 13);

    public BlockModCauldron() {
        super(Material.iron);
        setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, 0));
        setHardness(2.0F);
    }

    @Override
    public int getRenderType() {
        return 3;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityCauldron();
    }

    @Override
    public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
        super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
        float f = 0.125F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
        this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
        this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, pos, state, mask, list, collidingEntity);
        this.setBlockBoundsForItemRender();
    }

    @Override
    public void setBlockBoundsForItemRender() {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entityIn) {
        int i = state.getValue(LEVEL);
        float f = (float) pos.getY() + (6.0F + (float) (3 * i)) / 16.0F;

        if (!world.isRemote && entityIn.isBurning() && i > 0 && i < 13 && entityIn.getEntityBoundingBox().minY <= (double) f) {
            entityIn.extinguish();
            world.setBlockState(pos, ModBlocks.cauldron.getDefaultState());
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.inventory.getCurrentItem();
        int j1 = state.getValue(LEVEL);

        if (j1 == 13) {
            ItemStack cheese = new ItemStack(ModBlocks.cheese);
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
            if (stack == null) {
                return true;
            } else {
                if (stack.getItem() == Items.water_bucket) {
                    if (j1 < 3 && j1 <= 3) {
                        if (!player.capabilities.isCreativeMode) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.bucket));
                        }
                        player.triggerAchievement(StatList.field_181725_I);
                        setWaterLevel(world, pos, state, 3);
                    }
                    return true;
                }
                if (stack.getItem() == Items.bucket) {
                    if (j1 > 0 && j1 == 3) {
                        if (!player.capabilities.isCreativeMode) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.water_bucket));
                        }
                        player.triggerAchievement(StatList.field_181726_J);
                        setWaterLevel(world, pos, state, -3);
                    }
                    if (j1 > 0 && j1 == 6) {
                        if (!player.capabilities.isCreativeMode) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.milk_bucket));
                        }
                        world.setBlockState(pos, world.getBlockState(pos).getBlock().getDefaultState(), 2);
                    }
                    return true;
                }
                if (stack.getItem() == Items.milk_bucket) {
                    if (j1 == 0) {
                        if (!player.capabilities.isCreativeMode) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.bucket));
                        }
                        world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(6));
                    }
                    return true;
                } else {
                    if (stack.getItem() == Items.glass_bottle) {
                        if (j1 > 0 && j1 <= 3) {
                            if (!player.capabilities.isCreativeMode) {
                                ItemStack itemStack1 = new ItemStack(Items.potionitem, 1, 0);

                                if (!player.inventory.addItemStackToInventory(itemStack1)) {
                                    world.spawnEntityInWorld(new EntityItem(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, itemStack1));
                                } else if (player instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                                }
                                player.triggerAchievement(StatList.field_181726_J);
                                --stack.stackSize;
                                if (stack.stackSize <= 0) {
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                                }
                            }
                            setWaterLevel(world, pos, state, j1 - 1);
                        }
                    }
                    if (stack.getItem() == ModItems.storageJar && stack.getItemDamage() == StorageJarType.EMPTY.getMetaData()) {
                        if (j1 > 0 && j1 <= 3) {
                            if (!player.capabilities.isCreativeMode) {
                                ItemStack stackStorageJar = new ItemStack(ModItems.storageJar, 1, StorageJarType.WATER.getMetaData());

                                if (!player.inventory.addItemStackToInventory(stackStorageJar)) {
                                    world.spawnEntityInWorld(new EntityItem(world, (double) pos.getX() + 0.5D, (double) pos.getZ() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJar));
                                } else if (player instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                                }
                                player.triggerAchievement(StatList.field_181726_J);
                                --stack.stackSize;
                                if (stack.stackSize <= 0) {
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                                }
                            }
                            setWaterLevel(world, pos, state, j1 - 1);
                        }
                        if (j1 > 0 && j1 >= 4 && j1 <= 6) {
                            if (!player.capabilities.isCreativeMode) {
                                ItemStack stackStorageJarMilk = new ItemStack(ModItems.storageJar, 1, StorageJarType.MILK.getMetaData());

                                if (!player.inventory.addItemStackToInventory(stackStorageJarMilk)) {
                                    world.spawnEntityInWorld(new EntityItem(world, (double) pos.getX() + 0.5D, (double) pos.getZ() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJarMilk));
                                } else if (player instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                                }
                                --stack.stackSize;
                                if (stack.stackSize <= 0) {
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                                }
                            }
                            if (j1 == 4) {
                                world.setBlockState(pos, world.getBlockState(pos).getBlock().getDefaultState(), 2);
                            }
                            if (j1 == 5) {
                                world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(6 - 2));
                            }
                            if (j1 == 6) {
                                world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(6 - 1));

                            }
                        }
                        if (j1 > 0 && j1 >= 7 && j1 <= 9) {
                            if (!player.capabilities.isCreativeMode) {
                                ItemStack stackStorageJarRennet = new ItemStack(ModItems.storageJar, 1, StorageJarType.RENNET.getMetaData());

                                if (!player.inventory.addItemStackToInventory(stackStorageJarRennet)) {
                                    world.spawnEntityInWorld(new EntityItem(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJarRennet));
                                } else if (player instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                                }
                                --stack.stackSize;
                                if (stack.stackSize <= 0) {
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                                }
                            }
                            if (j1 == 7) {
                                world.setBlockState(pos, world.getBlockState(pos).getBlock().getDefaultState(), 2);
                            }
                            if (j1 == 8) {
                                world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(9 - 2));
                            }
                            if (j1 == 9) {
                                world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(9 - 1));
                            }
                        }
                    }
                    if (stack.getItem() == ModItems.storageJar && stack.getItemDamage() == StorageJarType.WATER.getMetaData()) {
                        if (j1 > 0 && j1 <= 3 || j1 == 0) {
                            if (!player.capabilities.isCreativeMode) {
                                ItemStack stackStorageJar = new ItemStack(ModItems.storageJar, 1, StorageJarType.EMPTY.getMetaData());

                                if (!player.inventory.addItemStackToInventory(stackStorageJar)) {
                                    world.spawnEntityInWorld(new EntityItem(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJar));
                                } else if (player instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                                }
                                --stack.stackSize;
                                if (stack.stackSize <= 0) {
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                                }
                            }
                            if (j1 == 0) {
                                setWaterLevel(world, pos, state, j1 + 1);
                            }
                            if (j1 == 1 || j1 == 2) {
                                setWaterLevel(world, pos, state, j1 + 1);
                            }
                            if (j1 == 3) {
                                setWaterLevel(world, pos, state, j1);
                            }
                        }
                    }
                    if (stack.getItem() == ModItems.storageJar && stack.getItemDamage() == StorageJarType.MILK.getMetaData()) {
                        if (j1 > 0 && j1 >= 4 && j1 <= 6 || j1 == 0) {
                            if (!player.capabilities.isCreativeMode) {
                                ItemStack stackStorageJar = new ItemStack(ModItems.storageJar, 1, StorageJarType.EMPTY.getMetaData());

                                if (!player.inventory.addItemStackToInventory(stackStorageJar)) {
                                    world.spawnEntityInWorld(new EntityItem(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJar));
                                } else if (player instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                                }
                                --stack.stackSize;
                                if (stack.stackSize <= 0) {
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                                }
                            }
                            if (j1 == 0) {
                                world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(4));
                            }
                            if (j1 == 4) {
                                world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(4 + 1));
                            }
                            if (j1 == 5 || j1 == 6) {
                                world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(4 + 2));
                            }
                        }
                    }
                    if (stack.getItem() == ModItems.storageJar && stack.getItemDamage() == StorageJarType.RENNET.getMetaData()) {
                        if (j1 > 0 && j1 >= 7 && j1 <= 9 || j1 == 0) {
                            if (!player.capabilities.isCreativeMode) {
                                ItemStack stackStorageJar = new ItemStack(ModItems.storageJar, 1, StorageJarType.EMPTY.getMetaData());

                                if (!player.inventory.addItemStackToInventory(stackStorageJar)) {
                                    world.spawnEntityInWorld(new EntityItem(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJar));
                                } else if (player instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                                }
                                --stack.stackSize;
                                if (stack.stackSize <= 0) {
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                                }
                            }
                            if (j1 == 0) {
                                world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(7));
                            }
                            if (j1 == 7) {
                                world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(7 + 1));
                            }
                            if (j1 == 8 || j1 == 9) {
                                world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(7 + 2));
                            }
                        }
                    }
                    /* CheeseMass starts here */
                    if (stack.getItem() == ModItems.storageJar && stack.getItemDamage() == StorageJarType.RENNET.getMetaData()) {
                        if (j1 > 0 && j1 == 4 || j1 == 5) {
                            if (!player.capabilities.isCreativeMode) {
                                ItemStack stackStorageJar = new ItemStack(ModItems.storageJar, 1, StorageJarType.EMPTY.getMetaData());

                                if (!player.inventory.addItemStackToInventory(stackStorageJar)) {
                                    world.spawnEntityInWorld(new EntityItem(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJar));
                                } else if (player instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                                }
                                --stack.stackSize;
                                if (stack.stackSize <= 0) {
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                                }
                            }
                            if (j1 == 4) {
                                world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(11));
                            }
                            if (j1 == 5) {
                                world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(12));
                            }
                        }
                    }
                    if (stack.getItem() == ModItems.storageJar && stack.getItemDamage() == StorageJarType.MILK.getMetaData()) {
                        if (j1 > 0 && j1 == 7 || j1 == 11) {
                            if (!player.capabilities.isCreativeMode) {
                                ItemStack stackStorageJar = new ItemStack(ModItems.storageJar, 1, StorageJarType.EMPTY.getMetaData());

                                if (!player.inventory.addItemStackToInventory(stackStorageJar)) {
                                    world.spawnEntityInWorld(new EntityItem(world, (double) pos.getZ() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJar));
                                } else if (player instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                                }
                                --stack.stackSize;
                                if (stack.stackSize <= 0)
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                            }
                            if (j1 == 7) {
                                world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(11));
                            }
                            if (j1 == 11) {
                                world.setBlockState(pos, world.getBlockState(pos).getBlock().getStateFromMeta(12));
                            }
                        }
                    } else if (j1 > 0 && j1 <= 3 && stack.getItem() instanceof ItemArmor) {
                        ItemArmor armor = (ItemArmor) stack.getItem();
                        if (armor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER && armor.hasColor(stack) || armor.getArmorMaterial() == ItemFarmerArmor.farmerArmorMaterial && armor.hasColor(stack)) {
                            armor.removeColor(stack);
                            player.triggerAchievement(StatList.field_181727_K);
                            setWaterLevel(world, pos, state, j1 - 1);
                            return true;
                        }
                    }
                    if (j1 > 0 && stack.getItem() instanceof ItemBanner && TileEntityBanner.getPatterns(stack) > 0) {
                        ItemStack banner = stack.copy();
                        banner.stackSize = 1;
                        TileEntityBanner.removeBannerData(banner);

                        if (stack.stackSize <= 1 && !player.capabilities.isCreativeMode) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, banner);
                        } else {
                            if (!player.inventory.addItemStackToInventory(banner)) {
                                world.spawnEntityInWorld(new EntityItem(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, banner));
                            } else if (player instanceof EntityPlayerMP) {
                                ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                            }

                            player.triggerAchievement(StatList.field_181728_L);

                            if (!player.capabilities.isCreativeMode) {
                                --stack.stackSize;
                            }
                        }

                        if (!player.capabilities.isCreativeMode) {
                            setWaterLevel(world, pos, state, j1 - 1);
                        }

                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
    }

    public void setWaterLevel(World world, BlockPos pos, IBlockState state, int level) {
        world.setBlockState(pos, state.withProperty(LEVEL, MathHelper.clamp_int(level, 0, 3)), 2);
        world.updateComparatorOutputLevel(pos, this);
    }

    public void fillWithRain(World world, BlockPos pos) {
        if (world.rand.nextInt(20) == 1) {
            IBlockState state = world.getBlockState(pos);

            if (state.getValue(LEVEL) < 3) {
                world.setBlockState(pos, state.cycleProperty(LEVEL), 2);
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.cauldron;
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World world, BlockPos pos) {
        return Items.cauldron;
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, BlockPos pos) {
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
    protected BlockState createBlockState() {
        return new BlockState(this, LEVEL);
    }
}