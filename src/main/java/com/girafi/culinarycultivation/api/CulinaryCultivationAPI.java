package com.girafi.culinarycultivation.api;

import com.girafi.culinarycultivation.api.crafting.IWinnowingMachineHandler;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class CulinaryCultivationAPI {
    public static IWinnowingMachineHandler winnowing;

    public static final ArmorMaterial FARMER_ARMOR_MATERIAL = EnumHelper.addArmorMaterial("FARMER_ARMOR", "farmer_armor", 9, new int[]{1, 2, 3, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0F);
}