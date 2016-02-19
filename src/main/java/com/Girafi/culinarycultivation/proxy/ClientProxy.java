package com.Girafi.culinarycultivation.proxy;

import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.item.equipment.tool.ItemDebugItem;
import com.Girafi.culinarycultivation.modSupport.ModSupport;
import com.Girafi.culinarycultivation.reference.Paths;
import com.Girafi.culinarycultivation.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import org.apache.commons.lang3.text.WordUtils;

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
        for (int i = 0; i <= ItemDebugItem.getModeName(i).length() + 1; i++) {
            String name = "debugItem" + WordUtils.capitalize(ItemDebugItem.getModeName(i));
            ModelBakery.registerItemVariants(debugItem, new ResourceLocation(Paths.ModAssets + name));
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(debugItem, i, new ModelResourceLocation(Paths.ModAssets + name, "inventory"));
        }
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
}