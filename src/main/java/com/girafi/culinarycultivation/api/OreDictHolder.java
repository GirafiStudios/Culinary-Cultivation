package com.girafi.culinarycultivation.api;

import com.girafi.culinarycultivation.util.StringUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictHolder {
    public static IOreDictEntry entry;

    private static NonNullList<String> strings = NonNullList.create();
    private static NonNullList<ItemStack> stacks = NonNullList.create();

    public static void add(ItemStack stack, String string) {
        stacks.add(stack);
        if (!string.equals("")) {
            strings.add(StringUtils.toCamelCase(string));
        }
    }

    public static void init() {
        OreDictionary.registerOre(strings.get(strings.size()), stacks.get(strings.size()));
    }
}