package com.girafi.culinarycultivation.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class NBTHelper {

    public static NBTTagCompound getTag(@Nonnull ItemStack stack) {
        if (!hasTag(stack)) {
            stack.setTagCompound(new NBTTagCompound());
        }
        return stack.getTagCompound();
    }

    public static boolean hasTag(@Nonnull ItemStack stack) {
        return stack.hasTagCompound();
    }

    public static boolean hasKey(@Nonnull ItemStack stack, String string) {
        return stack.getTagCompound() != null && stack.getTagCompound().hasKey(string);
    }

    public static void setInt(@Nonnull ItemStack stack, String key, int value) {
        getTag(stack).setInteger(key, value);
    }

    public static void changeInt(@Nonnull ItemStack stack, String key, int value) {
        setInt(stack, key, getInt(stack, key) + value);
    }

    public static int getInt(@Nonnull ItemStack stack, String key) {
        return hasTag(stack) ? getTag(stack).getInteger(key) : 0;
    }

    @Nonnull
    public static ItemStack readItemStack(NBTTagCompound compound) {
        Item item = Item.getByNameOrId(compound.getString("id"));
        if (item == null) return ItemStack.EMPTY;
        ItemStack stack = new ItemStack(item);
        stack.setCount(compound.getInteger("Count"));
        int damage = compound.getShort("Damage");

        if (damage < 0) {
            damage = 0;
        }
        stack.setItemDamage(damage);
        if (compound.hasKey("tag", 10)) {
            stack.setTagCompound(compound.getCompoundTag("tag"));
            stack.getItem().updateItemStackNBT(stack.getTagCompound());
        } else {
            stack.setTagCompound(null);
        }
        return stack;
    }

    public static NBTTagCompound writeItemStack(@Nonnull ItemStack stack, NBTTagCompound compound) {
        ResourceLocation resource = Item.REGISTRY.getNameForObject(stack.getItem());
        compound.setString("id", resource == null ? "minecraft:air" : resource.toString());
        compound.setInteger("Count", stack.getCount());
        compound.setShort("Damage", (short) stack.getItemDamage());

        if (stack.getTagCompound() != null) {
            compound.setTag("tag", stack.getTagCompound());
        }
        return compound;
    }
}