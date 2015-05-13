package com.Girafi.culinarycultivation.item;

import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.reference.Reference;
import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

public class ItemModFishFood extends SourceFood {
    private final boolean isCooked;
    private static double potionEffectProbability;

    public ItemModFishFood(boolean cooked) {
        super(0, 0.0F, false);
        this.isCooked = cooked;
    }

    public int func_150905_g(ItemStack stack) {
        FishType fishtype = FishType.getFishType(stack);
        if (fishtype.isHaveRawFish()) {
            return this.isCooked && fishtype.isHaveCookedFish() ? fishtype.getHealAmountCooked() : fishtype.getHealAmountRaw();
        } else
            return fishtype.isHaveCookedFish() &! fishtype.isHaveRawFish() ? fishtype.getHealAmountCooked() : fishtype.getHealAmountRaw();
    }

    public float func_150906_h(ItemStack stack) {
        FishType fishtype = FishType.getFishType(stack);
        if (fishtype.isHaveRawFish()) {
            return this.isCooked && fishtype.isHaveCookedFish() ? fishtype.getSaturationAmountCooked() : fishtype.getSaturationAmountRaw();
        } else
            return fishtype.isHaveCookedFish() &! fishtype.isHaveRawFish() ? fishtype.getSaturationAmountCooked() : fishtype.getSaturationAmountRaw();
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        FishType[] afishtype = FishType.values();
        int i = afishtype.length;

        for (int j = 0; j < i; ++j) {
            FishType fishtype = afishtype[j];
            fishtype.getIcon(register);
        }
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
    public IIcon getIconFromDamage(int damage) {
        FishType fishtype = FishType.getFishTypeList(damage);
        if (fishtype.isHaveRawFish()) {
            return this.isCooked && fishtype.isHaveCookedFish() ? fishtype.getTextureCooked() : fishtype.getTextureRaw();
        } else
            return fishtype.isHaveCookedFish() & !fishtype.isHaveRawFish() ? fishtype.getTextureCooked() : fishtype.getTextureRaw();
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        FishType[] afishtype = FishType.values();
        int i = afishtype.length;

        for (int j = 0; j < i; ++j) {
            FishType fishtype = afishtype[j];
            if (!fishtype.isHaveRawFish() && this.isCooked && fishtype.isHaveCookedFish()) {
                list.add(new ItemStack(this, 1, fishtype.getMetaData()));
            }
            if (fishtype.haveRawFish) {
                if (!this.isCooked || fishtype.isHaveCookedFish()) {
                    list.add(new ItemStack(this, 1, fishtype.getMetaData()));
                }
            }

        }
    }

    public String getUnlocalizedName(ItemStack stack) {
        FishType fishtype = FishType.getFishType(stack);
        if (fishtype.isHaveRawFish()) {
            return this.getUnlocalizedName() + "." + fishtype.getTextureName() + "." + (this.isCooked && fishtype.isHaveCookedFish() ? "cooked" : "raw");
        } else
            return this.getUnlocalizedName() + "." + fishtype.getTextureName() + "." + (this.isCooked && fishtype.isHaveCookedFish() ? "cooked" : "cooked");
    }

    public static enum FishType { //TODO Look at fish values!
        MACKEREL(0, "mackerel", 2, 0.1F, 6, 0.7F),
        TUNA(1, "tuna", 3, 0.2F, 6, 0.6F),
        TROUT(2, "trout", 2, 0.1F, 6, 0.4F),
        HERRING(3, "herring", 2, 0.1F, 6, 0.4F),
        PLAICE(4, "plaice", 2, 0.2F, 4, 0.6F),
        SMALLSQUID(5, "smallSquid", 2, 0.3F),
        CLOWNFISH(6, "clownfish", 0.2F, 3);
        private static final Map FishTypeMap = Maps.newHashMap();
        private final int metaData;
        private final String textureName;
        @SideOnly(Side.CLIENT)
        private IIcon textureRaw;
        @SideOnly(Side.CLIENT)
        private IIcon textureCooked;
        private final int healAmountRaw;
        private final float saturationAmountRaw;
        private final int healAmountCooked;
        private final float saturationAmountCooked;
        private boolean haveRawFish = false;
        private boolean haveCookedFish = false;

        private FishType(int metaData, String textureName, int healAmountRaw, float saturationAmountRaw, int healAmountCooked, float saturationAmountCooked) {
            this.metaData = metaData;
            this.textureName = textureName;
            this.healAmountRaw = healAmountRaw;
            this.saturationAmountRaw = saturationAmountRaw;
            this.healAmountCooked = healAmountCooked;
            this.saturationAmountCooked = saturationAmountCooked;
            this.haveRawFish = true;
            this.haveCookedFish = true;
        }

        private FishType(int metaData, String textureName, int healAmountRaw, float saturationAmountRaw) {
            this.metaData = metaData;
            this.textureName = textureName;
            this.healAmountRaw = healAmountRaw;
            this.saturationAmountRaw = saturationAmountRaw;
            this.healAmountCooked = 0;
            this.saturationAmountCooked = 0.0F;
            this.haveRawFish = true;
            this.haveCookedFish = false;
        }

        private FishType(int metaData, String textureName, float saturationAmountCooked, int healAmountCooked) {
            this.metaData = metaData;
            this.textureName = textureName;
            this.healAmountRaw = 0;
            this.saturationAmountRaw = 0.0F;
            this.healAmountCooked = healAmountCooked;
            this.saturationAmountCooked = saturationAmountCooked;
            this.haveRawFish = false;
            this.haveCookedFish = true;
        }

        public int getMetaData() { return this.metaData;}

        public String getTextureName() { return this.textureName; }

        public int getHealAmountRaw() { return this.healAmountRaw; }

        public float getSaturationAmountRaw() { return this.saturationAmountRaw; }

        public int getHealAmountCooked() { return this.healAmountCooked; }

        public float getSaturationAmountCooked() { return this.saturationAmountCooked; }

        @SideOnly(Side.CLIENT)
        public void getIcon(IIconRegister register) {
            if (this.haveRawFish) {
                this.textureRaw = register.registerIcon(Reference.MOD_ID + ":" + "fish_" + this.textureName + "_raw");
            }
            if (this.haveCookedFish) {
                this.textureCooked = register.registerIcon(Reference.MOD_ID + ":" + "fish_" + this.textureName + "_cooked");
            }
        }

        @SideOnly(Side.CLIENT)
        public IIcon getTextureRaw() { return this.textureRaw; }

        @SideOnly(Side.CLIENT)
        public IIcon getTextureCooked() { return this.textureCooked; }

        public boolean isHaveCookedFish() { return this.haveCookedFish; }

        public boolean isHaveRawFish() { return this.haveRawFish; }

        public static FishType getFishTypeList(int fishType) {
            FishType fishtype = (FishType)FishTypeMap.get(Integer.valueOf(fishType));
            return fishtype == null ? MACKEREL : fishtype;
        }

        public static FishType getFishType(ItemStack stack) {
            return stack.getItem() instanceof ItemModFishFood ? getFishTypeList(stack.getItemDamage()) : MACKEREL;
        } static {
            FishType[] var0 = values();
            int var1 = var0.length;
            for (int var2 = 0; var2 < var1; ++var2) {
                FishType var3 = var0[var2];
                FishTypeMap.put(Integer.valueOf(var3.getMetaData()), var3);
            }
        }
    }
}