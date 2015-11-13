package com.Girafi.culinarycultivation.init.recipes;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.item.equipment.armor.farmer.ItemFarmerArmor;
import com.google.common.collect.Lists;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import java.util.ArrayList;

public class RecipesFarmerArmorDyes implements IRecipe {

    @Override
    public boolean matches(InventoryCrafting crafting, World world) {
        ItemStack stack = null;
        ArrayList list = Lists.newArrayList();

        for (int i = 0; i < crafting.getSizeInventory(); ++i) {
            ItemStack craftingStack = crafting.getStackInSlot(i);

            if (craftingStack != null) {
                if (craftingStack.getItem() instanceof ItemFarmerArmor && craftingStack.getItem() != ModItems.farmerStrawhat) {
                    ItemFarmerArmor armor = (ItemFarmerArmor) craftingStack.getItem();

                    if (armor.getArmorMaterial() != ItemFarmerArmor.farmerArmorMaterial || stack != null) {
                        return false;
                    }

                    stack = craftingStack;
                } else {
                    if (craftingStack.getItem() != Items.dye) {
                        return false;
                    }

                    list.add(craftingStack);
                }
            }
        }
        return stack != null && !list.isEmpty();
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting crafting) {
        ItemStack stack = null;
        int[] aint = new int[3];
        int i = 0;
        int j = 0;
        ItemFarmerArmor farmerArmor = null;
        int k;
        int l;
        float f;
        float f1;
        int l1;

        for (k = 0; k < crafting.getSizeInventory(); ++k) {
            ItemStack craftingStack = crafting.getStackInSlot(k);

            if (craftingStack != null) {
                if (craftingStack.getItem() instanceof ItemFarmerArmor && craftingStack.getItem() != ModItems.farmerStrawhat) {
                    farmerArmor = (ItemFarmerArmor) craftingStack.getItem();

                    if (farmerArmor.getArmorMaterial() != ItemFarmerArmor.farmerArmorMaterial || stack != null) {
                        return null;
                    }

                    stack = craftingStack.copy();
                    stack.stackSize = 1;

                    if (farmerArmor.hasColor(craftingStack)) {
                        l = farmerArmor.getColor(stack);
                        f = (float) (l >> 16 & 255) / 255.0F;
                        f1 = (float) (l >> 8 & 255) / 255.0F;
                        float f2 = (float) (l & 255) / 255.0F;
                        i = (int) ((float) i + Math.max(f, Math.max(f1, f2)) * 255.0F);
                        aint[0] = (int) ((float) aint[0] + f * 255.0F);
                        aint[1] = (int) ((float) aint[1] + f1 * 255.0F);
                        aint[2] = (int) ((float) aint[2] + f2 * 255.0F);
                        ++j;
                    }
                } else {
                    if (craftingStack.getItem() != Items.dye) {
                        return null;
                    }

                    float[] afloat = EntitySheep.func_175513_a(EnumDyeColor.byDyeDamage(craftingStack.getMetadata()));
                    int j1 = (int) (afloat[0] * 255.0F);
                    int k1 = (int) (afloat[1] * 255.0F);
                    l1 = (int) (afloat[2] * 255.0F);
                    i += Math.max(j1, Math.max(k1, l1));
                    aint[0] += j1;
                    aint[1] += k1;
                    aint[2] += l1;
                    ++j;
                }
            }
        }

        if (farmerArmor == null) {
            return null;
        } else {
            k = aint[0] / j;
            int i1 = aint[1] / j;
            l = aint[2] / j;
            f = (float) i / (float) j;
            f1 = (float) Math.max(k, Math.max(i1, l));
            k = (int) ((float) k * f / f1);
            i1 = (int) ((float) i1 * f / f1);
            l = (int) ((float) l * f / f1);
            l1 = (k << 8) + i1;
            l1 = (l1 << 8) + l;
            farmerArmor.setColor(stack, l1);
            return stack;
        }
    }

    @Override
    public int getRecipeSize() {
        return 10;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting crafting) {
        ItemStack[] aStack = new ItemStack[crafting.getSizeInventory()];

        for (int i = 0; i < aStack.length; ++i) {
            ItemStack stack = crafting.getStackInSlot(i);
            aStack[i] = net.minecraftforge.common.ForgeHooks.getContainerItem(stack);
        }
        return aStack;
    }
}