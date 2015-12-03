package com.Girafi.culinarycultivation.item;

import com.google.common.collect.Maps;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Map;

public class ItemModFishFood extends SourceFood {
    private final boolean cooked;
    private static double potionEffectProbability;

    public ItemModFishFood(boolean cooked) {
        super(0, 0.0F, false);
        this.cooked = cooked;
        setUnlocalizedName("fish");
    }

    public int getHealAmount(ItemStack stack) {
        FishType fishtype = FishType.getFishType(stack);
        if (fishtype.isHaveRawFish()) {
            return this.cooked && fishtype.isHaveCookedFish() ? fishtype.getHealAmountCooked() : fishtype.getHealAmountRaw();
        } else
            return fishtype.isHaveCookedFish() & !fishtype.isHaveRawFish() ? fishtype.getHealAmountCooked() : fishtype.getHealAmountRaw();
    }

    public float getSaturationModifier(ItemStack stack) {
        FishType fishtype = FishType.getFishType(stack);
        if (fishtype.isHaveRawFish()) {
            return this.cooked && fishtype.isHaveCookedFish() ? fishtype.getSaturationAmountCooked() : fishtype.getSaturationAmountRaw();
        } else
            return fishtype.isHaveCookedFish() & !fishtype.isHaveRawFish() ? fishtype.getSaturationAmountCooked() : fishtype.getSaturationAmountRaw();
    }

    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        FishType fishType = FishType.getFishType(stack);
        potionEffectProbability = Math.random();

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
    public void getSubItems(Item item, CreativeTabs creativeTabs, List subItems) {
        FishType[] afishtype = FishType.values();
        int i = afishtype.length;

        for (int j = 0; j < i; ++j) {
            FishType fishtype = afishtype[j];
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

    public String getUnlocalizedName(ItemStack stack) {
        FishType fishtype = FishType.getFishType(stack);
        if (fishtype.isHaveRawFish()) {
            return this.getUnlocalizedName() + "." + fishtype.getUnlocalizedName() + "." + (this.cooked && fishtype.isHaveCookedFish() ? "cooked" : "raw");
        } else
            return this.getUnlocalizedName() + "." + fishtype.getUnlocalizedName() + "." + (this.cooked && fishtype.isHaveCookedFish() ? "cooked" : "cooked");
    }

    public static enum FishType {
        MACKEREL(0, "mackerel", 2, 0.1F, 6, 0.7F),
        TUNA(1, "tuna", 3, 0.2F, 6, 0.6F),
        TROUT(2, "trout", 2, 0.1F, 6, 0.4F),
        HERRING(3, "herring", 2, 0.1F, 6, 0.4F),
        PLAICE(4, "plaice", 2, 0.2F, 4, 0.6F),
        SMALLSQUID(5, "smallSquid", 2, 0.3F),
        CLOWNFISH(6, "clownfish", 0.2F, 3),
        FILLET(7, "fillet", 2, 0.3F, 5, 0.6F);
        private static final Map META_LOOKUP = Maps.newHashMap();
        private final int metaData;
        private final String unlocalizedName;
        private final int healAmountRaw;
        private final float saturationAmountRaw;
        private final int healAmountCooked;
        private final float saturationAmountCooked;
        private boolean haveRawFish = false;
        private boolean haveCookedFish = false;

        private FishType(int metaData, String unlocalizedName, int healAmountRaw, float saturationAmountRaw, int healAmountCooked, float saturationAmountCooked) {
            this.metaData = metaData;
            this.unlocalizedName = unlocalizedName;
            this.healAmountRaw = healAmountRaw;
            this.saturationAmountRaw = saturationAmountRaw;
            this.healAmountCooked = healAmountCooked;
            this.saturationAmountCooked = saturationAmountCooked;
            this.haveRawFish = true;
            this.haveCookedFish = true;
        }

        private FishType(int metaData, String unlocalizedName, int healAmountRaw, float saturationAmountRaw) {
            this.metaData = metaData;
            this.unlocalizedName = unlocalizedName;
            this.healAmountRaw = healAmountRaw;
            this.saturationAmountRaw = saturationAmountRaw;
            this.healAmountCooked = 0;
            this.saturationAmountCooked = 0.0F;
            this.haveRawFish = true;
            this.haveCookedFish = false;
        }

        private FishType(int metaData, String unlocalizedName, float saturationAmountCooked, int healAmountCooked) {
            this.metaData = metaData;
            this.unlocalizedName = unlocalizedName;
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

        public String getUnlocalizedName() {
            return this.unlocalizedName;
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
            FishType fishtype = (FishType) META_LOOKUP.get(Integer.valueOf(fishType));
            return fishtype == null ? MACKEREL : fishtype;
        }

        public static FishType getFishType(ItemStack stack) {
            return stack.getItem() instanceof ItemModFishFood ? getFishTypeList(stack.getItemDamage()) : MACKEREL;
        }

        static {
            FishType[] var0 = values();
            int var1 = var0.length;
            for (int var2 = 0; var2 < var1; ++var2) {
                FishType var3 = var0[var2];
                META_LOOKUP.put(Integer.valueOf(var3.getMetaData()), var3);
            }
        }
    }
}