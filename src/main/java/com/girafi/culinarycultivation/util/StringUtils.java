package com.girafi.culinarycultivation.util;

import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraft.util.text.translation.I18n;

public class StringUtils {

    public static String shiftTooltip() {
        return StringUtils.formatColorCode(Reference.MOD_ID + ".misc.shift");
    }

    public static String newLine() {
        return translateFormatted("\n");
    }

    /*
     * Formats any Minecraft color code. Handles
     */
    public static String formatColorCode(String string) {
        String formatted = translateToLocal(string);
        return formatted.replaceAll("&", "\u00a7");
    }

    /*
     * Redirects to the vanilla version for less calls to deprecated code.
     */
    public static String translateToLocal(String string) {
        return I18n.translateToLocal(string);
    }

    /*
     * Redirects to the vanilla version for less calls to deprecated code.
     */
    public static String translateFormatted(String string, Object... objects) {
        return I18n.translateToLocalFormatted(string, objects);
    }
}