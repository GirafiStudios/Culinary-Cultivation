package com.girafi.culinarycultivation.proxy;

import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.item.ItemStorageJar;
import com.girafi.culinarycultivation.item.equipment.armor.farmer.ItemFarmerArmor;
import com.girafi.culinarycultivation.item.equipment.tool.ItemDebugItem;
import com.girafi.culinarycultivation.modsupport.ModSupport;
import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        ModSupport.INSTANCE.clientSide();
        for (int i = 0; i <= ItemDebugItem.getModeName(i).length() + 1; i++) {
            ModelLoader.setCustomModelResourceLocation(ModItems.DEBUG_ITEM, i, new ModelResourceLocation(ModItems.DEBUG_ITEM.getRegistryName() + "_" + ItemDebugItem.getModeName(i), "inventory"));
        }
    }

    @Override
    public void postInit() {
        Minecraft mc = Minecraft.getMinecraft();
        mc.getItemColors().registerItemColorHandler((stack, tintIndex) -> tintIndex > 0 ? -1 : ((ItemFarmerArmor) stack.getItem()).getColor(stack), ModItems.FARMER_SHIRT, ModItems.FARMER_OVERALLS, ModItems.FARMER_BOOTS);

        mc.getItemColors().registerItemColorHandler((stack, tintIndex) -> {
            ItemStorageJar.StorageJarType storageJarType = ItemStorageJar.StorageJarType.getStorageJarType(stack);
            return tintIndex > 0 ? 16777215 : storageJarType.getMetaData() != 0 ? storageJarType.getColorNumber() : -1;
        }, ModItems.STORAGE_JAR);
    }

    @Override
    public void registerItemVariantModel(Item item, final String name) {
        if (item != null) {
            ModelLoader.setCustomMeshDefinition(item, stack -> new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, name), "inventory"));
        }
    }
}