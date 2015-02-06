package com.Girafi.culinarycultivation.item;

import com.Girafi.culinarycultivation.reference.Reference;
import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import java.util.Set;

public class ItemCakeKnife extends ItemTool {

    private static final Set EFFECTIVE_ON = Sets.newHashSet(new Block[]{Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium});

    public ItemCakeKnife(Item.ToolMaterial material) { //TODO Fix texture, fix EFFECTIVE_ON and all the other things
        super(1.0F, material, EFFECTIVE_ON);
        setUnlocalizedName("cakeKnife");
        setTextureName(Reference.MOD_ID.toLowerCase() + ":" + "cakeKnife");
        setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
        maxStackSize=1;
    }

////Setting unlocalized name
//Copyed from SourceItem
    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}