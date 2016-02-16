package com.Girafi.culinarycultivation.proxy;

import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.modSupport.ModSupport;
import com.Girafi.culinarycultivation.reference.Paths;
import com.Girafi.culinarycultivation.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;

import static com.Girafi.culinarycultivation.init.ModItems.debugItem;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        ModSupport.instance().clientSide();
        OBJLoader.instance.addDomain(Reference.MOD_ID);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.fanHousing), 0, new ModelResourceLocation(Reference.MOD_ID.toLowerCase() + ":" + "fanHousing", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.separator), 0, new ModelResourceLocation(Reference.MOD_ID.toLowerCase() + ":" + "separator", "inventory"));
    }

    @Override
    public void registerRenders() {
        addVariant(debugItem, "debugDefault", 0);
        addVariant(debugItem, "debugHunger", 0);
        addVariant(debugItem, "debugHungerPlus", 0);
        addVariant(debugItem, "debugFertilizer", 0);
        addVariant(debugItem, "debugHoe", 0);
        getMesher().register(debugItem, 0, new ModelResourceLocation(Paths.ModAssets + "debugDefault", "inventory"));
        getMesher().register(debugItem, 1, new ModelResourceLocation(Paths.ModAssets + "debugHunger", "inventory"));
        getMesher().register(debugItem, 2, new ModelResourceLocation(Paths.ModAssets + "debugHungerPlus", "inventory"));
        getMesher().register(debugItem, 3, new ModelResourceLocation(Paths.ModAssets + "debugFertilizer", "inventory"));
        getMesher().register(debugItem, 4, new ModelResourceLocation(Paths.ModAssets + "debugHoe", "inventory"));
    }

    @Override
    public void registerItemVariantModel(Item item, final String name) {
        if (item != null) {
            ModelBakery.registerItemVariants(item, new ResourceLocation(Paths.ModAssets + name));
            ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
                @Override
                public ModelResourceLocation getModelLocation(ItemStack stack) {
                    return new ModelResourceLocation(Paths.ModAssets + name, "inventory");
                }
            });
        }
    }

    public static void addVariant(Item item, String name, int damageValue) {
        if (item != null) {
            ModelBakery.registerItemVariants(item, new ResourceLocation(Paths.ModAssets + name));
            getMesher().register(item, damageValue, new ModelResourceLocation(Paths.ModAssets + name, "inventory"));
        }
    }

    protected static ItemModelMesher getMesher() {
        return Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
    }
}