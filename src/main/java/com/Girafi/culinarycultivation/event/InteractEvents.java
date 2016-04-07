package com.Girafi.culinarycultivation.event;

import com.Girafi.culinarycultivation.block.BlockCrop;
import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemStorageJar.StorageJarType;
import com.Girafi.culinarycultivation.item.equipment.armor.farmer.ItemFarmerArmor;
import com.Girafi.culinarycultivation.item.equipment.tool.ItemCaneKnife;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class InteractEvents {

    public static class CakeKnifeInteractionEvent {
        @SubscribeEvent
        public void cakeKnifeInteractionEvent(PlayerInteractEvent iEvent) { //TODO Test in offhand
            World world = iEvent.getWorld();
            IBlockState state = world.getBlockState(iEvent.getPos());
            EntityPlayer player = iEvent.getEntityPlayer();
            int x = (int) player.posX;
            int y = (int) player.posY;
            int z = (int) player.posZ;
            ItemStack getItem = state.getBlock().getItem(world, iEvent.getPos(), state);

            if (player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() == ModItems.cakeKnife && state.getBlock() == baseBlock()) {
                int bites = state.getValue(BlockCake.BITES);

                if (iEvent instanceof PlayerInteractEvent.LeftClickBlock) {
                    world.setBlockToAir(iEvent.getPos());
                    if (!world.isRemote) {
                        if (bites == 0) {
                            world.spawnEntityInWorld(new EntityItem(world, x, y, z, getItem));
                        }
                        world.spawnEntityInWorld(new EntityItem(world, x, y, z, new ItemStack(sliceItem(), 7 - bites)));
                    }
                }

                if (iEvent instanceof PlayerInteractEvent.RightClickBlock && !player.isSneaking() && state.getBlock() == baseBlock()) {
                    if (player.getFoodStats().needFood()) {
                        player.getFoodStats().addStats(-hungerAmount(), -saturationAmount());

                        if (!world.isRemote) {
                            world.spawnEntityInWorld(new EntityItem(world, x, y, z, new ItemStack(sliceItem())));
                        }
                    } else if (!world.isRemote) {
                        world.spawnEntityInWorld(new EntityItem(world, x, y, z, new ItemStack(sliceItem())));

                        if (bites < 6) {
                            world.setBlockState(iEvent.getPos(), state.withProperty(BlockCake.BITES, state.getValue(BlockCake.BITES) + 1), 3);
                        } else {
                            world.setBlockToAir(iEvent.getPos());
                        }
                    }
                }
            }
            if (player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem() == ModItems.cakeKnife && state.getBlock() != baseBlock() && state.getBlock() instanceof BlockCake) {
                if (iEvent instanceof PlayerInteractEvent.LeftClickBlock) {
                    world.setBlockToAir(iEvent.getPos());
                    if (!world.isRemote) {
                        if (state.getValue(BlockCake.BITES) == 0) {
                            world.spawnEntityInWorld(new EntityItem(world, x, y, z, getItem));
                        }
                    }
                }
            }
        }

        public Item sliceItem() {
            return ModItems.pieceOfCake;
        }

        public Block baseBlock() {
            return Blocks.cake;
        }

        private int hungerAmount() {
            return 2;
        }

        public float saturationAmount() {
            return 0.1F;
        }
    }

    public static class CakeInteractionEvent extends CakeKnifeInteractionEvent {
    }

    public static class CheeseInteractionEvent extends CakeKnifeInteractionEvent {
        @Override
        public Item sliceItem() {
            return ModItems.cheeseSlice;
        }

        @Override
        public Block baseBlock() {
            return ModBlocks.cheese;
        }

        @Override
        public float saturationAmount() {
            return 0.4F;
        }
    }

    public static class CauldronTransformation {
        @SubscribeEvent
        public void cauldronTransformationEvent(PlayerInteractEvent.RightClickBlock iEvent) {
            ItemStack heldItem = iEvent.getEntityPlayer().getHeldItem(iEvent.getHand());
            if (heldItem != null) {
                boolean nullCheck = heldItem.getItem() != null;
                Block block = iEvent.getWorld().getBlockState(iEvent.getPos()).getBlock();

                if (block == Blocks.cauldron) {
                    if (nullCheck && heldItem.getItem() == ModItems.storageJar || nullCheck && heldItem.getItem() == Items.milk_bucket) {
                        iEvent.getWorld().setBlockState(iEvent.getPos(), ModBlocks.cauldron.getDefaultState());
                    }
                }
            }
        }

        @SubscribeEvent
        public void removeDyeEvent(PlayerInteractEvent.RightClickBlock iEvent) {
            ItemStack heldItem = iEvent.getEntityPlayer().getHeldItem(iEvent.getHand());
            if (heldItem != null && heldItem.getItem() instanceof ItemFarmerArmor && iEvent.getWorld().getBlockState(iEvent.getPos()).getBlock() == Blocks.cauldron) {
                int i = iEvent.getWorld().getBlockState(iEvent.getPos()).getValue(BlockCauldron.LEVEL);
                ItemFarmerArmor farmerArmor = (ItemFarmerArmor) heldItem.getItem();
                if (farmerArmor.getArmorMaterial() == ItemFarmerArmor.farmerArmorMaterial && farmerArmor.hasColor(heldItem) && i > 0 && !iEvent.getWorld().isRemote) {
                    farmerArmor.removeColor(heldItem);
                    this.setWaterLevel(iEvent.getWorld(), iEvent.getPos(), iEvent.getWorld().getBlockState(iEvent.getPos()), i - 1);
                    iEvent.getEntityPlayer().addStat(StatList.armorCleaned);
                }
            }
        }

        private void setWaterLevel(World world, BlockPos pos, IBlockState state, int level) {
            world.setBlockState(pos, state.withProperty(BlockCauldron.LEVEL, MathHelper.clamp_int(level, 0, 3)), 2);
            world.updateComparatorOutputLevel(pos, Blocks.cauldron);
        }
    }

    public static class DebugItemEvent {
        @SubscribeEvent
        public void debugItemEvent(PlayerInteractEvent iEvent) {
            IBlockState state = iEvent.getWorld().getBlockState(iEvent.getPos());
            Block block = state.getBlock();
            ItemStack heldItem = iEvent.getEntityPlayer().getHeldItem(iEvent.getHand());
            boolean isSneaking = iEvent.getEntityPlayer().onGround && iEvent.getEntityPlayer().isSneaking();
            if (iEvent instanceof PlayerInteractEvent.LeftClickBlock && heldItem != null && heldItem.getItem() == ModItems.debugItem && heldItem.getItemDamage() == 0) {
                if (!iEvent.getWorld().isRemote) {
                    iEvent.getEntityPlayer().addChatComponentMessage(new TextComponentString(block.getLocalizedName() + " | " + "Meta: " + block.getMetaFromState(state) + " | " + "State: " + state));
                }
                iEvent.setCanceled(true);
            }
            if (iEvent instanceof PlayerInteractEvent.RightClickBlock && heldItem != null && heldItem.getItem() == ModItems.debugItem && heldItem.getItemDamage() == 0) {
                if (!isSneaking) {
                    int l = block.getMetaFromState(state) + 1;
                    if (l >= block.getBlockState().getValidStates().size()) {
                        iEvent.getWorld().setBlockState(iEvent.getPos(), block.getDefaultState(), 2);
                    } else
                        iEvent.getWorld().setBlockState(iEvent.getPos(), block.getStateFromMeta(l), 2);
                }
                if (isSneaking) {
                    int l = block.getMetaFromState(state) - 1;
                    if (l < 0) {
                        iEvent.getWorld().setBlockState(iEvent.getPos(), block.getDefaultState(), 2);
                    } else {
                        iEvent.getWorld().setBlockState(iEvent.getPos(), block.getStateFromMeta(l), 2);
                    }
                }
            }
        }
    }


    public static class StorageJarMilkFill {
        @SubscribeEvent
        public void storageJarMilkFillEvent(PlayerInteractEvent.EntityInteract iEvent) {
            ItemStack heldItem = iEvent.getEntityPlayer().getHeldItem(iEvent.getHand());
            if (iEvent.getTarget() instanceof EntityCow & !iEvent.getEntityLiving().isChild()) {
                if (heldItem != null && heldItem.getItem() == ModItems.storageJar && heldItem.getItemDamage() == StorageJarType.EMPTY.getMetaData() && !iEvent.getEntityPlayer().capabilities.isCreativeMode) {
                    ItemStack milkJarStack = new ItemStack(ModItems.storageJar, 1, StorageJarType.MILK.getMetaData());

                    iEvent.getEntityPlayer().playSound(SoundEvents.entity_cow_milk, 1.0F, 1.0F);

                    if (--heldItem.stackSize == 0) {
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
        public void caneKnifeOnSugarCane(BlockEvent.BreakEvent breakEvent) {
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
        public void vanillaRightClickCropsHarvesting(PlayerInteractEvent.RightClickBlock iEvent) {
            World world = iEvent.getWorld();
            BlockPos pos = iEvent.getPos();
            IBlockState state = iEvent.getWorld().getBlockState(pos);
            Block block = world.getBlockState(pos).getBlock();

            if (block instanceof BlockCrops && !(block instanceof BlockCrop) && !(block instanceof BlockBeetroot)) {
                int age = state.getValue(BlockCrops.AGE);
                if (age >= 7) {
                    block.dropBlockAsItem(world, pos, state, 0);
                    world.setBlockState(pos, state.withProperty(BlockCrop.AGE, 0), 2);
                }
            } else if (block instanceof BlockNetherWart || block instanceof BlockBeetroot) {
                int age3 = state.getValue(BlockNetherWart.AGE);
                if (age3 >= 3) {
                    block.dropBlockAsItem(world, pos, state, 0);
                    world.setBlockState(pos, state.withProperty(BlockNetherWart.AGE, 0), 2);
                }
            }
        }
    }
}