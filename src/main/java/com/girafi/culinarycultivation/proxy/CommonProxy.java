package com.girafi.culinarycultivation.proxy;

import com.girafi.culinarycultivation.api.annotations.RegisterEvent;
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

    private static void registerEvents(@Nonnull ASMDataTable asmDataTable) {
        String annotationClassName = RegisterEvent.class.getCanonicalName();
        Set<ASMDataTable.ASMData> asmDataSet = new HashSet<>(asmDataTable.getAll(annotationClassName));
        for (ASMDataTable.ASMData asmData : asmDataSet) {
            try {
                Class clazz = Class.forName(asmData.getClassName());
                if (RegisterEvent.IRegisterEvent.class.isAssignableFrom(clazz)) {
                    RegisterEvent.IRegisterEvent eventActive = RegisterEvent.IRegisterEvent.class.newInstance();
                    if (eventActive.isActive()) { //TODO isActive check is not working
                        MinecraftForge.EVENT_BUS.register(clazz.newInstance());
                        System.out.println("isActive");
                    }
                } else {
                    MinecraftForge.EVENT_BUS.register(clazz.newInstance());
                }
            } catch (Exception ignored) {
            }
        }
    }
}