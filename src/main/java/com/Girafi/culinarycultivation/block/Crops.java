package com.Girafi.culinarycultivation.block;

import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockFarmland;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class Crops {


    public static class BlockBeet extends BlockCrops {

        @SideOnly(Side.CLIENT)
        private IIcon[] iicon;

        @SideOnly(Side.CLIENT)
        public IIcon getIcon(int side, int meta) {
            if (meta < 7) {
                if (meta == 6) {
                    meta = 5;
                }
                return this.iicon[meta >> 1];
            }else {
                return this.iicon[3];
            }
        }

        protected Item func_149866_i()
        {
            return ModItems.beetRaw;
        }

        protected Item func_149865_P()
        {
            return ModItems.beetRaw;
        }

        @SideOnly(Side.CLIENT)
        public void registerBlockIcons(IIconRegister iIconRegister) {
            this.iicon = new IIcon[4];

            for (int i = 0; i < this.iicon.length; ++i) {
                this.iicon[i] = iIconRegister.registerIcon(Reference.MOD_ID + ":" + this.getTextureName() + "_stage_" + i);
            }
        }
    }

    public static class BlockBlackPepper extends BlockCrops { //TODO Might need to extend BlockBush and implement IGrowable
        //Highly inspired by BP Flax seeds, just to try find a way to get it working
        //TODO Make 1 block instead of 2, but still two high!

        @Override
        public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {

            int l = world.getBlockMetadata(x, y, z);
            if (l <= 2) {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
            } else if (l <= 4) {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            } else if (l <= 6) {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
            } else {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
        }

        @Override
        public void updateTick(World worldin, int x, int y, int z, Random rand) { //TODO Need to do a lot of work here!

            if (worldin.getBlockLightValue(x, y + 1, z) >= 9) {
                int l = worldin.getBlockMetadata(x, y, z);

                if ((l < 7) && (worldin.getBlock(x, y -1, z) instanceof BlockFarmland)) {
                    if (rand.nextInt(30) == 0) {
                        worldin.setBlockMetadataWithNotify(x, y, z, l + 1, 2);
                    }
                }
                if ((l == 7) && (worldin.getBlock(x, y - 1, z) instanceof BlockFarmland) && (worldin.getBlock(x, y + 1, z) instanceof BlockAir)) {
                    worldin.setBlock(x, y + 1, z, ModBlocks.blackPepper2, 0, 2);
                }

            }
        }

        @SideOnly(Side.CLIENT)
        private IIcon[] iicon;

        @SideOnly(Side.CLIENT)
        public IIcon getIcon(int side, int meta) {
            if (meta < 7) {
                if (meta == 6) {
                    meta = 5;
                }
                return this.iicon[meta >> 1];
            } else {
                return this.iicon[3];
            }
        }

        protected Item func_149866_i() {
            return ModItems.beetRaw;
        }

        protected Item func_149865_P() {
            return ModItems.beetRaw;
        }

        @SideOnly(Side.CLIENT)
        public void registerBlockIcons(IIconRegister iIconRegister) {
            this.iicon = new IIcon[4];

            for (int i = 0; i < this.iicon.length; ++i) {
                this.iicon[i] = iIconRegister.registerIcon(Reference.MOD_ID + ":" + this.getTextureName() + "_stage_" + i);
            }
        }
    }

    public static class BlockBlackPepperStep2 extends BlockCrops {

        @Override
        protected boolean canPlaceBlockOn(Block block)
        {
            return block == ModBlocks.blackPepper;
        }

        @SideOnly(Side.CLIENT)
        private IIcon[] iicon;

        @SideOnly(Side.CLIENT)
        public IIcon getIcon(int side, int meta) {
            if (meta < 7) {
                if (meta == 6) {
                    meta = 5;
                }
                return this.iicon[meta >> 1];
            } else {
                return this.iicon[3];
            }
        }

        protected Item func_149866_i() { return ModItems.beetRaw; }

        protected Item func_149865_P() { return ModItems.beetRaw; }

        @SideOnly(Side.CLIENT)
        public void registerBlockIcons(IIconRegister iIconRegister) {
            this.iicon = new IIcon[4];

            for (int i = 0; i < this.iicon.length; ++i) {
                this.iicon[i] = iIconRegister.registerIcon(Reference.MOD_ID + ":" + this.getTextureName() + "_stage_" + i);
            }
        }
    }
}