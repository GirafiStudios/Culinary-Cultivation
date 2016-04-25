package com.Girafi.culinarycultivation.init.recipes;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.util.LogHelper;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.Map;

import static com.Girafi.culinarycultivation.item.ItemCropSeeds.SeedType;

public class WinnowingMachineRecipes {
    private static final WinnowingMachineRecipes WINNOWING_MACHINE_INSTANCE = new WinnowingMachineRecipes();
    private Map<ItemStack, ItemStack> processingList = Maps.newHashMap();

    public static WinnowingMachineRecipes instance() {
        return WINNOWING_MACHINE_INSTANCE;
    }

    private WinnowingMachineRecipes() {
        this.addRecipe(Blocks.TALLGRASS, new ItemStack(ModItems.CROP_SEEDS, 1, SeedType.BLACKPEPPERDRUPE.getMetadata()));
    }

    private void addRecipe(Block input, ItemStack output) {
        this.addRecipe(new ItemStack(input), output, new ItemStack(ModItems.CHAFF_PILE));
    }

    public void addRecipe(ItemStack input, ItemStack output, ItemStack junkOutput) { //TODO Fix Junk Output
        if (this.getProccessingResult(input) != null) {
            LogHelper.info("Ignored winnowing machine recipe with conflicting input: " + input + " = " + output);
            return;
        }
        this.processingList.put(input, output);
    }

    public ItemStack getProccessingResult(ItemStack stack) {
        for (Map.Entry<ItemStack, ItemStack> entry : this.processingList.entrySet()) {
            if (this.compareItemStacks(stack, entry.getKey())) {
                return entry.getValue();
            }
        }

        return null;
    }

    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

    public Map<ItemStack, ItemStack> getProcessingList() {
        return this.processingList;
    }
}