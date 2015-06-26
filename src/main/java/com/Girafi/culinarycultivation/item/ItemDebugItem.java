package com.Girafi.culinarycultivation.item;

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

//    @SideOnly(Side.CLIENT)
//    private IIcon debug, hunger, hungerPlus, fertilizer, hoe;

    public ItemDebugItem() {
        super();
        setUnlocalizedName(Paths.ModAssets + "debugItem");
        maxStackSize = 1;
        setAlwaysEdible();
    }

//    @Override
//    @SideOnly(Side.CLIENT)
//    public void registerIcons(IIconRegister iIconRegister) {
//        debug = iIconRegister.registerIcon(Paths.ModAssets + "debugDefault");
//        hunger = iIconRegister.registerIcon(Paths.ModAssets + "debugHunger");
//        hungerPlus = iIconRegister.registerIcon(Paths.ModAssets + "debugHungerPlus");
//        fertilizer = iIconRegister.registerIcon(Paths.ModAssets + "debugFertilizer");
//        hoe = iIconRegister.registerIcon(Paths.ModAssets + "debugHoe");
//    }

//    @Override
//    public IIcon getIcon(ItemStack stack, int pass) {
//        return debug;
//    }
//
//    @Override
//    public IIcon getIconIndex(ItemStack stack) {
//        if (stack.getItemDamage() == 0)
//            return debug;
//        if (stack.getItemDamage() == 1)
//            return hunger;
//        if (stack.getItemDamage() == 2)
//            return hungerPlus;
//        if (stack.getItemDamage() == 3)
//            return fertilizer;
//        if (stack.getItemDamage() == 4)
//            return hoe;
//
//        return super.getIconIndex(stack);
//    }

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
            if (!worldIn.isRemote) {
                playerIn.addChatComponentMessage(new ChatComponentText("Switch mode by shift + scrolling"));
            }
        }
        if (stack.getItemDamage() == 1) {
            if (playerIn.canEat(this.alwaysEdible)) {
                playerIn.setItemInUse(stack, this.getMaxItemUseDuration(stack));
            }
        }
        if (stack.getItemDamage() == 2) {
            if (playerIn.canEat(this.alwaysEdible)) {
                playerIn.setItemInUse(stack, this.getMaxItemUseDuration(stack));
            }
        }
        return stack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 25;
    }

    public ItemDebugItem setAlwaysEdible() {
        this.alwaysEdible = true;
        return this;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (stack.getItemDamage() == 3) {
            if (applyBonemeal(stack, worldIn, pos, playerIn)) {
                if (!worldIn.isRemote) {
                    worldIn.playAuxSFX(2005, pos, 0);
                }
            }
            if (playerIn.isSneaking()) {
                if (worldIn.getBlockState(pos).getBlock() instanceof BlockCrops && applyBonemeal(stack, worldIn, pos, playerIn)) {
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
                        if (applyBonemeal(stack, worldIn, new BlockPos(x , y, z + 1), playerIn)) {
                            worldIn.playAuxSFX(2005, new BlockPos(x , y, z + 1), 0);
                        }
                        if (applyBonemeal(stack, worldIn, new BlockPos(x , y, z - 1), playerIn)) {
                            worldIn.playAuxSFX(2005, new BlockPos(x , y, z - 1), 0);
                        }
                        if (applyBonemeal(stack, worldIn, new BlockPos(x - 1, y, z - 1), playerIn)) {
                            worldIn.playAuxSFX(2005, new BlockPos(x - 1, y, z - 1), 0);
                        }
                        if (applyBonemeal(stack, worldIn, new BlockPos(x + 1, y, z - 1), playerIn)) {
                            worldIn.playAuxSFX(2005, new BlockPos(x + 1, y, z - 1), 0);
                        }
                    }
                }
            }
        }
        if (stack.getItemDamage() == 4)
            if (!playerIn.func_175151_a(pos.offset(side), side, stack))
            {
                return false;
            }
            else
            {
                int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(stack, playerIn, worldIn, pos);
                if (hook != 0) return hook > 0;

                IBlockState iblockstate = worldIn.getBlockState(pos);
                Block block = iblockstate.getBlock();

                if (side != EnumFacing.DOWN && worldIn.isAirBlock(pos.offsetUp()))
                {
                    if (block == Blocks.grass) {
                        return this.func_179232_a(stack, playerIn, worldIn, pos, Blocks.farmland.getDefaultState());
                    }

                    if (block == Blocks.dirt)
                    {
                        switch (SwitchDirtType.field_179590_a[((BlockDirt.DirtType)iblockstate.getValue(BlockDirt.VARIANT)).ordinal()])
                        {
                            case 1:
                                return this.func_179232_a(stack, playerIn, worldIn, pos, Blocks.farmland.getDefaultState());
                            case 2:
                                return this.func_179232_a(stack, playerIn, worldIn, pos, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                        }
                    }

                }

                return false;
            }
            /*if (!playerIn.func_175151_a(pos, side, stack)) {
                return false;
            } else {
                UseHoeEvent event = new UseHoeEvent(playerIn, stack, worldIn, pos);
                if (MinecraftForge.EVENT_BUS.post(event)) {
                    return false;
                }

                if (event.getResult() == Event.Result.ALLOW) {
                    stack.damageItem(1, playerIn);
                    return true;
                }

                Block block = worldIn.getBlockState(pos).getBlock();

                if (side != null 0 in 1.7.10  && worldIn.getBlock(x, y + 1, z).isAir(worldIn, x, y + 1, z) && (block == Blocks.grass || block == Blocks.dirt || block == Blocks.farmland)) {
                    Block block1 = Blocks.farmland;
                    worldIn.playSoundEffect((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F), (double) ((float) pos.getZ() + 0.5F), block1.stepSound.getStepResourcePath(), (block1.stepSound.getVolume() + 1.0F) / 2.0F, block1.stepSound.getPitch() * 0.8F);

                    if (worldIn.isRemote) {
                        return true;
                    } else {
                        //3 x 3
                        worldIn.setBlock(x, y, z, block1);
                        worldIn.setBlock(x + 1, y, z + 1, block1);
                        worldIn.setBlock(x + 1, y, z - 1, block1);
                        worldIn.setBlock(x + 1, y, z, block1);
                        worldIn.setBlock(x - 1, y, z + 1, block1);
                        worldIn.setBlock(x - 1, y, z - 1, block1);
                        worldIn.setBlock(x - 1, y, z, block1);
                        worldIn.setBlock(x, y, z + 1, block1);
                        worldIn.setBlock(x, y, z - 1, block1);
                        //4 x 4?
                        worldIn.setBlock(x + 1, y, z + 2, block1);
                        worldIn.setBlock(x + 1, y, z - 2, block1);
                        worldIn.setBlock(x + 2, y, z + 1, block1);
                        worldIn.setBlock(x + 2, y, z + 2, block1);
                        worldIn.setBlock(x + 2, y, z - 1, block1);
                        worldIn.setBlock(x + 2, y, z - 2, block1);
                        worldIn.setBlock(x + 2, y, z, block1);
                        worldIn.setBlock(x - 1, y, z + 2, block1);
                        worldIn.setBlock(x - 1, y, z - 2, block1);
                        worldIn.setBlock(x - 2, y, z + 1, block1);
                        worldIn.setBlock(x - 2, y, z + 2, block1);
                        worldIn.setBlock(x - 2, y, z - 1, block1);
                        worldIn.setBlock(x - 2, y, z - 2, block1);
                        worldIn.setBlock(x - 2, y, z, block1);
                        worldIn.setBlock(x, y, z + 2, block1);
                        worldIn.setBlock(x, y, z - 2, block1);
                        //7 x 7
                        worldIn.setBlock(x + 1, y, z + 3, block1);
                        worldIn.setBlock(x + 1, y, z - 3, block1);
                        worldIn.setBlock(x + 2, y, z + 3, block1);
                        worldIn.setBlock(x + 2, y, z - 3, block1);
                        worldIn.setBlock(x + 3, y, z + 1, block1);
                        worldIn.setBlock(x + 3, y, z + 2, block1);
                        worldIn.setBlock(x + 3, y, z + 3, block1);
                        worldIn.setBlock(x + 3, y, z - 1, block1);
                        worldIn.setBlock(x + 3, y, z - 2, block1);
                        worldIn.setBlock(x + 3, y, z - 3, block1);
                        worldIn.setBlock(x + 3, y, z, block1);
                        worldIn.setBlock(x + 3, y, z, block1);
                        worldIn.setBlock(x - 1, y, z + 3, block1);
                        worldIn.setBlock(x - 1, y, z - 3, block1);
                        worldIn.setBlock(x - 2, y, z + 3, block1);
                        worldIn.setBlock(x - 2, y, z - 3, block1);
                        worldIn.setBlock(x - 3, y, z + 1, block1);
                        worldIn.setBlock(x - 3, y, z + 2, block1);
                        worldIn.setBlock(x - 3, y, z + 3, block1);
                        worldIn.setBlock(x - 3, y, z - 1, block1);
                        worldIn.setBlock(x - 3, y, z - 2, block1);
                        worldIn.setBlock(x - 3, y, z - 3, block1);
                        worldIn.setBlock(x - 3, y, z, block1);
                        worldIn.setBlock(x, y, z + 3, block1);
                        worldIn.setBlock(x, y, z - 3, block1);
                        //9x9
                        worldIn.setBlock(x + 1, y, z + 4, block1);
                        worldIn.setBlock(x + 1, y, z - 4, block1);
                        worldIn.setBlock(x + 2, y, z + 4, block1);
                        worldIn.setBlock(x + 2, y, z - 4, block1);
                        worldIn.setBlock(x + 3, y, z + 4, block1);
                        worldIn.setBlock(x + 3, y, z - 4, block1);
                        worldIn.setBlock(x + 4, y, z + 1, block1);
                        worldIn.setBlock(x + 4, y, z + 2, block1);
                        worldIn.setBlock(x + 4, y, z + 3, block1);
                        worldIn.setBlock(x + 4, y, z + 4, block1);
                        worldIn.setBlock(x + 4, y, z - 1, block1);
                        worldIn.setBlock(x + 4, y, z - 2, block1);
                        worldIn.setBlock(x + 4, y, z - 3, block1);
                        worldIn.setBlock(x + 4, y, z - 4, block1);
                        worldIn.setBlock(x + 4, y, z, block1);
                        worldIn.setBlock(x + 4, y, z, block1);
                        worldIn.setBlock(x - 1, y, z + 4, block1);
                        worldIn.setBlock(x - 1, y, z - 4, block1);
                        worldIn.setBlock(x - 2, y, z + 4, block1);
                        worldIn.setBlock(x - 2, y, z - 4, block1);
                        worldIn.setBlock(x - 3, y, z + 4, block1);
                        worldIn.setBlock(x - 3, y, z - 4, block1);
                        worldIn.setBlock(x - 4, y, z + 1, block1);
                        worldIn.setBlock(x - 4, y, z + 2, block1);
                        worldIn.setBlock(x - 4, y, z + 3, block1);
                        worldIn.setBlock(x - 4, y, z + 4, block1);
                        worldIn.setBlock(x - 4, y, z - 1, block1);
                        worldIn.setBlock(x - 4, y, z - 2, block1);
                        worldIn.setBlock(x - 4, y, z - 3, block1);
                        worldIn.setBlock(x - 4, y, z - 4, block1);
                        worldIn.setBlock(x - 4, y, z, block1);
                        worldIn.setBlock(x, y, z + 4, block1);
                        worldIn.setBlock(x, y, z - 4, block1);
                        return true;
                    }
                } else {
                    return false;
                }
            }*/
        return false;
    }

    public static boolean applyBonemeal(ItemStack stack, World worldIn, BlockPos pos, EntityPlayer player) {
        IBlockState iblockstate = worldIn.getBlockState(pos);

        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, worldIn, pos, iblockstate, stack);
        if (hook != 0) return hook > 0;

        if (iblockstate.getBlock() instanceof IGrowable) {
            IGrowable igrowable = (IGrowable) iblockstate.getBlock();

            if (igrowable.isStillGrowing(worldIn, pos, iblockstate, worldIn.isRemote)) {
                if (!worldIn.isRemote) {
                    if (igrowable.canUseBonemeal(worldIn, worldIn.rand, pos, iblockstate)) {
                        igrowable.grow(worldIn, worldIn.rand, pos, iblockstate);
                    }
                    --stack.stackSize;
                }
                return true;
            }
        }
        return false;
    }

    protected boolean func_179232_a(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, IBlockState state) {
        worldIn.playSoundEffect((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), state.getBlock().stepSound.getStepSound(), (state.getBlock().stepSound.getVolume() + 1.0F) / 2.0F, state.getBlock().stepSound.getFrequency() * 0.8F);

        if (worldIn.isRemote) {
            return true;
        }
        else {
            worldIn.setBlockState(pos, state);
            stack.damageItem(1, playerIn);
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

}

final class SwitchDirtType {
    static final int[] field_179590_a = new int[BlockDirt.DirtType.values().length];
    private static final String __OBFID = "CL_00002179";

    static
    {
        try
        {
            field_179590_a[BlockDirt.DirtType.DIRT.ordinal()] = 1;
        }
        catch (NoSuchFieldError var2)
        {
            ;
        }

        try
        {
            field_179590_a[BlockDirt.DirtType.COARSE_DIRT.ordinal()] = 2;
        }
        catch (NoSuchFieldError var1)
        {
            ;
        }
    }
}