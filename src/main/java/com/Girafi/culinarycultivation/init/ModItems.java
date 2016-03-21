package com.Girafi.culinarycultivation.init;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import com.Girafi.culinarycultivation.item.*;
import com.Girafi.culinarycultivation.item.equipment.armor.farmer.ItemFarmerBoots;
import com.Girafi.culinarycultivation.item.equipment.armor.farmer.ItemFarmerOveralls;
import com.Girafi.culinarycultivation.item.equipment.armor.farmer.ItemFarmerShirt;
import com.Girafi.culinarycultivation.item.equipment.armor.farmer.ItemFarmerStrawhat;
import com.Girafi.culinarycultivation.item.equipment.tool.*;
import com.Girafi.culinarycultivation.reference.Paths;
import com.Girafi.culinarycultivation.reference.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.List;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {
    public static final Item cakeKnife = new ItemCakeKnife(Item.ToolMaterial.IRON);
    public static final Item caneKnife = new ItemCaneKnife(Item.ToolMaterial.STONE);
    public static final Item debugItem = new ItemDebugItem();
    public static final Item farmerBoots = new ItemFarmerBoots();
    public static final Item farmerOveralls = new ItemFarmerOveralls();
    public static final Item farmerShirt = new ItemFarmerShirt();
    public static final Item farmerStrawhat = new ItemFarmerStrawhat();
    public static final Item kitchenKnife = new ItemKitchenKnife();
    public static final Item meatCleaver = new ItemMeatCleaver();
    public static final Item storageJar = new ItemStorageJar();
    public static final Item toolHandle = new Item();
    public static final Item wooden_hoeLarge = new ItemLargeHoe(Item.ToolMaterial.WOOD);
    public static final Item stone_hoeLarge = new ItemLargeHoe(Item.ToolMaterial.STONE);
    public static final Item iron_hoeLarge = new ItemLargeHoe(Item.ToolMaterial.IRON);
    public static final Item golden_hoeLarge = new ItemLargeHoe(Item.ToolMaterial.GOLD);
    public static final Item diamond_hoeLarge = new ItemLargeHoe(Item.ToolMaterial.DIAMOND);

    /* Food */
    public static final Item cheeseSlice = new ItemFood(2, 0.4F, false);
    public static final Item cooked_fish = new ItemModFishFood(true).setHasSubtypes(true);
    public static final Item cooked_meat = new ItemModMeatFood(true).setHasSubtypes(true);
    public static final Item fish = new ItemModFishFood(false).setHasSubtypes(true);
    public static final Item cropFood = new ItemCropFood().setHasSubtypes(true);
    public static final Item cropSeeds = new ItemCropSeeds().setHasSubtypes(true);
    public static final Item meat = new ItemModMeatFood(false).setHasSubtypes(true);
    public static final Item pieceOfCake = new ItemFood(2, 0.1F, false);
    public static final Item calfBelly = new Item().setMaxStackSize(1);

    //public static final Item chickenWingHot = new SourceFood(5, 0.8F, true).setPotionEffect(Potion.fireResistance.id, 15, 0, 0.25F).setUnlocalizedName("chickenWingHot");
    //public static final Item sausage = new SourceFood(0, 0.0F, true).setUnlocalizedName("sausage"));

    public static void init() {
        registerItem(cheeseSlice, "cheeseSlice");
        registerItem(pieceOfCake, "pieceOfCake");
        registerItem(calfBelly, "calfBelly");
        registerItem(meat, "meat");
        registerItem(cooked_meat, "cooked_meat");
        registerItem(fish, "fish");
        registerItem(cooked_fish, "cooked_fish");
        registerItem(cropFood, "cropFood");
        registerItem(cropSeeds, "cropSeeds");
        registerItem(storageJar, "storageJar");
        registerItem(toolHandle, "toolHandle");
        registerItem(kitchenKnife, "kitchenKnife");
        registerItem(cakeKnife, "cakeKnife");
        registerItem(meatCleaver, "meatCleaver");
        registerItem(caneKnife, "caneKnife");
        registerItem(wooden_hoeLarge, "wooden_hoeLarge");
        registerItem(stone_hoeLarge, "stone_hoeLarge");
        registerItem(iron_hoeLarge, "iron_hoeLarge");
        registerItem(golden_hoeLarge, "golden_hoeLarge");
        registerItem(diamond_hoeLarge, "diamond_hoeLarge");
        registerItem(debugItem, "debugItem");
        registerItem(farmerStrawhat, "farmerStrawhat");
        registerItem(farmerShirt, "farmerShirt");
        registerItem(farmerOveralls, "farmerOveralls");
        registerItem(farmerBoots, "farmerBoots");
        Events.register(new ItemDebugItem());
    }

    public static void setup() { //TODO
        /*FishingHooks.addFish(new WeightedRandomFishable(new ItemStack(ModItems.fish, 1, ItemModFishFood.FishType.MACKEREL.getMetaData()), 48));
        FishingHooks.addFish(new WeightedRandomFishable(new ItemStack(ModItems.fish, 1, ItemModFishFood.FishType.TUNA.getMetaData()), 40));
        FishingHooks.addFish(new WeightedRandomFishable(new ItemStack(ModItems.fish, 1, ItemModFishFood.FishType.TROUT.getMetaData()), 25));
        FishingHooks.addFish(new WeightedRandomFishable(new ItemStack(ModItems.fish, 1, ItemModFishFood.FishType.HERRING.getMetaData()), 30));
        FishingHooks.addFish(new WeightedRandomFishable(new ItemStack(ModItems.fish, 1, ItemModFishFood.FishType.PLAICE.getMetaData()), 18));
        FishingHooks.addFish(new WeightedRandomFishable(new ItemStack(ModItems.fish, 1, ItemModFishFood.FishType.SMALLSQUID.getMetaData()), 11));
        FishingHooks.addJunk(new WeightedRandomFishable(new ItemStack(ModItems.meatCleaver, 1), 3).setMaxDamagePercent(0.25F));*/
    }

    private static Item registerItem(Item item, String name) {
        return registerItem(item, name, CreativeTab.CulinaryCultivation_Tab);
    }

    private static Item registerItem(Item item, String name, CreativeTabs tab) {
        item.setUnlocalizedName(Paths.ModAssets + name);
        item.setCreativeTab(tab);

        GameRegistry.registerItem(item, name);

        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            if (item.getHasSubtypes()) {
                List<ItemStack> subItems = new ArrayList<ItemStack>();
                item.getSubItems(item, tab, subItems);
                for (ItemStack stack : subItems) {
                    String subItemName = item.getUnlocalizedName(stack).replace("item.culinarycultivation:", "");

                    ModelLoader.setCustomModelResourceLocation(item, stack.getItemDamage(), new ModelResourceLocation(Paths.ModAssets + subItemName, "inventory"));
                }
            } else {
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Paths.ModAssets + name, "inventory"));
            }
        }
        return item;
    }
}