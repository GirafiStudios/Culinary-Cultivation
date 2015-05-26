package com.Girafi.culinarycultivation.block;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemStorageJar.StorageJarType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockCauldron;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockModCauldron extends BlockCauldron { //TODO Might have to make it an tile entity, tank-isch or something like that. The best would be to keep it as close to vanilla as possible

    public BlockModCauldron() {
        super();
        setBlockName("cauldron");
        setBlockTextureName("cauldron");
        setHardness(2.0F);
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
                        this.func_150024_a(world, x, y, z, 3);
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

                            this.func_150024_a(world, x, y, z, j1 - 1);
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

                            this.func_150024_a(world, x, y, z, j1 - 1);
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
                                this.func_150024_a(world, x, y, z, j1 + 1);
                            }
                            if (meta == 1 || meta == 2) {
                                this.func_150024_a(world, x, y, z, j1 + 1);
                            }
                            if (meta == 3){
                                this.func_150024_a(world, x, y, z, j1);
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
                                this.func_150024_a(world, x, y, z, j1 - 3);
                        }
                    } else if (j1 > 0 && stack.getItem() instanceof ItemArmor && ((ItemArmor) stack.getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.CLOTH) {
                        ItemArmor itemarmor = (ItemArmor) stack.getItem();
                        itemarmor.removeColor(stack);
                        this.func_150024_a(world, x, y, z, j1 - 1);
                        return true;
                    }
                    return false;
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemIconName() { return "cauldron"; }
}
