package com.Girafi.culinarycultivation.block;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.reference.Paths;
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
        setUnlocalizedName(Paths.ModAssets + "cheese");
        setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
        setHardness(0.5F);
        setStepSound(soundTypeCloth);
        disableStats();
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        this.eatCheese(worldIn, pos, worldIn.getBlockState(pos), playerIn);
    }

    private void eatCheese(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (player.canEat(false)) {
            player.getFoodStats().addStats(2, 0.4F);
            int i = ((Integer)state.getValue(BITES)).intValue();

            if (i < 6) {
                worldIn.setBlockState(pos, state.withProperty(BITES, Integer.valueOf(i + 1)), 3);
            }
            else {
                worldIn.setBlockToAir(pos);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Item getItem(World world, BlockPos pos) {
        return Item.getItemFromBlock(ModBlocks.cheese); }
}
