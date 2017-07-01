package com.girafi.culinarycultivation.proxy;

import com.girafi.culinarycultivation.api.annotations.IRegisterEvent;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.discovery.ASMDataTable;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public abstract class CommonProxy {

    public void preInit() {
    }

    public void postInit() {
    }

    public void registerItemVariantModel(Item item, final String name) {
    }

    public void registerAnnotations(@Nonnull ASMDataTable data) {
        registerEvents(data);
    }

    private static void registerEvents(@Nonnull ASMDataTable asmDataTable) {
        String className = EventBusSubscriber.class.getCanonicalName();
        Set<ASMDataTable.ASMData> asmDataSet = new HashSet<>(asmDataTable.getAll(className));
        for (ASMDataTable.ASMData asmData : asmDataSet) {
            try {
                Class clazz = Class.forName(asmData.getClassName());
                if (IRegisterEvent.class.isAssignableFrom(clazz)) {
                    IRegisterEvent eventActive = (IRegisterEvent) clazz.newInstance();
                    if (eventActive.isActive()) {
                        MinecraftForge.EVENT_BUS.register(clazz.newInstance());
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }
}