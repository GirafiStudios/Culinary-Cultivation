package com.girafi.culinarycultivation.item;

import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.util.reference.Paths;
import com.google.common.collect.Maps;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Map;

public class ItemModMeatFood extends ItemFood {
    private final boolean cooked;

    public ItemModMeatFood(boolean cooked) {
        super(0, 0.0F, true);
        this.cooked = cooked;
        this.setHasSubtypes(true);
    }

    @Override
    public int getHealAmount(@Nonnull ItemStack stack) {
        MeatType meattype = MeatType.getMeatType(stack);
        if (meattype.isHaveRawMeat()) {
            return this.cooked && meattype.isHaveCookedMeat() ? meattype.getHealAmountCooked() : meattype.getHealAmountRaw();
        } else
            return meattype.isHaveCookedMeat() & !meattype.isHaveRawMeat() ? meattype.getHealAmountCooked() : meattype.getHealAmountRaw();
    }

    @Override
    public float getSaturationModifier(@Nonnull ItemStack stack) {
        MeatType meattype = MeatType.getMeatType(stack);
        if (meattype.isHaveRawMeat()) {
            return this.cooked && meattype.isHaveCookedMeat() ? meattype.getSaturationAmountCooked() : meattype.getSaturationAmountRaw();
        } else
            return meattype.isHaveCookedMeat() & !meattype.isHaveRawMeat() ? meattype.getSaturationAmountCooked() : meattype.getSaturationAmountRaw();
    }

    @Override
    protected void onFoodEaten(@Nonnull ItemStack stack, World world, @Nonnull EntityPlayer player) {
        MeatType meatType = MeatType.getMeatType(stack);
        double potionEffectProbability = (float) Math.random();

        if (potionEffectProbability <= 0.3F) {
            if (meatType == MeatType.CHICKEN_WING && stack.getItem() == ModItems.MEAT) {
                player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 440)); //22 seconds
            } else if (meatType == MeatType.DRUMSTICK && stack.getItem() == ModItems.MEAT) {
                player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 320)); //16 seconds
            } else if (meatType == MeatType.CHICKEN_NUGGET && stack.getItem() == ModItems.MEAT) {
                player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 160)); //8 seconds
            }
        }
        super.onFoodEaten(stack, world, player);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(@Nonnull Item item, CreativeTabs creativeTabs, NonNullList<ItemStack> list) {
        for (MeatType meattype : MeatType.values()) {
            if (!meattype.isHaveRawMeat() && this.cooked && meattype.isHaveCookedMeat()) {
                list.add(new ItemStack(this, 1, meattype.getMetadata()));
            }
            if (meattype.isHaveRawMeat()) {
                if (!this.cooked || meattype.isHaveCookedMeat()) {
                    list.add(new ItemStack(this, 1, meattype.getMetadata()));
                }
            }
        }
    }

    @Override
    @Nonnull
    public String getUnlocalizedName(@Nonnull ItemStack stack) {
        MeatType meattype = MeatType.getMeatType(stack);
        if (this.cooked && meattype.isHaveCookedMeat()) {
            return "item." + Paths.MOD_ASSETS + "meat_" + meattype.getMeatName() + "_cooked";
        } else {
            return this.getUnlocalizedName() + "_" + meattype.getMeatName();
        }
    }

    public enum MeatType {
        LAMB(0, "lamb", 2, 0.3F, 5, 1.1F),
        LEG_SHEEP(1, "leg_sheep", 2, 0.3F, 7, 0.6F),
        VEAL(2, "veal", 2, 0.3F, 6, 1.0F),
        ROAST(3, "roast", 3, 0.3F, 8, 0.8F),
        RIBS_BEEF(4, "ribs_beef", 3, 0.3F),
        RIBS(5, "ribs", 3, 0.3F, 7, 0.9F),
        HAM(6, "ham", 3, 0.3F, 8, 0.7F),
        BACON(7, "bacon", 1, 0.3F, 4, 0.6F),
        PATTY(8, "patty", 2, 0.3F, 6, 0.7F),
        CHICKEN_WING(9, "chicken_wing", 2, 0.3F, 5, 0.5F),
        DRUMSTICK(10, "drumstick", 2, 0.3F, 5, 0.6F),
        CHICKEN_NUGGET(11, "chicken_nugget", 1, 0.3F, 3, 0.5F),
        SQUID_TENTACLE(12, "squid_tentacle", 2, 0.2F, 4, 0.4F),
        SQUID_MANTLE(13, "squid_mantle", 2, 0.3F, 5, 0.4F),
        SQUID_RING(14, "squid_ring", 1, 0.1F, 2, 0.2F);

        private static final Map<Integer, MeatType> META_LOOKUP = Maps.newHashMap();
        private final int metadata;
        private final String name;
        private final int healAmountRaw;
        private final float saturationAmountRaw;
        private final int healAmountCooked;
        private final float saturationAmountCooked;
        private boolean haveRawMeat = false;
        private boolean haveCookedMeat = false;

        MeatType(int metadata, String name, int healAmountRaw, float saturationAmountRaw, int healAmountCooked, float saturationAmountCooked, boolean haveRawMeat, boolean haveCookedMeat) {
            this.metadata = metadata;
            this.name = name;
            this.healAmountRaw = healAmountRaw;
            this.saturationAmountRaw = saturationAmountRaw;
            this.healAmountCooked = healAmountCooked;
            this.saturationAmountCooked = saturationAmountCooked;
            this.haveRawMeat = haveRawMeat;
            this.haveCookedMeat = haveCookedMeat;
        }

        MeatType(int metadata, String name, int healAmountRaw, float saturationAmountRaw, int healAmountCooked, float saturationAmountCooked) {
            this(metadata, name, healAmountRaw, saturationAmountRaw, healAmountCooked, saturationAmountCooked, true, true);
        }

        MeatType(int metadata, String name, int healAmountRaw, float saturationAmountRaw) {
            this(metadata, name, healAmountRaw, saturationAmountRaw, 0, 0.0F, true, false);
        }

        public int getMetadata() {
            return this.metadata;
        }

        public String getMeatName() {
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

        public boolean isHaveCookedMeat() {
            return this.haveCookedMeat;
        }

        public boolean isHaveRawMeat() {
            return this.haveRawMeat;
        }

        public static MeatType getMeatTypeList(int meat) {
            MeatType meattype = META_LOOKUP.get(meat);
            return meattype == null ? LAMB : meattype;
        }

        public static MeatType getMeatType(@Nonnull ItemStack stack) {
            return stack.getItem() instanceof ItemModMeatFood ? getMeatTypeList(stack.getItemDamage()) : LAMB;
        }

        static {
            for (MeatType meatType : values()) {
                META_LOOKUP.put(meatType.getMetadata(), meatType);
            }
        }
    }
}