package com.Girafi.culinarycultivation.proxy;

import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.item.ItemModFishFood;
import com.Girafi.culinarycultivation.item.ItemModMeatFood;
import com.Girafi.culinarycultivation.item.ItemStorageJar;
import com.Girafi.culinarycultivation.reference.Paths;
import com.Girafi.culinarycultivation.utility.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.Girafi.culinarycultivation.init.ModItems.*;

public class ClientProxy extends CommonProxy {

    @Override
    @SideOnly(Side.CLIENT)
    public void registerRenders() {
        registerItemRender(farmerStrawhat);
        registerItemRender(farmerOveralls);
        registerItemRender(farmerBoots);
        registerItemRender(beetroot);
        registerItemRender(beetrootSeeds);
        registerItemRender(beetrootSoup);
        registerItemRender(blackPepperDrupe);
        registerItemRender(cakeKnife);
        registerItemRender(calfBelly);
        registerItemRender(cheeseSlice);
        registerItemRender(knife);
        registerItemRender(meatCleaver);
        registerItemRender(pieceOfCake);
        registerItemRender(toolHandle);
        registerItemRender(ModBlocks.cauldron);
        registerItemRenderIgnoreMeta(ModBlocks.beetroots);
        registerItemRenderIgnoreMeta(ModBlocks.blackPepper);
        registerItemRenderIgnoreMeta(ModBlocks.cheese);

        addVariantName(debugItem, "debugDefault");
        addVariantName(debugItem, "debugHunger");
        addVariantName(debugItem, "debugHungerPlus");
        addVariantName(debugItem, "debugFertilizer");
        addVariantName(debugItem, "debugHoe");
        Utils.getMesher().register(debugItem, 0, new ModelResourceLocation(Paths.ModAssets + "debugDefault", "inventory"));
        Utils.getMesher().register(debugItem, 1, new ModelResourceLocation(Paths.ModAssets + "debugHunger", "inventory"));
        Utils.getMesher().register(debugItem, 2, new ModelResourceLocation(Paths.ModAssets + "debugHungerPlus", "inventory"));
        Utils.getMesher().register(debugItem, 3, new ModelResourceLocation(Paths.ModAssets + "debugFertilizer", "inventory"));
        Utils.getMesher().register(debugItem, 4, new ModelResourceLocation(Paths.ModAssets + "debugHoe", "inventory"));

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
            addVariantName(storageJar, "storageJar");
            addVariantName(storageJar, "storageJar_empty");
            if (jarType.getMetaData() == 0) {
                Utils.getMesher().register(storageJar, jarType.getMetaData(), new ModelResourceLocation(Paths.ModAssets + "storageJar_empty", "inventory"));
            } else
            Utils.getMesher().register(storageJar, jarType.getMetaData(), new ModelResourceLocation(Paths.ModAssets + "storageJar", "inventory"));
        }
    }

    public static void registerItemRender(Item item) {
        Utils.getMesher().register(item, 0, new ModelResourceLocation(GameRegistry.findUniqueIdentifierFor(item).toString(), "inventory"));
    }

    public static void registerItemRender(Block block) {
        Utils.getMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(GameRegistry.findUniqueIdentifierFor(block).toString(), "inventory"));
    }

    public static void registerItemRenderIgnoreMeta(final Item item) {
        Utils.getMesher().register(item, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(GameRegistry.findUniqueIdentifierFor(item).toString(), "inventory");
            }
        });
    }

    public static void registerItemRenderIgnoreMeta(final Block block) {
        Utils.getMesher().register(Item.getItemFromBlock(block), new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(GameRegistry.findUniqueIdentifierFor(block).toString(), "inventory");
            }
        });
    }

    public static void addVariantName(Item item, String name) {
        ModelBakery.addVariantName(item, Paths.ModAssets + name);
    }
}