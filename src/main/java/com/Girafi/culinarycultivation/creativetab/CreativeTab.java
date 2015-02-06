package com.Girafi.culinarycultivation.creativetab;

import com.Girafi.culinarycultivation.reference.Reference;
import com.Girafi.culinarycultivation.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTab
{
    public static final CreativeTabs CulinaryCultivation_Tab = new CreativeTabs(Reference.MOD_ID.toLowerCase())
    {
        @Override
        public Item getTabIconItem()
        {
            return ModItems.knife;
        }

    };
}