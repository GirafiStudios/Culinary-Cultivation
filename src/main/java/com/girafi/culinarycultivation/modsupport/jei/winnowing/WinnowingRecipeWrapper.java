package com.girafi.culinarycultivation.modsupport.jei.winnowing;

import com.girafi.culinarycultivation.init.recipes.WinnowingMachineRecipe;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class WinnowingRecipeWrapper extends BlankRecipeWrapper {
    private final List<ItemStack> inputs;
    private final List<ItemStack> outputs;
    private final List<ItemStack> outputsJunk;

    public WinnowingRecipeWrapper(WinnowingMachineRecipe winnowingRecipe) {
        this.inputs = Collections.singletonList(new ItemStack(Blocks.GRASS));
        this.outputs = Collections.singletonList(winnowingRecipe.getOutput());
        this.outputsJunk = Collections.singletonList(winnowingRecipe.getJunk());
    }

    @Override
    public List getInputs() {
        return inputs;
    }

    @Override
    public List getOutputs() {
        return outputsJunk;
    }
}