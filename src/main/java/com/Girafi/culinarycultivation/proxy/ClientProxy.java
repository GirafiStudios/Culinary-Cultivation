package com.Girafi.culinarycultivation.proxy;

import com.Girafi.culinarycultivation.utility.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.Girafi.culinarycultivation.init.ModItems.*;

public class ClientProxy extends CommonProxy {
    @Override
    @SideOnly(Side.CLIENT)
    public void registerRenders() {
        registerItemRender(cakeKnife);
        registerItemRender(cooked_fish);
        registerItemRender(cooked_meat);
        registerItemRender(debugItem);
        registerItemRender(farmerBoots);
        registerItemRender(fish);
        registerItemRender(knife);
        registerItemRender(meat);
        registerItemRender(meatCleaver);
        registerItemRender(pieceOfCake);
        registerItemRender(storageJar);
        registerItemRender(toolHandle);
        LogHelper.info("RegisterRenders have been runned");
    }

    public static void registerItemRender(Item item) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getUnlocalizedName().replace("item.", ""), "inventory"));
    }
}