package com.Girafi.culinarycultivation.item.equipment.tool;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.network.NetworkHandler;
import com.Girafi.culinarycultivation.network.packet.PacketDebugItemMode;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDebugItem extends Item {
    private boolean alwaysEdible;

    public ItemDebugItem() {
        super();
        maxStackSize = 1;
        setAlwaysEdible();
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "." + getModeName(stack.getItemDamage());
    }

    public static String getModeName(int metadata) {
        switch (metadata) {
            case 1:
                return "hunger";
            case 2:
                return "hungerPlus";
            case 3:
                return "fertilizer";
            case 4:
                return "hoe";
            default:
                return "debug";
        }
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        if (stack.getItemDamage() == 1) {
            return EnumAction.EAT;
        }
        if (stack.getItemDamage() == 2) {
            return EnumAction.EAT;
        } else {
            return null;
        }
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        if (stack.getItemDamage() == 1) {
            player.getFoodStats().addExhaustion(40F);
            if (player.getFoodStats().getFoodLevel() >= 0) {
                player.getFoodStats().addStats(-20, 0F);
            }
        }
        if (stack.getItemDamage() == 2) {
            player.getFoodStats().addStats(10000, 0F);
            player.heal(20);
            player.extinguish();
        }
        return stack;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack.getItemDamage() == 1) {
            if (player.canEat(alwaysEdible)) {
                player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
            }
        }
        if (stack.getItemDamage() == 2) {
            if (player.canEat(alwaysEdible)) {
                player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
            }
        }
        return stack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 25;
    }

    public ItemDebugItem setAlwaysEdible() {
        this.alwaysEdible = true;
        return this;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (stack.getItemDamage() == 3) {
            if (player.isSneaking() && applyBonemeal(stack, world, pos, player) && world.getBlockState(pos).getBlock() instanceof BlockCrops) {
                int x = pos.getX();
                int y = pos.getY();
                int z = pos.getZ();
                if (!world.isRemote) {
                    world.playAuxSFX(2005, pos, 0);
                    if (applyBonemeal(stack, world, new BlockPos(x + 1, y, z), player)) {
                        world.playAuxSFX(2005, new BlockPos(x + 1, y, z), 0);
                    }
                    if (applyBonemeal(stack, world, new BlockPos(x - 1, y, z), player)) {
                        world.playAuxSFX(2005, new BlockPos(x - 1, y, z), 0);
                    }
                    if (applyBonemeal(stack, world, new BlockPos(x + 1, y, z + 1), player)) {
                        world.playAuxSFX(2005, new BlockPos(x + 1, y, z + 1), 0);
                    }
                    if (applyBonemeal(stack, world, new BlockPos(x + 1, y - 1, z), player)) {
                        world.playAuxSFX(2005, new BlockPos(x + 1, y, z - 1), 0);
                    }
                    if (applyBonemeal(stack, world, new BlockPos(x - 1, y, z + 1), player)) {
                        world.playAuxSFX(2005, new BlockPos(x - 1, y, z + 1), 0);
                    }
                    if (applyBonemeal(stack, world, new BlockPos(x, y, z + 1), player)) {
                        world.playAuxSFX(2005, new BlockPos(x, y, z + 1), 0);
                    }
                    if (applyBonemeal(stack, world, new BlockPos(x, y, z - 1), player)) {
                        world.playAuxSFX(2005, new BlockPos(x, y, z - 1), 0);
                    }
                    if (applyBonemeal(stack, world, new BlockPos(x - 1, y, z - 1), player)) {
                        world.playAuxSFX(2005, new BlockPos(x - 1, y, z - 1), 0);
                    }
                    if (applyBonemeal(stack, world, new BlockPos(x + 1, y, z - 1), player)) {
                        world.playAuxSFX(2005, new BlockPos(x + 1, y, z - 1), 0);
                    }
                }
                return true;
            } else if (applyBonemeal(stack, world, pos, player)) {
                if (!world.isRemote) {
                    world.playAuxSFX(2005, pos, 0);
                }
                return true;
            }
        }
        if (stack.getItemDamage() == 4)
            if (!player.canPlayerEdit(pos.offset(side), side, stack)) {
                return false;
            } else {
                int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(stack, player, world, pos);
                if (hook != 0) return hook > 0;

                IBlockState iblockstate = world.getBlockState(pos);
                Block block = iblockstate.getBlock();

                if (side != EnumFacing.DOWN && world.isAirBlock(pos.offset(EnumFacing.UP))) {
                    if (block == Blocks.grass) {
                        return callUseHoe(stack, player, world, pos, Blocks.farmland.getDefaultState());
                    }

                    if (block == Blocks.dirt) {
                        switch (SwitchDirtType.TYPE_LOOKUP[iblockstate.getValue(BlockDirt.VARIANT).ordinal()]) {
                            case 1:
                                return callUseHoe(stack, player, world, pos, Blocks.farmland.getDefaultState());
                            case 2:
                                return callUseHoe(stack, player, world, pos, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                        }
                    }
                }
                return false;
            }
        return false;
    }

    public static boolean applyBonemeal(ItemStack stack, World world, BlockPos target, EntityPlayer player) {
        IBlockState iblockstate = world.getBlockState(target);
        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, world, target, iblockstate, stack);
        if (hook != 0) return hook > 0;
        if (iblockstate.getBlock() instanceof IGrowable) {
            IGrowable igrowable = (IGrowable) iblockstate.getBlock();
            if (igrowable.canGrow(world, target, iblockstate, world.isRemote)) {
                if (!world.isRemote) {
                    if (igrowable.canUseBonemeal(world, world.rand, target, iblockstate)) {
                        igrowable.grow(world, world.rand, target, iblockstate);
                    }
                }
                return true;
            }
        }
        return false;
    }

    protected boolean callUseHoe(ItemStack stack, EntityPlayer player, World world, BlockPos pos, IBlockState state) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        return useHoe(stack, player, world, new BlockPos(pos), Blocks.water.getBlockState().getBaseState())
                //3 x 3
                && useHoe(stack, player, world, new BlockPos(x + 1, y, z + 1), state)
                && useHoe(stack, player, world, new BlockPos(x + 1, y, z + 1), state)
                && useHoe(stack, player, world, new BlockPos(x + 1, y, z - 1), state)
                && useHoe(stack, player, world, new BlockPos(x + 1, y, z), state)
                && useHoe(stack, player, world, new BlockPos(x - 1, y, z + 1), state)
                && useHoe(stack, player, world, new BlockPos(x - 1, y, z - 1), state)
                && useHoe(stack, player, world, new BlockPos(x - 1, y, z), state)
                && useHoe(stack, player, world, new BlockPos(x, y, z + 1), state)
                && useHoe(stack, player, world, new BlockPos(x, y, z - 1), state)
                //5 x 5
                && useHoe(stack, player, world, new BlockPos(x + 1, y, z + 2), state)
                && useHoe(stack, player, world, new BlockPos(x + 1, y, z - 2), state)
                && useHoe(stack, player, world, new BlockPos(x + 2, y, z + 1), state)
                && useHoe(stack, player, world, new BlockPos(x + 2, y, z + 2), state)
                && useHoe(stack, player, world, new BlockPos(x + 2, y, z - 1), state)
                && useHoe(stack, player, world, new BlockPos(x + 2, y, z - 2), state)
                && useHoe(stack, player, world, new BlockPos(x + 2, y, z), state)
                && useHoe(stack, player, world, new BlockPos(x - 1, y, z + 2), state)
                && useHoe(stack, player, world, new BlockPos(x - 1, y, z - 2), state)
                && useHoe(stack, player, world, new BlockPos(x - 2, y, z + 1), state)
                && useHoe(stack, player, world, new BlockPos(x - 2, y, z + 2), state)
                && useHoe(stack, player, world, new BlockPos(x - 2, y, z - 1), state)
                && useHoe(stack, player, world, new BlockPos(x - 2, y, z - 2), state)
                && useHoe(stack, player, world, new BlockPos(x - 2, y, z), state)
                && useHoe(stack, player, world, new BlockPos(x, y, z + 2), state)
                && useHoe(stack, player, world, new BlockPos(x, y, z - 2), state)
                //7 x 7
                && useHoe(stack, player, world, new BlockPos(x + 1, y, z + 3), state)
                && useHoe(stack, player, world, new BlockPos(x + 1, y, z - 3), state)
                && useHoe(stack, player, world, new BlockPos(x + 2, y, z + 3), state)
                && useHoe(stack, player, world, new BlockPos(x + 2, y, z - 3), state)
                && useHoe(stack, player, world, new BlockPos(x + 3, y, z + 1), state)
                && useHoe(stack, player, world, new BlockPos(x + 3, y, z + 2), state)
                && useHoe(stack, player, world, new BlockPos(x + 3, y, z + 3), state)
                && useHoe(stack, player, world, new BlockPos(x + 3, y, z - 1), state)
                && useHoe(stack, player, world, new BlockPos(x + 3, y, z - 2), state)
                && useHoe(stack, player, world, new BlockPos(x + 3, y, z - 3), state)
                && useHoe(stack, player, world, new BlockPos(x + 3, y, z), state)
                && useHoe(stack, player, world, new BlockPos(x + 3, y, z), state)
                && useHoe(stack, player, world, new BlockPos(x - 1, y, z + 3), state)
                && useHoe(stack, player, world, new BlockPos(x - 1, y, z - 3), state)
                && useHoe(stack, player, world, new BlockPos(x - 2, y, z + 3), state)
                && useHoe(stack, player, world, new BlockPos(x - 2, y, z - 3), state)
                && useHoe(stack, player, world, new BlockPos(x - 3, y, z + 1), state)
                && useHoe(stack, player, world, new BlockPos(x - 3, y, z + 2), state)
                && useHoe(stack, player, world, new BlockPos(x - 3, y, z + 3), state)
                && useHoe(stack, player, world, new BlockPos(x - 3, y, z - 1), state)
                && useHoe(stack, player, world, new BlockPos(x - 3, y, z - 2), state)
                && useHoe(stack, player, world, new BlockPos(x - 3, y, z - 3), state)
                && useHoe(stack, player, world, new BlockPos(x - 3, y, z), state)
                && useHoe(stack, player, world, new BlockPos(x, y, z + 3), state)
                && useHoe(stack, player, world, new BlockPos(x, y, z - 3), state)
                //9x9
                && useHoe(stack, player, world, new BlockPos(x + 1, y, z + 4), state)
                && useHoe(stack, player, world, new BlockPos(x + 1, y, z - 4), state)
                && useHoe(stack, player, world, new BlockPos(x + 2, y, z + 4), state)
                && useHoe(stack, player, world, new BlockPos(x + 2, y, z - 4), state)
                && useHoe(stack, player, world, new BlockPos(x + 3, y, z + 4), state)
                && useHoe(stack, player, world, new BlockPos(x + 3, y, z - 4), state)
                && useHoe(stack, player, world, new BlockPos(x + 4, y, z + 1), state)
                && useHoe(stack, player, world, new BlockPos(x + 4, y, z + 2), state)
                && useHoe(stack, player, world, new BlockPos(x + 4, y, z + 3), state)
                && useHoe(stack, player, world, new BlockPos(x + 4, y, z + 4), state)
                && useHoe(stack, player, world, new BlockPos(x + 4, y, z - 1), state)
                && useHoe(stack, player, world, new BlockPos(x + 4, y, z - 2), state)
                && useHoe(stack, player, world, new BlockPos(x + 4, y, z - 3), state)
                && useHoe(stack, player, world, new BlockPos(x + 4, y, z - 4), state)
                && useHoe(stack, player, world, new BlockPos(x + 4, y, z), state)
                && useHoe(stack, player, world, new BlockPos(x + 4, y, z), state)
                && useHoe(stack, player, world, new BlockPos(x - 1, y, z + 4), state)
                && useHoe(stack, player, world, new BlockPos(x - 1, y, z - 4), state)
                && useHoe(stack, player, world, new BlockPos(x - 2, y, z + 4), state)
                && useHoe(stack, player, world, new BlockPos(x - 2, y, z - 4), state)
                && useHoe(stack, player, world, new BlockPos(x - 3, y, z + 4), state)
                && useHoe(stack, player, world, new BlockPos(x - 3, y, z - 4), state)
                && useHoe(stack, player, world, new BlockPos(x - 4, y, z + 1), state)
                && useHoe(stack, player, world, new BlockPos(x - 4, y, z + 2), state)
                && useHoe(stack, player, world, new BlockPos(x - 4, y, z + 3), state)
                && useHoe(stack, player, world, new BlockPos(x - 4, y, z + 4), state)
                && useHoe(stack, player, world, new BlockPos(x - 4, y, z - 1), state)
                && useHoe(stack, player, world, new BlockPos(x - 4, y, z - 2), state)
                && useHoe(stack, player, world, new BlockPos(x - 4, y, z - 3), state)
                && useHoe(stack, player, world, new BlockPos(x - 4, y, z - 4), state)
                && useHoe(stack, player, world, new BlockPos(x - 4, y, z), state)
                && useHoe(stack, player, world, new BlockPos(x, y, z + 4), state)
                && useHoe(stack, player, world, new BlockPos(x, y, z - 4), state);
    }

    protected boolean useHoe(ItemStack stack, EntityPlayer player, World world, BlockPos target, IBlockState newState) {
        if (world.getBlockState(target).getBlock() instanceof BlockDirt || world.getBlockState(target).getBlock() instanceof BlockGrass) {
            world.playSoundEffect((double) ((float) target.getX() + 0.5F), (double) ((float) target.getY() + 0.5F), (double) ((float) target.getZ() + 0.5F), newState.getBlock().stepSound.getStepSound(), (newState.getBlock().stepSound.getVolume() + 1.0F) / 2.0F, newState.getBlock().stepSound.getFrequency() * 0.8F);

            if (world.isRemote) {
                return true;
            } else {
                world.setBlockState(target, newState);
                stack.damageItem(1, player);
                return true;
            }
        }
        return true;
    }

    static final class SwitchDirtType {
        static final int[] TYPE_LOOKUP = new int[BlockDirt.DirtType.values().length];

        static {
            try {
                TYPE_LOOKUP[BlockDirt.DirtType.DIRT.ordinal()] = 1;
            } catch (NoSuchFieldError ignored) {
            }
            try {
                TYPE_LOOKUP[BlockDirt.DirtType.COARSE_DIRT.ordinal()] = 2;
            } catch (NoSuchFieldError ignored) {
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onMouseEvent(net.minecraftforge.client.event.MouseEvent event) {
        if (event.button < 0) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if (player.isSneaking()) {
                ItemStack stack = player.getHeldItem();
                if (stack != null && stack.getItem() == ModItems.debugItem) {
                    if (event.dwheel != 0) {
                        NetworkHandler.instance().sendToServer(new PacketDebugItemMode(player.inventory.currentItem, event.dwheel < 0));
                    }
                    event.setCanceled(true);
                }
            }
        }
    }
}