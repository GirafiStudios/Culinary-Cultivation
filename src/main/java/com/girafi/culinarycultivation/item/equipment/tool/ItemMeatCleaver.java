package com.girafi.culinarycultivation.item.equipment.tool;

import com.girafi.culinarycultivation.api.item.ICraftingTool;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemMeatCleaver extends ItemSword implements ICraftingTool {

    public ItemMeatCleaver() {
        super(Item.ToolMaterial.IRON);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add("A finer way to cleave meat"); //Temporary
    }
}