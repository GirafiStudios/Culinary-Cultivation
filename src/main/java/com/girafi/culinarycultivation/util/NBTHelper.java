package com.girafi.culinarycultivation.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper {

    public static NBTTagCompound getTag(ItemStack stack) {
        if (!hasTag(stack)) {
            stack.setTagCompound(new NBTTagCompound());
        }
        return stack.getTagCompound();
    }

    public static boolean hasTag(ItemStack stack) {
        return stack.hasTagCompound();
    }

    public static void setInt(ItemStack stack, String key, int value) {
        getTag(stack).setInteger(key, value);
    }

    public static void changeInt(ItemStack stack, String key, int value) {
        setInt(stack, key, getInt(stack, key) + value);
    }

    public static int getInt(ItemStack stack, String key) {
        return hasTag(stack) ? getTag(stack).getInteger(key) : 0;
    }
}