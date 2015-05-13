package com.Girafi.culinarycultivation.item;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.network.NetworkHandler;
import com.Girafi.culinarycultivation.network.packet.PacketDebugItemMode;
import com.Girafi.culinarycultivation.reference.Reference;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;

public class ItemDebugItem extends Item {

    private boolean alwaysEdible;

    @SideOnly(Side.CLIENT)
    private IIcon debug, hunger, hungerPlus, fertilizer, hoe;

    public ItemDebugItem() {
        super();
        setUnlocalizedName(Reference.MOD_ID.toLowerCase() + ":" + "debugItem");
        maxStackSize = 1;
        setAlwaysEdible();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iIconRegister) {
        debug = iIconRegister.registerIcon(Reference.MOD_ID + ":" + "debugDefault");
        hunger = iIconRegister.registerIcon(Reference.MOD_ID + ":" + "debugHunger");
        hungerPlus = iIconRegister.registerIcon(Reference.MOD_ID + ":" + "debugHungerPlus");
        fertilizer = iIconRegister.registerIcon(Reference.MOD_ID + ":" + "debugFertilizer");
        hoe = iIconRegister.registerIcon(Reference.MOD_ID + ":" + "debugHoe");
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return debug;
    }

    @Override
    public IIcon getIconIndex(ItemStack stack) {
        if (stack.getItemDamage() == 0)
            return debug;
        if (stack.getItemDamage() == 1)
            return hunger;
        if (stack.getItemDamage() == 2)
            return hungerPlus;
        if (stack.getItemDamage() == 3)
            return fertilizer;
        if (stack.getItemDamage() == 4)
            return hoe;

        return super.getIconIndex(stack);
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
            return EnumAction.eat;
        }
        if (stack.getItemDamage() == 2) {
            return EnumAction.eat;
        } else {
            return null;
        }
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World worldIn, EntityPlayer playerIn) {
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
                playerIn.addChatComponentMessage(new ChatComponentText("This mode is not implemented yet. Switch mode by shift + scrolling"));
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

    //TODO Make it bonemeal on a larger area, when used on crops
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, int x, int y, int z, int side, float par8, float par9, float par10) {
        if (stack.getItemDamage() == 3) {
            if (applyBonemeal(stack, worldIn, x, y, z, playerIn)) {
                if (!worldIn.isRemote) {
                    worldIn.playAuxSFX(2005, x, y, z, 0);
                }
            }
        }
        if (stack.getItemDamage() == 4) {
            if (!playerIn.canPlayerEdit(x, y, z, side, stack)) {
                return false;
            } else {
                UseHoeEvent event = new UseHoeEvent(playerIn, stack, worldIn, x, y, z);
                if (MinecraftForge.EVENT_BUS.post(event)) {
                    return false;
                }

                if (event.getResult() == Event.Result.ALLOW) {
                    stack.damageItem(1, playerIn);
                    return true;
                }

                Block block = worldIn.getBlock(x, y, z);

                if (side != 0 && worldIn.getBlock(x, y + 1, z).isAir(worldIn, x, y + 1, z) && (block == Blocks.grass || block == Blocks.dirt || block == Blocks.farmland)) {
                    Block block1 = Blocks.farmland;
                    worldIn.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), block1.stepSound.getStepResourcePath(), (block1.stepSound.getVolume() + 1.0F) / 2.0F, block1.stepSound.getPitch() * 0.8F);

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
            }
        }
        return true;
    }

    public static boolean applyBonemeal(ItemStack stack, World worldIn, int x, int y, int z, EntityPlayer playerIn)
    {
        Block block = worldIn.getBlock(x, y, z);

        BonemealEvent event = new BonemealEvent(playerIn, worldIn, block, x, y, z);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            return false;
        }

        if (event.getResult() == Event.Result.ALLOW) {
            if (!worldIn.isRemote) {}
            return true;
        }

        if (block instanceof IGrowable) {
            IGrowable igrowable = (IGrowable)block;
            if (igrowable.func_149851_a(worldIn, x, y, z, worldIn.isRemote))
            {
                if (!worldIn.isRemote)
                {
                    if (igrowable.func_149852_a(worldIn, worldIn.rand, x, y, z))
                    {
                        igrowable.func_149853_b(worldIn, worldIn.rand, x, y, z);
                    }
                }
                return true;
            }
        }
        return false;
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