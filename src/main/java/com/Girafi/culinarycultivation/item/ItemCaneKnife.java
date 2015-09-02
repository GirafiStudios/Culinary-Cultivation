package com.Girafi.culinarycultivation.item;

import com.Girafi.culinarycultivation.reference.Paths;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Set;

public class ItemCaneKnife extends ItemTool {

    private static final Set EFFECTIVE_ON = Sets.newHashSet(new Block[]{Blocks.reeds, Blocks.vine, Blocks.cocoa, Blocks.deadbush, Blocks.leaves, Blocks.leaves2, Blocks.tallgrass, Blocks.double_plant});

    public ItemCaneKnife(ToolMaterial material) {
        super(2.0F, material, EFFECTIVE_ON);
        setUnlocalizedName(Paths.ModAssets + "caneKnife");
    }

/*    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, BlockPos pos, EntityLivingBase living) {
            *//*if ((double) block.getBlockHardness(world, pos) != 0.0D) {
                stack.damageItem(1, living);
            }*//*

        if (ForgeEventFactory.onNeighborNotify(world, pos, world.getBlockState(pos), java.util.EnumSet.allOf(EnumFacing.class)).isCanceled()) {
            if (block != null && block == Blocks.reeds) {
                if (block == Blocks.reeds) {

                    block.breakBlock(world, pos.up(), world.getBlockState(pos));
                    block.breakBlock(world, pos.up(2), world.getBlockState(pos));
                }
            }
        }
        return true;
    }*/
}