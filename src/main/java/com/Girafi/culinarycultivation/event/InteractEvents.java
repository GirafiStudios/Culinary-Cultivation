package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.block.BlockModCauldron;
import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemStorageJar.StorageJarType;
import com.Girafi.culinarycultivation.network.NetworkHandler;
import com.Girafi.culinarycultivation.network.packet.PacketUpdateFoodOnClient;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCake;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class InteractEvents {

    public static class CakeKnifeEvent {
        @SubscribeEvent
        public void CakeKnifeInteractionEvent(PlayerInteractEvent iEvent) {
            EntityPlayer player = iEvent.entityPlayer;
            int x = iEvent.pos.getX();
            int y = iEvent.pos.getY();
            int z = iEvent.pos.getZ();
            int meta = iEvent.world.getBlockState(iEvent.pos).getBlock().getMetaFromState(iEvent.world.getBlockState(iEvent.pos));

            boolean b = player.onGround && player.isSneaking();
            if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == ModItems.cakeKnife) {
                if (iEvent.world.getBlockState(iEvent.pos).getBlock() == Blocks.cake) {
                    if (player.getFoodStats().needFood() && iEvent.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                        NetworkHandler.instance.sendTo(new PacketUpdateFoodOnClient(-2, -0.1F), (EntityPlayerMP) player);
                    }
                    if (b && iEvent.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK || b && iEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                        iEvent.world.setBlockToAir(iEvent.pos);
                        if (!iEvent.world.isRemote) {
                            if (meta == 0) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, x, y, z, new ItemStack(Items.cake)));
                            }
                            if (meta == 1) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, x, y, z, new ItemStack(ModItems.pieceOfCake, 6)));
                            }
                            if (meta == 2) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, x, y, z, new ItemStack(ModItems.pieceOfCake, 5)));
                            }
                            if (meta == 3) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, x, y, z, new ItemStack(ModItems.pieceOfCake, 4)));
                            }
                            if (meta == 4) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, x, y, z, new ItemStack(ModItems.pieceOfCake, 3)));
                            }
                            if (meta == 5) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, x, y, z, new ItemStack(ModItems.pieceOfCake, 2)));
                            }
                            if (meta == 6) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, x, y, z, new ItemStack(ModItems.pieceOfCake, 1)));
                            }
                        }
                    }
                    if (!b && iEvent.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK || !b && iEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                        if (player.canEat(false)) {
                            player.getFoodStats().addStats(-2, 0.0F);

                            int i = ((Integer)iEvent.world.getBlockState(iEvent.pos).getValue(BlockCake.BITES)).intValue();
                            if (!iEvent.world.isRemote) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, x, y, z, new ItemStack(ModItems.pieceOfCake)));
                            } else {
                                if (!iEvent.world.isRemote) {
                                    iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, x, y, z, new ItemStack(ModItems.pieceOfCake)));
                                    if (i >= 6) {
                                        iEvent.world.setBlockState(iEvent.pos, iEvent.world.getBlockState(iEvent.pos).withProperty(BlockCake.BITES, Integer.valueOf(i + 1)), 3);
                                    } else {
                                        iEvent.world.setBlockToAir(iEvent.pos);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static class CauldronTransformation {
        @SubscribeEvent
        public void CauldronTransformationEvent(PlayerInteractEvent iEvent) {
            if (iEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && iEvent.entityPlayer.getCurrentEquippedItem() != null) {
                ItemStack equipped = iEvent.entityPlayer.getCurrentEquippedItem();
                boolean nullCheck = equipped.getItem() != null;
                int meta = iEvent.world.getBlockState(iEvent.pos).getBlock().getMetaFromState(iEvent.world.getBlockState(iEvent.pos));
                Block getBlock = iEvent.world.getBlockState(iEvent.pos).getBlock();

                if (getBlock == Blocks.cauldron) {
                    if (nullCheck && equipped.getItem() == ModItems.storageJar || nullCheck && equipped.getItem() == Items.bucket || nullCheck && equipped.getItem() == Items.milk_bucket) {
                        iEvent.world.setBlockState(iEvent.pos, ModBlocks.cauldron.getDefaultState());
                        changeWater(iEvent.world, iEvent.pos, iEvent.world.getBlockState(iEvent.pos), meta);
                    }
                }
            }
        }

        public void changeWater(World worldIn, BlockPos pos, IBlockState state, int side) {
            worldIn.setBlockState(pos, state.withProperty(BlockModCauldron.level, Integer.valueOf(MathHelper.clamp_int(side, 0, 3))), 2);
            worldIn.updateComparatorOutputLevel(pos, ModBlocks.cauldron);
        }
    }

    public static class DebugItemEvent {
        @SubscribeEvent
        public void DebugItem(PlayerInteractEvent iEvent) { //TODO Make check that the variant is valid
            EntityPlayer player = iEvent.entityPlayer;
            ItemStack stack = player.inventory.getCurrentItem();
            boolean b = player.onGround && player.isSneaking();
            if (iEvent.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
                if (stack != null && player.getCurrentEquippedItem().getItem() == ModItems.debugItem && player.getCurrentEquippedItem().getItemDamage() == 0) {
                    if (!iEvent.world.isRemote) {
                        player.addChatComponentMessage(new ChatComponentText(iEvent.world.getBlockState(iEvent.pos).getBlock().getLocalizedName() + " | " + "State: " + iEvent.world.getBlockState(iEvent.pos)));
                    }
                    iEvent.setCanceled(true);
                }
            }
            if (!b && iEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                if (stack != null && player.getCurrentEquippedItem().getItem() == ModItems.debugItem && player.getCurrentEquippedItem().getItemDamage() == 0) {
                    int l = iEvent.world.getBlockState(iEvent.pos).getBlock().getMetaFromState(iEvent.world.getBlockState(iEvent.pos)) + 1;
                    iEvent.world.setBlockState(iEvent.pos, iEvent.world.getBlockState(iEvent.pos).getBlock().getStateFromMeta(l), 2);
                }
            }
            if (b && iEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                if (stack != null && player.getCurrentEquippedItem().getItem() == ModItems.debugItem && player.getCurrentEquippedItem().getItemDamage() == 0) {
                    int l = iEvent.world.getBlockState(iEvent.pos).getBlock().getMetaFromState(iEvent.world.getBlockState(iEvent.pos)) - 1;
                    iEvent.world.setBlockState(iEvent.pos, iEvent.world.getBlockState(iEvent.pos).getBlock().getStateFromMeta(l), 2);
                }
            }
        }
    }

    public static class StorageJarMilkFill {
        @SubscribeEvent
        public void StorageJarMiliFillEvent(EntityInteractEvent iEvent) {
            ItemStack stack = iEvent.entityPlayer.inventory.getCurrentItem();
            if (iEvent.target instanceof EntityCow & !iEvent.entityLiving.isChild()) {
                if (stack != null && stack.getItem() == ModItems.storageJar && stack.getItemDamage() == StorageJarType.EMPTY.getMetaData() && !iEvent.entityPlayer.capabilities.isCreativeMode) {
                    if (stack.stackSize-- == 1) {
                        iEvent.entityPlayer.inventory.setInventorySlotContents(iEvent.entityPlayer.inventory.currentItem, new ItemStack(ModItems.storageJar, 1, StorageJarType.MILK.getMetaData()));
                    } else if (!iEvent.entityPlayer.inventory.addItemStackToInventory(new ItemStack(ModItems.storageJar, 1, StorageJarType.MILK.getMetaData()))) {
                        iEvent.entityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.storageJar, 1, StorageJarType.MILK.getMetaData()), false);
                    }
                }
            }
        }
    }
}