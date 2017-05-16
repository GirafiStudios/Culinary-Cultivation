package com.girafi.culinarycultivation.event;

import com.girafi.culinarycultivation.api.annotations.RegisterEvent;
import com.girafi.culinarycultivation.block.BlockCrop;
import com.girafi.culinarycultivation.init.ModBlocks;
import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.item.equipment.tool.ItemCaneKnife;
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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class InteractEvents {

    @RegisterEvent
    public static class CakeKnifeInteractionEvent {
        @SubscribeEvent
        public void cakeKnifeInteractionEvent(PlayerInteractEvent iEvent) {
            World world = iEvent.getWorld();
            IBlockState state = world.getBlockState(iEvent.getPos());
            EntityPlayer player = iEvent.getEntityPlayer();
            ItemStack heldItem = player.getHeldItem(iEvent.getHand());
            double x = player.posX;
            double y = player.posY;
            double z = player.posZ;

            if (!heldItem.isEmpty() && heldItem.getItem() == ModItems.CAKE_KNIFE && state.getBlock() instanceof BlockCake) {
                int bites = state.getValue(BlockCake.BITES);

                if (iEvent instanceof PlayerInteractEvent.LeftClickBlock) {
                    if (bites == 0) {
                        world.setBlockToAir(iEvent.getPos());
                        if (!world.isRemote) {
                            world.spawnEntity(new EntityItem(world, x, y, z, state.getBlock().getPickBlock(state, null, world, iEvent.getPos(), player)));
                        }
                    } else if (bites > 0 && state.getBlock() == baseBlock()) {
                        world.setBlockToAir(iEvent.getPos());
                        if (!world.isRemote) {
                            world.spawnEntity(new EntityItem(world, x, y, z, new ItemStack(sliceItem(), 7 - bites)));
                        }
                    }
                }

                if (iEvent instanceof PlayerInteractEvent.RightClickBlock && !player.isSneaking() && state.getBlock() == baseBlock()) {
                    if (player.getFoodStats().needFood()) {
                        player.getFoodStats().addStats(-hungerAmount(), -saturationAmount());

                        if (!world.isRemote) {
                            world.spawnEntity(new EntityItem(world, x, y, z, new ItemStack(sliceItem())));
                        }
                    } else if (!world.isRemote) {
                        world.spawnEntity(new EntityItem(world, x, y, z, new ItemStack(sliceItem())));

                        if (bites < 6) {
                            world.setBlockState(iEvent.getPos(), state.withProperty(BlockCake.BITES, state.getValue(BlockCake.BITES) + 1), 3);
                        } else {
                            world.setBlockToAir(iEvent.getPos());
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

    @RegisterEvent
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

    //TODO Readd remove dye for cauldron

    @RegisterEvent
    public static class DebugItemEvent {
        @SubscribeEvent
        public void debugItemEvent(PlayerInteractEvent iEvent) {
            IBlockState state = iEvent.getWorld().getBlockState(iEvent.getPos());
            Block block = state.getBlock();
            ItemStack heldItem = iEvent.getEntityPlayer().getHeldItem(iEvent.getHand());
            boolean isSneaking = iEvent.getEntityPlayer().onGround && iEvent.getEntityPlayer().isSneaking();
            if (iEvent instanceof PlayerInteractEvent.LeftClickBlock && !heldItem.isEmpty() && heldItem.getItem() == ModItems.DEBUG_ITEM && heldItem.getItemDamage() == 0) {
                if (!iEvent.getWorld().isRemote) {
                    iEvent.getEntityPlayer().sendStatusMessage(new TextComponentString(block.getLocalizedName() + " | " + "Meta: " + block.getMetaFromState(state) + " | " + "State: " + state), true);
                }
                iEvent.setCanceled(true);
            }
            if (iEvent instanceof PlayerInteractEvent.RightClickBlock && !heldItem.isEmpty() && heldItem.getItem() == ModItems.DEBUG_ITEM && heldItem.getItemDamage() == 0) {
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

    @RegisterEvent
    public static class StorageJarMilkFill {
        /*@SubscribeEvent
        public void storageJarMilkFillEvent(PlayerInteractEvent.EntityInteract iEvent) {
            ItemStack heldItem = iEvent.getEntityPlayer().getHeldItem(iEvent.getHand());
            if (iEvent.getTarget() instanceof EntityCow & !iEvent.getEntityLiving().isChild()) {
                if (!heldItem.isEmpty() && heldItem.getItem() == ModItems.STORAGE_JAR && heldItem.getItemDamage() == StorageJarType.EMPTY.getMetaData() && !iEvent.getEntityPlayer().capabilities.isCreativeMode) {
                    ItemStack milkJarStack = new ItemStack(ModItems.STORAGE_JAR, 1, StorageJarType.MILK.getMetaData());

                    iEvent.getEntityPlayer().playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);

                    if (heldItem.isEmpty()) {
                        iEvent.getEntityPlayer().setHeldItem(EnumHand.MAIN_HAND, milkJarStack);
                    } else if (!iEvent.getEntityPlayer().inventory.addItemStackToInventory(milkJarStack)) {
                        iEvent.getEntityPlayer().dropItem(milkJarStack, false);
                    }
                }
            }
        }*/
    }

    @RegisterEvent
    public static class CaneKnife {
        @SubscribeEvent
        public void caneKnifeBreakEvent(BlockEvent.BreakEvent event) {
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

    @RegisterEvent
    public static class VanillaCrops implements RegisterEvent.IRegisterEvent {
        @Override
        public boolean isActive() {
            return true;
        }

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