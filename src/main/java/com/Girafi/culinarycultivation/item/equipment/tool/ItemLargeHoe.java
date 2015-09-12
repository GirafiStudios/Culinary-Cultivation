package com.Girafi.culinarycultivation.item.equipment.tool;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLargeHoe extends Item {
    protected Item.ToolMaterial theToolMaterial;

    public ItemLargeHoe(Item.ToolMaterial material) {
        this.theToolMaterial = material;
        this.maxStackSize = 1;
        this.setMaxDamage(material.getMaxUses());
        this.setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) { //TODO
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (!playerIn.canPlayerEdit(pos.offset(side), side, stack)) {
            return false;
        } else {
            int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(stack, playerIn, worldIn, pos);
            if (hook != 0) return hook > 0;

            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (side != EnumFacing.DOWN && worldIn.isAirBlock(pos.up())) {
                if (block == Blocks.grass) {
                    return this.useHoe(stack, playerIn, worldIn, pos, Blocks.farmland.getDefaultState())
                            && this.useHoe(stack, playerIn, worldIn, new BlockPos(x + 1, y, z + 1), Blocks.farmland.getDefaultState())
                            && this.useHoe(stack, playerIn, worldIn, new BlockPos(x + 1, y, z - 1), Blocks.farmland.getDefaultState())
                            && this.useHoe(stack, playerIn, worldIn, new BlockPos(x + 1, y, z), Blocks.farmland.getDefaultState())
                            && this.useHoe(stack, playerIn, worldIn, new BlockPos(x - 1, y, z + 1), Blocks.farmland.getDefaultState())
                            && this.useHoe(stack, playerIn, worldIn, new BlockPos(x - 1, y, z - 1), Blocks.farmland.getDefaultState())
                            && this.useHoe(stack, playerIn, worldIn, new BlockPos(x - 1, y, z), Blocks.farmland.getDefaultState())
                            && this.useHoe(stack, playerIn, worldIn, new BlockPos(x, y, z + 1), Blocks.farmland.getDefaultState())
                            && this.useHoe(stack, playerIn, worldIn, new BlockPos(x, y, z - 1), Blocks.farmland.getDefaultState());
                }

                if (block == Blocks.dirt) {
                    switch (SwitchDirtType.TYPE_LOOKUP[((BlockDirt.DirtType) iblockstate.getValue(BlockDirt.VARIANT)).ordinal()]) {
                        case 1:
                            return     this.useHoe(stack, playerIn, worldIn, pos, Blocks.farmland.getDefaultState())
                                    && this.useHoe(stack, playerIn, worldIn, new BlockPos(x + 1, y, z + 1), Blocks.farmland.getDefaultState())
                                    && this.useHoe(stack, playerIn, worldIn, new BlockPos(x + 1, y, z - 1), Blocks.farmland.getDefaultState())
                                    && this.useHoe(stack, playerIn, worldIn, new BlockPos(x + 1, y, z), Blocks.farmland.getDefaultState())
                                    && this.useHoe(stack, playerIn, worldIn, new BlockPos(x - 1, y, z + 1), Blocks.farmland.getDefaultState())
                                    && this.useHoe(stack, playerIn, worldIn, new BlockPos(x - 1, y, z - 1), Blocks.farmland.getDefaultState())
                                    && this.useHoe(stack, playerIn, worldIn, new BlockPos(x - 1, y, z), Blocks.farmland.getDefaultState())
                                    && this.useHoe(stack, playerIn, worldIn, new BlockPos(x, y, z + 1), Blocks.farmland.getDefaultState())
                                    && this.useHoe(stack, playerIn, worldIn, new BlockPos(x, y, z - 1), Blocks.farmland.getDefaultState());
                        case 2:
                            return this.useHoe(stack, playerIn, worldIn, pos, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                    }
                }
            }
            return false;
        }
    }

    protected boolean useHoe(ItemStack stack, EntityPlayer player, World worldIn, BlockPos target, IBlockState newState) {
        if (worldIn.getBlockState(target).getBlock() instanceof BlockDirt || worldIn.getBlockState(target).getBlock() instanceof BlockGrass) {
            worldIn.playSoundEffect((double) ((float) target.getX() + 0.5F), (double) ((float) target.getY() + 0.5F), (double) ((float) target.getZ() + 0.5F), newState.getBlock().stepSound.getStepSound(), (newState.getBlock().stepSound.getVolume() + 1.0F) / 2.0F, newState.getBlock().stepSound.getFrequency() * 0.8F);

            if (worldIn.isRemote) {
                return true;
            } else {
                worldIn.setBlockState(target, newState);
                stack.damageItem(1, player);
                return true;
            }
        }
        return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

    /**
     * Returns the name of the material this tool is made from as it is declared in EnumToolMaterial (meaning diamond
     * would return "EMERALD")
     */
    public String getMaterialName()
    {
        return this.theToolMaterial.toString();
    }

    static final class SwitchDirtType {
        static final int[] TYPE_LOOKUP = new int[BlockDirt.DirtType.values().length];

        static {
            try {
                TYPE_LOOKUP[BlockDirt.DirtType.DIRT.ordinal()] = 1;
            } catch (NoSuchFieldError var2) {
                ;
            } try {
                TYPE_LOOKUP[BlockDirt.DirtType.COARSE_DIRT.ordinal()] = 2;
            } catch (NoSuchFieldError var1) {
                ;
            }
        }
    }
}
