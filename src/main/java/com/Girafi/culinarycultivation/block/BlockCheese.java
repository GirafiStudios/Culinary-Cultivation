package com.Girafi.culinarycultivation.block;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.reference.Paths;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockCake;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockCheese extends BlockCake {
    @SideOnly(Side.CLIENT)
    public static IIcon iconTop;
    @SideOnly(Side.CLIENT)
    public static IIcon iconBottom;
    @SideOnly(Side.CLIENT)
    public static IIcon iconInner;

    public BlockCheese() {
        super();
        setBlockName(Paths.ModAssets + "cheese");
        setBlockTextureName(Paths.ModAssets + "cheese");
        setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
        setHardness(0.5F);
        setStepSound(soundTypeCloth);
        disableStats();
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
        this.eatCheese(world, x, y, z, player);
    }

    private void eatCheese(World world, int x, int y, int z, EntityPlayer player) {
        if (player.canEat(false)) {
            player.getFoodStats().addStats(2, 0.4F);
            int l = world.getBlockMetadata(x, y, z) + 1;

            if (l >= 6) {
                world.setBlockToAir(x, y, z);
            } else {
                world.setBlockMetadataWithNotify(x, y, z, l, 2);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int meta, int side) {
        return meta == 1 ? this.iconTop : (meta == 0 ? this.iconBottom : (side > 0 && meta == 4 ? iconInner : this.blockIcon));
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.blockIcon = register.registerIcon(this.getTextureName() + "_side");
        iconInner = register.registerIcon(this.getTextureName() + "_inner");
        iconTop = register.registerIcon(this.getTextureName() + "_top");
        iconBottom = register.registerIcon(this.getTextureName() + "_bottom");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Item getItem(World world, int x, int y, int z) {
        return Item.getItemFromBlock(ModBlocks.cheese);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemIconName() {
        return Paths.ModAssets + "cheeseWheel";
    }
}