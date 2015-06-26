package com.Girafi.culinarycultivation.modSupport.thaumcraft;

/*import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.wands.IWandTriggerManager;
import thaumcraft.api.wands.IWandable;

public class CrucibleSupport implements IWandTriggerManager {

    @Override
    public boolean performTrigger(World worldIn, ItemStack stack, EntityPlayer playerIn, BlockPos pos, EnumFacing enumFacing, int i) {
        IWandable wand = (IWandable)stack.getItem();
        if(!worldIn.isRemote) {
            worldIn.playSoundEffect((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, "thaumcraft:wand", 1.0F, 1.0F);
            worldIn.setBlockToAir(pos);
            worldIn.setBlockState(pos, BlocksTC.crucible.getDefaultState());
            worldIn.notifyNeighborsOfStateChange(pos, BlocksTC.crucible);
            worldIn.markBlockForUpdate(pos);
            //Packet.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(pos, -9999), new NetworkRegistry.TargetPoint(worldIn.provider.getDimensionId(), (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), 32.0D));
            return true;
        } else {
            return false;
        }
    }
}*/
