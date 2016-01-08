package com.Girafi.culinarycultivation.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * WinnowingMachine - wiiv
 * Created using Tabula 4.1.1
 */
@SideOnly(Side.CLIENT)
public class ModelWinnowingMachine extends ModelBase {
    public ModelRenderer side1;
    public ModelRenderer legs1;
    public ModelRenderer side2;
    public ModelRenderer legs2;
    public ModelRenderer input;
    public ModelRenderer side1_1;
    public ModelRenderer side2_1;
    public ModelRenderer output;

    public ModelWinnowingMachine() {
        this.textureWidth = 64;
        this.textureHeight = 128;
        this.input = new ModelRenderer(this, 0, 60);
        this.input.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.input.addBox(8.0F, -18.0F, -8.0F, 16, 6, 16, 0.0F);
        this.side1 = new ModelRenderer(this, 0, 0);
        this.side1.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.side1.addBox(-8.0F, -12.0F, -8.0F, 16, 16, 16, 0.0F);
        this.legs1 = new ModelRenderer(this, 0, 82);
        this.legs1.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.legs1.addBox(-5.0F, 0.0F, -6.0F, 3, 8, 12, 0.0F);
        this.side2_1 = new ModelRenderer(this, 0, 32);
        this.side2_1.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.side2_1.addBox(8.0F, -12.0F, -7.0F, 16, 14, 14, 0.0F);
        this.legs2 = new ModelRenderer(this, 0, 82);
        this.legs2.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.legs2.addBox(18.0F, 0.0F, -6.0F, 3, 8, 12, 0.0F);
        this.output = new ModelRenderer(this, 0, 102);
        this.output.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.output.addBox(11.0F, -2.0F, -10.0F, 4, 3, 8, 0.0F);
        this.setRotateAngle(output, 0.2617993877991494F, 0.0F, 0.0F);
        this.side1_1 = new ModelRenderer(this, 0, 0);
        this.side1_1.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.side1_1.addBox(-8.0F, -12.0F, -8.0F, 16, 16, 16, 0.0F);
        this.side2 = new ModelRenderer(this, 0, 32);
        this.side2.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.side2.addBox(8.0F, -12.0F, -7.0F, 16, 14, 14, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.input.render(f5);
        this.side1.render(f5);
        this.legs1.render(f5);
        this.side2_1.render(f5);
        this.legs2.render(f5);
        this.output.render(f5);
        this.side1_1.render(f5);
        this.side2.render(f5);
    }

    public void render() {
        float f5 = 0.0625F;
        this.input.render(f5);
        this.side1.render(f5);
        this.legs1.render(f5);
        this.side2_1.render(f5);
        this.legs2.render(f5);
        this.output.render(f5);
        this.side1_1.render(f5);
        this.side2.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}