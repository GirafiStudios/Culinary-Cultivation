package com.Girafi.culinarycultivation.block;

import com.Girafi.culinarycultivation.init.ModBlocks;
import net.minecraft.block.BlockCake;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCheese extends BlockCake {

    public BlockCheese() {
        super();
        this.setHardness(0.5F);
        this.setSoundType(SoundType.CLOTH);
        this.disableStats();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        this.eatCheese(world, pos, state, player);
        return true;
    }

    private void eatCheese(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (player.canEat(false)) {
            player.getFoodStats().addStats(2, 0.4F);
            int i = state.getValue(BITES);

            if (i < 6) {
                worldIn.setBlockState(pos, state.withProperty(BITES, i + 1), 3);
            } else {
                worldIn.setBlockToAir(pos);
            }
        }
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(Item.getItemFromBlock(ModBlocks.cheese));
    }
}