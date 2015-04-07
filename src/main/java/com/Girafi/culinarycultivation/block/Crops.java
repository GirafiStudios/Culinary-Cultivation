package com.Girafi.culinarycultivation.block;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

import java.util.Random;

public class Crops {


    public static class BlockBeet extends BlockCrops {

        private static double rand;
        @SideOnly(Side.CLIENT)
        private IIcon[] field_149868_a;

        @SideOnly(Side.CLIENT)
        public IIcon getIcon(int side, int meta) {
            if (meta < 7)
            {
                if (meta == 6)
                {
                    meta = 5;
                }
                return this.field_149868_a[meta >> 1];
            }else {
                return this.field_149868_a[3];
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
            this.field_149868_a = new IIcon[4];

            for (int i = 0; i < this.field_149868_a.length; ++i)
            {
                this.field_149868_a[i] = iIconRegister.registerIcon(Reference.MOD_ID + ":" + this.getTextureName() + "_stage_" + i);
            }
        }
    }
}