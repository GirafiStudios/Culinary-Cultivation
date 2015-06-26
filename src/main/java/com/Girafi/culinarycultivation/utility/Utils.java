package com.Girafi.culinarycultivation.utility;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.awt.*;

public class Utils {
    protected static int blockMetadata = -1;
    protected static World world;
    protected static BlockPos pos;

    public static Color setColor(int r, int g, int b) {
        Color c = new Color(r, g, b);
        return c;
    }

    public static int getBlockMetadata() {
        if (blockMetadata == -1) {
            IBlockState state = world.getBlockState(pos);
            blockMetadata = state.getBlock().getMetaFromState(state);
        }

        return blockMetadata;
    }
}
