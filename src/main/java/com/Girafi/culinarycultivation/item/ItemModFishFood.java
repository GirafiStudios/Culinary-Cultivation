package com.Girafi.culinarycultivation.item;

import com.Girafi.culinarycultivation.reference.Paths;
import com.google.common.collect.Maps;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Map;

public class ItemModFishFood extends ItemFood {
    private final boolean cooked;

    public ItemModFishFood(boolean cooked) {
        super(0, 0.0F, false);
        this.cooked = cooked;
    }

    @Override
    public int getHealAmount(ItemStack stack) {
        FishType fishtype = FishType.getFishType(stack);
        if (fishtype.isHaveRawFish()) {
            return this.cooked && fishtype.isHaveCookedFish() ? fishtype.getHealAmountCooked() : fishtype.getHealAmountRaw();
        } else
            return fishtype.isHaveCookedFish() & !fishtype.isHaveRawFish() ? fishtype.getHealAmountCooked() : fishtype.getHealAmountRaw();
    }

    @Override
    public float getSaturationModifier(ItemStack stack) {
        FishType fishtype = FishType.getFishType(stack);
        if (fishtype.isHaveRawFish()) {
            return this.cooked && fishtype.isHaveCookedFish() ? fishtype.getSaturationAmountCooked() : fishtype.getSaturationAmountRaw();
        } else
            return fishtype.isHaveCookedFish() & !fishtype.isHaveRawFish() ? fishtype.getSaturationAmountCooked() : fishtype.getSaturationAmountRaw();
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        FishType fishType = FishType.getFishType(stack);
        double potionEffectProbability = Math.random();

        if (fishType == FishType.CLOWNFISH) {
            if (potionEffectProbability <= 0.001F) {
                player.addPotionEffect(new PotionEffect(Potion.poison.id, 60));
            }
            if (potionEffectProbability <= 0.08F) {
                player.addPotionEffect(new PotionEffect(Potion.confusion.id, 160));
            }
        }
        super.onFoodEaten(stack, world, player);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> subItems) {
        for (FishType fishtype : FishType.values()) {
            if (!fishtype.isHaveRawFish() && this.cooked && fishtype.isHaveCookedFish()) {
                subItems.add(new ItemStack(this, 1, fishtype.getMetaData()));
            }
            if (fishtype.haveRawFish) {
                if (!this.cooked || fishtype.isHaveCookedFish()) {
                    subItems.add(new ItemStack(this, 1, fishtype.getMetaData()));
                }
            }

        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        FishType fishtype = FishType.getFishType(stack);
        if (fishtype.isHaveRawFish() & !this.cooked) {
            return this.getUnlocalizedName() + "_" + fishtype.getFishName();
        } else {
            return "item." + Paths.ModAssets + "fish_" + fishtype.getFishName() + "_cooked";
        }
    }

    public enum FishType {
        MACKEREL(0, "mackerel", 2, 0.1F, 6, 0.7F),
        TUNA(1, "tuna", 3, 0.2F, 6, 0.6F),
        TROUT(2, "trout", 2, 0.1F, 6, 0.4F),
        HERRING(3, "herring", 2, 0.1F, 6, 0.4F),
        PLAICE(4, "plaice", 2, 0.2F, 4, 0.6F),
        SMALLSQUID(5, "smallSquid", 2, 0.3F),
        CLOWNFISH(6, "clownfish", 0.2F, 3),
        FILLET(7, "fillet", 2, 0.3F, 5, 0.6F);
        private static final Map<Integer, FishType> META_LOOKUP = Maps.newHashMap();
        private final int metaData;
        private final String name;
        private final int healAmountRaw;
        private final float saturationAmountRaw;
        private final int healAmountCooked;
        private final float saturationAmountCooked;
        private boolean haveRawFish = false;
        private boolean haveCookedFish = false;

        FishType(int metaData, String name, int healAmountRaw, float saturationAmountRaw, int healAmountCooked, float saturationAmountCooked) {
            this.metaData = metaData;
            this.name = name;
            this.healAmountRaw = healAmountRaw;
            this.saturationAmountRaw = saturationAmountRaw;
            this.healAmountCooked = healAmountCooked;
            this.saturationAmountCooked = saturationAmountCooked;
            this.haveRawFish = true;
            this.haveCookedFish = true;
        }

        FishType(int metaData, String name, int healAmountRaw, float saturationAmountRaw) {
            this.metaData = metaData;
            this.name = name;
            this.healAmountRaw = healAmountRaw;
            this.saturationAmountRaw = saturationAmountRaw;
            this.healAmountCooked = 0;
            this.saturationAmountCooked = 0.0F;
            this.haveRawFish = true;
            this.haveCookedFish = false;
        }

        FishType(int metaData, String name, float saturationAmountCooked, int healAmountCooked) {
            this.metaData = metaData;
            this.name = name;
            this.healAmountRaw = 0;
            this.saturationAmountRaw = 0.0F;
            this.healAmountCooked = healAmountCooked;
            this.saturationAmountCooked = saturationAmountCooked;
            this.haveRawFish = false;
            this.haveCookedFish = true;
        }

        public int getMetaData() {
            return this.metaData;
        }

        public String getFishName() {
            return this.name;
        }

        public int getHealAmountRaw() {
            return this.healAmountRaw;
        }

        public float getSaturationAmountRaw() {
            return this.saturationAmountRaw;
        }

        public int getHealAmountCooked() {
            return this.healAmountCooked;
        }

        public float getSaturationAmountCooked() {
            return this.saturationAmountCooked;
        }

        public boolean isHaveCookedFish() {
            return this.haveCookedFish;
        }

        public boolean isHaveRawFish() {
            return this.haveRawFish;
        }

        public static FishType getFishTypeList(int fishType) {
            FishType fishtype = META_LOOKUP.get(fishType);
            return fishtype == null ? MACKEREL : fishtype;
        }

        public static FishType getFishType(ItemStack stack) {
            return stack.getItem() instanceof ItemModFishFood ? getFishTypeList(stack.getItemDamage()) : MACKEREL;
        }

        static {
            for (FishType fishType : values()) {
                META_LOOKUP.put(fishType.getMetaData(), fishType);
            }
        }
    }
}