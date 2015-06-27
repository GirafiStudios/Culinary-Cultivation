package com.Girafi.culinarycultivation.item.equipment.armor.farmer;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import com.Girafi.culinarycultivation.reference.Paths;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.util.EnumHelper;

public class ItemFarmerArmor extends ItemArmor implements ISpecialArmor {
    public static ArmorMaterial farmerArmorMaterial = EnumHelper.addArmorMaterial("FARMER", "FARMER" , 10, new int[]{1, 2, 3, 3}, 12);

    public ItemFarmerArmor(int type, String name) {
        this(type, name, farmerArmorMaterial);
    }

    public ItemFarmerArmor(int type, String name, ArmorMaterial mat) {
        super(mat, 0, type);
        setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
        setUnlocalizedName(Paths.ModAssets + name);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return Paths.ARMOR_MODEL + armorModelFile();
    }

    public String armorModelFile() {
        return "farmerArmorTest.png";
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        if(source.isUnblockable()) {
            return new ArmorProperties(0, 0, 0);
        }
        return new ArmorProperties(0, damageReduceAmount / 25D, armor.getMaxDamage() + 1 - armor.getItemDamage());
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        return damageReduceAmount;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        stack.damageItem(damage, entity);
    }
}
