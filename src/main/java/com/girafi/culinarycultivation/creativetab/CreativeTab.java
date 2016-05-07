package com.girafi.culinarycultivation.creativetab;

import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTab {
    public static final CreativeTabs CulinaryCultivation_Tab = new CreativeTabs(Reference.MOD_ID) {
        @Override
        public Item getTabIconItem() {
            return ModItems.MEAT_CLEAVER;
        }
    };
}