package com.Girafi.culinarycultivation.item;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.network.NetworkHandler;
import com.Girafi.culinarycultivation.network.packet.PacketDebugItemMode;
import com.Girafi.culinarycultivation.reference.Paths;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDebugItem extends Item {
    private boolean alwaysEdible;

    public ItemDebugItem() {
        super();
        this.setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
        setUnlocalizedName(Paths.ModAssets + "debugItem");
        maxStackSize = 1;
        setAlwaysEdible();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining) {
        if (stack.getItemDamage() == 0)
            return new ModelResourceLocation(Paths.ModAssets + "debugDefault", "inventory");
        if (stack.getItemDamage() == 1)
            return new ModelResourceLocation(Paths.ModAssets + "debugHunger", "inventory");
        if (stack.getItemDamage() == 2)
            return new ModelResourceLocation(Paths.ModAssets + "debugHungerPlus", "inventory");
        if (stack.getItemDamage() == 3)
            return new ModelResourceLocation(Paths.ModAssets + "debugFertilizer", "inventory");
        if (stack.getItemDamage() == 4)
            return new ModelResourceLocation(Paths.ModAssets + "debugHoe", "inventory");
        else return new ModelResourceLocation(Paths.ModAssets + "debugDefault", "inventory");
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack)
                + (stack.getItemDamage() == 0 ? ".debug" : (stack.getItemDamage() == 1 ? ".hunger" : (stack.getItemDamage() == 2 ? ".hungerPlus" :
                (stack.getItemDamage() == 3 ? ".fertilizer" : (stack.getItemDamage() == 4 ? ".hoe" : "")))));
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
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        if (stack.getItemDamage() == 1) {
            playerIn.getFoodStats().addExhaustion(40F);
            if (playerIn.getFoodStats().getFoodLevel() >= 0) {
                playerIn.getFoodStats().addStats(-20, 0F);
            }
        }
        if (stack.getItemDamage() == 2) {
            playerIn.getFoodStats().addStats(10000, 0F);
            playerIn.heal(20);
            playerIn.extinguish();
        }
        return stack;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        if (stack.getItemDamage() == 0) {
            if (!worldIn.isRemote && !playerIn.isSneaking()) {
                //playerIn.addChatComponentMessage(new ChatComponentText("Switch mode by shift + scrolling")); /TODO Fix
            }
        }
        if (stack.getItemDamage() == 1) {
            if (playerIn.canEat(alwaysEdible)) {
                playerIn.setItemInUse(stack, this.getMaxItemUseDuration(stack));
            }
        }
        if (stack.getItemDamage() == 2) {
            if (playerIn.canEat(alwaysEdible)) {
                playerIn.setItemInUse(stack, this.getMaxItemUseDuration(stack));
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
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (stack.getItemDamage() == 3) {
            if (playerIn.isSneaking() && applyBonemeal(stack, worldIn, pos, playerIn) && worldIn.getBlockState(pos).getBlock() instanceof BlockCrops) {
                int x = pos.getX();
                int y = pos.getY();
                int z = pos.getZ();
                if (!worldIn.isRemote) {
                    worldIn.playAuxSFX(2005, pos, 0);
                    if (applyBonemeal(stack, worldIn, new BlockPos(x + 1, y, z), playerIn)) {
                        worldIn.playAuxSFX(2005, new BlockPos(x + 1, y, z), 0);
                    }
                    if (applyBonemeal(stack, worldIn, new BlockPos(x - 1, y, z), playerIn)) {
                        worldIn.playAuxSFX(2005, new BlockPos(x - 1, y, z), 0);
                    }
                    if (applyBonemeal(stack, worldIn, new BlockPos(x + 1, y, z + 1), playerIn)) {
                        worldIn.playAuxSFX(2005, new BlockPos(x + 1, y, z + 1), 0);
                    }
                    if (applyBonemeal(stack, worldIn, new BlockPos(x + 1, y - 1, z), playerIn)) {
                        worldIn.playAuxSFX(2005, new BlockPos(x + 1, y, z - 1), 0);
                    }
                    if (applyBonemeal(stack, worldIn, new BlockPos(x - 1, y, z + 1), playerIn)) {
                        worldIn.playAuxSFX(2005, new BlockPos(x - 1, y, z + 1), 0);
                    }
                    if (applyBonemeal(stack, worldIn, new BlockPos(x, y, z + 1), playerIn)) {
                        worldIn.playAuxSFX(2005, new BlockPos(x, y, z + 1), 0);
                    }
                    if (applyBonemeal(stack, worldIn, new BlockPos(x, y, z - 1), playerIn)) {
                        worldIn.playAuxSFX(2005, new BlockPos(x, y, z - 1), 0);
                    }
                    if (applyBonemeal(stack, worldIn, new BlockPos(x - 1, y, z - 1), playerIn)) {
                        worldIn.playAuxSFX(2005, new BlockPos(x - 1, y, z - 1), 0);
                    }
                    if (applyBonemeal(stack, worldIn, new BlockPos(x + 1, y, z - 1), playerIn)) {
                        worldIn.playAuxSFX(2005, new BlockPos(x + 1, y, z - 1), 0);
                    }
                }
                return true;
            } else if (applyBonemeal(stack, worldIn, pos, playerIn)) {
                if (!worldIn.isRemote) {
                    worldIn.playAuxSFX(2005, pos, 0);
                }
                return true;
            }
        }
        if (stack.getItemDamage() == 4)
            if (!playerIn.canPlayerEdit(pos.offset(side), side, stack)) {
                return false;
            } else {
                int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(stack, playerIn, worldIn, pos);
                if (hook != 0) return hook > 0;

                IBlockState iblockstate = worldIn.getBlockState(pos);
                Block block = iblockstate.getBlock();

                if (side != EnumFacing.DOWN && worldIn.isAirBlock(pos.offset(EnumFacing.UP))) {
                    if (block == Blocks.grass) {
                        return this.func_179232_a(stack, playerIn, worldIn, pos, Blocks.farmland.getDefaultState());
                    }

                    if (block == Blocks.dirt) {
                        switch (SwitchDirtType.TYPE_LOOKUP[((BlockDirt.DirtType) iblockstate.getValue(BlockDirt.VARIANT)).ordinal()]) {
                            case 1:
                                return func_179232_a(stack, playerIn, worldIn, pos, Blocks.farmland.getDefaultState());
                            case 2:
                                return this.func_179232_a(stack, playerIn, worldIn, pos, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                        }
                    }
                }
                return false;
            }
        return false;
    }

    public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos target, EntityPlayer player) {
        IBlockState iblockstate = worldIn.getBlockState(target);
        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, worldIn, target, iblockstate, stack);
        if (hook != 0) return hook > 0;
        if (iblockstate.getBlock() instanceof IGrowable) {
            IGrowable igrowable = (IGrowable) iblockstate.getBlock();
            if (igrowable.canGrow(worldIn, target, iblockstate, worldIn.isRemote)) {
                if (!worldIn.isRemote) {
                    if (igrowable.canUseBonemeal(worldIn, worldIn.rand, target, iblockstate)) {
                        igrowable.grow(worldIn, worldIn.rand, target, iblockstate);
                    }
                }
                return true;
            }
        }
        return false;
    }

    protected boolean func_179232_a(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, IBlockState state) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        worldIn.playSoundEffect((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), state.getBlock().stepSound.getStepSound(), (state.getBlock().stepSound.getVolume() + 1.0F) / 2.0F, state.getBlock().stepSound.getFrequency() * 0.8F);

        if (worldIn.isRemote) {
            return true;
        } else {
            worldIn.setBlockState(pos, Blocks.water.getBlockState().getBaseState());
            //3 x 3
            worldIn.setBlockState(new BlockPos(x + 1, y, z + 1), state);
            worldIn.setBlockState(new BlockPos(x + 1, y, z - 1), state);
            worldIn.setBlockState(new BlockPos(x + 1, y, z), state);
            worldIn.setBlockState(new BlockPos(x - 1, y, z + 1), state);
            worldIn.setBlockState(new BlockPos(x - 1, y, z - 1), state);
            worldIn.setBlockState(new BlockPos(x - 1, y, z), state);
            worldIn.setBlockState(new BlockPos(x, y, z + 1), state);
            worldIn.setBlockState(new BlockPos(x, y, z - 1), state);
            //5 x 5
            worldIn.setBlockState(new BlockPos(x + 1, y, z + 2), state);
            worldIn.setBlockState(new BlockPos(x + 1, y, z - 2), state);
            worldIn.setBlockState(new BlockPos(x + 2, y, z + 1), state);
            worldIn.setBlockState(new BlockPos(x + 2, y, z + 2), state);
            worldIn.setBlockState(new BlockPos(x + 2, y, z - 1), state);
            worldIn.setBlockState(new BlockPos(x + 2, y, z - 2), state);
            worldIn.setBlockState(new BlockPos(x + 2, y, z), state);
            worldIn.setBlockState(new BlockPos(x - 1, y, z + 2), state);
            worldIn.setBlockState(new BlockPos(x - 1, y, z - 2), state);
            worldIn.setBlockState(new BlockPos(x - 2, y, z + 1), state);
            worldIn.setBlockState(new BlockPos(x - 2, y, z + 2), state);
            worldIn.setBlockState(new BlockPos(x - 2, y, z - 1), state);
            worldIn.setBlockState(new BlockPos(x - 2, y, z - 2), state);
            worldIn.setBlockState(new BlockPos(x - 2, y, z), state);
            worldIn.setBlockState(new BlockPos(x, y, z + 2), state);
            worldIn.setBlockState(new BlockPos(x, y, z - 2), state);
            //7 x 7
            worldIn.setBlockState(new BlockPos(x + 1, y, z + 3), state);
            worldIn.setBlockState(new BlockPos(x + 1, y, z - 3), state);
            worldIn.setBlockState(new BlockPos(x + 2, y, z + 3), state);
            worldIn.setBlockState(new BlockPos(x + 2, y, z - 3), state);
            worldIn.setBlockState(new BlockPos(x + 3, y, z + 1), state);
            worldIn.setBlockState(new BlockPos(x + 3, y, z + 2), state);
            worldIn.setBlockState(new BlockPos(x + 3, y, z + 3), state);
            worldIn.setBlockState(new BlockPos(x + 3, y, z - 1), state);
            worldIn.setBlockState(new BlockPos(x + 3, y, z - 2), state);
            worldIn.setBlockState(new BlockPos(x + 3, y, z - 3), state);
            worldIn.setBlockState(new BlockPos(x + 3, y, z), state);
            worldIn.setBlockState(new BlockPos(x + 3, y, z), state);
            worldIn.setBlockState(new BlockPos(x - 1, y, z + 3), state);
            worldIn.setBlockState(new BlockPos(x - 1, y, z - 3), state);
            worldIn.setBlockState(new BlockPos(x - 2, y, z + 3), state);
            worldIn.setBlockState(new BlockPos(x - 2, y, z - 3), state);
            worldIn.setBlockState(new BlockPos(x - 3, y, z + 1), state);
            worldIn.setBlockState(new BlockPos(x - 3, y, z + 2), state);
            worldIn.setBlockState(new BlockPos(x - 3, y, z + 3), state);
            worldIn.setBlockState(new BlockPos(x - 3, y, z - 1), state);
            worldIn.setBlockState(new BlockPos(x - 3, y, z - 2), state);
            worldIn.setBlockState(new BlockPos(x - 3, y, z - 3), state);
            worldIn.setBlockState(new BlockPos(x - 3, y, z), state);
            worldIn.setBlockState(new BlockPos(x, y, z + 3), state);
            worldIn.setBlockState(new BlockPos(x, y, z - 3), state);
            //9x9
            worldIn.setBlockState(new BlockPos(x + 1, y, z + 4), state);
            worldIn.setBlockState(new BlockPos(x + 1, y, z - 4), state);
            worldIn.setBlockState(new BlockPos(x + 2, y, z + 4), state);
            worldIn.setBlockState(new BlockPos(x + 2, y, z - 4), state);
            worldIn.setBlockState(new BlockPos(x + 3, y, z + 4), state);
            worldIn.setBlockState(new BlockPos(x + 3, y, z - 4), state);
            worldIn.setBlockState(new BlockPos(x + 4, y, z + 1), state);
            worldIn.setBlockState(new BlockPos(x + 4, y, z + 2), state);
            worldIn.setBlockState(new BlockPos(x + 4, y, z + 3), state);
            worldIn.setBlockState(new BlockPos(x + 4, y, z + 4), state);
            worldIn.setBlockState(new BlockPos(x + 4, y, z - 1), state);
            worldIn.setBlockState(new BlockPos(x + 4, y, z - 2), state);
            worldIn.setBlockState(new BlockPos(x + 4, y, z - 3), state);
            worldIn.setBlockState(new BlockPos(x + 4, y, z - 4), state);
            worldIn.setBlockState(new BlockPos(x + 4, y, z), state);
            worldIn.setBlockState(new BlockPos(x + 4, y, z), state);
            worldIn.setBlockState(new BlockPos(x - 1, y, z + 4), state);
            worldIn.setBlockState(new BlockPos(x - 1, y, z - 4), state);
            worldIn.setBlockState(new BlockPos(x - 2, y, z + 4), state);
            worldIn.setBlockState(new BlockPos(x - 2, y, z - 4), state);
            worldIn.setBlockState(new BlockPos(x - 3, y, z + 4), state);
            worldIn.setBlockState(new BlockPos(x - 3, y, z - 4), state);
            worldIn.setBlockState(new BlockPos(x - 4, y, z + 1), state);
            worldIn.setBlockState(new BlockPos(x - 4, y, z + 2), state);
            worldIn.setBlockState(new BlockPos(x - 4, y, z + 3), state);
            worldIn.setBlockState(new BlockPos(x - 4, y, z + 4), state);
            worldIn.setBlockState(new BlockPos(x - 4, y, z - 1), state);
            worldIn.setBlockState(new BlockPos(x - 4, y, z - 2), state);
            worldIn.setBlockState(new BlockPos(x - 4, y, z - 3), state);
            worldIn.setBlockState(new BlockPos(x - 4, y, z - 4), state);
            worldIn.setBlockState(new BlockPos(x - 4, y, z), state);
            worldIn.setBlockState(new BlockPos(x, y, z + 4), state);
            worldIn.setBlockState(new BlockPos(x, y, z - 4), state);
            return true;
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onMouseEvent(net.minecraftforge.client.event.MouseEvent event) {
        if (event.button < 0) {
            EntityPlayer entityPlayer = Minecraft.getMinecraft().thePlayer;
            if (entityPlayer.isSneaking()) {
                ItemStack itemStack = entityPlayer.getHeldItem();
                if (itemStack != null && itemStack.getItem() == ModItems.debugItem) {
                    if (event.dwheel != 0)
                        NetworkHandler.instance().sendToServer(new PacketDebugItemMode(entityPlayer.inventory.currentItem, event.dwheel < 0));
                    event.setCanceled(true);
                }
            }
        }
    }

    static final class SwitchDirtType {
        static final int[] TYPE_LOOKUP = new int[BlockDirt.DirtType.values().length];

        static {
            try {
                TYPE_LOOKUP[BlockDirt.DirtType.DIRT.ordinal()] = 1;
            } catch (NoSuchFieldError var2) {
                ;
            }
            try {
                TYPE_LOOKUP[BlockDirt.DirtType.COARSE_DIRT.ordinal()] = 2;
            } catch (NoSuchFieldError var1) {
                ;
            }
        }
    }
}