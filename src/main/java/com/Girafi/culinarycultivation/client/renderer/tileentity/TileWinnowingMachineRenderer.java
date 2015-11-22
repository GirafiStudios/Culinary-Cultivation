package com.Girafi.culinarycultivation.client.renderer.tileentity;

import com.Girafi.culinarycultivation.client.model.ModelWinnowingMachine;
import com.Girafi.culinarycultivation.reference.Paths;
import com.Girafi.culinarycultivation.reference.Reference;
import com.Girafi.culinarycultivation.tileentity.TileEntityWinnowingMachine;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import thaumcraft.common.lib.utils.BlockStateUtils;

@SideOnly(Side.CLIENT)
public class TileWinnowingMachineRenderer extends TileEntitySpecialRenderer {
    private ModelWinnowingMachine model = new ModelWinnowingMachine();
    private static final ResourceLocation WinnowingMachine_Texture = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/models/winnowingMachine.png");

    public TileWinnowingMachineRenderer() {
    }

    public void renderEntityAt(TileEntityWinnowingMachine winnowingMachine, double x, double y, double z, float f) {
        int f1 = 2;
        if (winnowingMachine.getWorld() != null) {
            f1 = BlockStateUtils.getFacing(winnowingMachine.getBlockMetadata()).ordinal();
        }

        this.bindTexture(WinnowingMachine_Texture);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
        GL11.glPushMatrix();
        this.model.render();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        switch (f1) {
            case 2:
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                break;
            case 3:
                GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
                break;
            case 4:
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                break;
            case 5:
                GL11.glRotatef(0.0F, 0.0F, 1.0F, 0.0F);
        }
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double d, double d1, double d2, float f, int f1) {
        this.renderEntityAt((TileEntityWinnowingMachine) tileEntity, d, d1, d2, f);
    }
}