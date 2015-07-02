package com.Girafi.culinarycultivation.proxy;

import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemModFishFood;
import com.Girafi.culinarycultivation.reference.Paths;
import com.Girafi.culinarycultivation.utility.LogHelper;
import com.Girafi.culinarycultivation.utility.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.Girafi.culinarycultivation.init.ModItems.*;
import static com.Girafi.culinarycultivation.init.ModItems.fish;

public class ClientProxy extends CommonProxy {

    @Override
    @SideOnly(Side.CLIENT)
    public void registerRenders() {
        registerItemRender(cakeKnife);
        registerItemRender(cooked_fish);
        registerItemRender(cooked_meat);
        registerItemRender(debugItem);
        registerItemRender(farmerBoots);

        /*ItemModFishFood.FishType[] afish = ItemModFishFood.FishType.values();
        int i = afish.length;
        for (int j = 0; j < i; ++j) {
            ItemModFishFood.FishType fishType = afish[j];*/
        Utils.getMesher().register(ModItems.fish, ItemModFishFood.FishType.MACKEREL.getMetaData(), new ModelResourceLocation(Paths.ModAssets + ItemModFishFood.FishType.MACKEREL.getUnlocalizedName(), "inventory"));
        Utils.getMesher().register(storageJar, 0, new ModelResourceLocation(Paths.ModAssets + "storageJar", "inventory"));

        LogHelper.info(Paths.ModAssets + ItemModFishFood.FishType.MACKEREL.getUnlocalizedName());
        //}
        registerItemRender(knife);
        registerItemRender(meat);
        registerItemRender(meatCleaver);
        registerItemRender(pieceOfCake);
        registerItemRender(storageJar);
        registerItemRender(toolHandle);
        registerItemRender(ModBlocks.cheese);
        LogHelper.info("RegisterRenders have been runned");
    }

    public static void registerItemRender(Item item) {
        Utils.getMesher().register(item, 0, new ModelResourceLocation(item.getUnlocalizedName().replace("item.", ""), "inventory"));
    }

    public static void registerItemRender(Block block) {
        Utils.getMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getUnlocalizedName().replace("tile.", ""), "inventory"));
    }

    public static void registerItemVariant(Item item) {
        ModelBakery.addVariantName(item, new String[]{});
        //ModelBakery.addVariantName(item, item.getUnlocalizedName().replace("item.", ""));
    }
}