package com.Girafi.culinarycultivation.item;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.reference.Reference;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import java.util.Set;

public class ItemCakeKnife extends ItemTool {

    private static final Set EFFECTIVE_ON = Sets.newHashSet(new Block[]{Blocks.cake});

    public ItemCakeKnife(Item.ToolMaterial material) {
        super(-2.0F, material, EFFECTIVE_ON);
        setUnlocalizedName(Reference.MOD_ID.toLowerCase() + ":" + "cakeKnife");
        setTextureName(Reference.MOD_ID.toLowerCase() + ":" + "cakeKnife");
        setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
        maxStackSize=1;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        stack.damageItem(0, attacker);
        return true;
    }

    @Override
    public int getItemEnchantability() {return this.toolMaterial.WOOD.getHarvestLevel();
    }

    @Override
    public boolean isRepairable()
    {
        return false;
    }

}