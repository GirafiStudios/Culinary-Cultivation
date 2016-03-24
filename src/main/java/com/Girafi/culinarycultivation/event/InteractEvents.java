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
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class InteractEvents {

    public static class ToolOnBlockInteractionEvent {
        @SubscribeEvent
        public void CakeKnifeInteractionEvent(PlayerInteractEvent iEvent) {
            if (iEvent.getAction() == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK || iEvent.getAction() == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                EntityPlayer player = iEvent.getEntityPlayer();
                if (player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() == equippedItem() && iEvent.getWorld().getBlockState(iEvent.getPos()).getBlock() == baseBlock()) {
                    int x = (int) iEvent.getEntityPlayer().posX;
                    int y = (int) iEvent.getEntityPlayer().posY;
                    int z = (int) iEvent.getEntityLiving().posZ;
                    boolean b = player.isSneaking();
                    int state = state(iEvent.getWorld(), iEvent.getPos());

                    if (player.getFoodStats().needFood() && iEvent.getAction() != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                        NetworkHandler.instance.sendTo(new PacketUpdateFoodOnClient(-hungerAmount(), -saturationAmount()), (EntityPlayerMP) player);
                    }
                    if (b) {
                        iEvent.getWorld().setBlockToAir(iEvent.getPos());
                        if (!iEvent.getWorld().isRemote) {
                            if (state == 0) {
                                iEvent.getWorld().spawnEntityInWorld(new EntityItem(iEvent.getWorld(), x, y, z, new ItemStack(baseItem())));
                            }
                            if (state == 1) {
                                iEvent.getWorld().spawnEntityInWorld(new EntityItem(iEvent.getWorld(), x, y, z, new ItemStack(sliceItem(), 6)));
                            }
                            if (state == 2) {
                                iEvent.getWorld().spawnEntityInWorld(new EntityItem(iEvent.getWorld(), x, y, z, new ItemStack(sliceItem(), 5)));
                            }
                            if (state == 3) {
                                iEvent.getWorld().spawnEntityInWorld(new EntityItem(iEvent.getWorld(), x, y, z, new ItemStack(sliceItem(), 4)));
                            }
                            if (state == 4) {
                                iEvent.getWorld().spawnEntityInWorld(new EntityItem(iEvent.getWorld(), x, y, z, new ItemStack(sliceItem(), 3)));
                            }
                            if (state == 5) {
                                iEvent.getWorld().spawnEntityInWorld(new EntityItem(iEvent.getWorld(), x, y, z, new ItemStack(sliceItem(), 2)));
                            }
                            if (state == 6) {
                                iEvent.getWorld().spawnEntityInWorld(new EntityItem(iEvent.getWorld(), x, y, z, new ItemStack(sliceItem(), 1)));
                            }
                        }
                    }
                    if (!b) {
                        if (player.getFoodStats().needFood() && iEvent.getAction() == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                            player.getFoodStats().addStats(-hungerAmount(), -saturationAmount());

                            if (!iEvent.getWorld().isRemote) {
                                iEvent.getWorld().spawnEntityInWorld(new EntityItem(iEvent.getWorld(), x, y, z, new ItemStack(sliceItem())));
                            }
                        } else if (!iEvent.getWorld().isRemote) {
                            iEvent.getWorld().spawnEntityInWorld(new EntityItem(iEvent.getWorld(), x, y, z, new ItemStack(sliceItem())));
                            if (state < 6) {
                                iEvent.getWorld().setBlockState(iEvent.getPos(), stateWithProperty(iEvent.getWorld(), iEvent.getPos()), 3);
                            } else {
                                iEvent.getWorld().setBlockToAir(iEvent.getPos());
                            }
                        }
                    }
                    if (iEvent.getAction() == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
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

        private IBlockState stateWithProperty(World world, BlockPos pos) {
            return world.getBlockState(pos).withProperty(BlockCake.BITES, state(world, pos) + 1);
        }

        private int hungerAmount() {
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
            if (iEvent.getAction() == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && iEvent.getEntityPlayer().inventory.getCurrentItem() != null) {
                ItemStack equipped = iEvent.getEntityPlayer().inventory.getCurrentItem();
                boolean nullCheck = equipped.getItem() != null;
                int meta = iEvent.getWorld().getBlockState(iEvent.getPos()).getBlock().getMetaFromState(iEvent.getWorld().getBlockState(iEvent.getPos()));
                Block getBlock = iEvent.getWorld().getBlockState(iEvent.getPos()).getBlock();

                if (getBlock == Blocks.cauldron) {
                    if (nullCheck && equipped.getItem() == ModItems.storageJar || nullCheck && equipped.getItem() == Items.bucket || nullCheck && equipped.getItem() == Items.milk_bucket) {
                        iEvent.getWorld().setBlockState(iEvent.getPos(), ModBlocks.cauldron.getDefaultState());
                        changeWater(iEvent.getWorld(), iEvent.getPos(), iEvent.getWorld().getBlockState(iEvent.getPos()), meta);
                    }
                }
            }
        }

        private void changeWater(World world, BlockPos pos, IBlockState state, int side) {
            world.setBlockState(pos, state.withProperty(BlockModCauldron.LEVEL, MathHelper.clamp_int(side, 0, 3)), 2);
            world.updateComparatorOutputLevel(pos, ModBlocks.cauldron);
        }

        @SubscribeEvent
        public void RemoveDyeEvent(PlayerInteractEvent iEvent) {
            if (iEvent.getAction() == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && iEvent.getEntityPlayer().inventory.getCurrentItem() != null && iEvent.getEntityPlayer().inventory.getCurrentItem().getItem() instanceof ItemFarmerArmor && iEvent.getWorld().getBlockState(iEvent.getPos()).getBlock() == Blocks.cauldron) {
                int i = iEvent.getWorld().getBlockState(iEvent.getPos()).getValue(BlockCauldron.LEVEL);
                ItemFarmerArmor farmerArmor = (ItemFarmerArmor) iEvent.getEntityPlayer().inventory.getCurrentItem().getItem();
                if (farmerArmor.getArmorMaterial() == ItemFarmerArmor.farmerArmorMaterial && farmerArmor.hasColor(iEvent.getEntityPlayer().inventory.getCurrentItem()) && i > 0) {
                    farmerArmor.removeColor(iEvent.getEntityPlayer().inventory.getCurrentItem());
                    iEvent.getEntityPlayer().addStat(StatList.armorCleaned);
                    this.setWaterLevel(iEvent.getWorld(), iEvent.getPos(), iEvent.getWorld().getBlockState(iEvent.getPos()), i - 1);
                }
            }
        }

        private void setWaterLevel(World worldIn, BlockPos pos, IBlockState state, int level) {
            worldIn.setBlockState(pos, state.withProperty(BlockCauldron.LEVEL, MathHelper.clamp_int(level, 0, 3)), 2);
            worldIn.updateComparatorOutputLevel(pos, Blocks.cauldron);
        }
    }

    public static class DebugItemEvent {
        @SubscribeEvent
        public void DebugItem(PlayerInteractEvent iEvent) {
            EntityPlayer player = iEvent.getEntityPlayer();
            ItemStack stack = player.inventory.getCurrentItem();
            boolean b = player.onGround && player.isSneaking();
            if (iEvent.getAction() == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
                if (stack != null && player.inventory.getCurrentItem().getItem() == ModItems.debugItem && player.inventory.getCurrentItem().getItemDamage() == 0) {
                    if (!iEvent.getWorld().isRemote) {
                        player.addChatComponentMessage(new TextComponentString(iEvent.getWorld().getBlockState(iEvent.getPos()).getBlock().getLocalizedName() + " | " + "State: " + iEvent.getWorld().getBlockState(iEvent.getPos())));
                    }
                    iEvent.setCanceled(true);
                }
            }
            if (iEvent.getAction() == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && stack != null && player.inventory.getCurrentItem().getItem() == ModItems.debugItem && player.inventory.getCurrentItem().getItemDamage() == 0) {
                BlockStateContainer state = iEvent.getWorld().getBlockState(iEvent.getPos()).getBlock().getBlockState();
                if (!b) {
                    int l = iEvent.getWorld().getBlockState(iEvent.getPos()).getBlock().getMetaFromState(iEvent.getWorld().getBlockState(iEvent.getPos())) + 1;
                    if (l >= state.getValidStates().size()) {
                        iEvent.getWorld().setBlockState(iEvent.getPos(), iEvent.getWorld().getBlockState(iEvent.getPos()).getBlock().getDefaultState(), 2);
                    } else
                        iEvent.getWorld().setBlockState(iEvent.getPos(), iEvent.getWorld().getBlockState(iEvent.getPos()).getBlock().getStateFromMeta(l), 2);
                }
                if (b) {
                    int l = iEvent.getWorld().getBlockState(iEvent.getPos()).getBlock().getMetaFromState(iEvent.getWorld().getBlockState(iEvent.getPos())) - 1;
                    if (l < 0) {
                        iEvent.getWorld().setBlockState(iEvent.getPos(), iEvent.getWorld().getBlockState(iEvent.getPos()).getBlock().getDefaultState(), 2);
                    } else
                        iEvent.getWorld().setBlockState(iEvent.getPos(), iEvent.getWorld().getBlockState(iEvent.getPos()).getBlock().getStateFromMeta(l), 2);
                }
            }
        }
    }


    public static class StorageJarMilkFill { //TODO Fix that it gives you double.
        @SubscribeEvent
        public void StorageJarMilkFillEvent(EntityInteractEvent iEvent) {
            ItemStack stack = iEvent.getEntityPlayer().inventory.getCurrentItem();
            if (iEvent.getTarget() instanceof EntityCow & !iEvent.getEntityLiving().isChild()) {
                if (stack != null && stack.getItem() == ModItems.storageJar && stack.getItemDamage() == StorageJarType.EMPTY.getMetaData() && !iEvent.getEntityPlayer().capabilities.isCreativeMode) {
                    ItemStack milkJarStack = new ItemStack(ModItems.storageJar, 1, StorageJarType.MILK.getMetaData());

                    iEvent.getEntityPlayer().playSound(SoundEvents.entity_cow_milk, 1.0F, 1.0F);
                    if (--stack.stackSize == 0) {
                        iEvent.getEntityPlayer().setHeldItem(EnumHand.MAIN_HAND, milkJarStack);
                    } else if (!iEvent.getEntityPlayer().inventory.addItemStackToInventory(milkJarStack)) {
                        iEvent.getEntityPlayer().dropPlayerItemWithRandomChoice(milkJarStack, false);
                    }
                }
            }
        }
    }

    public static class CaneKnife {
        @SubscribeEvent
        public void CaneKnifeOnSugarCane(BlockEvent.BreakEvent breakEvent) {
            if (breakEvent.getPlayer().inventory.getCurrentItem() != null && breakEvent.getPlayer().inventory.getCurrentItem().getItem() instanceof ItemCaneKnife) {
                World world = breakEvent.getWorld();
                BlockPos pos = breakEvent.getPos();
                Block block = world.getBlockState(pos).getBlock();
                if (block == Blocks.reeds) {
                    //4 Tall
                    if (world.getBlockState(pos.up()).getBlock() != Blocks.reeds) {
                        if (world.getBlockState(pos.down(4)) != Blocks.reeds && world.getBlockState(pos.down(3)).getBlock() == Blocks.reeds) {
                            block.dropBlockAsItem(world, pos, breakEvent.getState(), 0);
                            world.setBlockToAir(pos.down(2));
                        }
                        //3 Tall
                        if (world.getBlockState(pos.down(3)) != Blocks.reeds && world.getBlockState(pos.down(2)).getBlock() == Blocks.reeds) {
                            block.dropBlockAsItem(world, pos, breakEvent.getState(), 0);
                            world.setBlockToAir(pos.down());
                        }
                    }
                    //4 Tall, 3th block
                    if (world.getBlockState(pos.down()).getBlock() == Blocks.reeds && world.getBlockState(pos.up()).getBlock() == Blocks.reeds && world.getBlockState(pos.up(2)).getBlock() != Blocks.reeds) {
                        if (world.getBlockState(pos.down(2)).getBlock() == Blocks.reeds) {
                            block.dropBlockAsItem(world, pos, breakEvent.getState(), 0);
                            world.setBlockToAir(pos.down());
                        }
                    }
                    //Harvesting bottom block
                    if (world.getBlockState(pos.down()).getBlock() != Blocks.reeds) {
                        breakEvent.setCanceled(true);
                        if (world.getBlockState(pos.up()).getBlock() == Blocks.reeds) {
                            block.dropBlockAsItem(world, pos, breakEvent.getState(), 0);
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
            if (iEvent.getAction() == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
                World world = iEvent.getWorld();
                BlockPos pos = iEvent.getPos();
                IBlockState state = iEvent.getWorld().getBlockState(pos);
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