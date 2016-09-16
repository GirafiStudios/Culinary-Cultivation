package com.girafi.culinarycultivation.inventory;

import net.minecraft.item.ItemStack;

public class SeedBagInventory extends InventoryItem {

    public SeedBagInventory(ItemStack stack) {
        super(stack, "seed_bag", 1, 1280);
    }
}