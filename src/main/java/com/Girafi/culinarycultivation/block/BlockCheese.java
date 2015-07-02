package com.Girafi.culinarycultivation.block;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.reference.Paths;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.BlockCake;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockCheese extends BlockCake { //TODO Change hunger you get from eating it

    public BlockCheese() {
        super();
        setUnlocalizedName(Paths.ModAssets + "cheese");
        setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
        setHardness(0.5F);
        setStepSound(soundTypeCloth);
        disableStats();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Item getItem(World world, BlockPos pos) {
        return Item.getItemFromBlock(ModBlocks.cheese); }
}
