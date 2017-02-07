package com.girafi.culinarycultivation.init;

import com.girafi.culinarycultivation.CulinaryCultivation;
import com.girafi.culinarycultivation.block.*;
import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;

import static com.girafi.culinarycultivation.item.ItemCropProduct.ProductType;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {

    public static final BlockDoubleCrop BLACK_PEPPER = new BlockDoubleCrop();
    public static final BlockDoubleCrop CORN = new BlockDoubleCrop();
    public static final BlockCrop CUCUMBER = new BlockCrop();
    public static final BlockCrop TOMATO = new BlockCrop();

    public static final Block CHEESE = new BlockCheese();
    public static final Block CAULDRON = new BlockModCauldron();
    public static final Block FAN_HOUSING = new BlockFanHousing();
    public static final Block SEPARATOR = new BlockSeparator();

    public static void init() {
        //Crops
        registerCrop(BLACK_PEPPER, "black_pepper");
        registerCrop(CORN, "corn");
        registerCrop(CUCUMBER, "cucumber");
        registerCrop(TOMATO, "tomato");

        registerBlock(CAULDRON, "cauldron", null);
        registerBlock(FAN_HOUSING, "fan_housing");
        registerBlock(SEPARATOR, "separator");
        registerBlock(CHEESE, "cheese");
    }

    public static void setup() {
        BLACK_PEPPER.setSeed(ProductType.BLACK_PEPPER_DRUPE, 1, 5).setRightClickHarvest();
        CORN.setCrop(ProductType.CORN, 2, 4);
        CUCUMBER.setCrop(ProductType.CUCUMBER, 0, 4);
        TOMATO.setCrop(ProductType.TOMATO, 1, 4).setRightClickHarvest();
    }

    private static Block registerCrop(Block block, String name) {
        return registerBlock(block, name, null);
    }

    private static Block registerBlock(Block block, String name) {
        return registerBlock(block, name, CulinaryCultivation.TAB);
    }

    private static Block registerBlock(Block block, String name, @Nullable CreativeTabs tab) {
        block.setUnlocalizedName(new ResourceLocation(Reference.MOD_ID, name).toString());
        if (tab != null) {
            block.setCreativeTab(tab);
        }

        GameRegistry.register(block, new ResourceLocation(Reference.MOD_ID, name));
        GameRegistry.register(new ItemBlock(block), block.getRegistryName());
        registerBlockVariant(block, name);

        return block;
    }

    private static void registerBlockVariant(Block block, String name) {
        CulinaryCultivation.proxy.registerItemVariantModel(Item.getItemFromBlock(block), name);
    }
}