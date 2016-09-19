package com.girafi.culinarycultivation.modsupport.jei.winnowing;

import com.girafi.culinarycultivation.init.recipes.WinnowingMachineRecipe;
import mezz.jei.api.gui.IGuiIngredient;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.*;
import java.util.List;

import static com.girafi.culinarycultivation.modsupport.jei.winnowing.WinnowingRecipeCategory.junkSlot;
import static com.girafi.culinarycultivation.modsupport.jei.winnowing.WinnowingRecipeCategory.outputSlot;

public class WinnowingRecipeWrapper extends BlankRecipeWrapper {
    private final List<ItemStack> inputs;
    private final List<ItemStack> outputs;
    private final List<ItemStack> junks;
    private final List<ItemStack> all;
    private final Map<Pair<Item, Integer>, String> outputChances;
    private final Map<Pair<Item, Integer>, String> junkChances;
    private IGuiIngredient<ItemStack> output;
    private IGuiIngredient<ItemStack> junk;
    private String chanceNormal;
    private String chanceJunk;

    public WinnowingRecipeWrapper(Pair<Item, Integer> input, WinnowingMachineRecipe recipe) {
        this.inputs = Collections.singletonList(new ItemStack(input.getKey(), 1, input.getValue()));
        this.outputs = new ArrayList<>();
        this.junks = new ArrayList<>();
        this.outputChances = new HashMap<>();
        this.junkChances = new HashMap<>();
        if (recipe.getOutput() != null) {
            for (ItemStack stack: recipe.getOutput().getSet()) {
                if (stack != null) {
                    this.outputs.add(stack);
                    this.outputChances.put(Pair.of(stack.getItem(), stack.getItemDamage()),recipe.getOutput().get(stack) + "%");
                }
            }
        } else this.outputs.add(null);

        if (recipe.getJunk() != null) {
            for (ItemStack stack: recipe.getJunk().getSet()) {
                if (stack != null) {
                    this.junks.add(stack);
                    this.junkChances.put(Pair.of(stack.getItem(), stack.getItemDamage()),recipe.getJunk().get(stack) + "%");
                }
            }
        } else this.junks.add(null);

        this.all = new ArrayList<>();
        this.all.addAll(outputs);
        this.all.addAll(junks);
    }

    public void set(IRecipeLayout layout) {
        Map<Integer, ? extends IGuiIngredient<ItemStack>> ingredients = layout.getItemStacks().getGuiIngredients();
        output = ingredients.get(outputSlot);
        junk = ingredients.get(junkSlot);
    }

    @Override
    @Nonnull
    public List getInputs() {
        return inputs;
    }

    @Override
    @Nonnull
    public List getOutputs() {
        return all;
    }

    @Nonnull
    public List getOutput() {
        return outputs;
    }

    @Nonnull
    public List getJunk() {
        return junks;
    }

    private ItemStack getStack(IGuiIngredient<ItemStack> input) {
        return input.getCurrentlyDisplayed() != null ? input.getCurrentlyDisplayed().getValue() : null;
    }
    private void rebuild() {
        ItemStack theOutput = getStack(output);
        if (theOutput != null) chanceNormal = outputChances.get(Pair.of(theOutput.getItem(), theOutput.getItemDamage()));
        else chanceNormal = null;

        ItemStack theJunk = getStack(junk);
        if (theJunk != null) chanceJunk = junkChances.get(Pair.of(theJunk.getItem(), theJunk.getItemDamage()));
        else chanceJunk = null;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        rebuild();
        if (chanceNormal != null && !chanceNormal.equals("")) {
            FontRenderer fontRendererObj = minecraft.fontRendererObj;
            int stringWidth = fontRendererObj.getStringWidth(chanceNormal);
            fontRendererObj.drawString(chanceNormal, recipeWidth - stringWidth - 20, 8, Color.gray.getRGB());
        }

        if (chanceJunk != null && !chanceJunk.equals("")) {
            FontRenderer fontRendererObj = minecraft.fontRendererObj;
            fontRendererObj.drawString(chanceJunk, recipeWidth - 16, 16, Color.gray.getRGB());
        }
    }
}