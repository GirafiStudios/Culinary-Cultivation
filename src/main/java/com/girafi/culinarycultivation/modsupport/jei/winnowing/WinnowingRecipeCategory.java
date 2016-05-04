package com.girafi.culinarycultivation.modsupport.jei.winnowing;

import com.girafi.culinarycultivation.util.reference.Reference;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class WinnowingRecipeCategory extends BlankRecipeCategory {
    private final ICraftingGridHelper craftingGridHelper;
    private final IDrawableStatic background;

    public WinnowingRecipeCategory(IGuiHelper guiHelper) {
        craftingGridHelper = guiHelper.createCraftingGridHelper(1, 0);
        background = guiHelper.createDrawable(new ResourceLocation("textures/gui/container/crafting_table.png"), 29, 16, 116, 54);
    }

    @Override
    public String getUid() {
        return Reference.MOD_ID + "." + "winnowing";
    }

    @Override
    public String getTitle() {
        return I18n.translateToLocal(Reference.MOD_ID + "." + "winnowing");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(0, false, 94, 18);

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                int index = 1 + x + (y * 3);
                guiItemStacks.init(index, true, x * 18, y * 18);
            }
        }

        if (recipeWrapper instanceof WinnowingRecipeWrapper) {
            WinnowingRecipeWrapper wrapper = (WinnowingRecipeWrapper) recipeWrapper;
            craftingGridHelper.setInput(guiItemStacks, wrapper.getInputs());
            craftingGridHelper.setOutput(guiItemStacks, wrapper.getOutputs());
        }
    }
}