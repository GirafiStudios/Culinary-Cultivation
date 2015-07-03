package com.Girafi.culinarycultivation.block;

import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemStorageJar.StorageJarType;
import com.Girafi.culinarycultivation.tileentity.TileEntityCauldron;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
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
        setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, Integer.valueOf(0)));
        setUnlocalizedName("cauldron");
        setHardness(2.0F);
    }

    @Override
    public int getRenderType()
    {
        return 3;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityCauldron();
    }

    @Override
    public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity) {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
        float f = 0.125F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
        this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
        this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
        this.setBlockBoundsForItemRender();
    }

    public void setBlockBoundsForItemRender() {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public boolean isOpaqueCube() {return false;}

    public boolean isFullCube() {return false;}

    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        int i = ((Integer)state.getValue(LEVEL)).intValue();
        float f = (float)pos.getY() + (6.0F + (float)(3 * i)) / 16.0F;

        if (!worldIn.isRemote && entityIn.isBurning() && i > 0 && i < 13 && entityIn.getEntityBoundingBox().minY <= (double)f)
        {
            entityIn.extinguish();
            worldIn.setBlockState(pos, ModBlocks.cauldron.getDefaultState());
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.inventory.getCurrentItem();
        int j1 = ((Integer) state.getValue(LEVEL)).intValue();

        if (j1 == 13) {
            if (!playerIn.capabilities.isCreativeMode) {
                ItemStack cheese = new ItemStack(ModBlocks.cheese);
                if (!playerIn.inventory.addItemStackToInventory(cheese)) {
                    worldIn.spawnEntityInWorld(new EntityItem(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, cheese));
                } else if (playerIn instanceof EntityPlayerMP) {
                    ((EntityPlayerMP) playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                }
                worldIn.setBlockState(pos, state.getBlock().getDefaultState());
                return true;
            }
        }

        if (worldIn.isRemote) {
            return true;
        } else {
            if (stack == null) {
                return true;
            } else {
                if (stack.getItem() == Items.water_bucket) {
                    if (j1 < 3 && j1 <= 3) {
                        if (!playerIn.capabilities.isCreativeMode) {
                            playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, new ItemStack(Items.bucket));
                        }
                        setWaterLevel(worldIn, pos, state, 3);
                    }
                    return true;
                }
                if (stack.getItem() == Items.bucket) {
                    if (j1 > 0 && j1 == 3) {
                        if (!playerIn.capabilities.isCreativeMode) {
                            playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, new ItemStack(Items.water_bucket));
                        }
                        setWaterLevel(worldIn, pos, state, -3);
                    }
                    if (j1 > 0 && j1 == 6) {
                        if (!playerIn.capabilities.isCreativeMode) {
                            playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, new ItemStack(Items.milk_bucket));
                        }
                        worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getDefaultState(), 2);
                    }
                    return true;
                }
                if (stack.getItem() == Items.milk_bucket) {
                    if (j1 == 0) {
                        if (!playerIn.capabilities.isCreativeMode) {
                            playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, new ItemStack(Items.bucket));
                        }
                        worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getStateFromMeta(6));
                    }
                    return true;
                } else {
                    if (stack.getItem() == Items.glass_bottle) {
                        if (j1 > 0 && j1 <= 3) {
                            if (!playerIn.capabilities.isCreativeMode) {
                                ItemStack itemstack1 = new ItemStack(Items.potionitem, 1, 0);

                                if (!playerIn.inventory.addItemStackToInventory(itemstack1)) {
                                    worldIn.spawnEntityInWorld(new EntityItem(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, itemstack1));
                                } else if (playerIn instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                                }
                                --stack.stackSize;
                                if (stack.stackSize <= 0) {
                                    playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, (ItemStack) null);
                                }
                            }
                            setWaterLevel(worldIn, pos, state, j1 - 1);
                        }
                    }
                    if (stack.getItem() == ModItems.storageJar && stack.getItemDamage() == StorageJarType.EMPTY.getMetaData()) {
                        if (j1 > 0 && j1 <= 3) {
                            if (!playerIn.capabilities.isCreativeMode) {
                                ItemStack stackStorageJar = new ItemStack(ModItems.storageJar, 1, StorageJarType.WATER.getMetaData());

                                if (!playerIn.inventory.addItemStackToInventory(stackStorageJar)) {
                                    worldIn.spawnEntityInWorld(new EntityItem(worldIn, (double) pos.getX() + 0.5D, (double) pos.getZ() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJar));
                                } else if (playerIn instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                                }
                                --stack.stackSize;
                                if (stack.stackSize <= 0) {
                                    playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, (ItemStack) null);
                                }
                            }
                            setWaterLevel(worldIn, pos, state, j1 - 1);
                        }
                        if (j1 > 0 && j1 >= 4 && j1 <= 6) {
                            if (!playerIn.capabilities.isCreativeMode) {
                                ItemStack stackStorageJarMilk = new ItemStack(ModItems.storageJar, 1, StorageJarType.MILK.getMetaData());

                                if (!playerIn.inventory.addItemStackToInventory(stackStorageJarMilk)) {
                                    worldIn.spawnEntityInWorld(new EntityItem(worldIn, (double) pos.getX() + 0.5D, (double) pos.getZ() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJarMilk));
                                } else if (playerIn instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                                }
                                --stack.stackSize;
                                if (stack.stackSize <= 0) {
                                    playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, (ItemStack) null);
                                }
                            }
                            if (j1 == 4) {
                                worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getDefaultState(), 2);
                            }
                            if (j1 == 5) {
                                worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getStateFromMeta(6 - 2));
                            }
                            if (j1 == 6) {
                                worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getStateFromMeta(6 - 1));

                            }
                        }
                        if (j1 > 0 && j1 >= 7 && j1 <= 9) {
                            if (!playerIn.capabilities.isCreativeMode) {
                                ItemStack stackStorageJarRennet = new ItemStack(ModItems.storageJar, 1, StorageJarType.RENNET.getMetaData());

                                if (!playerIn.inventory.addItemStackToInventory(stackStorageJarRennet)) {
                                    worldIn.spawnEntityInWorld(new EntityItem(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJarRennet));
                                } else if (playerIn instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                                }
                                --stack.stackSize;
                                if (stack.stackSize <= 0) {
                                    playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, (ItemStack) null);
                                }
                            }
                            if (j1 == 7) {
                                worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getDefaultState(), 2);
                            }
                            if (j1 == 8) {
                                worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getStateFromMeta(9 - 2));
                            }
                            if (j1 == 9) {
                                worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getStateFromMeta(9 - 1));
                            }
                        }
                    }
                    if (stack.getItem() == ModItems.storageJar && stack.getItemDamage() == StorageJarType.WATER.getMetaData()) {
                        if (j1 > 0 && j1 <= 3 || j1 == 0) {
                            if (!playerIn.capabilities.isCreativeMode) {
                                ItemStack stackStorageJar = new ItemStack(ModItems.storageJar, 1, StorageJarType.EMPTY.getMetaData());

                                if (!playerIn.inventory.addItemStackToInventory(stackStorageJar)) {
                                    worldIn.spawnEntityInWorld(new EntityItem(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJar));
                                } else if (playerIn instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                                }
                                --stack.stackSize;
                                if (stack.stackSize <= 0) {
                                    playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, (ItemStack) null);
                                }
                            }
                            if (j1 == 0) {
                                setWaterLevel(worldIn, pos, state, j1 + 1);
                            }
                            if (j1 == 1 || j1 == 2) {
                                setWaterLevel(worldIn, pos, state, j1 + 1);
                            }
                            if (j1 == 3) {
                                setWaterLevel(worldIn, pos, state, j1);
                            }
                        }
                    }
                    if (stack.getItem() == ModItems.storageJar && stack.getItemDamage() == StorageJarType.MILK.getMetaData()) {
                        if (j1 > 0 && j1 >= 4 && j1 <= 6 || j1 == 0) {
                            if (!playerIn.capabilities.isCreativeMode) {
                                ItemStack stackStorageJar = new ItemStack(ModItems.storageJar, 1, StorageJarType.EMPTY.getMetaData());

                                if (!playerIn.inventory.addItemStackToInventory(stackStorageJar)) {
                                    worldIn.spawnEntityInWorld(new EntityItem(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJar));
                                } else if (playerIn instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                                }
                                --stack.stackSize;
                                if (stack.stackSize <= 0) {
                                    playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, (ItemStack) null);
                                }
                            }
                            if (j1 == 0) {
                                worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getStateFromMeta(4));
                            }
                            if (j1 == 4) {
                                worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getStateFromMeta(4 + 1));
                            }
                            if (j1 == 5 || j1 == 6) {
                                worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getStateFromMeta(4 + 2));
                            }
                        }
                    }
                    if (stack.getItem() == ModItems.storageJar && stack.getItemDamage() == StorageJarType.RENNET.getMetaData()) {
                        if (j1 > 0 && j1 >= 7 && j1 <= 9 || j1 == 0) {
                            if (!playerIn.capabilities.isCreativeMode) {
                                ItemStack stackStorageJar = new ItemStack(ModItems.storageJar, 1, StorageJarType.EMPTY.getMetaData());

                                if (!playerIn.inventory.addItemStackToInventory(stackStorageJar)) {
                                    worldIn.spawnEntityInWorld(new EntityItem(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJar));
                                } else if (playerIn instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                                }
                                --stack.stackSize;
                                if (stack.stackSize <= 0) {
                                    playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, (ItemStack) null);
                                }
                            }
                            if (j1 == 0) {
                                worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getStateFromMeta(7));
                            }
                            if (j1 == 7) {
                                worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getStateFromMeta(7 + 1));
                            }
                            if (j1 == 8 || j1 == 9) {
                                worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getStateFromMeta(7 + 2));
                            }
                        }
                    }
                    // CheeseMass starts here \\
                    if (stack.getItem() == ModItems.storageJar && stack.getItemDamage() == StorageJarType.RENNET.getMetaData()) {
                        if (j1 > 0 && j1 == 4 || j1 == 5) {
                            if (!playerIn.capabilities.isCreativeMode) {
                                ItemStack stackStorageJar = new ItemStack(ModItems.storageJar, 1, StorageJarType.EMPTY.getMetaData());

                                if (!playerIn.inventory.addItemStackToInventory(stackStorageJar)) {
                                    worldIn.spawnEntityInWorld(new EntityItem(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJar));
                                } else if (playerIn instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                                }
                                --stack.stackSize;
                                if (stack.stackSize <= 0) {
                                    playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, (ItemStack) null);
                                }
                            }
                            if (j1 == 4) {
                                worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getStateFromMeta(11));
                            }
                            if (j1 == 5) {
                                worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getStateFromMeta(12));
                            }
                        }
                    }
                    if (stack.getItem() == ModItems.storageJar && stack.getItemDamage() == StorageJarType.MILK.getMetaData()) {
                        if (j1 > 0 && j1 == 7 || j1 == 11) {
                            if (!playerIn.capabilities.isCreativeMode) {
                                ItemStack stackStorageJar = new ItemStack(ModItems.storageJar, 1, StorageJarType.EMPTY.getMetaData());

                                if (!playerIn.inventory.addItemStackToInventory(stackStorageJar)) {
                                    worldIn.spawnEntityInWorld(new EntityItem(worldIn, (double) pos.getZ() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, stackStorageJar));
                                } else if (playerIn instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                                }
                                --stack.stackSize;
                                if (stack.stackSize <= 0) {
                                    playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, (ItemStack) null);
                                }
                            }
                            if (j1 == 7) {
                                worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getStateFromMeta(11));
                            }
                            if (j1 == 11) {
                                worldIn.setBlockState(pos, worldIn.getBlockState(pos).getBlock().getStateFromMeta(12));
                            }
                        }
                    } else if (j1 > 0 && j1 <= 3 && stack.getItem() instanceof ItemArmor) {
                        ItemArmor itemarmor = (ItemArmor) stack.getItem();
                        if (itemarmor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER && itemarmor.hasColor(stack)) {
                            itemarmor.removeColor(stack);
                            setWaterLevel(worldIn, pos, state, j1 - 1);
                            return true;
                        }
                    }
                    ItemStack itemstack1;
                    if (j1 > 0 && stack.getItem() instanceof ItemBanner && TileEntityBanner.getPatterns(stack) > 0) {
                        itemstack1 = stack.copy();
                        itemstack1.stackSize = 1;
                        TileEntityBanner.removeBannerData(itemstack1);

                        if (stack.stackSize <= 1 && !playerIn.capabilities.isCreativeMode) {
                            playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, itemstack1);
                        } else {
                            if (!playerIn.inventory.addItemStackToInventory(itemstack1)) {
                                worldIn.spawnEntityInWorld(new EntityItem(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, itemstack1));
                            } else if (playerIn instanceof EntityPlayerMP) {
                                ((EntityPlayerMP) playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                            }

                            if (!playerIn.capabilities.isCreativeMode) {
                                --stack.stackSize;
                            }
                        }

                        if (!playerIn.capabilities.isCreativeMode) {
                            setWaterLevel(worldIn, pos, state, j1 - 1);
                        }

                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
    }

    public void setWaterLevel(World worldIn, BlockPos pos, IBlockState state, int level) {
        worldIn.setBlockState(pos, state.withProperty(LEVEL, Integer.valueOf(MathHelper.clamp_int(level, 0, 3))), 2);
        worldIn.updateComparatorOutputLevel(pos, this);
    }

    public void fillWithRain(World worldIn, BlockPos pos) {
        if (worldIn.rand.nextInt(20) == 1) {
            IBlockState iblockstate = worldIn.getBlockState(pos);

            if (((Integer)iblockstate.getValue(LEVEL)).intValue() < 3) {
                worldIn.setBlockState(pos, iblockstate.cycleProperty(LEVEL), 2);
            }
        }
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.cauldron;
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos) {
        return Items.cauldron;
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public int getComparatorInputOverride(World worldIn, BlockPos pos) {
        return ((Integer)worldIn.getBlockState(pos).getValue(LEVEL)).intValue();
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(LEVEL, Integer.valueOf(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((Integer)state.getValue(LEVEL)).intValue();
    }

    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] {LEVEL});
    }
}