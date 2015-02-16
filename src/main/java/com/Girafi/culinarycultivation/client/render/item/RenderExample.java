package com.Girafi.culinarycultivation.client.render.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class RenderExample implements IItemRenderer {//This class is not doing anything, just saved as an example

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        switch (type) {
            case ENTITY:
                return false;
            case EQUIPPED:
                return false;
            case EQUIPPED_FIRST_PERSON:
                return false;
            case INVENTORY:
                return false;
            default:
                return false;
        }
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return type == ItemRenderType.INVENTORY || type == ItemRenderType.ENTITY;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        switch (type) {
            case ENTITY:
                break;
            case EQUIPPED:
                GL11.glScalef(-90, 0, 0);
                GL11.glTranslatef(0, 1, -3);
                GL11.glRotatef(-90, 1.0F, 0.0F, 0.0F);
                break;
            case EQUIPPED_FIRST_PERSON:
                GL11.glRotatef(90, 1.0F, 0, 0);
                break;
            case INVENTORY:
                break;
            default:
                break;
        }
        GL11.glPopMatrix();
    }
}