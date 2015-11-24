package com.Girafi.culinarycultivation.client.renderer.tileentity;

import com.Girafi.culinarycultivation.client.model.ModelWinnowingMachine;
import com.Girafi.culinarycultivation.reference.Reference;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileWinnowingMachineRenderer extends TileEntitySpecialRenderer {
    private ModelWinnowingMachine model = new ModelWinnowingMachine();
    private static final ResourceLocation WinnowingMachine_Texture = new ResourceLocation(Reference.MOD_ID.toLowerCase(), "textures/models/winnowingMachine.png");

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double d, double d1, double d2, float f, int f1) {
    }
}