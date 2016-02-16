package com.Girafi.culinarycultivation.item;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import com.Girafi.culinarycultivation.init.ModItems;
import com.Girafi.culinarycultivation.reference.Paths;
import com.google.common.collect.Maps;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class ItemStorageJar extends Item {

    public ItemStorageJar() {
        setHasSubtypes(true);
        setMaxDamage(0);
        setContainerItem(this);
    }

    public static enum StorageJarType {
        EMPTY(0, "empty"),
        WATER(1, "water", setColor(52, 95, 218)),
        MILK(2, "milk", setColor(255, 255, 255)),
        RENNET(3, "rennet", setColor(184, 185, 151));

        private static final Map StorageJarTypeMap = Maps.newHashMap();
        private final int metaData;
        private final String unlocalizedName;
        private final int colorNumber;

        private StorageJarType(int metaData, String unlocalizedName) {
            this.metaData = metaData;
            this.unlocalizedName = unlocalizedName;
            this.colorNumber = 0;
        }

        private StorageJarType(int metaData, String unlocalizedName, int colorNumber) {
            this.metaData = metaData;
            this.unlocalizedName = unlocalizedName;
            this.colorNumber = colorNumber;
        }

        public int getMetaData() {
            return this.metaData;
        }

        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }

        public int getColorNumber() {
            return this.colorNumber;
        }

        public static StorageJarType getStorageJarTypeList(int storageJar) {
            StorageJarType storageJarType = (StorageJarType) StorageJarTypeMap.get(Integer.valueOf(storageJar));
            return storageJarType == null ? EMPTY : storageJarType;
        }

        public static StorageJarType getStorageJarType(ItemStack stack) {
            return stack.getItem() instanceof ItemStorageJar ? getStorageJarTypeList(stack.getItemDamage()) : EMPTY;
        }

        static {
            StorageJarType[] var0 = values();
            int var1 = var0.length;
            for (int var2 = 0; var2 < var1; ++var2) {
                StorageJarType var3 = var0[var2];
                StorageJarTypeMap.put(Integer.valueOf(var3.getMetaData()), var3);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
        super.getSubItems(item, creativeTab, list);
        StorageJarType[] astorageJar = StorageJarType.values();
        int i = astorageJar.length;
        for (int j = 0; j < i; ++j) {
            StorageJarType storageJarType = astorageJar[j];
            if (storageJarType.getMetaData() != 0) {
                list.add(new ItemStack(this, 1, storageJarType.getMetaData()));
            }
        }
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        if (!hasContainerItem(itemStack) || itemStack.getItemDamage() == 0) {
            return null;
        }
        return new ItemStack(getContainerItem());
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        if (stack.getItemDamage() == 0) {
            return EnumAction.NONE;
        }
        return EnumAction.DRINK;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        if (!player.capabilities.isCreativeMode) {
            --stack.stackSize;
        }
        if (!player.capabilities.isCreativeMode) {
            if (stack.stackSize <= 0) {
                return new ItemStack(ModItems.storageJar, 1, StorageJarType.EMPTY.getMetaData());
            }
            if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.storageJar, 1, StorageJarType.EMPTY.getMetaData()))) {
                player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.storageJar, 1, StorageJarType.EMPTY.getMetaData()), false);
            }
        }
        if (stack.getItem() == ModItems.storageJar && stack.getItemDamage() == StorageJarType.MILK.getMetaData()) {
            player.curePotionEffects(new ItemStack(Items.milk_bucket));
        }
        return stack;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack.getItemDamage() == StorageJarType.EMPTY.getMetaData()) {
            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);
            if (movingobjectposition == null) {
                return stack;
            } else {
                if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    BlockPos pos = movingobjectposition.getBlockPos();
                    if (!world.isBlockModifiable(player, pos)) {
                        return stack;
                    }
                    if (!player.canPlayerEdit(pos.offset(movingobjectposition.sideHit), movingobjectposition.sideHit, stack)) {
                        return stack;
                    }

                    if (world.getBlockState(pos).getBlock().getMaterial() == Material.water) {
                        --stack.stackSize;
                        if (stack.stackSize <= 0) {
                            return new ItemStack(ModItems.storageJar, 1, StorageJarType.WATER.getMetaData());
                        }
                        if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.storageJar, 1, StorageJarType.WATER.getMetaData()))) {
                            player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.storageJar, 1, StorageJarType.WATER.getMetaData()), false);
                        }
                    }
                }
            }
        }
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int color) {
        StorageJarType storageJarType = StorageJarType.getStorageJarType(stack);
        if (stack.getItemDamage() != 0) {
            return color > 0 ? 16777215 : storageJarType.getColorNumber();
        } else
            return 16777215;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        StorageJarType storageJarType = StorageJarType.getStorageJarType(stack);
        if (storageJarType.getMetaData() == 0) {
            return this.getUnlocalizedName() + "_" + storageJarType.getUnlocalizedName();
        } else {
            return this.getUnlocalizedName();
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        StorageJarType storageJarType = StorageJarType.getStorageJarType(stack);
        return StatCollector.translateToLocal("item." + Paths.ModAssets + "storageJar_" + storageJarType.getUnlocalizedName() + ".name").trim();
    }

    public static int setColor(int r, int g, int b) {
        Color c = new Color(r, g, b);
        return c.getRGB();
    }
}