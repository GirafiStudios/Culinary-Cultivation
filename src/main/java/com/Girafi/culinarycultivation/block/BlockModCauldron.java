package com.Girafi.culinarycultivation.block;

import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemStorageJar.StorageJarType;
import com.Girafi.culinarycultivation.tileentity.TileEntityCauldron;
import net.minecraft.block.Block;
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

    public static final PropertyInteger level = PropertyInteger.create("level", 0, 15);

    public BlockModCauldron() {
        super(Material.iron);
        this.setDefaultState(this.blockState.getBaseState().withProperty(level, Integer.valueOf(0)));
        this.setUnlocalizedName("cauldron");
        setHardness(2.0F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityCauldron();
    }

//    @Override
//    public int getRenderType() {
//        return CulinaryCultivation.proxy.getRenderIdForRenderer(RenderCauldron.class);
//    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

//    @SideOnly(Side.CLIENT)
//    @Override
//    public IIcon getIcon(int meta, int side) {
//        return meta == 1 ? iconTop : (meta == 0 ? iconBottom : blockIcon);
//    }

//    @SideOnly(Side.CLIENT)
//    @Override
//    public void registerBlockIcons(IIconRegister register) {
//        iconInner = register.registerIcon(getTextureName() + "_" + "inner");
//        iconTop = register.registerIcon(getTextureName() + "_top");
//        iconBottom = register.registerIcon(getTextureName() + "_" + "bottom");
//        blockIcon = register.registerIcon(getTextureName() + "_side");
//
//        iconMilk = register.registerIcon(Paths.ModAssets + "milk_still");
//        iconRennet = register.registerIcon(Paths.ModAssets + "rennet_still");
//        iconCheeseMass = register.registerIcon(Paths.ModAssets + "cheeseMass_still");
//    }

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

//    @SideOnly(Side.CLIENT)
//    public static IIcon getCauldronIcon(String s) {
//        return s.equals("inner") ? iconInner : (s.equals("bottom") ? iconBottom : null);
//    }

    public void setBlockBoundsForItemRender() {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public boolean isOpaqueCube() {return false;}

    public boolean isFullCube() {return false;}

    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        int i = ((Integer)state.getValue(level)).intValue();
        float f = (float)pos.getY() + (6.0F + (float)(3 * i)) / 16.0F;

        if (!worldIn.isRemote && entityIn.isBurning() && i > 0 && i < 15 && entityIn.getEntityBoundingBox().minY <= (double)f)
        {
            entityIn.extinguish();
            worldIn.setBlockState(pos, (IBlockState) ModBlocks.cauldron); //TODO Check if this works
            //world.setBlock(x, y, z, ModBlocks.cauldron);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.inventory.getCurrentItem();
        int j1 = ((Integer) state.getValue(level)).intValue();

        if (j1 == 15) {
            if (!playerIn.capabilities.isCreativeMode) {
                ItemStack cheese = new ItemStack(ModBlocks.cheese);
                if (!playerIn.inventory.addItemStackToInventory(cheese)) {
                    worldIn.spawnEntityInWorld(new EntityItem(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, cheese));
                } else if (playerIn instanceof EntityPlayerMP) {
                    ((EntityPlayerMP) playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                }
                worldIn.setBlockState(pos, state.getBlock().getDefaultState()); //ModBlocks.cauldron
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
                        changeWater(worldIn, pos, state, 3);
                    }
                    return true;
                }
                if (stack.getItem() == Items.bucket) {
                    if (j1 > 0 && j1 == 3) {
                        if (!playerIn.capabilities.isCreativeMode) {
                            playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, new ItemStack(Items.water_bucket));
                        }
                        changeWater(worldIn, pos, state, -3);
                    }
                    if (j1 > 0 && j1 == 6) {
                        if (!playerIn.capabilities.isCreativeMode) {
                            playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, new ItemStack(Items.milk_bucket));
                        }
                        worldIn.setBlockState(pos, state, 0);
                    }
                    return true;
                }
                if (stack.getItem() == Items.milk_bucket) {
                    if (j1 == 0) {
                        if (!playerIn.capabilities.isCreativeMode) {
                            playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, new ItemStack(Items.bucket));
                        }
                        worldIn.setBlockState(pos, state, 6);
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
                            changeWater(worldIn, pos, state, j1 - 1);
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
                            changeWater(worldIn, pos, state, j1 - 1);
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
                                worldIn.setBlockState(pos, state, 0);
                            }
                            if (j1 == 5) {
                                worldIn.setBlockState(pos, state, 6 - 2);
                            }
                            if (j1 == 6) {
                                worldIn.setBlockState(pos, state, 6 - 1);
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
                                worldIn.setBlockState(pos, state, 0);
                            }
                            if (j1 == 8) {
                                worldIn.setBlockState(pos, state, 9 - 2);
                            }
                            if (j1 == 9) {
                                worldIn.setBlockState(pos, state, 9 - 1);
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
                                changeWater(worldIn, pos, state, j1 + 1);
                            }
                            if (j1 == 1 || j1 == 2) {
                                changeWater(worldIn, pos, state, j1 + 1);
                            }
                            if (j1 == 3) {
                                changeWater(worldIn, pos, state, j1);
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
                                worldIn.setBlockState(pos, state, 4);
                            }
                            if (j1 == 4) {
                                worldIn.setBlockState(pos, state, 4 + 1);
                            }
                            if (j1 == 5 || j1 == 6) {
                                worldIn.setBlockState(pos, state, 4 + 2);
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
                                worldIn.setBlockState(pos, state, 7);
                            }
                            if (j1 == 7) {
                                worldIn.setBlockState(pos, state, 7 + 1);
                            }
                            if (j1 == 8 || j1 == 9) {
                                worldIn.setBlockState(pos, state, 7 + 2);
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
                                worldIn.setBlockState(pos, state, 13);
                            }
                            if (j1 == 5) {
                                worldIn.setBlockState(pos, state, 14);
                            }
                        }
                    }
                    if (stack.getItem() == ModItems.storageJar && stack.getItemDamage() == StorageJarType.MILK.getMetaData()) {
                        if (j1 > 0 && j1 == 7 || j1 == 13) {
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
                                worldIn.setBlockState(pos, state, 13);
                            }
                            if (j1 == 13) {
                                worldIn.setBlockState(pos, state, 14);

                            }
                        }
                    } else if (j1 > 0 && stack.getItem() instanceof ItemArmor) { //TODO Add check that metadata is under 3
                        ItemArmor itemarmor = (ItemArmor) stack.getItem();
                        if (itemarmor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER && itemarmor.hasColor(stack)) {
                            itemarmor.removeColor(stack);
                            changeWater(worldIn, pos, state, j1 - 1);
                            return true;
                        }
                    }
                    ItemStack itemstack1;
                    if (j1 > 0 && stack.getItem() instanceof ItemBanner && TileEntityBanner.func_175113_c(stack) > 0) {
                        itemstack1 = stack.copy();
                        itemstack1.stackSize = 1;
                        TileEntityBanner.func_175117_e(itemstack1);

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
                            changeWater(worldIn, pos, state, j1 - 1);
                        }

                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
    }

    public void changeWater(World worldIn, BlockPos pos, IBlockState state, int side) {
        worldIn.setBlockState(pos, state.withProperty(level, Integer.valueOf(MathHelper.clamp_int(side, 0, 3))), 2);
        worldIn.updateComparatorOutputLevel(pos, this);
    }

    public void fillWithRain(World worldIn, BlockPos pos) {
        if (worldIn.rand.nextInt(20) == 1) {
            IBlockState iblockstate = worldIn.getBlockState(pos);

            if (((Integer)iblockstate.getValue(level)).intValue() < 3) {
                worldIn.setBlockState(pos, iblockstate.cycleProperty(level), 2);
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
        return ((Integer)worldIn.getBlockState(pos).getValue(level)).intValue();
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(level, Integer.valueOf(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((Integer)state.getValue(level)).intValue();
    }

    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {level});
    }

    @SideOnly(Side.CLIENT)
    public static float getRenderLiquidLevel(int i) {
        int j = MathHelper.clamp_int(i, 0, 3);
        return (float) (6 + 3 * j) / 16.0F;
    }

    @SideOnly(Side.CLIENT)
    public static float getRenderMilkLevel(int i) {
        int j = MathHelper.clamp_int(i, 4, 6);
        return (float) (6 + 3 * j - 9) / 16.0F;
    }

    @SideOnly(Side.CLIENT)
    public static float getRenderRennetLevel(int i) {
        int j = MathHelper.clamp_int(i, 7, 9);
        return (float) (6 + 3 * j - 18) / 16.0F;
    }
    @SideOnly(Side.CLIENT)
    public static float getRenderCheeseMassLevel(int i) {
        int j = MathHelper.clamp_int(i, 13, 14);
        return (float) (6 + 3 * j - 33) / 16.0F;
    }
}