package com.Girafi.culinarycultivation.proxy;

import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.item.ItemModFishFood;
import com.Girafi.culinarycultivation.item.ItemModMeatFood;
import com.Girafi.culinarycultivation.item.ItemStorageJar;
import com.Girafi.culinarycultivation.reference.Paths;
import com.Girafi.culinarycultivation.utility.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.Girafi.culinarycultivation.init.ModItems.*;

public class ClientProxy extends CommonProxy {

    @Override
    @SideOnly(Side.CLIENT)
    public void registerRenders() {
        registerItemRender(cakeKnife);
        registerItemRender(debugItem);
        //registerItemRender(farmerBoots);
        registerItemRender(knife);
        registerItemRender(meatCleaver);
        registerItemRender(pieceOfCake);
        registerItemRender(toolHandle);
        registerItemRender(ModBlocks.cheese);
        registerItemRender(ModBlocks.cauldron);

        ItemModFishFood.FishType[] afish = ItemModFishFood.FishType.values();
        int ifish = afish.length;
        for (int j = 0; j < ifish; ++j) {
            ItemModFishFood.FishType fishType = afish[j];
            if (fishType.isHaveRawFish()) {
                addVariantName(fish, fishType.getUnlocalizedName());
            }
            if (fishType.isHaveCookedFish()) {
                addVariantName(cooked_fish, "cooked_" + fishType.getUnlocalizedName());
            }
            Utils.getMesher().register(fish, fishType.getMetaData(), new ModelResourceLocation(Paths.ModAssets + fishType.getUnlocalizedName(), "inventory"));
            Utils.getMesher().register(cooked_fish, fishType.getMetaData(), new ModelResourceLocation(Paths.ModAssets + "cooked_" + fishType.getUnlocalizedName(), "inventory"));
        }
        ItemModMeatFood.MeatType[] ameat = ItemModMeatFood.MeatType.values();
        int imeat = ameat.length;
        for (int j = 0; j < imeat; ++j) {
            ItemModMeatFood.MeatType meatType = ameat[j];
            if (meatType.isHaveRawMeat()) {
                addVariantName(meat, meatType.getUnlocalizedName());
            }
            if (meatType.isHaveCookedMeat()) {
                addVariantName(cooked_meat, "cooked_" + meatType.getUnlocalizedName());
            }
            Utils.getMesher().register(meat, meatType.getMetaData(), new ModelResourceLocation(Paths.ModAssets + meatType.getUnlocalizedName(), "inventory"));
            Utils.getMesher().register(cooked_meat, meatType.getMetaData(), new ModelResourceLocation(Paths.ModAssets + "cooked_" + meatType.getUnlocalizedName(), "inventory"));
        }
        ItemStorageJar.StorageJarType[] ajar = ItemStorageJar.StorageJarType.values();
        int ijar = ajar.length;
        for (int j = 0; j < ijar; ++j) {
            ItemStorageJar.StorageJarType jarType = ajar[j];
            addVariantName(storageJar, "storageJar_" + jarType.getUnlocalizedName());
            Utils.getMesher().register(storageJar, jarType.getMetaData(), new ModelResourceLocation(Paths.ModAssets + "storageJar_" + jarType.getUnlocalizedName(), "inventory"));
        }
    }

    public static void registerItemRender(Item item) {
        Utils.getMesher().register(item, 0, new ModelResourceLocation(GameRegistry.findUniqueIdentifierFor(item).toString(), "inventory"));
    }

    public static void registerItemRender(Block block) {
        Utils.getMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(GameRegistry.findUniqueIdentifierFor(block).toString(), "inventory"));
    }

    public static void addVariantName(Item item, String name) {
        ModelBakery.addVariantName(item, Paths.ModAssets + name);
    }
}