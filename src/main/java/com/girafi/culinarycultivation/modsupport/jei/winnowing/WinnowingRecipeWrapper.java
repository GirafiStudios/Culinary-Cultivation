package com.girafi.culinarycultivation.modsupport.jei.winnowing;

import com.girafi.culinarycultivation.api.CulinaryCultivationAPI;
import com.girafi.culinarycultivation.api.crafting.IWinnowingMachineRecipe;
import com.google.common.collect.Multimap;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WinnowingRecipeWrapper extends BlankRecipeWrapper {
    private final List<ItemStack> inputs;
    private final List<ItemStack> outputs;
    private String chanceNormal = "";
    private String chanceJunk = "";

    public WinnowingRecipeWrapper(Pair<Item, Integer> input, IWinnowingMachineRecipe recipe) {
        Multimap<Pair<Item, Integer>, IWinnowingMachineRecipe> map = CulinaryCultivationAPI.winnowing.getProcessingList();
        this.inputs = Collections.singletonList(new ItemStack(input.getKey(), 1, input.getValue()));
        this.outputs = new ArrayList<ItemStack>();
        if (recipe.getOutput() != null) {
            this.outputs.add(recipe.getOutput());
            this.chanceNormal = recipe.getOutputChance() + "%";
        } else this.outputs.add(null);

        if (recipe.getJunk() != null) {
            this.outputs.add(recipe.getJunk());
            this.chanceJunk = recipe.getJunkChance() + "%";
        } else this.outputs.add(null);
    }

    @Override
    public List getInputs() {
        return inputs;
    }

    @Override
    public List getOutputs() {
        return outputs;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        if (chanceNormal != null && !chanceNormal.equals("")) {
            FontRenderer fontRendererObj = minecraft.fontRendererObj;
            int stringWidth = fontRendererObj.getStringWidth(chanceNormal);
            fontRendererObj.drawString(chanceNormal, recipeWidth - stringWidth - 20, 8, Color.gray.getRGB());
        }

        if (chanceJunk != null && !chanceJunk.equals("")) {
            FontRenderer fontRendererObj = minecraft.fontRendererObj;
            int stringWidth = fontRendererObj.getStringWidth(chanceNormal);
            fontRendererObj.drawString(chanceJunk, recipeWidth - 16, 16, Color.gray.getRGB());
        }
    }}