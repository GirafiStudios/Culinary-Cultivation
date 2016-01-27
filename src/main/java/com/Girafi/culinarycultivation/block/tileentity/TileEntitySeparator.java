package com.Girafi.culinarycultivation.block.tileentity;

import net.minecraft.util.ITickable;

public class TileEntitySeparator extends SourceTileEntity implements ITickable {
    public static boolean isMultiblockFormed;
    private boolean isInvalidBlock;
    private int checkingX, checkingY, checkingZ;

    @Override
    public void update() {
/*        if (!worldObj.isRemote) {
            checkMultiblock();
            if (isMultiblockFormed) {
            }
        }*/
    }

/*    private void checkMultiblock() {
        checkingX++;
        if (checkingX > 1) {
            checkingX = -1;
            checkingY++;
            if (checkingY > 1) {
                checkingY = -1;
                checkingZ++;
                if (checkingZ > 1) {
                    checkingZ = -1;
                    isMultiblockFormed = !isInvalidBlock;
                    isInvalidBlock = false;
                    LogHelper.info("Formed: " + isMultiblockFormed);
                }
            }
        }

        if (checkingX == 0 && checkingY == 0 && checkingZ == 0) return;
        Block block = worldObj.getBlockState(new BlockPos(pos.getX() + checkingX, pos.getY() + checkingY, pos.getZ() + checkingZ)).getBlock();

        boolean x = (checkingX == 1 && checkingY == 0 && checkingZ == 0);
        boolean x_1 = (checkingX == -1 && checkingY == 0 && checkingZ == 0);
        boolean z = (checkingX == 0 && checkingY == 0 && checkingZ == 1);
        boolean z_1 = (checkingX == 0 && checkingY == 0 && checkingZ == -1);

        if (checkingX == 0 && checkingY == 1 && checkingZ == 0) {
            if (block != Blocks.hopper) {
                isInvalidBlock = true;
            }
        } else if (checkingX == 1 && checkingY == 0 && checkingZ == 0) {
            if (!(block instanceof BlockPlanks)) {
                isInvalidBlock = true;
            }
        }
    }

    public void outputSeed(ItemStack stack) {

    }

    public void outputJunk(ItemStack stack) {

    }*/
}
