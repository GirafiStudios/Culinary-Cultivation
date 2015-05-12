package com.Girafi.culinarycultivation.item;

import com.Girafi.culinarycultivation.reference.Reference;
import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import org.apache.commons.lang3.text.WordUtils;

import java.util.List;
import java.util.Map;

public class ItemModMeatFood extends SourceFood { //TODO Same as fish, but with seasoned meat too. Don't add that to NEI.
    private final boolean isCooked;

    public ItemModMeatFood(boolean cooked) {
        super(0, 0.0F, false);
        this.isCooked = cooked;
    }

    public int func_150905_g(ItemStack stack) {
        MeatType meattype = MeatType.getMeatType(stack);
        return this.isCooked && meattype.isCookedMeat() ? meattype.getHealAmountCooked() : meattype.getHealAmountRaw();
    }

    public float func_150906_h(ItemStack stack) {
        MeatType meattype = MeatType.getMeatType(stack);
        return this.isCooked && meattype.isCookedMeat() ? meattype.getSaturationAmountCooked() : meattype.getSaturationAmountRaw();
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        MeatType[] ameattype = MeatType.values();
        int i = ameattype.length;

        for (int j = 0; j < i; ++j) {
            MeatType meattype = ameattype[j];
            meattype.getIcon(register);
        }
    }

    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
        super.onFoodEaten(stack, world, player);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        MeatType meattype = MeatType.getMeatTypeList(damage);
        return this.isCooked && meattype.isCookedMeat() ? meattype.getTextureCooked() : meattype.getTextureRaw();
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        MeatType[] ameattype = MeatType.values();
        int i = ameattype.length;

        for (int j = 0; j < i; ++j) {
            MeatType meattype = ameattype[j];
            if (!this.isCooked || meattype.isCookedMeat()) {
                list.add(new ItemStack(this, 1, meattype.getMetaData()));
            }
        }
    }

    public String getUnlocalizedName(ItemStack stack) {
        MeatType meattype = MeatType.getMeatType(stack);
        return "item." + Reference.MOD_ID.toLowerCase() + ":" + meattype.getTextureName() + WordUtils.capitalize(this.isCooked && meattype.isCookedMeat() ? "cooked" : "raw");
    }

    public static enum MeatType { //DONT START WITH LAMB, JUST A TEST
        LAMB(0, "lamb", 2, 0.3F, 5, 1.1F);
        private static final Map MeatTypeMap = Maps.newHashMap();
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
        private boolean haveCookedMeat = false;

        private MeatType(int metaData, String textureName, int healAmountRaw, float saturationAmountRaw, int healAmountCooked, float saturationAmountCooked) {
            this.metaData = metaData;
            this.textureName = textureName;
            this.healAmountRaw = healAmountRaw;
            this.saturationAmountRaw = saturationAmountRaw;
            this.healAmountCooked = healAmountCooked;
            this.saturationAmountCooked = saturationAmountCooked;
            this.haveCookedMeat = true;
        }

        private MeatType(int metaData, String textureName, int healAmountRaw, float saturationAmountRaw) {
            this.metaData = metaData;
            this.textureName = textureName;
            this.healAmountRaw = healAmountRaw;
            this.saturationAmountRaw = saturationAmountRaw;
            this.healAmountCooked = 0;
            this.saturationAmountCooked = 0.0F;
            this.haveCookedMeat = false;
        }

        public int getMetaData() { return this.metaData;}

        public String getTextureName() { return this.textureName; }

        public int getHealAmountRaw() { return this.healAmountRaw; }

        public float getSaturationAmountRaw() { return this.saturationAmountRaw; }

        public int getHealAmountCooked() { return this.healAmountCooked; }

        public float getSaturationAmountCooked() { return this.saturationAmountCooked; }

        @SideOnly(Side.CLIENT)
        public void getIcon(IIconRegister register) {
            this.textureRaw = register.registerIcon(Reference.MOD_ID + ":" + this.textureName + "Raw");

            if (this.haveCookedMeat) {
                this.textureCooked = register.registerIcon(Reference.MOD_ID + ":" + this.textureName + "Cooked");
            }
        }

        @SideOnly(Side.CLIENT)
        public IIcon getTextureRaw() { return this.textureRaw; }

        @SideOnly(Side.CLIENT)
        public IIcon getTextureCooked() { return this.textureCooked; }

        public boolean isCookedMeat() { return this.haveCookedMeat; }

        public static MeatType getMeatTypeList(int meat) {
            MeatType meattype = (MeatType)MeatTypeMap.get(Integer.valueOf(meat));
            return meattype == null ? LAMB : meattype;
        }

        public static MeatType getMeatType(ItemStack stack) {
            return stack.getItem() instanceof ItemModMeatFood ? getMeatTypeList(stack.getItemDamage()) : LAMB;
        } static {
            MeatType[] var0 = values();
            int var1 = var0.length;
            for (int var2 = 0; var2 < var1; ++var2) {
                MeatType var3 = var0[var2];
                MeatTypeMap.put(Integer.valueOf(var3.getMetaData()), var3);
            }
        }
    }
}