package com.Girafi.culinarycultivation.block;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockCake;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockCheese extends BlockCake { //TODO The hitbox might be a little off //TODO Change food you get from eating it
    public BlockCheese() {
        super();
        setBlockName(Reference.MOD_ID.toLowerCase() + ":" + "cheese");
        setBlockTextureName(Reference.MOD_ID.toLowerCase() + ":" + "cheese");
        setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
        setHardness(0.5F);
        setStepSound(soundTypeCloth);
        disableStats();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Item getItem(World world, int x, int y, int z) {
        return Item.getItemFromBlock(ModBlocks.cheese); }

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemIconName() { return Reference.MOD_ID + ":" + "cheeseWheel"; }
}
