package com.girafi.culinarycultivation.modsupport.waila;

import com.girafi.culinarycultivation.block.BlockDoubleCrop;
import com.girafi.culinarycultivation.block.BlockModCauldron;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import mcp.mobius.waila.cbcore.LangUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class WailaHUDHandler implements IWailaDataProvider {

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
                World world = accessor.getWorld();
                BlockPos pos = accessor.getPosition();
                IBlockState stateUp = world.getBlockState(pos.up());
                IBlockState stateDown = world.getBlockState(pos.down());
                float growthValue = (accessor.getMetadata() / 14.0F) * 100.0F;
                if (growthValue < 100.0 && accessor.getMetadata() != 7 && accessor.getMetadata() != 6) {
                    currenttip.add(String.format("%s : %.0f %%", LangUtil.translateG("hud.msg.growth"), growthValue));
                } else if (growthValue < 100.0 && accessor.getMetadata() == 6 && stateUp.getBlock() instanceof BlockAir) {
                    currenttip.add(String.format("%s : %.0f %%", LangUtil.translateG("hud.msg.growth"), growthValue));
                } else if (accessor.getMetadata() == 7 || accessor.getMetadata() == 14) {
                    currenttip.add(String.format("%s : %s", LangUtil.translateG("hud.msg.growth"), LangUtil.translateG("hud.msg.mature")));
                }
                if (stateUp.getBlock() instanceof BlockDoubleCrop && stateDown.getBlock() instanceof BlockFarmland) {
                    float growthValueTop = (block.getMetaFromState(stateUp) / 14.0F) * 100.0F;
                    if (growthValueTop < 100.0 && accessor.getMetadata() == 6) {
                        currenttip.add(String.format("%s : %.0f %%", LangUtil.translateG("hud.msg.growth"), growthValueTop));
                    }
                }
            }
        }

        if (block instanceof BlockModCauldron) {
            if (accessor.getMetadata() == 12) {
                currenttip.add("Cheese ripening in progress");
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

    public static void register() {
        IWailaDataProvider provider = new WailaHUDHandler();

        ModuleRegistrar.instance().registerBodyProvider(provider, BlockDoubleCrop.class);
        ModuleRegistrar.instance().registerBodyProvider(provider, BlockModCauldron.class);
    }
}