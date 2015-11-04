package com.Girafi.culinarycultivation.item.equipment.tool;

import com.Girafi.culinarycultivation.creativetab.CreativeTab;
import com.Girafi.culinarycultivation.reference.Paths;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Set;

public class ItemKnife extends ItemTool {
    private static final Set EFFECTIVE_ON = Sets.newHashSet(new Block[]{});

    public ItemKnife(Item.ToolMaterial material) {
        super(1.5F, material, EFFECTIVE_ON);
        setUnlocalizedName(Paths.ModAssets + "knife");
        setCreativeTab(CreativeTab.CulinaryCultivation_Tab);
        maxStackSize=1;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        stack.damageItem(1, attacker);
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block blockIn, BlockPos pos, EntityLivingBase player) {
        if ((double) blockIn.getBlockHardness(world, pos) != 0.0D) {
            stack.damageItem(2, player);
        }
        return true;
    }

    @Override
    public int getItemEnchantability() {return this.toolMaterial.WOOD.getHarvestLevel();
    }
}