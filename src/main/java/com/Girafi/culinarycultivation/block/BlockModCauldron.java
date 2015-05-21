package com.Girafi.culinarycultivation.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockCauldron;

public class BlockModCauldron extends BlockCauldron { //TODO Might have to make it an tile entity, tank-isch or something like that. The best would be to keep it as close to vanilla as possible

    public BlockModCauldron() {
        super();
        setBlockName("cauldron");
        setBlockTextureName("cauldron");
        setHardness(2.0F);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getItemIconName() { return "cauldron"; }
}
