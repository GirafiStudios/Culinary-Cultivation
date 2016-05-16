package com.girafi.culinarycultivation.init;

import com.girafi.culinarycultivation.creativetab.CreativeTab;
import com.girafi.culinarycultivation.item.*;
import com.girafi.culinarycultivation.item.equipment.armor.farmer.ItemFarmerBoots;
import com.girafi.culinarycultivation.item.equipment.armor.farmer.ItemFarmerOveralls;
import com.girafi.culinarycultivation.item.equipment.armor.farmer.ItemFarmerShirt;
import com.girafi.culinarycultivation.item.equipment.armor.farmer.ItemFarmerStrawhat;
import com.girafi.culinarycultivation.item.equipment.tool.*;
import com.girafi.culinarycultivation.util.reference.Paths;
import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {
    public static final Item CAKE_KNIFE = new ItemCakeKnife(Item.ToolMaterial.IRON);
    public static final Item CANE_KNIFE = new ItemCaneKnife(Item.ToolMaterial.STONE);
    public static final Item DEBUG_ITEM = new ItemDebugItem();
    public static final Item FARMER_BOOTS = new ItemFarmerBoots();
    public static final Item FARMER_OVERALLS = new ItemFarmerOveralls();
    public static final Item FARMER_SHIRT = new ItemFarmerShirt();
    public static final Item FARMER_STRAWHAT = new ItemFarmerStrawhat();
    public static final Item KITCHEN_KNIFE = new ItemKitchenKnife();
    public static final Item MEAT_CLEAVER = new ItemMeatCleaver();
    public static final Item STORAGE_JAR = new ItemStorageJar();
    public static final Item TOOL_HANDLE = new Item();
    public static final Item WOODEN_HOE_LARGE = new ItemLargeHoe(Item.ToolMaterial.WOOD);
    public static final Item STONE_HOE_LARGE = new ItemLargeHoe(Item.ToolMaterial.STONE);
    public static final Item IRON_HOE_LARGE = new ItemLargeHoe(Item.ToolMaterial.IRON);
    public static final Item GOLDEN_HOE_LARGE = new ItemLargeHoe(Item.ToolMaterial.GOLD);
    public static final Item DIAMOND_HOE_LARGE = new ItemLargeHoe(Item.ToolMaterial.DIAMOND);

    /* Food */
    public static final Item CHEESE_SLICE = new ItemFood(2, 0.4F, false);
    public static final Item COOKED_FISH = new ItemModFishFood(true).setHasSubtypes(true);
    public static final Item COOKED_MEAT = new ItemModMeatFood(true).setHasSubtypes(true);
    public static final Item FISH = new ItemModFishFood(false).setHasSubtypes(true);
    public static final Item CROP_FOOD = new ItemCropFood().setHasSubtypes(true);
    public static final Item CROP_SEEDS = new ItemCropSeeds().setHasSubtypes(true);
    public static final Item MEAT = new ItemModMeatFood(false).setHasSubtypes(true);
    public static final Item CAKE_PIECE = new ItemFood(2, 0.1F, false);
    public static final Item CALF_BELLY = new Item().setMaxStackSize(1);

    public static final Item CHAFF_PILE = new Item();

    //public static final Item chickenWingHot = new SourceFood(5, 0.8F, true).setPotionEffect(Potion.fireResistance.id, 15, 0, 0.25F).setUnlocalizedName("chickenWingHot");
    //public static final Item sausage = new SourceFood(0, 0.0F, true).setUnlocalizedName("sausage"));

    public static void init() {
        registerItem(CHEESE_SLICE, "cheeseSlice");
        registerItem(CAKE_PIECE, "pieceOfCake");
        registerItem(CALF_BELLY, "calfBelly");
        registerItem(MEAT, "meat");
        registerItem(COOKED_MEAT, "cooked_meat");
        registerItem(FISH, "fish");
        registerItem(COOKED_FISH, "cooked_fish");
        registerItem(CROP_FOOD, "cropFood");
        registerItem(CROP_SEEDS, "cropSeeds");
        registerItem(CHAFF_PILE, "pileOfChaff");
        registerItem(STORAGE_JAR, "storageJar");
        registerItem(TOOL_HANDLE, "toolHandle");
        registerItem(KITCHEN_KNIFE, "kitchenKnife");
        registerItem(CAKE_KNIFE, "cakeKnife");
        registerItem(MEAT_CLEAVER, "meatCleaver");
        registerItem(CANE_KNIFE, "caneKnife");
        registerItem(WOODEN_HOE_LARGE, "wooden_hoeLarge");
        registerItem(STONE_HOE_LARGE, "stone_hoeLarge");
        registerItem(IRON_HOE_LARGE, "iron_hoeLarge");
        registerItem(GOLDEN_HOE_LARGE, "golden_hoeLarge");
        registerItem(DIAMOND_HOE_LARGE, "diamond_hoeLarge");
        registerItem(DEBUG_ITEM, "debugItem");
        registerItem(FARMER_STRAWHAT, "farmerStrawhat");
        registerItem(FARMER_SHIRT, "farmerShirt");
        registerItem(FARMER_OVERALLS, "farmerOveralls");
        registerItem(FARMER_BOOTS, "farmerBoots");
        Events.register(new ItemDebugItem());
    }
    
    private static Item registerItem(Item item, String name) {
        return registerItem(item, name, CreativeTab.CulinaryCultivation_Tab);
    }

    private static Item registerItem(Item item, String name, CreativeTabs tab) {
        item.setUnlocalizedName(Paths.MOD_ASSETS + name);
        item.setCreativeTab(tab);

        GameRegistry.register(item, new ResourceLocation(Reference.MOD_ID, name));

        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            if (item.getHasSubtypes()) {
                List<ItemStack> subItems = new ArrayList<ItemStack>();
                item.getSubItems(item, tab, subItems);
                for (ItemStack stack : subItems) {
                    String subItemName = item.getUnlocalizedName(stack).replace("item.culinarycultivation:", "");

                    ModelLoader.setCustomModelResourceLocation(item, stack.getItemDamage(), new ModelResourceLocation(Paths.MOD_ASSETS + subItemName, "inventory"));
                }
            } else {
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Paths.MOD_ASSETS + name, "inventory"));
            }
        }
        return item;
    }
}