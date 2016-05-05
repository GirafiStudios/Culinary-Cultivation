package com.girafi.culinarycultivation.modsupport.jei.winnowing;

import com.girafi.culinarycultivation.modsupport.jei.JEIPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nonnull;

public class WinnowingRecipeCategory extends BlankRecipeCategory {
    protected static final int inputSlot = 0;
    protected static final int outputSlot = 1;
    protected static final int junkSlot = 2;
    private final IDrawableStatic background;

    public WinnowingRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(new ResourceLocation("culinarycultivation:textures/gui/winnow.png"), 19, 13, 137, 54);
    }

    @Override
    public String getUid() {
        return JEIPlugin.WINNOWING;
    }

    @Override
    public String getTitle() {
        return I18n.translateToLocal(getUid());
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }


    //MINUS WHAT
    //14, 8
    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(inputSlot, true, 4, 21);
        guiItemStacks.init(outputSlot, false, 94, 21);
        guiItemStacks.init(junkSlot, false, 119, 25);

        guiItemStacks.setFromRecipe(inputSlot, recipeWrapper.getInputs());
        guiItemStacks.setFromRecipe(outputSlot, recipeWrapper.getOutputs().get(0));
        guiItemStacks.setFromRecipe(junkSlot, recipeWrapper.getOutputs().get(1));
    }
}