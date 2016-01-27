package com.Girafi.culinarycultivation.proxy;

import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.item.*;
import com.Girafi.culinarycultivation.modSupport.ModSupport;
import com.Girafi.culinarycultivation.reference.Paths;
import com.Girafi.culinarycultivation.reference.Reference;
import com.Girafi.culinarycultivation.utility.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.Girafi.culinarycultivation.init.ModItems.*;

public class ClientProxy extends CommonProxy {

    @Override
    @SideOnly(Side.CLIENT)
    public void preInit() {
        ModSupport.instance().clientSide();
        OBJLoader.instance.addDomain(Reference.MOD_ID);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.fanHousing), 0, new ModelResourceLocation(Reference.MOD_ID.toLowerCase() + ":" + "fanHousing", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.separator), 0, new ModelResourceLocation(Reference.MOD_ID.toLowerCase() + ":" + "separator", "inventory"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerRenders() {
        registerItemRender(beetroot);
        registerItemRender(beetrootSeeds);
        registerItemRender(beetrootSoup);
        registerItemRender(cakeKnife);
        registerItemRender(calfBelly);
        registerItemRender(caneKnife);
        registerItemRender(cheeseSlice);
        registerItemRender(farmerBoots);
        registerItemRender(farmerOveralls);
        registerItemRender(farmerShirt);
        registerItemRender(farmerStrawhat);
        registerItemRender(kitchenKnife);
        registerItemRender(meatCleaver);
        registerItemRender(pieceOfCake);
        registerItemRender(toolHandle);
        registerItemRender(wooden_hoeLarge);
        registerItemRender(stone_hoeLarge);
        registerItemRender(iron_hoeLarge);
        registerItemRender(golden_hoeLarge);
        registerItemRender(diamond_hoeLarge);
        registerItemRender(ModBlocks.cauldron);
        registerItemRenderIgnoreMeta(ModBlocks.beetroots);
        registerItemRenderIgnoreMeta(ModBlocks.blackPepper);
        registerItemRenderIgnoreMeta(ModBlocks.cheese);
        registerItemRenderIgnoreMeta(ModBlocks.cucumber);
        registerItemRenderIgnoreMeta(ModBlocks.tomato);

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
        ItemCropFood.CropType[] aCropType = ItemCropFood.CropType.values();
        int iCropType = aCropType.length;
        for (int j = 0; j < iCropType; ++j) {
            ItemCropFood.CropType cropType = aCropType[j];
            addVariantName(cropFood, cropType.getUnlocalizedName());
            Utils.getMesher().register(cropFood, cropType.getMetadata(), new ModelResourceLocation(Paths.ModAssets + cropType.getUnlocalizedName(), "inventory"));
        }
        ItemCropSeeds.SeedType[] aSeedType = ItemCropSeeds.SeedType.values();
        int iSeedType = aSeedType.length;
        for (int j = 0; j < iSeedType; ++j) {
            ItemCropSeeds.SeedType seedType = aSeedType[j];
            addVariantName(cropSeeds, seedType.getUnlocalizedName());
            Utils.getMesher().register(cropSeeds, seedType.getMetadata(), new ModelResourceLocation(Paths.ModAssets + seedType.getUnlocalizedName(), "inventory"));
        }
    }

    public static void registerItemRender(Item item) {
        Utils.getMesher().register(item, 0, new ModelResourceLocation(GameData.getItemRegistry().getNameForObject(item), "inventory"));
    }

    public static void registerItemRender(Block block) {
        Utils.getMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(GameData.getItemRegistry().getNameForObject(Item.getItemFromBlock(block)), "inventory"));
    }

    public static void registerItemRenderIgnoreMeta(final Item item) {
        Utils.getMesher().register(item, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(GameData.getItemRegistry().getNameForObject(item), "inventory");
            }
        });
    }

    public static void registerItemRenderIgnoreMeta(final Block block) {
        Utils.getMesher().register(Item.getItemFromBlock(block), new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(GameData.getItemRegistry().getNameForObject(Item.getItemFromBlock(block)), "inventory");
            }
        });
    }

    public static void addVariantName(Item item, String name) {
        ModelBakery.registerItemVariants(item, new ResourceLocation(Paths.ModAssets + name));
    }
}