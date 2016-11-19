package com.girafi.culinarycultivation.item.equipment.tool;

import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.network.NetworkHandler;
import com.girafi.culinarycultivation.network.packet.PacketChangeMode;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class ItemDebugItem extends Item {
    private boolean alwaysEdible;

    public ItemDebugItem() {
        super();
        this.maxStackSize = 1;
        this.setAlwaysEdible();
    }

    @Override
    @Nonnull
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "_" + getModeName(stack.getItemDamage());
    }

    public static String getModeName(int metadata) {
        switch (metadata) {
            case 1:
                return "hunger";
            case 2:
                return "hunger_plus";
            case 3:
                return "fertilizer";
            case 4:
                return "hoe";
            default:
                return "debug";
        }
    }

    @Override
    @Nonnull
    public EnumAction getItemUseAction(ItemStack stack) {
        if (stack.getItemDamage() == 1 || stack.getItemDamage() == 2) {
            return EnumAction.EAT;
        } else {
            return EnumAction.NONE;
        }
    }

    @Override
    @Nonnull
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, World world, EntityLivingBase entityLiving) {
        EntityPlayer player = (EntityPlayer) entityLiving;
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
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItemDamage() == 1) {
            if (player.canEat(alwaysEdible)) {
                player.setActiveHand(hand);
            }
        }
        if (stack.getItemDamage() == 2) {
            if (player.canEat(alwaysEdible)) {
                player.setActiveHand(hand);
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 25;
    }

    private ItemDebugItem setAlwaysEdible() {
        this.alwaysEdible = true;
        return this;
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItemDamage() == 3) {
            if (player.isSneaking() && applyBonemeal(stack, world, pos, player) && world.getBlockState(pos).getBlock() instanceof BlockCrops) {
                if (!world.isRemote) {
                    world.playEvent(2005, pos, 0);
                    for (int x = -1; x <= 1; x++) {
                        for (int z = -1; z <= 1; z++) {
                            if (applyBonemeal(stack, world, pos.add(x, 0, z), player)) {
                                world.playEvent(2005, pos.add(x, 0, z), 0);
                            }
                        }
                    }
                }
                return EnumActionResult.SUCCESS;
            } else if (applyBonemeal(stack, world, pos, player)) {
                if (!world.isRemote) {
                    world.playEvent(2005, pos, 0);
                }
                return EnumActionResult.SUCCESS;
            }
        }
        if (stack.getItemDamage() == 4)
            if (!player.canPlayerEdit(pos.offset(side), side, stack)) {
                return EnumActionResult.FAIL;
            } else {
                int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(stack, player, world, pos);
                if (hook != 0) return hook > 0 ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;

                IBlockState state = world.getBlockState(pos);
                Block block = state.getBlock();

                if (side != EnumFacing.DOWN && world.isAirBlock(pos.offset(EnumFacing.UP))) {
                    if (block == Blocks.GRASS) {
                        this.useDebugItemHoe(stack, player, world, pos, Blocks.FARMLAND.getDefaultState());
                        return EnumActionResult.SUCCESS;
                    }

                    if (block == Blocks.DIRT) {
                        switch (state.getValue(BlockDirt.VARIANT)) {
                            case DIRT:
                                this.useDebugItemHoe(stack, player, world, pos, Blocks.FARMLAND.getDefaultState());
                                return EnumActionResult.SUCCESS;
                            case COARSE_DIRT:
                                this.useDebugItemHoe(stack, player, world, pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                                return EnumActionResult.SUCCESS;
                            case PODZOL:
                                break;
                        }
                    }
                }
                return EnumActionResult.PASS;
            }
        return EnumActionResult.PASS;
    }

    private static boolean applyBonemeal(ItemStack stack, World world, BlockPos target, EntityPlayer player) {
        IBlockState state = world.getBlockState(target);
        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, world, target, state, stack);
        if (hook != 0) return hook > 0;
        if (state.getBlock() instanceof IGrowable) {
            IGrowable igrowable = (IGrowable) state.getBlock();
            if (igrowable.canGrow(world, target, state, world.isRemote)) {
                if (!world.isRemote) {
                    if (igrowable.canUseBonemeal(world, world.rand, target, state)) {
                        igrowable.grow(world, world.rand, target, state);
                    }
                }
                return true;
            }
        }
        return false;
    }

    private void useDebugItemHoe(ItemStack stack, EntityPlayer player, World world, BlockPos pos, IBlockState newState) {
        this.useHoe(stack, player, world, pos, Blocks.WATER.getBlockState().getBaseState());
        for (int x = -4; x <= 4; x++) {
            for (int z = -4; z <= 4; z++) {
                this.useHoe(stack, player, world, pos.add(x, 0, z), newState);
            }
        }
    }

    private void useHoe(ItemStack stack, EntityPlayer player, World world, BlockPos pos, IBlockState newState) {
        if (world.getBlockState(pos).getBlock() instanceof BlockDirt || world.getBlockState(pos).getBlock() instanceof BlockGrass) {
            world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

            if (!world.isRemote) {
                world.setBlockState(pos, newState);
                stack.damageItem(1, player);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onMouseEvent(MouseEvent event) {
        if (event.getButton() < 0) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            if (player.isSneaking()) {
                ItemStack stack = player.getHeldItemMainhand();
                if (stack.getItem() == ModItems.DEBUG_ITEM) {
                    if (event.getDwheel() != 0) {
                        NetworkHandler.WRAPPER.sendToServer(new PacketChangeMode(4, stack, player.inventory.currentItem, event.getDwheel() < 0));
                    }
                    event.setCanceled(true);
                }
            }
        }
    }
}