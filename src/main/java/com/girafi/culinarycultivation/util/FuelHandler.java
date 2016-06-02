package com.girafi.culinarycultivation.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

import static com.girafi.culinarycultivation.init.ModItems.CHAFF_PILE;
import static com.girafi.culinarycultivation.init.ModItems.TOOL_HANDLE;

public class FuelHandler implements IFuelHandler {
    @Override
    public int getBurnTime(ItemStack fuel) {
        if (fuel.getItem() == TOOL_HANDLE) {
            return 200;
        } else if (fuel.getItem() == CHAFF_PILE) {
            return 50;
        }
        return 0;
    }
}