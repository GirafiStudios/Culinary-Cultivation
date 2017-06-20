package com.girafi.culinarycultivation.item.equipment.armor.farmer;

import com.girafi.culinarycultivation.api.CulinaryCultivationAPI;
import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.util.LogHelper;
import com.girafi.culinarycultivation.util.StringUtil;
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
import net.minecraft.util.FoodStats;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.List;

public class ItemFarmerArmor extends ItemArmor implements ISpecialArmor {
    private static final Field EXHAUSTION = ReflectionHelper.findField(FoodStats.class, "field_75126_c", "foodExhaustionLevel");
    private final String armorPieceName;
    private ItemStack[] armorSet;

    public ItemFarmerArmor(EntityEquipmentSlot equipmentSlot, String name) {
        this(equipmentSlot, name, CulinaryCultivationAPI.FARMER_ARMOR_MATERIAL);
    }

    private ItemFarmerArmor(EntityEquipmentSlot equipmentSlot, String name, ArmorMaterial mat) {
        super(mat, 0, equipmentSlot);
        this.armorPieceName = name;
    }

    public static boolean hasArmorSetPiece(EntityPlayer player, int i) {
        ItemStack stack = player.inventory.armorInventory.get(3 - i);
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

    @Override
    @Nonnull
    public String getArmorTexture(@Nonnull ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return type == null ? Paths.ARMOR_MODEL + armorPieceName + ".png" : Paths.ARMOR_MODEL + armorPieceName + "_overlay" + ".png";
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, @Nonnull ItemStack stack) {
        int exhaustionReductionChance;
        if (hasFullArmorSet(player)) {
            exhaustionReductionChance = 15;
        } else {
            exhaustionReductionChance = 2;
        }
        if (world.rand.nextInt(100) == exhaustionReductionChance && getExhaustionLevel(player) > 0 && player.onGround) {
            player.addExhaustion(-(getExhaustionLevel(player) / 100) * 0.15F);
        }
    }

    private float getExhaustionLevel(EntityPlayer player) {
        try {
            return EXHAUSTION.getFloat(player.getFoodStats());
        } catch (Exception ignored) {
            LogHelper.error("Farmer Armor could not reduce exhaustion properly.");
        }
        return 0;
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, @Nonnull ItemStack armor, DamageSource source, double damage, int slot) {
        if (source.isUnblockable()) {
            return new ArmorProperties(0, 0, 0);
        }
        return new ArmorProperties(0, damageReduceAmount / 25D, armor.getMaxDamage() + 1 - armor.getItemDamage());
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, @Nonnull ItemStack armor, int slot) {
        return damageReduceAmount;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, @Nonnull ItemStack stack, DamageSource source, int damage, int slot) {
        stack.damageItem(damage, entity);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        if (GuiScreen.isShiftKeyDown()) {
            tooltip.add(StringUtil.formatColorCode(Reference.MOD_ID + ".armorset.farmer.name") + " (" + getPiecesEquipped(player) + "/" + getArmorSetStacks().length + ")");
            ItemStack[] stacks = getArmorSetStacks();
            for (int i = 0; i < stacks.length; i++) {
                tooltip.add((hasArmorSetPiece(player, i) ? TextFormatting.YELLOW : "") + " " + stacks[i].getDisplayName());
            }
            tooltip.add("");
            tooltip.add(StringUtil.translateToLocal(Reference.MOD_ID + ".armorset.farmer.desc"));
            tooltip.add(StringUtil.translateToLocal(Reference.MOD_ID + ".armorset.farmer.descFull"));
        } else {
            tooltip.add(StringUtil.shiftTooltip());
        }
    }

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

    private boolean hasFullArmorSet(EntityPlayer player) {
        return hasArmorSetPiece(player, 0) && hasArmorSetPiece(player, 1) && hasArmorSetPiece(player, 2) && hasArmorSetPiece(player, 3);
    }

    private int getPiecesEquipped(EntityPlayer player) {
        int pieces = 0;
        for (int i = 0; i < 4; i++)
            if (hasArmorSetPiece(player, i)) {
                pieces++;
            }
        return pieces;
    }

    @Override
    public boolean hasColor(@Nonnull ItemStack stack) {
        return ((ItemFarmerArmor) stack.getItem()).getArmorMaterial() == CulinaryCultivationAPI.FARMER_ARMOR_MATERIAL && (stack.hasTagCompound() && (stack.getTagCompound().hasKey("display", 10) && stack.getTagCompound().getCompoundTag("display").hasKey("color", 3)));
    }

    @Override
    public int getColor(@Nonnull ItemStack stack) {
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

                if (displayTag.hasKey("color", 3)) {
                    return displayTag.getInteger("color");
                }
            }
        }
        return -1;
    }

    @Override
    public void removeColor(@Nonnull ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag != null) {
            NBTTagCompound displayTag = tag.getCompoundTag("display");

            if (displayTag.hasKey("color")) {
                displayTag.removeTag("color");
            }
        }
    }

    @Override
    public void setColor(@Nonnull ItemStack stack, int color) {
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