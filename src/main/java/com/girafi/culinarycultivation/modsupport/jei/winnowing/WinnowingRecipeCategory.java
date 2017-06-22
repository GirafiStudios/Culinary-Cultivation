package com.girafi.culinarycultivation.modsupport.jei.winnowing;

import com.girafi.culinarycultivation.modsupport.jei.JEIPlugin;
import com.girafi.culinarycultivation.util.StringUtil;
import com.girafi.culinarycultivation.util.reference.Reference;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;

public class WinnowingRecipeCategory extends BlankRecipeCategory<WinnowingRecipeWrapper> {
    static final int inputSlot = 0;
    static final int outputSlot = 1;
    static final int junkSlot = 2;
    private final IDrawableStatic background;

    public WinnowingRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(new ResourceLocation(Reference.MOD_ID, "textures/gui/winnow.png"), 19, 13, 137, 54);
    }

    @Override
    @Nonnull
    public String getUid() {
        return JEIPlugin.WINNOWING;
    }

    @Override
    @Nonnull
    public String getTitle() {
        return StringUtil.translateToLocal(getUid());
    }

    @Override
    @Nonnull
    public String getModName() {
        return Reference.MOD_NAME;
    }

    @Override
    @Nonnull
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull WinnowingRecipeWrapper recipeWrapper, @Nonnull IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(inputSlot, true, 4, 21);
        guiItemStacks.init(outputSlot, false, 94, 21);
        guiItemStacks.init(junkSlot, false, 119, 25);

        List<ItemStack> inputs = ingredients.getInputs(ItemStack.class).get(0);
        if (inputs != null) {
            guiItemStacks.set(inputSlot, inputs);
        }
        guiItemStacks.set(outputSlot, ingredients.getOutputs(ItemStack.class).get(0));
        guiItemStacks.set(junkSlot, recipeWrapper.getJunk());
        recipeWrapper.setValues(recipeLayout);
    }
}