package com.girafi.culinarycultivation.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
/**
 * Any class with this annotation, will be subscribed to {@link net.minecraftforge.common.MinecraftForge.EVENT_BUS} at mod construction time.
 */
public @interface RegisterEvent {
    /**
     * Works as a helper interface for {@link RegisterEvent}, to determine whether it should be registered or not.
     * Useful for config options.
     */
    interface IRegisterEvent {
        default boolean isActive() {
            return true;
        }
    }
}