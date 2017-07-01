package com.girafi.culinarycultivation.client.renderer.tileentity;

import com.girafi.culinarycultivation.block.tileentity.TileEntityCauldron;
import com.girafi.culinarycultivation.block.tileentity.TileFluidTank;
import com.girafi.culinarycultivation.util.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

public class TileEntityCauldronRenderer extends TileEntitySpecialRenderer<TileEntityCauldron> {

    @Override
    public void render(TileEntityCauldron cauldron, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (cauldron != null) {
            final TileFluidTank tank = cauldron.tank;

            if (tank != null && tank.getFluid() != null && tank.getFluidAmount() > 0) {
                FluidStack fluid = tank.getFluid();
                BlockPos pos = cauldron.getPos();

                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();

                final float staticX = (float) (pos.getX() - TileEntityRendererDispatcher.staticPlayerX);
                final float staticY = (float) (pos.getY() - TileEntityRendererDispatcher.staticPlayerY);
                final float staticZ = (float) (pos.getZ() - TileEntityRendererDispatcher.staticPlayerZ);

                GlStateManager.translate(staticX, staticY, staticZ);
                RenderUtils.renderFluid(fluid, cauldron.getPos(), 0.06D, 0.14D, 0.00D, 0.06D, 0.06D, 0.1D, 0.82D, (double) tank.getFluidAmount() / (double) tank.getCapacity() * 0.8d, 0.88d, fluid.getFluid().getColor(fluid));

                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        }
    }
}