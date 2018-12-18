package com.girafi.culinarycultivation.item;

import com.girafi.culinarycultivation.api.item.IOreDictEntry;
import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.util.OreDictHelper;
import com.girafi.culinarycultivation.util.reference.Paths;
import com.google.common.collect.Maps;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Map;

public class ItemModFishFood extends ItemFood implements IOreDictEntry {
    private final boolean cooked;

    public ItemModFishFood(boolean cooked) {
        super(0, 0.0F, false);
        this.cooked = cooked;
        this.setHasSubtypes(true);
    }

    @Override
    public int getHealAmount(@Nonnull ItemStack stack) {
        FishType fishtype = FishType.getFishType(stack);
        if (fishtype.isHaveRawFish()) {
            return this.cooked && fishtype.isHaveCookedFish() ? fishtype.getHealAmountCooked() : fishtype.getHealAmountRaw();
        } else
            return fishtype.isHaveCookedFish() & !fishtype.isHaveRawFish() ? fishtype.getHealAmountCooked() : fishtype.getHealAmountRaw();
    }

    @Override
    public float getSaturationModifier(@Nonnull ItemStack stack) {
        FishType fishtype = FishType.getFishType(stack);
        if (fishtype.isHaveRawFish()) {
            return this.cooked && fishtype.isHaveCookedFish() ? fishtype.getSaturationAmountCooked() : fishtype.getSaturationAmountRaw();
        } else
            return fishtype.isHaveCookedFish() & !fishtype.isHaveRawFish() ? fishtype.getSaturationAmountCooked() : fishtype.getSaturationAmountRaw();
    }

    @Override
    protected void onFoodEaten(@Nonnull ItemStack stack, World world, @Nonnull EntityPlayer player) {
        FishType fishType = FishType.getFishType(stack);
        double potionEffectProbability = Math.random();

        if (fishType == FishType.CLOWNFISH) {
            if (potionEffectProbability <= 0.001F) {
                player.addPotionEffect(new PotionEffect(MobEffects.POISON, 60));
            }
            if (potionEffectProbability <= 0.08F) {
                player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 160));
            }
        }
        super.onFoodEaten(stack, world, player);
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs creativeTabs, @Nonnull NonNullList<ItemStack> subItems) {
        for (FishType fishtype : FishType.values()) {
            if (!fishtype.isHaveRawFish() && this.cooked && fishtype.isHaveCookedFish()) {
                subItems.add(new ItemStack(this, 1, fishtype.getMetadata()));
            }
            if (fishtype.haveRawFish) {
                if (!this.cooked || fishtype.isHaveCookedFish()) {
                    subItems.add(new ItemStack(this, 1, fishtype.getMetadata()));
                }
            }

        }
    }

    @Override
    @Nonnull
    public String getTranslationKey(@Nonnull ItemStack stack) {
        FishType fishtype = FishType.getFishType(stack);
        if (fishtype.isHaveRawFish() & !this.cooked) {
            return this.getTranslationKey() + "_" + fishtype.getFishName();
        } else {
            return "item." + Paths.MOD_ASSETS + "fish_" + fishtype.getFishName() + "_cooked";
        }
    }

    @Override
    public void getOreDictEntries() {
        for (FishType fishtype : FishType.values()) {
            int metadata = fishtype.getMetadata();
            String name = fishtype.getFishName();
            if (fishtype.isHaveRawFish() && metadata != FishType.FILLET.getMetadata() && metadata != FishType.SMALL_SQUID.getMetadata()) {
                OreDictHelper.add(ModItems.FISH, metadata, "filletFish");
                OreDictHelper.add(ModItems.FISH, metadata, "fish");
            }
            OreDictHelper.add(ModItems.FISH, metadata, "food", name + "Raw");
            OreDictHelper.add(ModItems.COOKED_FISH, metadata, "food", name + "Cooked");
        }
        OreDictHelper.add("filletFish", new ItemStack(Items.FISH, 1, ItemFishFood.FishType.COD.getMetadata()), new ItemStack(Items.FISH, 1, ItemFishFood.FishType.SALMON.getMetadata()), new ItemStack(Items.FISH, 1, ItemFishFood.FishType.CLOWNFISH.getMetadata()));
    }

    public enum FishType {
        MACKEREL(0, "mackerel", 2, 0.1F, 6, 0.7F),
        TUNA(1, "tuna", 3, 0.2F, 6, 0.6F),
        TROUT(2, "trout", 2, 0.1F, 6, 0.4F),
        HERRING(3, "herring", 2, 0.1F, 6, 0.4F),
        PLAICE(4, "plaice", 2, 0.2F, 4, 0.6F),
        SMALL_SQUID(5, "small_squid", 2, 0.3F),
        CLOWNFISH(6, "clownfish", 0.2F, 3),
        FILLET(7, "fillet", 2, 0.3F, 5, 0.6F);
        private static final Map<Integer, FishType> META_LOOKUP = Maps.newHashMap();
        private final int metadata;
        private final String name;
        private final int healAmountRaw;
        private final float saturationAmountRaw;
        private final int healAmountCooked;
        private final float saturationAmountCooked;
        private boolean haveRawFish = false;
        private boolean haveCookedFish = false;

        FishType(int metadata, String name, int healAmountRaw, float saturationAmountRaw, int healAmountCooked, float saturationAmountCooked, boolean haveRawFish, boolean haveCookedFish) {
            this.metadata = metadata;
            this.name = name;
            this.healAmountRaw = healAmountRaw;
            this.saturationAmountRaw = saturationAmountRaw;
            this.healAmountCooked = healAmountCooked;
            this.saturationAmountCooked = saturationAmountCooked;
            this.haveRawFish = haveRawFish;
            this.haveCookedFish = haveCookedFish;
        }

        FishType(int metadata, String name, int healAmountRaw, float saturationAmountRaw, int healAmountCooked, float saturationAmountCooked) {
            this(metadata, name, healAmountRaw, saturationAmountRaw, healAmountCooked, saturationAmountCooked, true, true);
        }

        FishType(int metadata, String name, int healAmountRaw, float saturationAmountRaw) {
            this(metadata, name, healAmountRaw, saturationAmountRaw, 0, 0.0F, true, false);
        }

        FishType(int metadata, String name, float saturationAmountCooked, int healAmountCooked) {
            this(metadata, name, 0, 0.0F, healAmountCooked, saturationAmountCooked, false, true);
        }

        public int getMetadata() {
            return this.metadata;
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

        public static FishType getFishType(@Nonnull ItemStack stack) {
            return stack.getItem() instanceof ItemModFishFood ? getFishTypeList(stack.getItemDamage()) : MACKEREL;
        }

        static {
            for (FishType fishType : values()) {
                META_LOOKUP.put(fishType.getMetadata(), fishType);
            }
        }
    }
}