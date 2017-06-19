package com.girafi.culinarycultivation;

import com.girafi.culinarycultivation.client.gui.GuiHandler;
import com.girafi.culinarycultivation.init.ModBlocks;
import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.init.recipes.Recipes;
import com.girafi.culinarycultivation.modsupport.ModSupport;
import com.girafi.culinarycultivation.network.NetworkHandler;
import com.girafi.culinarycultivation.proxy.CommonProxy;
import com.girafi.culinarycultivation.util.ConfigurationHandler;
import com.girafi.culinarycultivation.util.OreDictHelper;
import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.io.File;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = Reference.DEPENDENCIES, guiFactory = Reference.GUI_FACTORY_CLASS)
public class CulinaryCultivation {
    @Mod.Instance(Reference.MOD_ID)
    public static CulinaryCultivation instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_ClASS)
    public static CommonProxy proxy;

    public static final CreativeTabs TAB = new CreativeTabs(Reference.MOD_ID) {
        @Override
        @SideOnly(Side.CLIENT)
        @Nonnull
        public ItemStack getTabIconItem() {
            return new ItemStack(ModItems.MEAT_CLEAVER);
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigurationHandler.init(new File(event.getModConfigurationDirectory(), "CulinaryCultivation.cfg"));
        proxy.registerAnnotations(event.getAsmData());
        ModSupport.INSTANCE.initIndex();
        ModItems.register();
        ModBlocks.register();
        ModItems.setup();
        ModBlocks.setup();
        OreDictHelper.register();
        NetworkHandler.register();
        Recipes.initHandlers();
        proxy.preInit();
        ModSupport.INSTANCE.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        ModBlocks.initTiles();
        Recipes.init();
        ModSupport.INSTANCE.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
        ModSupport.INSTANCE.postInit();
    }
}