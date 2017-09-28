package com.girafi.culinarycultivation.crafting;

import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.item.ItemGeneral;
import com.girafi.culinarycultivation.util.NBTHelper;
import com.girafi.culinarycultivation.util.StringUtil;
import com.girafi.culinarycultivation.util.reference.Reference;
import com.google.common.collect.Lists;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

@EventBusSubscriber
public class RecipesSeasoning extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
    public static final String SEASONING_NBT_KEY = "Seasoning";

    private RecipesSeasoning() {
        setRegistryName(new ResourceLocation(Reference.MOD_ID, "seasoning"));
    }

    @Override
    public boolean matches(@Nonnull InventoryCrafting crafting, @Nonnull World world) {
        ItemStack stack = ItemStack.EMPTY;
        List<ItemStack> list = Lists.newArrayList();

        for (int i = 0; i < crafting.getSizeInventory(); ++i) {
            ItemStack craftingStack = crafting.getStackInSlot(i);

            if (!craftingStack.isEmpty()) {
                if (craftingStack.getItem() instanceof ItemFood) {
                    ItemFood food = (ItemFood) craftingStack.getItem();

                    if (food.getHealAmount(craftingStack) <= 0 || !stack.isEmpty()) {
                        return false;
                    }
                    stack = craftingStack;
                } else {
                    int meta = craftingStack.getMetadata();
                    if (craftingStack.getItem() != ModItems.GENERAL && (meta != ItemGeneral.Type.PEPPER.getMetadata() || meta != ItemGeneral.Type.SALT.getMetadata())) {
                        return false;
                    }
                    list.add(craftingStack);
                }
            }
        }
        return !stack.isEmpty() && !list.isEmpty();
    }

    @Override
    @Nonnull
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting crafting) {
        ItemStack stack = ItemStack.EMPTY;

        for (int k = 0; k < crafting.getSizeInventory(); ++k) {
            ItemStack craftingStack = crafting.getStackInSlot(k);

            if (!craftingStack.isEmpty()) {
                if (craftingStack.getItem() instanceof ItemFood) {
                    ItemFood food = (ItemFood) craftingStack.getItem();

                    if (food.getHealAmount(craftingStack) <= 0 || !stack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }

                    stack = craftingStack.copy();
                    stack.setCount(1);
                }
            }
            NBTTagCompound tag = new NBTTagCompound();

            int meta = craftingStack.getMetadata();
            if (craftingStack.getItem() == ModItems.GENERAL) {
                if (meta == ItemGeneral.Type.PEPPER.getMetadata()) {
                    tag.setString(SEASONING_NBT_KEY, "Peppered");
                } else if (meta == ItemGeneral.Type.SALT.getMetadata()) {
                    tag.setString(SEASONING_NBT_KEY, "Salted");
                }
            }

            if (NBTHelper.hasTag(craftingStack) && stack.getTagCompound() != null) { //TODO Fix
                System.out.println("Already have tag");
                tag.setString(SEASONING_NBT_KEY, stack.getTagCompound().getString(SEASONING_NBT_KEY));
            }

            if (!tag.hasNoTags()) {
                stack.setTagCompound(tag);
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    @Nonnull
    public NonNullList<ItemStack> getRemainingItems(@Nonnull InventoryCrafting crafting) {
        NonNullList<ItemStack> stacks = NonNullList.withSize(crafting.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < stacks.size(); ++i) {
            ItemStack stack = crafting.getStackInSlot(i);
            stacks.set(i, ForgeHooks.getContainerItem(stack));
        }
        return stacks;
    }

    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @SubscribeEvent
    public static void addTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof ItemFood && NBTHelper.hasKey(stack, SEASONING_NBT_KEY) && stack.getTagCompound() != null) {
            if (Objects.equals(stack.getTagCompound().getString(SEASONING_NBT_KEY), "Peppered")) {
                event.getToolTip().add("" + TextFormatting.DARK_GRAY + TextFormatting.ITALIC + StringUtil.translateFormatted(Reference.MOD_ID + ".pepper"));
            } else if (Objects.equals(stack.getTagCompound().getString(SEASONING_NBT_KEY), "Salted")) {
                event.getToolTip().add("" + TextFormatting.WHITE + TextFormatting.ITALIC + StringUtil.translateFormatted(Reference.MOD_ID + ".salt"));
            }
        }
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        event.getRegistry().register(new RecipesSeasoning());
    }
}