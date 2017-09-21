package com.girafi.culinarycultivation.util;

import com.girafi.culinarycultivation.api.item.IOreDictEntry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictHelper {
    public static NonNullList<IOreDictEntry> entries = NonNullList.create();

    public static void addForType(Item first, Item second, String firstPrefix, String secondPrefix, String name, int meta) {
        add(first, meta, firstPrefix, name);
        add(second, meta, secondPrefix, name);
    }

    public static void add(Item item, int meta, String prefix, String name) {
        add(prefix + "_" + name, new ItemStack(item, 1, meta));
    }

    public static void add(Item item, int meta, String name) {
        add(name, new ItemStack(item, 1, meta));
    }

    public static void add(String name, ItemStack... stacks) {
        for (ItemStack stack : stacks) {
            OreDictionary.registerOre(StringUtil.toCamelCase(name), stack);
        }
    }

    public static void add(ItemStack stack, String... names) {
        for (String name : names) {
            OreDictionary.registerOre(StringUtil.toCamelCase(name), stack);
        }
    }

    public static void register() {
        for (IOreDictEntry entry : entries) {
            entry.getOreDictEntries();
        }
    }
}