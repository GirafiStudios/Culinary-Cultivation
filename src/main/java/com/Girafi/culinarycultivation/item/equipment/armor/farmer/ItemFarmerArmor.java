package com.Girafi.culinarycultivation.item.equipment.armor.farmer;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.reference.Paths;
import com.Girafi.culinarycultivation.reference.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.util.EnumHelper;

import java.util.List;
import java.util.Locale;

public class ItemFarmerArmor extends ItemArmor implements ISpecialArmor {
    private final String armorPieceName;
    public static ArmorMaterial farmerArmorMaterial = EnumHelper.addArmorMaterial("farmerArmor", "farmerArmor", 9, new int[]{1, 2, 3, 2}, 12, SoundEvents.item_armor_equip_generic);

    public ItemFarmerArmor(EntityEquipmentSlot equipmentSlot, String name) {
        this(equipmentSlot, name, farmerArmorMaterial);
    }

    private ItemFarmerArmor(EntityEquipmentSlot equipmentSlot, String name, ArmorMaterial mat) {
        super(mat, 0, equipmentSlot);
        this.armorPieceName = name;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return type == null ? Paths.ARMOR_MODEL + armorPieceName + ".png" : Paths.ARMOR_MODEL + armorPieceName + "_overlay" + ".png";
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        if (source.isUnblockable()) {
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

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean b) {
        if (GuiScreen.isShiftKeyDown()) {
            addStringToTooltip(getArmorSetTitle(player), list);
            ItemStack[] stacks = getArmorSetStacks();
            for (int i = 0; i < stacks.length; i++) {
                addStringToTooltip((hasArmorSetPiece(player, i) ? TextFormatting.YELLOW : "") + " " + stacks[i].getDisplayName(), list);
            }
            addArmorStatsDesc(list);
        } else
            addStringToTooltip(I18n.translateToLocal(Reference.MOD_ID.toLowerCase(Locale.US) + ".misc.shift"), list);
    }

    private void addStringToTooltip(String s, List<String> tooltip) {
        tooltip.add(s.replaceAll("&", "\u00a7"));
    }

    private ItemStack[] armorSet;

    private ItemStack[] getArmorSetStacks() {
        if (armorSet == null)
            armorSet = new ItemStack[]{
                    new ItemStack(ModItems.farmerStrawhat),
                    new ItemStack(ModItems.farmerShirt),
                    new ItemStack(ModItems.farmerOveralls),
                    new ItemStack(ModItems.farmerBoots)
            };
        return armorSet;
    }

    public boolean hasFullArmorSet(EntityPlayer player) {
        return hasArmorSetPiece(player, 0) && hasArmorSetPiece(player, 1) && hasArmorSetPiece(player, 2) && hasArmorSetPiece(player, 3);
    }

    private boolean hasArmorSetPiece(EntityPlayer player, int i) {
        ItemStack stack = player.inventory.armorInventory[3 - i];
        if (stack == null) {
            return false;
        }
        switch (i) {
            case 0:
                return stack.getItem() == ModItems.farmerStrawhat;
            case 1:
                return stack.getItem() == ModItems.farmerShirt;
            case 2:
                return stack.getItem() == ModItems.farmerOveralls;
            case 3:
                return stack.getItem() == ModItems.farmerBoots;
        }
        return false;
    }

    private int getPiecesEquipped(EntityPlayer player) {
        int pieces = 0;
        for (int i = 0; i < 4; i++)
            if (hasArmorSetPiece(player, i)) {
                pieces++;
            }
        return pieces;
    }

    private String getArmorSetName() {
        return I18n.translateToLocal(Reference.MOD_ID.toLowerCase(Locale.US) + ".armorset.farmer.name");
    }

    private String getArmorSetTitle(EntityPlayer player) {
        return getArmorSetName() + " (" + getPiecesEquipped(player) + "/" + getArmorSetStacks().length + ")";
    }

    private void addArmorStatsDesc(List<String> list) {
        addStringToTooltip("", list);
        addStringToTooltip(I18n.translateToLocal(Reference.MOD_ID.toLowerCase(Locale.US) + ".armorset.farmer.desc"), list);
        addStringToTooltip(I18n.translateToLocal(Reference.MOD_ID.toLowerCase(Locale.US) + ".armorset.farmer.descFull"), list);
    }

    @Override
    public boolean hasColor(ItemStack stack) {
        return ((ItemFarmerArmor) stack.getItem()).getArmorMaterial() == ItemFarmerArmor.farmerArmorMaterial && (stack.hasTagCompound() && (stack.getTagCompound().hasKey("display", 10) && stack.getTagCompound().getCompoundTag("display").hasKey("color", 3)));
    }

    @Override
    public int getColor(ItemStack stack) {
        if (!this.hasColor(stack)) {
            if (stack.getItem() == ModItems.farmerShirt) {
                return 0x971212;
            }
            if (stack.getItem() == ModItems.farmerOveralls) {
                return 0x7d92c1;
            }
            if (stack.getItem() == ModItems.farmerBoots) {
                return 10511680;
            }
        } else {
            NBTTagCompound tag = stack.getTagCompound();

            if (tag != null) {
                NBTTagCompound displayTag = tag.getCompoundTag("display");

                if (displayTag != null && displayTag.hasKey("color", 3)) {
                    return displayTag.getInteger("color");
                }
            }
        }
        return -1;
    }

    @Override
    public void removeColor(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag != null) {
            NBTTagCompound displayTag = tag.getCompoundTag("display");

            if (displayTag.hasKey("color")) {
                displayTag.removeTag("color");
            }
        }
    }

    @Override
    public void setColor(ItemStack stack, int color) {
        NBTTagCompound tag = stack.getTagCompound();

        if (tag == null) {
            tag = new NBTTagCompound();
            stack.setTagCompound(tag);
        }
        NBTTagCompound displayTag = tag.getCompoundTag("display");

        if (!tag.hasKey("display", 10)) {
            tag.setTag("display", displayTag);
        }
        displayTag.setInteger("color", color);
    }
}