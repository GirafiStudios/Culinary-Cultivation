package com.Girafi.culinarycultivation.modSupport.waila;

import com.Girafi.culinarycultivation.block.BlockDoubleCrop;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockFarmland;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class WailaDoubleCropHandler implements IWailaDataProvider {

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        Block block = accessor.getBlock();
        if (config.getConfig("general.showcrop")) {
            if (block instanceof BlockDoubleCrop) {
                int x = accessor.getPosition().getX();
                int y = accessor.getPosition().getY();
                int z = accessor.getPosition().getZ();
                Block blockUp = accessor.getWorld().getBlockState(new BlockPos(x, y + 1, z)).getBlock();
                float growthValue = (accessor.getMetadata() / 14.0F) * 100.0F;
                if (growthValue < 100.0 && accessor.getMetadata() != 7 && accessor.getMetadata() != 6) {
                    currenttip.add(String.format("%s : %.0f %%", LangUtil.translateG("hud.msg.growth"), growthValue));
                } else if (growthValue < 100.0 && accessor.getMetadata() == 6 && blockUp instanceof BlockAir) {
                    currenttip.add(String.format("%s : %.0f %%", LangUtil.translateG("hud.msg.growth"), growthValue));
                } else if (accessor.getMetadata() == 7 || accessor.getMetadata() == 14) {
                    currenttip.add(String.format("%s : %s", LangUtil.translateG("hud.msg.growth"), LangUtil.translateG("hud.msg.mature")));
                }
                if (blockUp instanceof BlockDoubleCrop && accessor.getWorld().getBlockState(new BlockPos(x, y - 1, z)).getBlock() instanceof BlockFarmland) {
                    float growthValueTop = (block.getMetaFromState(accessor.getWorld().getBlockState(new BlockPos(x, y + 1, z))) / 14.0F) * 100.0F;
                    if (growthValueTop < 100.0 && accessor.getMetadata() == 6) {
                        currenttip.add(String.format("%s : %.0f %%", LangUtil.translateG("hud.msg.growth"), growthValueTop));
                    }
                }
            }
        }
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        return tag;
    }
}