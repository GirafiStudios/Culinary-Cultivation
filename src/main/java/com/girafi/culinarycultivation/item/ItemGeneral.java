package com.girafi.culinarycultivation.item;

import com.girafi.culinarycultivation.api.item.IOreDictEntry;
import com.girafi.culinarycultivation.util.OreDictHelper;
import com.girafi.culinarycultivation.util.reference.Paths;
import com.google.common.collect.Maps;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.Map;

public class ItemGeneral extends Item implements IOreDictEntry {

    public ItemGeneral() {
        this.setHasSubtypes(true);
    }

    @Override
    @Nonnull
    public Item setMaxStackSize(int maxStackSize) {
        for (Type type : Type.values()) {
            switch (type) {
                case CALF_BELLY:
                    maxStackSize = 1;
                    break;
            }
        }
        return super.setMaxStackSize(maxStackSize);
    }

    @Override
    @Nonnull
    public String getUnlocalizedName(@Nonnull ItemStack stack) {
        Type type = Type.byItemStack(stack);
        return "item." + Paths.MOD_ASSETS + type.getName();
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> subItems) {
        for (Type type : Type.values()) {
            subItems.add(new ItemStack(this, 1, type.getMetadata()));
        }
    }

    @Override
    public int getItemBurnTime(ItemStack stack) {
        Type type = Type.byItemStack(stack);
        switch (type) {
            case TOOL_HANDLE:
                return 200;
            case CHAFF_PILE:
                return 50;
            default:
                return super.getItemBurnTime(stack);

        }
    }

    @Override
    public void getOreDictEntries() {
        OreDictHelper.add(new ItemStack(this, 1, Type.PEPPER.getMetadata()), "foodPepper", "foodBlackPepper");
        OreDictHelper.add("dustSalt", new ItemStack(this, 1, Type.SALT.getMetadata()));
    }

    public enum Type {
        PEPPER(0, "pepper"),
        SALT(1, "salt"),
        CHAFF_PILE(2, "chaff_pile"),
        CALF_BELLY(3, "calf_belly"),
        TOOL_HANDLE(4, "tool_handle");

        private static final Map<Integer, Type> META_LOOKUP = Maps.newHashMap();
        private final int meta;
        private final String name;

        Type(int meta, String name) {
            this.meta = meta;
            this.name = name;
        }

        public int getMetadata() {
            return this.meta;
        }

        public String getName() {
            return this.name;
        }

        public static Type byMetadata(int meta) {
            Type type = META_LOOKUP.get(meta);
            return type == null ? PEPPER : type;
        }

        public static Type byItemStack(@Nonnull ItemStack stack) {
            return stack.getItem() instanceof ItemGeneral ? byMetadata(stack.getMetadata()) : PEPPER;
        }

        static {
            for (Type type : values()) {
                META_LOOKUP.put(type.getMetadata(), type);
            }
        }
    }
}