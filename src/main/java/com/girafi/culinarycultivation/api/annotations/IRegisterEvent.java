package com.girafi.culinarycultivation.api.annotations;

import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public interface IRegisterEvent {
    /**
     * Works as a helper interface for {@link EventBusSubscriber}, to determine whether it should be registered or not.
     * Useful for config options.
     */
    default boolean isActive() {
        return true;
    }
}