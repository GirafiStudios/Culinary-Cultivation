package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.block.BlockModCauldron;
import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemStorageJar.StorageJarType;
import com.Girafi.culinarycultivation.network.NetworkHandler;
import com.Girafi.culinarycultivation.network.packet.PacketUpdateFoodOnClient;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCake;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class InteractEvents {

    public static class ToolOnBlockInteractionEvent {
        @SubscribeEvent
        public void CakeKnifeInteractionEvent(PlayerInteractEvent iEvent) {
            if (iEvent.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK || iEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                EntityPlayer player = iEvent.entityPlayer;
                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == equippedItem() && iEvent.world.getBlockState(iEvent.pos).getBlock() == baseBlock()) {
                    int x = (int) iEvent.entityPlayer.posX;
                    int y = (int) iEvent.entityPlayer.posY;
                    int z = (int) iEvent.entityPlayer.posZ;
                    boolean b = player.isSneaking();
                    int state = state(iEvent.world, iEvent.pos);

                    if (player.getFoodStats().needFood() && iEvent.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                        NetworkHandler.instance.sendTo(new PacketUpdateFoodOnClient(-hungerAmount(), -saturationAmount()), (EntityPlayerMP) player);
                    }
                    if (b) {
                        iEvent.world.setBlockToAir(iEvent.pos);
                        if (!iEvent.world.isRemote) {
                            if (state == 0) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, x, y, z, new ItemStack(baseItem())));
                            }
                            if (state == 1) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, x, y, z, new ItemStack(sliceItem(), 6)));
                            }
                            if (state == 2) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, x, y, z, new ItemStack(sliceItem(), 5)));
                            }
                            if (state == 3) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, x, y, z, new ItemStack(sliceItem(), 4)));
                            }
                            if (state == 4) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, x, y, z, new ItemStack(sliceItem(), 3)));
                            }
                            if (state == 5) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, x, y, z, new ItemStack(sliceItem(), 2)));
                            }
                            if (state == 6) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, x, y, z, new ItemStack(sliceItem(), 1)));
                            }
                        }
                    }
                    if (!b) {
                        if (player.getFoodStats().needFood() && iEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                            player.getFoodStats().addStats(-hungerAmount(), -saturationAmount());

                            if (!iEvent.world.isRemote) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, x, y, z, new ItemStack(sliceItem())));
                            }
                        } else if (!iEvent.world.isRemote) {
                            iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, x, y, z, new ItemStack(sliceItem())));
                            if (state < 6) {
                                iEvent.world.setBlockState(iEvent.pos, stateWithProperty(iEvent.world, iEvent.pos), 3);
                            } else {
                                iEvent.world.setBlockToAir(iEvent.pos);
                            }
                        }
                    }
                    if (iEvent.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
                        iEvent.setCanceled(true);
                    }
                }
            }
        }
        public Item equippedItem() {
            return ModItems.cakeKnife;
        }

        public Item sliceItem() {
            return ModItems.pieceOfCake;
        }

        public Item baseItem() {
            return Items.cake;
        }

        public Block baseBlock() {
            return Blocks.cake;
        }

        public int state(World world, BlockPos pos) {
            return ((Integer) world.getBlockState(pos).getValue(BlockCake.BITES)).intValue();
        }

        public IBlockState stateWithProperty(World world, BlockPos pos) {
            return world.getBlockState(pos).withProperty(BlockCake.BITES, Integer.valueOf(state(world, pos) + 1));
        }

        public int hungerAmount() {
            return 2;
        }

        public float saturationAmount() {
            return 0.1F;
        }
    }

    public static class CakeInteractionEvent extends ToolOnBlockInteractionEvent {}

    public static class CheeseInteractionEvent extends ToolOnBlockInteractionEvent {
        public Item equippedItem() {
            return ModItems.knife;
        }

        public Item sliceItem() {
            return ModItems.cheeseSlice;
        }

        public Item baseItem() {
            return Item.getItemFromBlock(ModBlocks.cheese);
        }

        public Block baseBlock() {
            return ModBlocks.cheese;
        }

        public float saturationAmount() {
            return 0.4F;
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
            worldIn.setBlockState(pos, state.withProperty(BlockModCauldron.LEVEL, Integer.valueOf(MathHelper.clamp_int(side, 0, 3))), 2);
            worldIn.updateComparatorOutputLevel(pos, ModBlocks.cauldron);
        }
    }

    public static class DebugItemEvent {
        @SubscribeEvent
        public void DebugItem(PlayerInteractEvent iEvent) {

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
            if (iEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && stack != null && player.getCurrentEquippedItem().getItem() == ModItems.debugItem && player.getCurrentEquippedItem().getItemDamage() == 0) {
                BlockState state = iEvent.world.getBlockState(iEvent.pos).getBlock().getBlockState();
                if (!b) {
                    int l = iEvent.world.getBlockState(iEvent.pos).getBlock().getMetaFromState(iEvent.world.getBlockState(iEvent.pos)) + 1;
                    if (l >= state.getValidStates().size()) {
                        iEvent.world.setBlockState(iEvent.pos, iEvent.world.getBlockState(iEvent.pos).getBlock().getDefaultState(), 2);
                    } else
                        iEvent.world.setBlockState(iEvent.pos, iEvent.world.getBlockState(iEvent.pos).getBlock().getStateFromMeta(l), 2);
                }
                if (b) {
                    int l = iEvent.world.getBlockState(iEvent.pos).getBlock().getMetaFromState(iEvent.world.getBlockState(iEvent.pos)) - 1;
                    if (l < 0) {
                        iEvent.world.setBlockState(iEvent.pos, iEvent.world.getBlockState(iEvent.pos).getBlock().getDefaultState(), 2);
                    } else
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