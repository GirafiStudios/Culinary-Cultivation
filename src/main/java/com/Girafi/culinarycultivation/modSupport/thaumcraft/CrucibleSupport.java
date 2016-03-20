/*package com.Girafi.culinarycultivation.modSupport.thaumcraft;

import com.Girafi.culinarycultivation.network.NetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.wands.IWandTriggerManager;
import thaumcraft.common.lib.network.fx.PacketFXBlockBamf;

public class CrucibleSupport implements IWandTriggerManager {

    @Override
    public boolean performTrigger(World world, ItemStack stack, EntityPlayer player, BlockPos pos, EnumFacing side, int event) {
        if (!world.isRemote) {
            world.setBlockToAir(pos);
            world.setBlockState(pos, BlocksTC.crucible.getDefaultState());
            world.notifyNeighborsOfStateChange(pos, BlocksTC.crucible);
            world.markBlockForUpdate(pos);
            NetworkHandler.instance.sendToAllAround(new PacketFXBlockBamf(pos, -9999, true, true, true), new NetworkRegistry.TargetPoint(world.provider.getDimensionId(), (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), 32.0D));
            world.playSoundEffect((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, "thaumcraft:wand", 1.0F, 1.0F);
            return true;
        } else {
            return false;
        }
    }
}*/