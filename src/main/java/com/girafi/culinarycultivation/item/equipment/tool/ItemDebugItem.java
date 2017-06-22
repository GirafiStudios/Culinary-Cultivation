package com.girafi.culinarycultivation.item.equipment.tool;

import com.girafi.culinarycultivation.api.annotations.RegisterEvent;
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
import javax.annotation.Nullable;

@RegisterEvent
public class ItemDebugItem extends Item {
    private boolean alwaysEdible;

    public ItemDebugItem() {
        super();
        this.maxStackSize = 1;
        this.setAlwaysEdible();
    }

    @Override
    @Nonnull
    public String getUnlocalizedName(@Nonnull ItemStack stack) {
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
    public EnumAction getItemUseAction(@Nonnull ItemStack stack) {
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
            player.getFoodStats().addStats(10000, 20.0F);
            player.heal(20);
            player.extinguish();
        }
        return stack;
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItemDamage() == 1 || stack.getItemDamage() == 2) {
            if (player.canEat(alwaysEdible)) {
                player.setActiveHand(hand);
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public int getMaxItemUseDuration(@Nonnull ItemStack stack) {
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
            if (player.isSneaking() && applyBonemeal(player, world, pos, stack, hand) && world.getBlockState(pos).getBlock() instanceof BlockCrops) {
                if (!world.isRemote) {
                    world.playEvent(2005, pos, 0);
                    for (int x = -1; x <= 1; x++) {
                        for (int z = -1; z <= 1; z++) {
                            if (applyBonemeal(player, world, pos.add(x, 0, z), stack, hand)) {
                                world.playEvent(2005, pos.add(x, 0, z), 0);
                            }
                        }
                    }
                }
                return EnumActionResult.SUCCESS;
            } else if (applyBonemeal(player, world, pos, stack, hand)) {
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

    private static boolean applyBonemeal(@Nonnull EntityPlayer player, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull ItemStack stack, @Nullable EnumHand hand) {
        IBlockState state = world.getBlockState(pos);
        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, world, pos, state, stack, hand);
        if (hook != 0) return hook > 0;
        if (state.getBlock() instanceof IGrowable) {
            IGrowable igrowable = (IGrowable) state.getBlock();
            if (igrowable.canGrow(world, pos, state, world.isRemote)) {
                if (!world.isRemote) {
                    if (igrowable.canUseBonemeal(world, world.rand, pos, state)) {
                        igrowable.grow(world, world.rand, pos, state);
                    }
                }
                return true;
            }
        }
        return false;
    }

    private void useDebugItemHoe(@Nonnull ItemStack stack, EntityPlayer player, World world, BlockPos pos, IBlockState newState) {
        this.useHoe(stack, player, world, pos, Blocks.WATER.getBlockState().getBaseState());
        for (int x = -4; x <= 4; x++) {
            for (int z = -4; z <= 4; z++) {
                this.useHoe(stack, player, world, pos.add(x, 0, z), newState);
            }
        }
    }

    private void useHoe(@Nonnull ItemStack stack, EntityPlayer player, World world, BlockPos pos, IBlockState newState) {
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