package com.girafi.culinarycultivation.api.annotations;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
/**
 * Any class with this annotation, will be subscribed to {@link MinecraftForge.EVENT_BUS} at mod construction time.
 * Similar to {@link EventBusSubscriber}, but can't be static.
 */
public @interface EventRegister {

    interface IOptionalEvent {
        /**
         * Works as a helper interface for {@link EventRegister}, to determine whether it should be registered or not.
         * Useful for config options.
         */
        default boolean isActive() {
            return true;
        }
    }
}