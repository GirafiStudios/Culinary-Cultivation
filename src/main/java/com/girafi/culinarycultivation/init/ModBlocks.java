package com.girafi.culinarycultivation.init;

import com.girafi.culinarycultivation.CulinaryCultivation;
import com.girafi.culinarycultivation.block.*;
import com.girafi.culinarycultivation.block.tileentity.TileEntityCauldron;
import com.girafi.culinarycultivation.block.tileentity.TileEntitySeparator;
import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import javax.annotation.Nullable;

import static com.girafi.culinarycultivation.item.ItemCropProduct.ProductType;

@ObjectHolder(Reference.MOD_ID)
public class ModBlocks {
    public static final BlockDoubleCrop BLACK_PEPPER = new BlockDoubleCrop();
    public static final BlockDoubleCrop CORN = new BlockDoubleCrop();
    public static final BlockCrop CUCUMBER = new BlockCrop();
    public static final BlockCrop TOMATO = new BlockCrop();
    public static final Block CHEESE = new BlockCheese();
    public static final Block CAULDRON = new BlockModCauldron();
    public static final Block FAN_HOUSING = new BlockFanHousing();
    public static final Block SEPARATOR = new BlockSeparator();

    public static void register() {
        //Crops
        registerCrop(BLACK_PEPPER, "black_pepper");
        registerCrop(CORN, "corn");
        registerCrop(CUCUMBER, "cucumber");
        registerCrop(TOMATO, "tomato");

        registerBlock(CAULDRON, "cauldron");
        registerBlock(FAN_HOUSING, "fan_housing");
        registerBlock(SEPARATOR, "separator");
        registerBlock(CHEESE, "cheese");
    }

    public static void initTiles() {
        GameRegistry.registerTileEntity(TileEntityCauldron.class, new ResourceLocation(Reference.MOD_ID, "cauldron"));
        GameRegistry.registerTileEntity(TileEntitySeparator.class, new ResourceLocation(Reference.MOD_ID, "separator"));
    }

    public static void setup() {
        BLACK_PEPPER.setSeed(ProductType.BLACK_PEPPER_DRUPE, 1, 5).setRightClickHarvest();
        CORN.setCrop(ProductType.CORN, 2, 4);
        CUCUMBER.setCrop(ProductType.CUCUMBER, 0, 4);
        TOMATO.setCrop(ProductType.TOMATO, 1, 4).setRightClickHarvest();
    }

    private static void registerCrop(Block block, String name) {
        registerBlock(block, null, name, null);
    }

    private static void registerBlock(Block block, ItemBlock itemBlock, String name) {
        registerBlock(block, itemBlock, name, CulinaryCultivation.TAB);
    }

    private static void registerBlock(Block block, String name) {
        registerBlock(block, name, CulinaryCultivation.TAB);
    }

    private static void registerBlock(Block block, String name, @Nullable CreativeTabs tab) {
        registerBlock(block, new ItemBlock(block), name, tab);
    }

    private static void registerBlock(Block block, ItemBlock itemBlock, String name, @Nullable CreativeTabs tab) {
        if (tab != null) {
            block.setCreativeTab(tab);
        }
        ResourceLocation resourceLocation = new ResourceLocation(Reference.MOD_ID, name);
        block.setTranslationKey(resourceLocation.toString());
        block.setRegistryName(resourceLocation);
        ForgeRegistries.BLOCKS.register(block);

        if (itemBlock != null) {
            itemBlock.setRegistryName(name);
            ForgeRegistries.ITEMS.register(itemBlock);
        }
        CulinaryCultivation.proxy.registerItemVariantModel(Item.getItemFromBlock(block), name);
    }
}