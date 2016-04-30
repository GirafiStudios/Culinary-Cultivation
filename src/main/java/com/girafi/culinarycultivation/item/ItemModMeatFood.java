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
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Map;

public class ItemModMeatFood extends ItemFood {
    private final boolean cooked;

    public ItemModMeatFood(boolean cooked) {
        super(0, 0.0F, true); //TODO Fix what Food wolves can eat
        this.cooked = cooked;
    }

    @Override
    public int getHealAmount(ItemStack stack) {
        MeatType meattype = MeatType.getMeatType(stack);
        if (meattype.isHaveRawMeat()) {
            return this.cooked && meattype.isHaveCookedMeat() ? meattype.getHealAmountCooked() : meattype.getHealAmountRaw();
        } else
            return meattype.isHaveCookedMeat() & !meattype.isHaveRawMeat() ? meattype.getHealAmountCooked() : meattype.getHealAmountRaw();
    }

    @Override
    public float getSaturationModifier(ItemStack stack) {
        MeatType meattype = MeatType.getMeatType(stack);
        if (meattype.isHaveRawMeat()) {
            return this.cooked && meattype.isHaveCookedMeat() ? meattype.getSaturationAmountCooked() : meattype.getSaturationAmountRaw();
        } else
            return meattype.isHaveCookedMeat() & !meattype.isHaveRawMeat() ? meattype.getSaturationAmountCooked() : meattype.getSaturationAmountRaw();
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        MeatType meatType = MeatType.getMeatType(stack);
        double potionEffectProbability = (float) Math.random();

        if (potionEffectProbability <= 0.3F) {
            if (meatType == MeatType.CHICKENWING && stack.getItem() == ModItems.MEAT) {
                player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 440)); //22 seconds
            } else if (meatType == MeatType.DRUMSTICK && stack.getItem() == ModItems.MEAT) {
                player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 320)); //16 seconds
            } else if (meatType == MeatType.CHICKENNUGGET && stack.getItem() == ModItems.MEAT) {
                player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 160)); //8 seconds
            }
        }
        super.onFoodEaten(stack, world, player);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
        for (MeatType meattype : MeatType.values()) {
            if (!meattype.isHaveRawMeat() && this.cooked && meattype.isHaveCookedMeat()) {
                list.add(new ItemStack(this, 1, meattype.getMetaData()));
            }
            if (meattype.isHaveRawMeat()) {
                if (!this.cooked || meattype.isHaveCookedMeat()) {
                    list.add(new ItemStack(this, 1, meattype.getMetaData()));
                }
            }
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        MeatType meattype = MeatType.getMeatType(stack);
        if (this.cooked && meattype.isHaveCookedMeat()) {
            return "item." + Paths.MOD_ASSETS + "meat_" + meattype.getMeatName() + "_cooked";
        } else {
            return this.getUnlocalizedName() + "_" + meattype.getMeatName();
        }
    }

    public enum MeatType {
        LAMB(0, "lamb", 2, 0.3F, 5, 1.1F),
        LEGSHEEP(1, "legSheep", 2, 0.3F, 7, 0.6F),
        VEAL(2, "veal", 2, 0.3F, 6, 1.0F),
        ROAST(3, "roast", 3, 0.3F, 8, 0.8F),
        RIBSBEEF(4, "ribsBeef", 3, 0.3F),
        RIBS(5, "ribs", 3, 0.3F, 7, 0.9F),
        HAM(6, "ham", 3, 0.3F, 8, 0.7F),
        BACON(7, "bacon", 1, 0.3F, 4, 0.6F),
        PATTY(8, "patty", 2, 0.3F, 6, 0.7F),
        CHICKENWING(9, "chickenWing", 2, 0.3F, 5, 0.5F),
        DRUMSTICK(10, "drumstick", 2, 0.3F, 5, 0.6F),
        CHICKENNUGGET(11, "chickenNugget", 1, 0.3F, 3, 0.5F),
        SQUIDTENTACLE(12, "squidTentacle", 2, 0.2F, 4, 0.4F),
        SQUIDMANTLE(13, "squidMantle", 2, 0.3F, 5, 0.4F),
        SQUIDRING(14, "squidRing", 1, 0.1F, 2, 0.2F);

        private static final Map<Integer, MeatType> META_LOOKUP = Maps.newHashMap();
        private final int metaData;
        private final String name;
        private final int healAmountRaw;
        private final float saturationAmountRaw;
        private final int healAmountCooked;
        private final float saturationAmountCooked;
        private boolean haveRawMeat = false;
        private boolean haveCookedMeat = false;

        MeatType(int metaData, String name, int healAmountRaw, float saturationAmountRaw, int healAmountCooked, float saturationAmountCooked) {
            this.metaData = metaData;
            this.name = name;
            this.healAmountRaw = healAmountRaw;
            this.saturationAmountRaw = saturationAmountRaw;
            this.healAmountCooked = healAmountCooked;
            this.saturationAmountCooked = saturationAmountCooked;
            this.haveRawMeat = true;
            this.haveCookedMeat = true;
        }

        MeatType(int metaData, String name, int healAmountRaw, float saturationAmountRaw) {
            this.metaData = metaData;
            this.name = name;
            this.healAmountRaw = healAmountRaw;
            this.saturationAmountRaw = saturationAmountRaw;
            this.healAmountCooked = 0;
            this.saturationAmountCooked = 0.0F;
            this.haveRawMeat = true;
            this.haveCookedMeat = false;
        }

        MeatType(int metaData, String name, float saturationAmountCooked, int healAmountCooked) {
            this.metaData = metaData;
            this.name = name;
            this.healAmountRaw = 0;
            this.saturationAmountRaw = 0.0F;
            this.healAmountCooked = healAmountCooked;
            this.saturationAmountCooked = saturationAmountCooked;
            this.haveRawMeat = false;
            this.haveCookedMeat = true;
        }

/*        MeatType(int metaData, String name, int healAmountRaw, float saturationAmountRaw, int healAmountCooked, float saturationAmountCooked, int healAmountSeasoned, float saturationAmountSeasoned) { //W.I.P. Seasoned meat
            this.metaData = metaData;
            this.name = name;
            this.healAmountRaw = healAmountRaw;
            this.saturationAmountRaw = saturationAmountRaw;
            this.healAmountCooked = healAmountCooked;
            this.saturationAmountCooked = saturationAmountCooked;
            this.haveRawMeat = true;
            this.haveCookedMeat = true;
        }*/

        public int getMetaData() {
            return this.metaData;
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

        public static MeatType getMeatType(ItemStack stack) {
            return stack.getItem() instanceof ItemModMeatFood ? getMeatTypeList(stack.getItemDamage()) : LAMB;
        }

        static {
            for (MeatType meatType : values()) {
                META_LOOKUP.put(meatType.getMetaData(), meatType);
            }
        }
    }
}