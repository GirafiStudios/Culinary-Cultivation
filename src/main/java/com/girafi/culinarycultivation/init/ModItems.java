package com.girafi.culinarycultivation.init;

import com.girafi.culinarycultivation.CulinaryCultivation;
import com.girafi.culinarycultivation.api.item.IOreDictEntry;
import com.girafi.culinarycultivation.event.MobDropEvent;
import com.girafi.culinarycultivation.item.ItemCropProduct;
import com.girafi.culinarycultivation.item.ItemModFishFood;
import com.girafi.culinarycultivation.item.ItemModMeatFood;
import com.girafi.culinarycultivation.item.equipment.armor.farmer.ItemFarmerBoots;
import com.girafi.culinarycultivation.item.equipment.armor.farmer.ItemFarmerOveralls;
import com.girafi.culinarycultivation.item.equipment.armor.farmer.ItemFarmerShirt;
import com.girafi.culinarycultivation.item.equipment.armor.farmer.ItemFarmerStrawhat;
import com.girafi.culinarycultivation.item.equipment.tool.*;
import com.girafi.culinarycultivation.util.OreDictHelper;
import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;

import static com.girafi.culinarycultivation.item.ItemCropProduct.Type;

@ObjectHolder(Reference.MOD_ID)
public class ModItems {
    /* Tools */
    public static final Item CANE_KNIFE = new ItemCaneKnife();
    public static final Item DEBUG_ITEM = new ItemDebugItem();
    public static final Item FARMER_BOOTS = new ItemFarmerBoots();
    public static final Item FARMER_OVERALLS = new ItemFarmerOveralls();
    public static final Item FARMER_SHIRT = new ItemFarmerShirt();
    public static final Item FARMER_STRAWHAT = new ItemFarmerStrawhat();
    public static final Item HOE_LARGE_DIAMOND = new ItemLargeHoe(Item.ToolMaterial.DIAMOND);
    public static final Item HOE_LARGE_GOLDEN = new ItemLargeHoe(Item.ToolMaterial.GOLD);
    public static final Item HOE_LARGE_IRON = new ItemLargeHoe(Item.ToolMaterial.IRON);
    public static final Item HOE_LARGE_STONE = new ItemLargeHoe(Item.ToolMaterial.STONE);
    public static final Item HOE_LARGE_WOODEN = new ItemLargeHoe(Item.ToolMaterial.WOOD);
    public static final Item KITCHEN_KNIFE = new ItemKitchenKnife();
    public static final Item MEAT_CLEAVER = new ItemMeatCleaver();
    public static final Item SEED_BAG = new ItemSeedBag();
    public static final Item STORAGE_JAR = new ItemStorageJar();
    public static final Item TOOL_HANDLE = new Item();

    /* Food */
    public static final Item CHEESE_SLICE = new ItemFood(2, 0.4F, false);
    public static final Item COOKED_FISH = new ItemModFishFood(true);
    public static final Item COOKED_MEAT = new ItemModMeatFood(true);
    public static final Item FISH = new ItemModFishFood(false);
    public static final Item CROP_COOKED = new ItemCropProduct(Type.CROP_COOKED);
    public static final Item CROP_FOOD = new ItemCropProduct(Type.CROP);
    public static final Item CROP_SEEDS = new ItemCropProduct(Type.SEED);
    public static final Item MEAT = new ItemModMeatFood(false);
    public static final Item CALF_BELLY = new Item().setMaxStackSize(1);

    /* Misc */
    public static final Item CHAFF_PILE = new Item();

    public static void register() {
        registerItem(CHEESE_SLICE, "cheese_slice");
        registerItem(CALF_BELLY, "calf_belly");
        registerItem(MEAT, "meat");
        registerItem(COOKED_MEAT, "cooked_meat");
        registerItem(FISH, "fish");
        registerItem(COOKED_FISH, "cooked_fish");
        registerItem(CROP_FOOD, "crop_food");
        registerItem(CROP_COOKED, "crop_cooked");
        registerItem(CROP_SEEDS, "crop_seeds");
        registerItem(CHAFF_PILE, "chaff_pile");
        registerItem(STORAGE_JAR, "storage_jar");
        registerItem(TOOL_HANDLE, "tool_handle");
        registerItem(KITCHEN_KNIFE, "kitchen_knife");
        registerItem(MEAT_CLEAVER, "meat_cleaver");
        registerItem(CANE_KNIFE, "cane_knife");
        registerItem(HOE_LARGE_WOODEN, "wooden_hoe_large");
        registerItem(HOE_LARGE_STONE, "stone_hoe_large");
        registerItem(HOE_LARGE_IRON, "iron_hoe_large");
        registerItem(HOE_LARGE_GOLDEN, "golden_hoe_large");
        registerItem(HOE_LARGE_DIAMOND, "diamond_hoe_large");
        registerItem(SEED_BAG, "seed_bag");
        registerItem(DEBUG_ITEM, "debug_item");
        registerItem(FARMER_STRAWHAT, "farmer_strawhat");
        registerItem(FARMER_SHIRT, "farmer_shirt");
        registerItem(FARMER_OVERALLS, "farmer_overalls");
        registerItem(FARMER_BOOTS, "farmer_boots");
    }

    public static void setup() {
        MobDropEvent.register(EntityChicken.class, new ItemStack(ModItems.MEAT, 1, ItemModMeatFood.MeatType.CHICKEN_WING.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, ItemModMeatFood.MeatType.CHICKEN_WING.getMetadata()), 0, 2);
        MobDropEvent.registerChild(EntityCow.class, new ItemStack(ModItems.MEAT, 1, ItemModMeatFood.MeatType.VEAL.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, ItemModMeatFood.MeatType.VEAL.getMetadata()), 0, 3);
        MobDropEvent.register(EntityCow.class, new ItemStack(ModItems.MEAT, 1, ItemModMeatFood.MeatType.RIBS_BEEF.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, ItemModMeatFood.MeatType.RIBS.getMetadata()), 0, 3);
        MobDropEvent.register(EntityCow.class, new ItemStack(ModItems.MEAT, 1, ItemModMeatFood.MeatType.ROAST.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, ItemModMeatFood.MeatType.ROAST.getMetadata()), 0, 1);
        MobDropEvent.register(EntityCow.class, true, new ItemStack(ModItems.CALF_BELLY), ItemStack.EMPTY, false, ModItems.MEAT_CLEAVER, -1, 0, 1);
        MobDropEvent.register(EntityPig.class, new ItemStack(ModItems.MEAT, 1, ItemModMeatFood.MeatType.HAM.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, ItemModMeatFood.MeatType.HAM.getMetadata()), 0, 1);
        MobDropEvent.register(EntityPig.class, new ItemStack(ModItems.MEAT, 1, ItemModMeatFood.MeatType.RIBS.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, ItemModMeatFood.MeatType.RIBS.getMetadata()), 1, 2);
        MobDropEvent.registerChild(EntitySheep.class, new ItemStack(ModItems.MEAT, 1, ItemModMeatFood.MeatType.LAMB.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, ItemModMeatFood.MeatType.LAMB.getMetadata()), 1, 2);
        MobDropEvent.register(EntitySheep.class, new ItemStack(ModItems.MEAT, 1, ItemModMeatFood.MeatType.LEG_SHEEP.getMetadata()), new ItemStack(ModItems.COOKED_MEAT, 1, ItemModMeatFood.MeatType.LEG_SHEEP.getMetadata()), 0, 2);
        MobDropEvent.register(EntitySquid.class, false, new ItemStack(ModItems.MEAT, 1, ItemModMeatFood.MeatType.SQUID_MANTLE.getMetadata()), ItemStack.EMPTY, false, ModItems.MEAT_CLEAVER, 25, 1, 2);
        MobDropEvent.register(EntitySquid.class, false, new ItemStack(ModItems.MEAT, 1, ItemModMeatFood.MeatType.SQUID_TENTACLE.getMetadata()), ItemStack.EMPTY, false, ModItems.MEAT_CLEAVER, 25, 0, 1);
    }

    private static Item registerItem(Item item, String name) {
        return registerItem(item, name, CulinaryCultivation.TAB);
    }

    private static Item registerItem(Item item, String name, CreativeTabs tab) {
        ResourceLocation resourceLocation = new ResourceLocation(Reference.MOD_ID, name);
        item.setUnlocalizedName(resourceLocation.toString());
        item.setRegistryName(resourceLocation);
        item.setCreativeTab(tab);
        ForgeRegistries.ITEMS.register(item);

        if (item instanceof IOreDictEntry) {
            IOreDictEntry entry = (IOreDictEntry) item;
            OreDictHelper.entries.add(entry);
        }

        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            if (item.getHasSubtypes()) {
                NonNullList<ItemStack> subItems = NonNullList.create();
                item.getSubItems(tab, subItems);
                for (ItemStack stack : subItems) {
                    String subItemName = item.getUnlocalizedName(stack).replace("item.culinarycultivation:", "");

                    ModelLoader.setCustomModelResourceLocation(item, stack.getItemDamage(), new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, subItemName), "inventory"));
                }
            } else {
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, name), "inventory"));
            }
        }
        return item;
    }
}