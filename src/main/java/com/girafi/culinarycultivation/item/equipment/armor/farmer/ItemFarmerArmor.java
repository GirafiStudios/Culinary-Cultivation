package com.girafi.culinarycultivation.item.equipment.armor.farmer;

import com.girafi.culinarycultivation.api.CulinaryCultivationAPI;
import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.util.reference.Paths;
import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.ISpecialArmor;

import java.util.List;

public class ItemFarmerArmor extends ItemArmor implements ISpecialArmor {
    private final String armorPieceName;

    public ItemFarmerArmor(EntityEquipmentSlot equipmentSlot, String name) {
        this(equipmentSlot, name, CulinaryCultivationAPI.FARMER_ARMOR_MATERIAL);
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
            addStringToTooltip(I18n.translateToLocal(Reference.MOD_ID + ".misc.shift"), list);
    }

    private void addStringToTooltip(String s, List<String> tooltip) {
        tooltip.add(s.replaceAll("&", "\u00a7"));
    }

    private ItemStack[] armorSet;

    private ItemStack[] getArmorSetStacks() {
        if (armorSet == null)
            armorSet = new ItemStack[]{
                    new ItemStack(ModItems.FARMER_STRAWHAT),
                    new ItemStack(ModItems.FARMER_SHIRT),
                    new ItemStack(ModItems.FARMER_OVERALLS),
                    new ItemStack(ModItems.FARMER_BOOTS)
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
                return stack.getItem() == ModItems.FARMER_STRAWHAT;
            case 1:
                return stack.getItem() == ModItems.FARMER_SHIRT;
            case 2:
                return stack.getItem() == ModItems.FARMER_OVERALLS;
            case 3:
                return stack.getItem() == ModItems.FARMER_BOOTS;
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
        return I18n.translateToLocal(Reference.MOD_ID + ".armorset.farmer.name");
    }

    private String getArmorSetTitle(EntityPlayer player) {
        return getArmorSetName() + " (" + getPiecesEquipped(player) + "/" + getArmorSetStacks().length + ")";
    }

    private void addArmorStatsDesc(List<String> list) {
        addStringToTooltip("", list);
        addStringToTooltip(I18n.translateToLocal(Reference.MOD_ID + ".armorset.farmer.desc"), list);
        addStringToTooltip(I18n.translateToLocal(Reference.MOD_ID + ".armorset.farmer.descFull"), list);
    }

    @Override
    public boolean hasColor(ItemStack stack) {
        return ((ItemFarmerArmor) stack.getItem()).getArmorMaterial() == CulinaryCultivationAPI.FARMER_ARMOR_MATERIAL && (stack.hasTagCompound() && (stack.getTagCompound().hasKey("display", 10) && stack.getTagCompound().getCompoundTag("display").hasKey("color", 3)));
    }

    @Override
    public int getColor(ItemStack stack) {
        if (!this.hasColor(stack)) {
            if (stack.getItem() == ModItems.FARMER_SHIRT) {
                return 0x971212;
            }
            if (stack.getItem() == ModItems.FARMER_OVERALLS) {
                return 0x7d92c1;
            }
            if (stack.getItem() == ModItems.FARMER_BOOTS) {
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