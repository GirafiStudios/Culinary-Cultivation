package com.girafi.culinarycultivation.modsupport.jei.winnowing;

import com.girafi.culinarycultivation.crafting.WinnowingMachineRecipe;
import com.girafi.culinarycultivation.crafting.WinnowingMachineRecipes;
import mezz.jei.api.gui.IGuiIngredient;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.girafi.culinarycultivation.modsupport.jei.winnowing.WinnowingRecipeCategory.junkSlot;
import static com.girafi.culinarycultivation.modsupport.jei.winnowing.WinnowingRecipeCategory.outputSlot;

public class WinnowingRecipeWrapper implements IRecipeWrapper {
    public final List<ItemStack> inputs;
    public final List<ItemStack> junks;
    public final List<ItemStack> outputs;
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
            recipe.getOutput().getSet().stream().filter(stack -> !stack.isEmpty()).forEach(stack -> {
                this.outputs.add(stack);
                this.outputChances.put(Pair.of(stack.getItem(), stack.getItemDamage()), recipe.getOutput().get(stack) + "%");
            });
        } else {
            this.outputs.add(null);
        }

        if (recipe.getJunk() != null) {
            recipe.getJunk().getSet().stream().filter(stack -> !stack.isEmpty()).forEach(stack -> {
                this.junks.add(stack);
                this.junkChances.put(Pair.of(stack.getItem(), stack.getItemDamage()), recipe.getJunk().get(stack) + "%");
            });
        } else {
            this.junks.add(null);
        }
    }

    public void setValues(IRecipeLayout layout) {
        Map<Integer, ? extends IGuiIngredient<ItemStack>> ingredients = layout.getItemStacks().getGuiIngredients();
        output = ingredients.get(outputSlot);
        junk = ingredients.get(junkSlot);
    }

    public static List<WinnowingRecipeWrapper> getRecipes() {
        List<WinnowingRecipeWrapper> wrappers = new ArrayList<>();
        Map<Pair<Item, Integer>, WinnowingMachineRecipe> recipes = WinnowingMachineRecipes.instance().getRecipes();
        wrappers.addAll(recipes.keySet().stream().map(pair -> new WinnowingRecipeWrapper(pair, recipes.get(pair))).collect(Collectors.toList()));

        return wrappers;
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, inputs);
        ingredients.setOutputLists(ItemStack.class, Collections.singletonList(outputs));
    }

    @Nonnull
    List<ItemStack> getJunk() {
        return junks;
    }

    @Nonnull
    private ItemStack getStack(IGuiIngredient<ItemStack> input) {
        return input.getDisplayedIngredient() != null ? input.getDisplayedIngredient() : ItemStack.EMPTY;
    }

    private void rebuild() {
        ItemStack theOutput = getStack(output);
        if (!theOutput.isEmpty()) {
            chanceNormal = outputChances.get(Pair.of(theOutput.getItem(), theOutput.getItemDamage()));
        } else {
            chanceNormal = null;
        }

        ItemStack theJunk = getStack(junk);
        if (!theJunk.isEmpty()) {
            chanceJunk = junkChances.get(Pair.of(theJunk.getItem(), theJunk.getItemDamage()));
        } else {
            chanceJunk = null;
        }
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        this.rebuild();
        if (chanceNormal != null && !chanceNormal.equals("")) {
            FontRenderer fontRendererObj = minecraft.fontRenderer;
            int stringWidth = fontRendererObj.getStringWidth(chanceNormal);
            fontRendererObj.drawString(chanceNormal, recipeWidth - stringWidth - 20, 8, Color.gray.getRGB());
        }

        if (chanceJunk != null && !chanceJunk.equals("")) {
            FontRenderer fontRendererObj = minecraft.fontRenderer;
            fontRendererObj.drawString(chanceJunk, recipeWidth - 16, 16, Color.gray.getRGB());
        }
    }
}