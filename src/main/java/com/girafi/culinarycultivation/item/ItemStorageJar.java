package com.girafi.culinarycultivation.item;

import com.girafi.culinarycultivation.init.ModItems;
import com.google.common.collect.Maps;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.translation.I18n;
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

    public enum StorageJarType {
        EMPTY(0, "empty"),
        WATER(1, "water", setColor(52, 95, 218)),
        MILK(2, "milk", setColor(255, 255, 255)),
        RENNET(3, "rennet", setColor(184, 185, 151));

        private static final Map<Integer, StorageJarType> StorageJarTypeMap = Maps.newHashMap();
        private final int metaData;
        private final String unlocalizedName;
        private final int colorNumber;

        StorageJarType(int metaData, String unlocalizedName) {
            this.metaData = metaData;
            this.unlocalizedName = unlocalizedName;
            this.colorNumber = 0;
        }

        StorageJarType(int metaData, String unlocalizedName, int colorNumber) {
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
            StorageJarType storageJarType = StorageJarTypeMap.get(storageJar);
            return storageJarType == null ? EMPTY : storageJarType;
        }

        public static StorageJarType getStorageJarType(ItemStack stack) {
            return stack.getItem() instanceof ItemStorageJar ? getStorageJarTypeList(stack.getItemDamage()) : EMPTY;
        }

        static {
            for (StorageJarType jarType : values()) {
                StorageJarTypeMap.put(jarType.getMetaData(), jarType);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List<ItemStack> list) {
        super.getSubItems(item, creativeTab, list);
        for (StorageJarType storageJarType : StorageJarType.values()) {
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
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
        ItemStack emptyJarStack = new ItemStack(ModItems.STORAGE_JAR, 1, StorageJarType.EMPTY.getMetaData());

        if (entityLiving instanceof EntityPlayer && !((EntityPlayer) entityLiving).capabilities.isCreativeMode) {
            if (--stack.stackSize == 0) {
                entityLiving.setHeldItem(EnumHand.MAIN_HAND, emptyJarStack);
            } else if (!((EntityPlayer) entityLiving).inventory.addItemStackToInventory(emptyJarStack)) {
                ((EntityPlayer) entityLiving).dropItem(emptyJarStack, false);
            }

            if (!world.isRemote && stack.getItem() == ModItems.STORAGE_JAR && stack.getItemDamage() == StorageJarType.MILK.getMetaData()) {
                entityLiving.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
            }
        }
        return stack.stackSize <= 0 ? emptyJarStack : stack;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
        if (stack.getItemDamage() == StorageJarType.EMPTY.getMetaData()) {
            RayTraceResult rayTraceResult = this.rayTrace(world, player, true);

            if (rayTraceResult == null) {
                return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
            } else {
                if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                    BlockPos pos = rayTraceResult.getBlockPos();

                    if (!world.isBlockModifiable(player, pos) || !player.canPlayerEdit(pos.offset(rayTraceResult.sideHit), rayTraceResult.sideHit, stack)) {
                        return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
                    }

                    if (world.getBlockState(pos).getMaterial() == Material.WATER) {
                        world.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, this.fillJar(stack, player, new ItemStack(ModItems.STORAGE_JAR, 1, StorageJarType.WATER.getMetaData())));
                    }
                }
            }
        }
        player.setActiveHand(hand);
        return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
    }

    private ItemStack fillJar(ItemStack stack, EntityPlayer player, ItemStack jarStack) {
        --stack.stackSize;
        player.addStat(StatList.getObjectUseStats(this));

        if (stack.stackSize <= 0) {
            return jarStack;
        } else {
            if (!player.inventory.addItemStackToInventory(jarStack)) {
                player.dropItem(jarStack, false);
            }

            return stack;
        }
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return EnumActionResult.FAIL;
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
        return I18n.translateToLocal("item." + this.getRegistryName() + "_" + storageJarType.getUnlocalizedName() + ".name").trim();
    }

    private static int setColor(int r, int g, int b) {
        Color c = new Color(r, g, b);
        return c.getRGB();
    }
}