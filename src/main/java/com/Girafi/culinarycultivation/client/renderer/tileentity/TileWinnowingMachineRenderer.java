package com.Girafi.culinarycultivation.client.renderer.tileentity;

import com.Girafi.culinarycultivation.client.model.ModelWinnowingMachine;
import com.Girafi.culinarycultivation.reference.Reference;
import com.Girafi.culinarycultivation.tileentity.TileEntityWinnowingMachine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class TileWinnowingMachineRenderer extends TileEntitySpecialRenderer<TileEntityWinnowingMachine> {
    private static final ResourceLocation texture = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/models/winnowingMachine.png");

    private ModelWinnowingMachine model = new ModelWinnowingMachine();

    @Override
    public void renderTileEntityAt(TileEntityWinnowingMachine winnowingMachine, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
        GlStateManager.rotate(180, 1, 0, 0);

        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        model.render();

        GlStateManager.popMatrix();
    }
}