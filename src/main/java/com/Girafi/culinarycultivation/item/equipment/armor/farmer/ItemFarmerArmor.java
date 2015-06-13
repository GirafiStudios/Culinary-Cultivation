package com.Girafi.culinarycultivation.item.equipment.armor.farmer;

import com.Girafi.culinarycultivation.CulinaryCultivation;
import com.Girafi.culinarycultivation.client.render.block.RenderCauldron;
import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import com.Girafi.culinarycultivation.reference.Paths;
import com.Girafi.culinarycultivation.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.util.EnumHelper;

public class ItemFarmerArmor extends ItemArmor implements ISpecialArmor {
    public static ArmorMaterial farmerArmorMaterial = EnumHelper.addArmorMaterial("FARMER", 10, new int[]{1, 2, 3, 3}, 12);

    public ItemFarmerArmor(int type, String name) {
        this(type, name, farmerArmorMaterial);
    }

    public ItemFarmerArmor(int type, String name, ArmorMaterial mat) {
        super(mat, 0, type);
        setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
        setUnlocalizedName(Paths.ModAssets + name);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register) {
        itemIcon = forItem(register, this);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return Paths.ARMOR_MODEL + armorModelFile();
    }

    public String armorModelFile() {
        return "farmerArmorTest.png";
    }

    public static IIcon forItem(IIconRegister register, Item item) {
        return forName(register, item.getUnlocalizedName().replaceAll("item\\.", ""));
    }

    public static IIcon forName(IIconRegister register, String name) {
        return register.registerIcon(name);
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
