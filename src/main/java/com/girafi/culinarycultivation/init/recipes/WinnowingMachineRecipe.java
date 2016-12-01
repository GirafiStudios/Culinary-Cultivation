package com.girafi.culinarycultivation.init.recipes;

import gnu.trove.map.TObjectDoubleMap;
import gnu.trove.map.hash.TObjectDoubleHashMap;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

public class WinnowingMachineRecipe {
    private final WeightedItems output = new WeightedItems();
    private final WeightedItems junk = new WeightedItems();

    public WeightedItems getOutput() {
        return output;
    }

    public WeightedItems getJunk() {
        return junk;
    }

    public static class WeightedItems {
        private final NavigableMap<Double, ItemStack> map = new TreeMap<>();
        private final TObjectDoubleMap<ItemStack> actual = new TObjectDoubleHashMap<>();
        private double total = 0;

        public void add(@Nonnull ItemStack stack, double weight) {
            if (weight <= 0) return;
            if (weight + total > 100D) return; //Stop at 100%
            total += weight;
            map.put(total, stack);
            actual.put(stack, weight);
        }

        public Set<ItemStack> getSet() {
            return actual.keySet();
        }

        public double get(@Nonnull ItemStack stack) {
            return actual.get(stack);
        }

        //Returns the result
        @Nonnull
        public ItemStack get(World world) {
            //Let's update the map so that it reaches maximum effectiveness
            if (total < 100D) add(ItemStack.EMPTY, 100D - total);
            return map.ceilingEntry((world.rand.nextDouble() * total)).getValue();
        }
    }
}