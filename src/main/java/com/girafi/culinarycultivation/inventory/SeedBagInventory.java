package com.girafi.culinarycultivation.inventory;

import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.item.ItemCropProduct;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nonnull;

public class SeedBagInventory extends InventoryItem {

    public SeedBagInventory(@Nonnull ItemStack stack) {
        super(stack, "seed_bag", 1, 960);
    }

    @Override
    public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack) {
        Item item = stack.getItem();
        ItemCropProduct.ProductType cropProduct = ItemCropProduct.ProductType.byItemStack(stack);
        return (item instanceof ItemSeeds || item instanceof ItemSeedFood || item == ModItems.CROP_SEEDS || item == ModItems.CROP_FOOD && cropProduct.canPlantCrop()) && item instanceof IPlantable;
    }
}