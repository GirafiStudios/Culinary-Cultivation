package com.Girafi.culinarycultivation.utility;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;

public class Utils {
    public static Color setColor(int r, int g, int b) {
        Color c = new Color(r, g, b);
        return c;
    }

    public static ItemModelMesher getMesher() {
        ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
        return mesher;
    }
}
