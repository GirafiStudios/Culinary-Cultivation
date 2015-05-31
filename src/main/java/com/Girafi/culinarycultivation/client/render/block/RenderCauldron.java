package com.Girafi.culinarycultivation.client.render.block;

import com.Girafi.culinarycultivation.block.BlockCheese;
import com.Girafi.culinarycultivation.block.BlockModCauldron;
import com.Girafi.culinarycultivation.item.ItemStorageJar;
import com.Girafi.culinarycultivation.utility.Utils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderCauldron extends SourceBlockRenderHandler {

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks render) {
        render.renderStandardBlock(block, x, y, z);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(render.blockAccess, x, y, z));
        int l = block.colorMultiplier(render.blockAccess, x, y, z);
        float f = (float) (l >> 16 & 255) / 255.0F;
        float f1 = (float) (l >> 8 & 255) / 255.0F;
        float f2 = (float) (l & 255) / 255.0F;
        float f4;

        if (EntityRenderer.anaglyphEnable) {
            float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
            f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
            float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
            f = f3;
            f1 = f4;
            f2 = f5;
        }
        tessellator.setColorOpaque_F(f, f1, f2);
        IIcon iicon1 = block.getBlockTextureFromSide(2);
        f4 = 0.125F;
        render.renderFaceXPos(block, (double) ((float) x - 1.0F + f4), (double) y, (double) z, iicon1);
        render.renderFaceXNeg(block, (double) ((float) x + 1.0F - f4), (double) y, (double) z, iicon1);
        render.renderFaceZPos(block, (double) x, (double) y, (double) ((float) z - 1.0F + f4), iicon1);
        render.renderFaceZNeg(block, (double) x, (double) y, (double) ((float) z + 1.0F - f4), iicon1);
        IIcon iicon2 = BlockModCauldron.getCauldronIcon("inner");
        render.renderFaceYPos(block, (double) x, (double) ((float) y - 1.0F + 0.25F), (double) z, iicon2);
        render.renderFaceYNeg(block, (double) x, (double) ((float) y + 1.0F - 0.75F), (double) z, iicon2);
        int meta = render.blockAccess.getBlockMetadata(x, y, z);

        if (meta > 0 && meta <= 3) {
            IIcon iicon = BlockLiquid.getLiquidIcon("water_still");
            render.renderFaceYPos(block, (double) x, (double) ((float) y - 1.0F + BlockModCauldron.getRenderLiquidLevel(meta)), (double) z, iicon);
        }
        if (meta > 3 && meta <= 6) {
            IIcon milk = BlockModCauldron.iconMilk;
            render.renderFaceYPos(block, (double) x, (double) ((float) y - 1.0F + BlockModCauldron.getRenderMilkLevel(meta)), (double) z, milk);
        }
        if (meta > 6 && meta <= 9) {
            IIcon rennet = BlockModCauldron.iconRennet;
            render.renderFaceYPos(block, (double) x, (double) ((float) y - 1.0F + BlockModCauldron.getRenderRennetLevel(meta)), (double) z, rennet);
        }
        if (meta > 12 && meta <= 14) {
            IIcon cheeseMass = BlockModCauldron.iconCheeseMass;
            render.renderFaceYPos(block, (double) x, (double) ((float) y - 1.0F + BlockModCauldron.getRenderCheeseMassLevel(meta)), (double) z, cheeseMass);
        }
        if (meta == 15) {
            IIcon cheese = BlockCheese.iconTop;
            render.renderFaceYPos(block, (double) x, (double) ((float) y - 1.0F + BlockModCauldron.getRenderLiquidLevel(meta)), (double) z, cheese);
        }
        return true;
    }

}
