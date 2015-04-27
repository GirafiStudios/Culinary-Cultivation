package com.Girafi.culinarycultivation.item;

import com.Girafi.culinarycultivation.reference.Reference;
import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

public class ItemModFishFood extends SourceFood {

    private final boolean isCooked;

    public ItemModFishFood(boolean cooked) {
        super(0, 0.0F, false);
        this.isCooked = cooked;
    }

    public int func_150905_g(ItemStack stack) {
        FishType fishtype = FishType.getFishType(stack);
        return this.isCooked && fishtype.isCookedFish() ? fishtype.getHealAmountCooked() : fishtype.getHealAmountRaw();
    }

    public float func_150906_h(ItemStack stack) {
        FishType fishtype = FishType.getFishType(stack);
        return this.isCooked && fishtype.isCookedFish() ? fishtype.getSaturationAmountCooked() : fishtype.getSaturationAmountRaw();
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
        super.onFoodEaten(stack, world, player);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        FishType fishtype = FishType.getFishTypeList(damage);
        return this.isCooked && fishtype.isCookedFish() ? fishtype.getTextureCooked() : fishtype.getTextureRaw();
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        FishType[] afishtype = FishType.values();
        int i = afishtype.length;

        for (int j = 0; j < i; ++j) {
            FishType fishtype = afishtype[j];
            if (!this.isCooked || fishtype.isCookedFish()) {
                list.add(new ItemStack(this, 1, fishtype.getMetaData()));
            }
        }
    }

    public String getUnlocalizedName(ItemStack stack) {
        FishType fishtype = FishType.getFishType(stack);
        return this.getUnlocalizedName() + "." + fishtype.getTextureName() + "." + (this.isCooked && fishtype.isCookedFish() ? "cooked" : "raw");
    }

    public static enum FishType {
        MACKEREL(0, "mackerel", 2, 0.1F, 5, 0.7F),
        TUNA(1, "tuna", 3, 0.2F, 6, 0.7F),
        PLAICE(2, "plaice", 2, 0.1F, 5, 0.5F);
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
        private boolean cookedFish = false;

        private FishType(int metaData, String textureName, int healAmountRaw, float saturationAmountRaw, int healAmountCooked, float saturationAmountCooked) {
            this.metaData = metaData;
            this.textureName = textureName;
            this.healAmountRaw = healAmountRaw;
            this.saturationAmountRaw = saturationAmountRaw;
            this.healAmountCooked = healAmountCooked;
            this.saturationAmountCooked = saturationAmountCooked;
            this.cookedFish = true;
        }

        public int getMetaData() { return this.metaData;}

        public String getTextureName() { return this.textureName; }

        public int getHealAmountRaw() { return this.healAmountRaw; }

        public float getSaturationAmountRaw() { return this.saturationAmountRaw; }

        public int getHealAmountCooked() { return this.healAmountCooked; }

        public float getSaturationAmountCooked() { return this.saturationAmountCooked; }

        @SideOnly(Side.CLIENT)
        public void getIcon(IIconRegister register) {
            this.textureRaw = register.registerIcon(Reference.MOD_ID + ":" + "fish_" + this.textureName + "_raw");

            if (this.cookedFish) {
                this.textureCooked = register.registerIcon(Reference.MOD_ID + ":" + "fish_" + this.textureName + "_cooked");
            }
        }

        @SideOnly(Side.CLIENT)
        public IIcon getTextureRaw() { return this.textureRaw; }

        @SideOnly(Side.CLIENT)
        public IIcon getTextureCooked() { return this.textureCooked; }

        public boolean isCookedFish() { return this.cookedFish; }

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