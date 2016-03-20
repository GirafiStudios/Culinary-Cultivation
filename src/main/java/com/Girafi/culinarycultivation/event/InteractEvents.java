package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.block.BlockCrop;
import com.Girafi.culinarycultivation.block.BlockModCauldron;
import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemStorageJar.StorageJarType;
import com.Girafi.culinarycultivation.item.equipment.armor.farmer.ItemFarmerArmor;
import com.Girafi.culinarycultivation.item.equipment.tool.ItemCaneKnife;
import com.Girafi.culinarycultivation.network.NetworkHandler;
import com.Girafi.culinarycultivation.network.packet.PacketUpdateFoodOnClient;
import net.minecraft.block.*;
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
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
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
            return world.getBlockState(pos).getValue(BlockCake.BITES);
        }

        public IBlockState stateWithProperty(World world, BlockPos pos) {
            return world.getBlockState(pos).withProperty(BlockCake.BITES, state(world, pos) + 1);
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
            return ModItems.kitchenKnife;
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

        public void changeWater(World world, BlockPos pos, IBlockState state, int side) {
            world.setBlockState(pos, state.withProperty(BlockModCauldron.LEVEL, MathHelper.clamp_int(side, 0, 3)), 2);
            world.updateComparatorOutputLevel(pos, ModBlocks.cauldron);
        }

        @SubscribeEvent
        public void RemoveDyeEvent(PlayerInteractEvent iEvent) {
            if (iEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && iEvent.entityPlayer.getCurrentEquippedItem() != null && iEvent.entityPlayer.getCurrentEquippedItem().getItem() instanceof ItemFarmerArmor && iEvent.world.getBlockState(iEvent.pos).getBlock() == Blocks.cauldron) {
                int i = iEvent.world.getBlockState(iEvent.pos).getValue(BlockCauldron.LEVEL);
                ItemFarmerArmor itemFarmerArmor = (ItemFarmerArmor) iEvent.entityPlayer.getCurrentEquippedItem().getItem();
                if (itemFarmerArmor.getArmorMaterial() == ItemFarmerArmor.farmerArmorMaterial && itemFarmerArmor.hasColor(iEvent.entityPlayer.getCurrentEquippedItem()) && i > 0) {
                    itemFarmerArmor.removeColor(iEvent.entityPlayer.getCurrentEquippedItem());
                    iEvent.entityPlayer.triggerAchievement(StatList.field_181727_K);
                    this.setWaterLevel(iEvent.world, iEvent.pos, iEvent.world.getBlockState(iEvent.pos), i - 1);
                }
            }
        }

        public void setWaterLevel(World worldIn, BlockPos pos, IBlockState state, int level) {
            worldIn.setBlockState(pos, state.withProperty(BlockCauldron.LEVEL, MathHelper.clamp_int(level, 0, 3)), 2);
            worldIn.updateComparatorOutputLevel(pos, Blocks.cauldron);
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
        public void StorageJarMilkFillEvent(EntityInteractEvent iEvent) {
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

    public static class CaneKnife {
        @SubscribeEvent
        public void CaneKnifeOnSugarCane(BlockEvent.BreakEvent breakEvent) {
            if (breakEvent.getPlayer().getCurrentEquippedItem() != null && breakEvent.getPlayer().getCurrentEquippedItem().getItem() instanceof ItemCaneKnife) {
                World world = breakEvent.world;
                BlockPos pos = breakEvent.pos;
                Block block = world.getBlockState(pos).getBlock();
                if (block == Blocks.reeds) {
                    //4 Tall
                    if (world.getBlockState(pos.up()).getBlock() != Blocks.reeds) {
                        if (world.getBlockState(pos.down(4)) != Blocks.reeds && world.getBlockState(pos.down(3)).getBlock() == Blocks.reeds) {
                            block.dropBlockAsItem(world, pos, breakEvent.state, 0);
                            world.setBlockToAir(pos.down(2));
                        }
                        //3 Tall
                        if (world.getBlockState(pos.down(3)) != Blocks.reeds && world.getBlockState(pos.down(2)).getBlock() == Blocks.reeds) {
                            block.dropBlockAsItem(world, pos, breakEvent.state, 0);
                            world.setBlockToAir(pos.down());
                        }
                    }
                    //4 Tall, 3th block
                    if (world.getBlockState(pos.down()).getBlock() == Blocks.reeds && world.getBlockState(pos.up()).getBlock() == Blocks.reeds && world.getBlockState(pos.up(2)).getBlock() != Blocks.reeds) {
                        if (world.getBlockState(pos.down(2)).getBlock() == Blocks.reeds) {
                            block.dropBlockAsItem(world, pos, breakEvent.state, 0);
                            world.setBlockToAir(pos.down());
                        }
                    }
                    //Harvesting bottom block
                    if (world.getBlockState(pos.down()).getBlock() != Blocks.reeds) {
                        breakEvent.setCanceled(true);
                        if (world.getBlockState(pos.up()).getBlock() == Blocks.reeds) {
                            block.dropBlockAsItem(world, pos, breakEvent.state, 0);
                            world.setBlockToAir(pos.up());
                        }
                    }
                }
            }
        }
    }

    public static class VanillaCrops {
        @SubscribeEvent
        public void VanillaRightClickCropsHarvesting(PlayerInteractEvent iEvent) {
            if (iEvent.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                World world = iEvent.world;
                BlockPos pos = iEvent.pos;
                IBlockState state = iEvent.world.getBlockState(pos);
                Block block = world.getBlockState(pos).getBlock();

                if (block instanceof BlockCrops && !(block instanceof BlockCrop)) {
                    int age = state.getValue(BlockCrops.AGE);
                    if (age >= 7) {
                        block.dropBlockAsItem(world, pos, state, 0);
                        world.setBlockState(pos, state.withProperty(BlockCrop.AGE, 0), 2);
                    }
                } else if (block instanceof BlockNetherWart) {
                    int ageWart = state.getValue(BlockNetherWart.AGE);
                    if (ageWart >= 3) {
                        block.dropBlockAsItem(world, pos, state, 0);
                        world.setBlockState(pos, state.withProperty(BlockNetherWart.AGE, 0), 2);
                    }
                }
            }
        }
    }
}