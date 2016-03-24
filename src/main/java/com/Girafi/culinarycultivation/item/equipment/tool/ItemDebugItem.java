package com.Girafi.culinarycultivation.item.equipment.tool;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.network.NetworkHandler;
import com.Girafi.culinarycultivation.network.packet.PacketDebugItemMode;
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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDebugItem extends Item {
    private boolean alwaysEdible;

    public ItemDebugItem() {
        super();
        this.maxStackSize = 1;
        this.setAlwaysEdible();
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
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
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
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
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
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
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
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
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
                return EnumActionResult.SUCCESS;
            } else if (applyBonemeal(stack, world, pos, player)) {
                if (!world.isRemote) {
                    world.playAuxSFX(2005, pos, 0);
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
                    if (block == Blocks.grass) {
                        this.useDebugItemHoe(stack, player, world, pos, Blocks.farmland.getDefaultState());
                        return EnumActionResult.SUCCESS;
                    }

                    if (block == Blocks.dirt) {
                        switch (state.getValue(BlockDirt.VARIANT)) {
                            case DIRT:
                                this.useDebugItemHoe(stack, player, world, pos, Blocks.farmland.getDefaultState());
                                return EnumActionResult.SUCCESS;
                            case COARSE_DIRT:
                                this.useDebugItemHoe(stack, player, world, pos, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
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
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        this.useHoe(stack, player, world, new BlockPos(pos), Blocks.water.getBlockState().getBaseState());
        //3 x 3
        this.useHoe(stack, player, world, new BlockPos(x + 1, y, z + 1), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 1, y, z + 1), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 1, y, z - 1), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 1, y, z), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 1, y, z + 1), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 1, y, z - 1), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 1, y, z), newState);
        this.useHoe(stack, player, world, new BlockPos(x, y, z + 1), newState);
        this.useHoe(stack, player, world, new BlockPos(x, y, z - 1), newState);
        //5 x 5
        this.useHoe(stack, player, world, new BlockPos(x + 1, y, z + 2), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 1, y, z - 2), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 2, y, z + 1), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 2, y, z + 2), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 2, y, z - 1), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 2, y, z - 2), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 2, y, z), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 1, y, z + 2), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 1, y, z - 2), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 2, y, z + 1), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 2, y, z + 2), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 2, y, z - 1), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 2, y, z - 2), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 2, y, z), newState);
        this.useHoe(stack, player, world, new BlockPos(x, y, z + 2), newState);
        this.useHoe(stack, player, world, new BlockPos(x, y, z - 2), newState);
        //7 x 7
        this.useHoe(stack, player, world, new BlockPos(x + 1, y, z + 3), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 1, y, z - 3), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 2, y, z + 3), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 2, y, z - 3), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 3, y, z + 1), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 3, y, z + 2), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 3, y, z + 3), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 3, y, z - 1), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 3, y, z - 2), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 3, y, z - 3), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 3, y, z), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 3, y, z), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 1, y, z + 3), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 1, y, z - 3), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 2, y, z + 3), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 2, y, z - 3), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 3, y, z + 1), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 3, y, z + 2), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 3, y, z + 3), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 3, y, z - 1), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 3, y, z - 2), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 3, y, z - 3), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 3, y, z), newState);
        this.useHoe(stack, player, world, new BlockPos(x, y, z + 3), newState);
        this.useHoe(stack, player, world, new BlockPos(x, y, z - 3), newState);
        //9x9
        this.useHoe(stack, player, world, new BlockPos(x + 1, y, z + 4), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 1, y, z - 4), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 2, y, z + 4), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 2, y, z - 4), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 3, y, z + 4), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 3, y, z - 4), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 4, y, z + 1), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 4, y, z + 2), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 4, y, z + 3), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 4, y, z + 4), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 4, y, z - 1), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 4, y, z - 2), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 4, y, z - 3), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 4, y, z - 4), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 4, y, z), newState);
        this.useHoe(stack, player, world, new BlockPos(x + 4, y, z), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 1, y, z + 4), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 1, y, z - 4), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 2, y, z + 4), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 2, y, z - 4), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 3, y, z + 4), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 3, y, z - 4), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 4, y, z + 1), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 4, y, z + 2), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 4, y, z + 3), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 4, y, z + 4), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 4, y, z - 1), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 4, y, z - 2), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 4, y, z - 3), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 4, y, z - 4), newState);
        this.useHoe(stack, player, world, new BlockPos(x - 4, y, z), newState);
        this.useHoe(stack, player, world, new BlockPos(x, y, z + 4), newState);
        this.useHoe(stack, player, world, new BlockPos(x, y, z - 4), newState);
    }

    private void useHoe(ItemStack stack, EntityPlayer player, World world, BlockPos pos, IBlockState newState) {
        if (world.getBlockState(pos).getBlock() instanceof BlockDirt || world.getBlockState(pos).getBlock() instanceof BlockGrass) {
            world.playSound(player, pos, SoundEvents.item_hoe_till, SoundCategory.BLOCKS, 1.0F, 1.0F);

            if (!world.isRemote) {
                world.setBlockState(pos, newState);
                stack.damageItem(1, player);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onMouseEvent(net.minecraftforge.client.event.MouseEvent event) {
        if (event.getButton() < 0) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if (player.isSneaking()) {
                ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
                if (stack != null && stack.getItem() == ModItems.debugItem) {
                    if (event.getDwheel() != 0) {
                        NetworkHandler.instance().sendToServer(new PacketDebugItemMode(player.inventory.currentItem, event.getDwheel() < 0));
                    }
                    event.setCanceled(true);
                }
            }
        }
    }
}