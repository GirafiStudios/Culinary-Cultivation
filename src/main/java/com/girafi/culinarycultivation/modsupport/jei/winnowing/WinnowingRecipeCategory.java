package com.girafi.culinarycultivation.modsupport.jei.winnowing;

import com.girafi.culinarycultivation.modsupport.jei.JEIPlugin;
import com.girafi.culinarycultivation.util.reference.Paths;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class WinnowingRecipeCategory extends BlankRecipeCategory<WinnowingRecipeWrapper> {
    static final int inputSlot = 0;
    static final int outputSlot = 1;
    static final int junkSlot = 2;
    private final IDrawableStatic background;

    public WinnowingRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(new ResourceLocation(Paths.MOD_ASSETS + "textures/gui/winnow.png"), 19, 13, 137, 54);
    }

    @Override
    @Nonnull
    public String getUid() {
        return JEIPlugin.WINNOWING;
    }

    @Override
    @Nonnull
    public String getTitle() {
        return I18n.format(getUid());
    }

    @Override
    @Nonnull
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull WinnowingRecipeWrapper recipeWrapper) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(inputSlot, true, 4, 21);
        guiItemStacks.init(outputSlot, false, 94, 21);
        guiItemStacks.init(junkSlot, false, 119, 25);

        guiItemStacks.setFromRecipe(inputSlot, recipeWrapper.getInputs());
        guiItemStacks.setFromRecipe(outputSlot, recipeWrapper.getOutput());
        guiItemStacks.setFromRecipe(junkSlot, recipeWrapper.getJunk());
        recipeWrapper.set(recipeLayout);
    }
}