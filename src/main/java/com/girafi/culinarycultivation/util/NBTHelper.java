package com.girafi.culinarycultivation.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

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

    public static ItemStack readItemStack(NBTTagCompound compound) {
        Item item = Item.getByNameOrId(compound.getString("id"));
        if (item == null) return null;
        ItemStack stack = new ItemStack(item);
        stack.stackSize = compound.getInteger("Count");
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

    public static NBTTagCompound writeItemStack(ItemStack stack, NBTTagCompound compound) {
        ResourceLocation resource = Item.REGISTRY.getNameForObject(stack.getItem());
        compound.setString("id", resource == null ? "minecraft:air" : resource.toString());
        compound.setInteger("Count", stack.stackSize);
        compound.setShort("Damage", (short) stack.getItemDamage());

        if (stack.getTagCompound() != null) {
            compound.setTag("tag", stack.getTagCompound());
        }
        return compound;
    }
}