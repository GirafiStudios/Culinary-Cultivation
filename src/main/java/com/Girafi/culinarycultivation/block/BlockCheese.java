package com.Girafi.culinarycultivation.block;

import com.Girafi.culinarycultivation.init.ModBlocks;
import net.minecraft.block.BlockCake;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCheese extends BlockCake {

    public BlockCheese() {
        super();
        setHardness(0.5F);
        setStepSound(soundTypeCloth);
        disableStats();
    }

    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
        this.eatCheese(world, pos, world.getBlockState(pos), player);
    }

    private void eatCheese(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (player.canEat(false)) {
            player.getFoodStats().addStats(2, 0.4F);
            int i = state.getValue(BITES);

            if (i < 6) {
                world.setBlockState(pos, state.withProperty(BITES, i + 1), 3);
            } else {
                world.setBlockToAir(pos);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, BlockPos pos) {
        return Item.getItemFromBlock(ModBlocks.cheese);
    }
}