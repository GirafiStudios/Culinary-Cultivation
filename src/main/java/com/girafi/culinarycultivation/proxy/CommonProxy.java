package com.girafi.culinarycultivation.proxy;

import com.girafi.culinarycultivation.api.annotations.EventRegister;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
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

    private static void registerEvents(@Nonnull ASMDataTable dataTable) {
        String className = EventRegister.class.getCanonicalName();
        Set<ASMDataTable.ASMData> dataSet = new HashSet<>(dataTable.getAll(className));
        for (ASMDataTable.ASMData data : dataSet) {
            try {
                Class clazz = Class.forName(data.getClassName());
                if (EventRegister.IOptionalEvent.class.isAssignableFrom(clazz)) {
                    EventRegister.IOptionalEvent eventActive = (EventRegister.IOptionalEvent) clazz.newInstance();
                    if (eventActive.isActive()) {
                        MinecraftForge.EVENT_BUS.register(clazz.newInstance());
                    }
                } else {
                    MinecraftForge.EVENT_BUS.register(clazz.newInstance());
                }
            } catch (Exception ignored) {
            }
        }
    }
}