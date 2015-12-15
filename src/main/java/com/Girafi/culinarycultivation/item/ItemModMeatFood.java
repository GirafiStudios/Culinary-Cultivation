package com.Girafi.culinarycultivation.item;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.reference.Paths;
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
import org.apache.commons.lang3.text.WordUtils;

import java.util.List;
import java.util.Map;

public class ItemModMeatFood extends SourceFood {
    private final boolean cooked;
    private static double potionEffectProbability;

    public ItemModMeatFood(boolean cooked) {
        super(0, 0.0F, true); //TODO Fix what Food wolves can eat
        this.cooked = cooked;
    }

    public int getHealAmount(ItemStack stack) {
        MeatType meattype = MeatType.getMeatType(stack);
        if (meattype.isHaveRawMeat()) {
            return this.cooked && meattype.isHaveCookedMeat() ? meattype.getHealAmountCooked() : meattype.getHealAmountRaw();
        } else
            return meattype.isHaveCookedMeat() & !meattype.isHaveRawMeat() ? meattype.getHealAmountCooked() : meattype.getHealAmountRaw();
    }

    public float getSaturationModifier(ItemStack stack) {
        MeatType meattype = MeatType.getMeatType(stack);
        if (meattype.isHaveRawMeat()) {
            return this.cooked && meattype.isHaveCookedMeat() ? meattype.getSaturationAmountCooked() : meattype.getSaturationAmountRaw();
        } else
            return meattype.isHaveCookedMeat() & !meattype.isHaveRawMeat() ? meattype.getSaturationAmountCooked() : meattype.getSaturationAmountRaw();
    }

    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        MeatType meatType = MeatType.getMeatType(stack);
        potionEffectProbability = (float) Math.random();

        if (potionEffectProbability <= 0.3F) {
            if (meatType == MeatType.CHICKENWING && stack.getItem() == ModItems.meat) {
                player.addPotionEffect(new PotionEffect(Potion.hunger.id, 440)); //22 seconds
            } else if (meatType == MeatType.DRUMSTICK && stack.getItem() == ModItems.meat) {
                player.addPotionEffect(new PotionEffect(Potion.hunger.id, 320)); //16 seconds
            } else if (meatType == MeatType.CHICKENNUGGET && stack.getItem() == ModItems.meat) {
                player.addPotionEffect(new PotionEffect(Potion.hunger.id, 160)); //8 seconds
            }
        }
        super.onFoodEaten(stack, world, player);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b) {
        MeatType meatType = MeatType.getMeatType(stack);
        if (meatType == MeatType.LAMB) {
            //Just kept for later
        }
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        MeatType[] ameattype = MeatType.values();
        int i = ameattype.length;

        for (int j = 0; j < i; ++j) {
            MeatType meattype = ameattype[j];
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

    public String getUnlocalizedName(ItemStack stack) {
        MeatType meattype = MeatType.getMeatType(stack);
        return "item." + Paths.ModAssets + meattype.getUnlocalizedName() + WordUtils.capitalize(this.cooked && meattype.isHaveCookedMeat() ? "cooked" : "raw");
}

    public static enum MeatType {
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

        private static final Map META_LOOKUP = Maps.newHashMap();
        private final int metaData;
        private final String unlocalizedName;
        private final int healAmountRaw;
        private final float saturationAmountRaw;
        private final int healAmountCooked;
        private final float saturationAmountCooked;
        private boolean haveRawMeat = false;
        private boolean haveCookedMeat = false;

        private MeatType(int metaData, String unlocalizedName, int healAmountRaw, float saturationAmountRaw, int healAmountCooked, float saturationAmountCooked) {
            this.metaData = metaData;
            this.unlocalizedName = unlocalizedName;
            this.healAmountRaw = healAmountRaw;
            this.saturationAmountRaw = saturationAmountRaw;
            this.healAmountCooked = healAmountCooked;
            this.saturationAmountCooked = saturationAmountCooked;
            this.haveRawMeat = true;
            this.haveCookedMeat = true;
        }

        private MeatType(int metaData, String unlocalizedName, int healAmountRaw, float saturationAmountRaw) {
            this.metaData = metaData;
            this.unlocalizedName = unlocalizedName;
            this.healAmountRaw = healAmountRaw;
            this.saturationAmountRaw = saturationAmountRaw;
            this.healAmountCooked = 0;
            this.saturationAmountCooked = 0.0F;
            this.haveRawMeat = true;
            this.haveCookedMeat = false;
        }

        private MeatType(int metaData, String unlocalizedName, float saturationAmountCooked, int healAmountCooked) {
            this.metaData = metaData;
            this.unlocalizedName = unlocalizedName;
            this.healAmountRaw = 0;
            this.saturationAmountRaw = 0.0F;
            this.healAmountCooked = healAmountCooked;
            this.saturationAmountCooked = saturationAmountCooked;
            this.haveRawMeat = false;
            this.haveCookedMeat = true;
        }

/*        private MeatType(int metaData, String unlocalizedName, int healAmountRaw, float saturationAmountRaw, int healAmountCooked, float saturationAmountCooked, int healAmountSeasoned, float saturationAmountSeasoned) { //W.I.P. Seasoned meat
            this.metaData = metaData;
            this.unlocalizedName = unlocalizedName;
            this.healAmountRaw = healAmountRaw;
            this.saturationAmountRaw = saturationAmountRaw;
            this.healAmountCooked = healAmountCooked;
            this.saturationAmountCooked = saturationAmountCooked;
            this.haveRawMeat = true;
            this.haveCookedMeat = true;
        }*/

        public int getMetaData() { return this.metaData;}

        public String getUnlocalizedName() { return this.unlocalizedName; }

        public int getHealAmountRaw() { return this.healAmountRaw; }

        public float getSaturationAmountRaw() { return this.saturationAmountRaw; }

        public int getHealAmountCooked() { return this.healAmountCooked; }

        public float getSaturationAmountCooked() { return this.saturationAmountCooked; }

        public boolean isHaveCookedMeat() { return this.haveCookedMeat; }

        public boolean isHaveRawMeat() { return this.haveRawMeat; }

        public static MeatType getMeatTypeList(int meat) {
            MeatType meattype = (MeatType)META_LOOKUP.get(Integer.valueOf(meat));
            return meattype == null ? LAMB : meattype;
        }

        public static MeatType getMeatType(ItemStack stack) {
            return stack.getItem() instanceof ItemModMeatFood ? getMeatTypeList(stack.getItemDamage()) : LAMB;
        } static {
            MeatType[] var0 = values();
            int var1 = var0.length;
            for (int var2 = 0; var2 < var1; ++var2) {
                MeatType var3 = var0[var2];
                META_LOOKUP.put(Integer.valueOf(var3.getMetaData()), var3);
            }
        }
    }
}