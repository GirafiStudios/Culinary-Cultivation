package com.girafi.culinarycultivation.item.equipment.armor.farmer;

import com.girafi.culinarycultivation.util.StringUtils;
import com.girafi.culinarycultivation.util.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemFarmerBoots extends ItemFarmerArmor {

    public ItemFarmerBoots() {
        super(EntityEquipmentSlot.FEET, "farmer_armor_boots");
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, @Nonnull ItemStack stack) {
        super.onArmorTick(world, player, stack);
        BlockPos pos = player.getPosition();
        Block playerStandingOnBlock = player.world.getBlockState(pos.down()).getBlock();

        if (player.onGround && player.isSneaking()) {
            if (playerStandingOnBlock instanceof BlockFarmland) {
                player.setPosition(pos.getX(), pos.getY() + 0.5D, pos.getZ());
                playerStandingOnBlock.onFallenUpon(world, pos.down(), player, 1.0F);

                if (world.rand.nextInt(100) <= 50) {
                    stack.damageItem(1, player);
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        tooltip.add(StringUtils.translateToLocal(Reference.MOD_ID + ".armorset.farmer.boots.desc"));
        tooltip.add("");
        super.addInformation(stack, player, tooltip, advanced);
    }
}