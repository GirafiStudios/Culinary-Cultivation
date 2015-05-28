package com.Girafi.culinarycultivation.block;

import com.Girafi.culinarycultivation.CulinaryCultivation;
import com.Girafi.culinarycultivation.client.render.block.RenderCauldron;
import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemStorageJar.StorageJarType;
import com.Girafi.culinarycultivation.tileentity.TileEntityCauldron;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockModCauldron extends SourceBlockTileEntity{
    @SideOnly(Side.CLIENT)
    public static IIcon iconInner;
    @SideOnly(Side.CLIENT)
    public static IIcon iconTop;
    @SideOnly(Side.CLIENT)
    public static IIcon iconBottom;

    public BlockModCauldron() {
        super();
        setBlockName("cauldron");
        setBlockTextureName("cauldron");
        setHardness(2.0F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityCauldron();
    }

    @Override
    public int getRenderType() {
        return CulinaryCultivation.proxy.getRenderIdForRenderer(RenderCauldron.class);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int side) {
        super.breakBlock(world, x, y, z, block, side);
        world.removeTileEntity(x, y, z);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        return side == 1 ? this.iconTop : (side == 0 ? this.iconBottom : this.blockIcon);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register) {
        this.iconInner = register.registerIcon(this.getTextureName() + "_" + "inner");
        this.iconTop = register.registerIcon(this.getTextureName() + "_top");
        this.iconBottom = register.registerIcon(this.getTextureName() + "_" + "bottom");
        this.blockIcon = register.registerIcon(this.getTextureName() + "_side");
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity) {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
        float f = 0.125F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
        this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
        this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
        this.setBlockBoundsForItemRender();
    }

    @SideOnly(Side.CLIENT)
    public static IIcon getCauldronIcon(String s) {
        return s.equals("inner") ? iconInner : (s.equals("bottom") ? iconBottom : null);
    }

    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        int l = func_150027_b(world.getBlockMetadata(x, y, z));
        float f = (float) y + (6.0F + (float) (3 * l)) / 16.0F;

        if (!world.isRemote && entity.isBurning() && l > 0 && entity.boundingBox.minY <= (double) f) {
            entity.extinguish();
            this.changeWater(world, x, y, z, l - 1);
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        } else {
            ItemStack stack = player.inventory.getCurrentItem();
            if (stack == null) {
                return true;
            } else {
                int meta = world.getBlockMetadata(x, y, z);
                int j1 = func_150027_b(meta);
                if (stack.getItem() == Items.water_bucket) {
                    if (j1 < 3) {
                        if (!player.capabilities.isCreativeMode) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.bucket));
                        }
                        this.changeWater(world, x, y, z, 3);
                    }
                    return true;
                } else {
                    if (stack.getItem() == Items.glass_bottle) {
                        if (j1 > 0) {
                            if (!player.capabilities.isCreativeMode) {
                                ItemStack itemstack1 = new ItemStack(Items.potionitem, 1, 0);

                                if (!player.inventory.addItemStackToInventory(itemstack1)) {
                                    world.spawnEntityInWorld(new EntityItem(world, (double) x + 0.5D, (double) y + 1.5D, (double) z + 0.5D, itemstack1));
                                } else if (player instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                                }
                                --stack.stackSize;
                                if (stack.stackSize <= 0) {
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                                }
                            }
                            this.changeWater(world, x, y, z, j1 - 1);
                        }
                    }
                    if (stack.getItem() == ModItems.storageJar) {
                        if (j1 > 0) {
                            if (!player.capabilities.isCreativeMode) {
                                ItemStack stackStorageJar = new ItemStack(ModItems.storageJar, 1, StorageJarType.WATER.getMetaData());

                                if (!player.inventory.addItemStackToInventory(stackStorageJar)) {
                                    world.spawnEntityInWorld(new EntityItem(world, (double) x + 0.5D, (double) y + 1.5D, (double) z + 0.5D, stackStorageJar));
                                } else if (player instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                                }
                                --stack.stackSize;
                                if (stack.stackSize <= 0) {
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                                }
                            }
                            this.changeWater(world, x, y, z, j1 - 1);
                        }
                    }
                    if (stack.getItem() == ModItems.storageJar && stack.getItemDamage() == StorageJarType.WATER.getMetaData()) {
                        if (j1 > 0 || j1 == 0) {
                            if (!player.capabilities.isCreativeMode) {
                                ItemStack stackStorageJarWater = new ItemStack(ModItems.storageJar, 1, StorageJarType.EMPTY.getMetaData());

                                if (!player.inventory.addItemStackToInventory(stackStorageJarWater)) {
                                    world.spawnEntityInWorld(new EntityItem(world, (double) x + 0.5D, (double) y + 1.5D, (double) z + 0.5D, stackStorageJarWater));
                                } else if (player instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                                }
                                --stack.stackSize;
                                if (stack.stackSize <= 0) {
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                                }
                            }
                            if (meta == 0) {
                                this.changeWater(world, x, y, z, j1 + 1);
                            }
                            if (meta == 1 || meta == 2) {
                                this.changeWater(world, x, y, z, j1 + 1);
                            }
                            if (meta == 3) {
                                this.changeWater(world, x, y, z, j1);
                            }
                        }
                    }
                    if (stack.getItem() == Items.bucket) {
                        if (j1 > 0 && meta == 3) {
                            if (!player.capabilities.isCreativeMode) {
                                ItemStack bucket = new ItemStack(Items.water_bucket);
                                if (!player.inventory.addItemStackToInventory(bucket)) {
                                    world.spawnEntityInWorld(new EntityItem(world, (double) x + 0.5D, (double) y + 1.5D, (double) z + 0.5D, bucket));
                                } else if (player instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
                                }
                                --stack.stackSize;
                                if (stack.stackSize <= 0) {
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                                }
                            }
                            this.changeWater(world, x, y, z, j1 - 3);
                        }
                    } else if (j1 > 0 && stack.getItem() instanceof ItemArmor && ((ItemArmor) stack.getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.CLOTH) {
                        ItemArmor itemarmor = (ItemArmor) stack.getItem();
                        itemarmor.removeColor(stack);
                        this.changeWater(world, x, y, z, j1 - 1);
                        return true;
                    }
                    return false;
                }
            }
        }
    }

    public void changeWater(World world, int x, int y, int z, int side) {
        world.setBlockMetadataWithNotify(x, y, z, MathHelper.clamp_int(side, 0, 3), 2);
        world.func_147453_f(x, y, z, this);
    }

    public void fillWithRain(World world, int x, int y, int z) {
        if (world.rand.nextInt(20) == 1) {
            int l = world.getBlockMetadata(x, y, z);
            if (l < 3) {
                world.setBlockMetadataWithNotify(x, y, z, l + 1, 2);
            }
        }
    }

    public Item getItemDropped(int p_149650_1_, Random random, int p_149650_3_) {
        return Items.cauldron;
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z) {
        return Items.cauldron;
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        int i1 = world.getBlockMetadata(x, y, z);
        return func_150027_b(i1);
    }

    public static int func_150027_b(int meta) {
        return meta;
    }

    @SideOnly(Side.CLIENT)
    public static float getRenderLiquidLevel(int i) {
        int j = MathHelper.clamp_int(i, 0, 3);
        return (float) (6 + 3 * j) / 16.0F;
    }
}