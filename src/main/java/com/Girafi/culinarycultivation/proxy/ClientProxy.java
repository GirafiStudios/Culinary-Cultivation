package com.Girafi.culinarycultivation.proxy;

import com.Girafi.culinarycultivation.init.ModBlocks;
import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.ItemStorageJar;
import com.Girafi.culinarycultivation.item.equipment.armor.farmer.ItemFarmerArmor;
import com.Girafi.culinarycultivation.item.equipment.tool.ItemDebugItem;
import com.Girafi.culinarycultivation.modSupport.ModSupport;
import com.Girafi.culinarycultivation.reference.Paths;
import com.Girafi.culinarycultivation.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import org.apache.commons.lang3.text.WordUtils;

import static com.Girafi.culinarycultivation.init.ModItems.debugItem;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        ModSupport.instance().clientSide();
        OBJLoader.INSTANCE.addDomain(Reference.MOD_ID);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.fanHousing), 0, new ModelResourceLocation(Reference.MOD_ID.toLowerCase() + ":" + "fanHousing", "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.separator), 0, new ModelResourceLocation(Reference.MOD_ID.toLowerCase() + ":" + "separator", "inventory"));
        for (int i = 0; i <= ItemDebugItem.getModeName(i).length() + 1; i++) {
            ModelLoader.setCustomModelResourceLocation(debugItem, i, new ModelResourceLocation(Paths.ModAssets + "debugItem" + WordUtils.capitalize(ItemDebugItem.getModeName(i)), "inventory"));
        }
    }
    @Override
    public void postInit() {
        Minecraft mc = Minecraft.getMinecraft();
        mc.getItemColors().registerItemColorHandler(new IItemColor() {
            @Override
            public int getColorFromItemstack(ItemStack stack, int tintIndex) {
                return tintIndex > 0 ? -1 : ((ItemFarmerArmor) stack.getItem()).getColor(stack);
            }

        }, ModItems.farmerShirt, ModItems.farmerOveralls, ModItems.farmerBoots);

        mc.getItemColors().registerItemColorHandler(new IItemColor() {
            @Override
            public int getColorFromItemstack(ItemStack stack, int tintIndex) {
                ItemStorageJar.StorageJarType storageJarType = ItemStorageJar.StorageJarType.getStorageJarType(stack);
                return tintIndex > 0 ? 16777215 :  storageJarType.getMetaData() != 0 ? storageJarType.getColorNumber() : -1;
            }
        }, ModItems.storageJar);
    }

    @Override
    public void registerItemVariantModel(Item item, final String name) {
        if (item != null) {
            ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
                @Override
                public ModelResourceLocation getModelLocation(ItemStack stack) {
                    return new ModelResourceLocation(Paths.ModAssets + name, "inventory");
                }
            });
        }
    }
}