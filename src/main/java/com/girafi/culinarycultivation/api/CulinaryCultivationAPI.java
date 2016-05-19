package com.girafi.culinarycultivation.api;

import com.girafi.culinarycultivation.api.crafting.IWinnowingMachineHandler;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

public class CulinaryCultivationAPI {
    public static IWinnowingMachineHandler winnowing;

    public static final ItemArmor.ArmorMaterial FARMER_ARMOR_MATERIAL = EnumHelper.addEnum(ItemArmor.ArmorMaterial.class, "FARMER_ARMOR",
            new Class[]{String.class, int.class, int[].class, int.class, SoundEvent.class, float.class},
            "farmer_armor", 9, new int[]{1, 2, 3, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F); //TODO
}