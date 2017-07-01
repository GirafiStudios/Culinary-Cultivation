package com.girafi.culinarycultivation.event;

import com.girafi.culinarycultivation.api.annotations.IRegisterEvent;
import com.girafi.culinarycultivation.block.BlockCrop;
import com.girafi.culinarycultivation.init.ModBlocks;
import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.item.equipment.tool.ItemCaneKnife;
import com.girafi.culinarycultivation.util.ConfigurationHandler;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class InteractEvents {

    @EventBusSubscriber
    public static class CakeKnifeInteractionEvent {
        @SubscribeEvent
        public void cakeKnifeInteractionEvent(PlayerInteractEvent event) {
            World world = event.getWorld();
            IBlockState state = world.getBlockState(event.getPos());
            EntityPlayer player = event.getEntityPlayer();
            ItemStack heldItem = player.getHeldItem(event.getHand());
            double x = player.posX;
            double y = player.posY;
            double z = player.posZ;

            if (!heldItem.isEmpty() && heldItem.getItem() == ModItems.CAKE_KNIFE && state.getBlock() instanceof BlockCake) {
                int bites = state.getValue(BlockCake.BITES);

                if (event instanceof PlayerInteractEvent.LeftClickBlock) {
                    if (bites == 0) {
                        world.setBlockToAir(event.getPos());
                        if (!world.isRemote) {
                            world.spawnEntity(new EntityItem(world, x, y, z, state.getBlock().getPickBlock(state, null, world, event.getPos(), player)));
                        }
                    } else if (bites > 0 && state.getBlock() == baseBlock()) {
                        world.setBlockToAir(event.getPos());
                        if (!world.isRemote) {
                            world.spawnEntity(new EntityItem(world, x, y, z, new ItemStack(sliceItem(), 7 - bites)));
                        }
                    }
                }

                if (event instanceof PlayerInteractEvent.RightClickBlock && !player.isSneaking() && state.getBlock() == baseBlock()) {
                    if (player.getFoodStats().needFood()) {
                        player.getFoodStats().addStats(-hungerAmount(), -saturationAmount());

                        if (!world.isRemote) {
                            world.spawnEntity(new EntityItem(world, x, y, z, new ItemStack(sliceItem())));
                        }
                    } else if (!world.isRemote) {
                        world.spawnEntity(new EntityItem(world, x, y, z, new ItemStack(sliceItem())));

                        if (bites < 6) {
                            world.setBlockState(event.getPos(), state.withProperty(BlockCake.BITES, state.getValue(BlockCake.BITES) + 1), 3);
                        } else {
                            world.setBlockToAir(event.getPos());
                        }
                    }
                }
            }
        }

        public Item sliceItem() {
            return ModItems.CAKE_PIECE;
        }

        public Block baseBlock() {
            return Blocks.CAKE;
        }

        private int hungerAmount() {
            return 2;
        }

        public float saturationAmount() {
            return 0.1F;
        }
    }

    @EventBusSubscriber
    public static class CheeseInteractionEvent extends CakeKnifeInteractionEvent {
        @Override
        public Item sliceItem() {
            return ModItems.CHEESE_SLICE;
        }

        @Override
        public Block baseBlock() {
            return ModBlocks.CHEESE;
        }

        @Override
        public float saturationAmount() {
            return 0.4F;
        }
    }

    @EventBusSubscriber
    public static class DebugItemEvent {
        @SubscribeEvent
        public static void debugItemEvent(PlayerInteractEvent event) {
            IBlockState state = event.getWorld().getBlockState(event.getPos());
            Block block = state.getBlock();
            ItemStack heldItem = event.getEntityPlayer().getHeldItem(event.getHand());
            boolean isSneaking = event.getEntityPlayer().onGround && event.getEntityPlayer().isSneaking();
            if (event instanceof PlayerInteractEvent.LeftClickBlock && !heldItem.isEmpty() && heldItem.getItem() == ModItems.DEBUG_ITEM && heldItem.getItemDamage() == 0) {
                if (!event.getWorld().isRemote) {
                    event.getEntityPlayer().sendStatusMessage(new TextComponentString(block.getLocalizedName() + " | " + "Meta: " + block.getMetaFromState(state) + " | " + "State: " + state), true);
                }
                event.setCanceled(true);
            }
            if (event instanceof PlayerInteractEvent.RightClickBlock && !heldItem.isEmpty() && heldItem.getItem() == ModItems.DEBUG_ITEM && heldItem.getItemDamage() == 0) {
                if (!isSneaking) {
                    int l = block.getMetaFromState(state) + 1;
                    if (l >= block.getBlockState().getValidStates().size()) {
                        event.getWorld().setBlockState(event.getPos(), block.getDefaultState(), 2);
                    } else
                        event.getWorld().setBlockState(event.getPos(), block.getStateFromMeta(l), 2);
                }
                if (isSneaking) {
                    int l = block.getMetaFromState(state) - 1;
                    if (l < 0) {
                        event.getWorld().setBlockState(event.getPos(), block.getDefaultState(), 2);
                    } else {
                        event.getWorld().setBlockState(event.getPos(), block.getStateFromMeta(l), 2);
                    }
                }
            }
        }
    }

    @EventBusSubscriber
    public static class CaneKnife {
        @SubscribeEvent
        public static void caneKnifeBreakEvent(BlockEvent.BreakEvent event) {
            ItemStack heldStack = event.getPlayer().inventory.getCurrentItem();
            if (!heldStack.isEmpty() && heldStack.getItem() instanceof ItemCaneKnife) {
                World world = event.getWorld();
                BlockPos pos = event.getPos();
                Block block = world.getBlockState(pos).getBlock();

                if (block == Blocks.REEDS) {
                    //4 Tall
                    if (world.getBlockState(pos.up()).getBlock() != Blocks.REEDS) {
                        if (world.getBlockState(pos.down(4)) != Blocks.REEDS && world.getBlockState(pos.down(3)).getBlock() == Blocks.REEDS) {
                            block.dropBlockAsItem(world, pos, event.getState(), 0);
                            world.setBlockToAir(pos.down(2));
                        }
                        //3 Tall
                        if (world.getBlockState(pos.down(3)) != Blocks.REEDS && world.getBlockState(pos.down(2)).getBlock() == Blocks.REEDS) {
                            block.dropBlockAsItem(world, pos, event.getState(), 0);
                            world.setBlockToAir(pos.down());
                        }
                    }
                    //4 Tall, 3th block
                    if (world.getBlockState(pos.down()).getBlock() == Blocks.REEDS && world.getBlockState(pos.up()).getBlock() == Blocks.REEDS && world.getBlockState(pos.up(2)).getBlock() != Blocks.REEDS) {
                        if (world.getBlockState(pos.down(2)).getBlock() == Blocks.REEDS) {
                            block.dropBlockAsItem(world, pos, event.getState(), 0);
                            world.setBlockToAir(pos.down());
                        }
                    }
                    //Harvesting bottom block
                    if (world.getBlockState(pos.down()).getBlock() != Blocks.REEDS) {
                        event.setCanceled(true);
                        if (world.getBlockState(pos.up()).getBlock() == Blocks.REEDS) {
                            block.dropBlockAsItem(world, pos, event.getState(), 0);
                            world.setBlockToAir(pos.up());
                        }
                    }
                }
                if (block instanceof BlockDoublePlant) {
                    BlockDoublePlant.EnumPlantType type = world.getBlockState(pos).getValue(BlockDoublePlant.VARIANT);
                    if (type == BlockDoublePlant.EnumPlantType.GRASS) {
                        world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Blocks.DOUBLE_PLANT, 1, BlockDoublePlant.EnumPlantType.GRASS.getMeta())));
                        world.setBlockToAir(pos);
                    }
                    if (type == BlockDoublePlant.EnumPlantType.FERN) {
                        world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Blocks.DOUBLE_PLANT, 1, BlockDoublePlant.EnumPlantType.FERN.getMeta())));
                        world.setBlockToAir(pos);
                    }
                }
            }
        }
    }

    @EventBusSubscriber
    public static class VanillaCrops implements IRegisterEvent {
        @Override
        public boolean isActive() {
            return ConfigurationHandler.canRightClickHarvestVanillaCrops;
        }

        @SubscribeEvent
        public void vanillaRightClickCropsHarvesting(PlayerInteractEvent.RightClickBlock event) {
            World world = event.getWorld();
            BlockPos pos = event.getPos();
            IBlockState state = event.getWorld().getBlockState(pos);
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