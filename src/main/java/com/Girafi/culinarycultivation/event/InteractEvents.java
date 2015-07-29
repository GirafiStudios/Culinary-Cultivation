package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemStorageJar.StorageJarType;
import com.Girafi.culinarycultivation.network.NetworkHandler;
import com.Girafi.culinarycultivation.network.packet.PacketUpdateFoodOnClient;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class InteractEvents {
    public static class ToolOnBlockInteractionEvent {
        @SubscribeEvent
        public void CakeKnifeInteractionEvent(PlayerInteractEvent iEvent) {
            EntityPlayer player = iEvent.entityPlayer;
            int meta = iEvent.world.getBlockMetadata(iEvent.x, iEvent.y, iEvent.z);
            boolean b = player.onGround && player.isSneaking();
            if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == equippedItem()) {
                if (iEvent.world.getBlock(iEvent.x, iEvent.y, iEvent.z) == baseBlock()) {
                    if (player.getFoodStats().needFood() && iEvent.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                        NetworkHandler.instance.sendTo(new PacketUpdateFoodOnClient(-hungerAmount(), -saturationAmount()), (EntityPlayerMP) player);
                    }
                    if (b && iEvent.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK || b && iEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                        iEvent.world.setBlockToAir(iEvent.x, iEvent.y, iEvent.z);
                        if (!iEvent.world.isRemote) {
                            if (meta == 0) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, iEvent.x, iEvent.y, iEvent.z, new ItemStack(baseItem())));
                            }
                            if (meta == 1) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, iEvent.x, iEvent.y, iEvent.z, new ItemStack(sliceItem(), 5)));
                            }
                            if (meta == 2) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, iEvent.x, iEvent.y, iEvent.z, new ItemStack(sliceItem(), 4)));
                            }
                            if (meta == 3) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, iEvent.x, iEvent.y, iEvent.z, new ItemStack(sliceItem(), 3)));
                            }
                            if (meta == 4) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, iEvent.x, iEvent.y, iEvent.z, new ItemStack(sliceItem(), 2)));
                            }
                            if (meta == 5) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, iEvent.x, iEvent.y, iEvent.z, new ItemStack(sliceItem(), 1)));
                            }
                        }
                    }
                    if (!b && iEvent.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK || !b && iEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                        int l = iEvent.world.getBlockMetadata(iEvent.x, iEvent.y, iEvent.z) + 1;
                        if (player.canEat(false)) {
                            if (iEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                                player.getFoodStats().addStats(-hungerAmount(), -saturationAmount());
                            }
                            if (iEvent.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
                                if (l >= 6) {
                                    iEvent.world.setBlockToAir(iEvent.x, iEvent.y, iEvent.z);
                                } else {
                                    iEvent.world.setBlockMetadataWithNotify(iEvent.x, iEvent.y, iEvent.z, l, 2);
                                }
                            }
                            if (!iEvent.world.isRemote) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, iEvent.x, iEvent.y, iEvent.z, new ItemStack(sliceItem())));
                            }
                        } else {
                            if (!iEvent.world.isRemote) {
                                iEvent.world.spawnEntityInWorld(new EntityItem(iEvent.world, iEvent.x, iEvent.y, iEvent.z, new ItemStack(sliceItem())));
                                if (l >= 6) {
                                    iEvent.world.setBlockToAir(iEvent.x, iEvent.y, iEvent.z);
                                } else {
                                    iEvent.world.setBlockMetadataWithNotify(iEvent.x, iEvent.y, iEvent.z, l, 2);
                                }
                            }
                        }
                        if (iEvent.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
                            iEvent.setCanceled(true);
                        }
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

        public int hungerAmount() {
            return 2;
        }

        public float saturationAmount() {
            return 0.1F;
        }
    }

    public static class CakeInteractionEvent extends ToolOnBlockInteractionEvent {
    }

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
                int meta = iEvent.world.getBlockMetadata(iEvent.x, iEvent.y, iEvent.z);
                int j1 = func_150027_b(meta);
                Block getBlock = iEvent.world.getBlock(iEvent.x, iEvent.y, iEvent.z);

                if (getBlock == Blocks.cauldron) {
                    if (nullCheck && equipped.getItem() == ModItems.storageJar || nullCheck && equipped.getItem() == Items.bucket || nullCheck && equipped.getItem() == Items.milk_bucket) {
                        iEvent.world.setBlock(iEvent.x, iEvent.y, iEvent.z, ModBlocks.cauldron);
                        changeWater(iEvent.world, iEvent.x, iEvent.y, iEvent.z, j1);
                    }
                }
            }
        }

        public void changeWater(World world, int x, int y, int z, int p_150024_5_) {
            world.setBlockMetadataWithNotify(x, y, z, MathHelper.clamp_int(p_150024_5_, 0, 3), 2);
            world.func_147453_f(x, y, z, ModBlocks.cauldron);
        }

        public static int func_150027_b(int var1) {
            return var1;
        }
    }

    public static class DebugItemEvent {
        @SubscribeEvent
        public void DebugItem(PlayerInteractEvent iEvent) {
            EntityPlayer player = iEvent.entityPlayer;
            ItemStack stack = iEvent.entityPlayer.inventory.getCurrentItem();
            boolean b = player.onGround && player.isSneaking();
            if (iEvent.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
                if (stack != null && player.getCurrentEquippedItem().getItem() == ModItems.debugItem && player.getCurrentEquippedItem().getItemDamage() == 0) {
                    if (!iEvent.world.isRemote) {
                        player.addChatComponentMessage(new ChatComponentText(iEvent.world.getBlock(iEvent.x, iEvent.y, iEvent.z).getLocalizedName() + " | " + "Metadata: " + iEvent.world.getBlockMetadata(iEvent.x, iEvent.y, iEvent.z)));
                    }
                    iEvent.setCanceled(true);
                }
            }
            if (!b && iEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                if (stack != null && player.getCurrentEquippedItem().getItem() == ModItems.debugItem && player.getCurrentEquippedItem().getItemDamage() == 0) {
                    int l = iEvent.world.getBlockMetadata(iEvent.x, iEvent.y, iEvent.z) + 1;
                    iEvent.world.setBlockMetadataWithNotify(iEvent.x, iEvent.y, iEvent.z, l, 2);
                }
            }
            if (b && iEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                if (stack != null && player.getCurrentEquippedItem().getItem() == ModItems.debugItem && player.getCurrentEquippedItem().getItemDamage() == 0) {
                    int l = iEvent.world.getBlockMetadata(iEvent.x, iEvent.y, iEvent.z) - 1;
                    iEvent.world.setBlockMetadataWithNotify(iEvent.x, iEvent.y, iEvent.z, l, 2);
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