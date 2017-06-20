package com.girafi.culinarycultivation.util;

import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraft.util.text.translation.I18n;
import org.apache.commons.lang3.StringUtils;

public class StringUtil {

    /**
     * Formats the inserted string into camel case
     */
    public static String toCamelCase(String string) {
        String[] words = StringUtils.splitByCharacterTypeCamelCase(string);

        boolean firstWordNotFound = true;
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (firstWordNotFound && word.length() > 0) {
                words[i] = word.toLowerCase();
                firstWordNotFound = false;
            } else {
                words[i] = StringUtils.capitalize(word.toLowerCase());
            }
        }
        return StringUtils.join(words).replaceAll("[\\s_]", "");
    }

    /*
     * Universal method for adding "Press Shift for info" tooltip
     */
    public static String shiftTooltip() {
        return formatColorCode(Reference.MOD_ID + ".misc.shift");
    }

    /*
     * Formats any Minecraft color code. Handles formatting as well.
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