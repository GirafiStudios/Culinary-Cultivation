package com.girafi.culinarycultivation.block;

import com.girafi.culinarycultivation.init.ModItems;
import com.girafi.culinarycultivation.item.ItemCropProduct;
import com.girafi.culinarycultivation.util.ConfigurationHandler;
import net.minecraft.block.*;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockDoubleCrop extends BlockBush implements IGrowable {
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 14);
    @Nonnull
    private ItemStack crop = ItemStack.EMPTY;
    @Nonnull
    private ItemStack seed = ItemStack.EMPTY;
    private int minDropValueCrop;
    private int maxDropValueCrop;
    private int minDropValueSeed;
    private int maxDropValueSeed;
    private boolean canRightClickHarvest;

    public BlockDoubleCrop() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, 0));
        this.setTickRandomly(true);
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        this.disableStats();
    }

    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        int age = state.getValue(AGE);
        if (age <= 7) {
            return new AxisAlignedBB(0F, 0F, 0F, 1.0F, age == 0 ? 0.25F : age == 1 || age == 2 ? 0.5F : age == 3 || age == 4 ? 0.8F : 1.0F, 1.0F);
        } else if (age >= 8) {
            return new AxisAlignedBB(0F, 0F, 0F, 1.0F, age == 8 || age == 9 || age == 10 ? 0.35F : 1.0F, 1.0F);
        }
        return FULL_BLOCK_AABB;
    }

    @Override
    @Nonnull
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    @Nonnull
    public ItemStack getPickBlock(@Nonnull IBlockState state, RayTraceResult target, @Nonnull World world, @Nonnull BlockPos pos, EntityPlayer player) {
        if (seed.isEmpty()) {
            return crop;
        }
        return seed;
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        return state.getBlock() == Blocks.FARMLAND || state.getBlock() instanceof BlockDoubleCrop;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        int age = state.getValue(AGE);
        if (age == 14 || age == 7) {
            if (ConfigurationHandler.canRightClickHarvestAllCulinaryCultivationCrops) {
                this.rightClickHarvest(world, pos, state);
            } else if (canRightClickHarvest && ConfigurationHandler.canRightClickHarvestAllCulinaryCultivationDoubleCrops) {
                this.rightClickHarvest(world, pos, state);
            }
        }
        return super.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
    }

    private boolean rightClickHarvest(World world, BlockPos pos, IBlockState state) {
        int age = state.getValue(AGE);
        if (age == 7) {
            super.dropBlockAsItem(world, pos.up(), world.getBlockState(pos.up()), 0);
            world.setBlockState(pos, state.withProperty(AGE, 6), 2);
            world.setBlockState(pos.up(), state.withProperty(AGE, 11), 2);
            return true;
        }
        if (age >= 14) {
            super.dropBlockAsItem(world, pos, state, 0);
            world.setBlockState(pos.down(), state.withProperty(AGE, 6), 2);
            world.setBlockState(pos, state.withProperty(AGE, 11), 2);
            return true;
        }
        return true;
    }

    @Override
    public void neighborChanged(@Nullable IBlockState state, @Nullable World world, @Nullable BlockPos pos, Block neighborBlock, BlockPos neighborPos) {
        if (state.getValue(AGE) == 7 && world.getBlockState(pos.up()).getBlock() instanceof BlockAir) {
            world.setBlockState(pos, state.withProperty(AGE, 6), 2);
        }
        super.neighborChanged(state, world, pos, neighborBlock, neighborPos);
    }

    @Override
    public void updateTick(@Nullable World world, @Nullable BlockPos pos, @Nullable IBlockState state, Random rand) {
        super.updateTick(world, pos, state, rand);

        this.checkAndDropBlock(world, pos, state);

        if (world.getLightFromNeighbors(pos.up()) >= 9) {
            int age = state.getValue(AGE);

            if (age < 14) {
                float f = getGrowthChance(this, world, pos);

                if (rand.nextInt((int) (25.0F / f) + 1) == 0) {
                    if (age == 6 && world.getBlockState(pos.up()).getBlock() instanceof BlockAir) {
                        world.setBlockState(pos.up(), state.withProperty(AGE, 8), 2);
                    } else if (age != 6 && age != 7 && age != 13)
                        world.setBlockState(pos, state.withProperty(AGE, age + 1), 2);
                }
                if (age == 6 && world.getBlockState(pos.up()).getBlock() instanceof BlockDoubleCrop && world.getBlockState(pos.up()) == state.withProperty(AGE, 13)) {
                    world.setBlockState(pos, state.withProperty(AGE, 7), 2);
                    world.setBlockState(pos.up(), state.withProperty(AGE, 14), 2);
                }
            }
        }
    }

    private static float getGrowthChance(Block block, World world, BlockPos pos) {
        float f = 1.0F;
        BlockPos posDown = pos.down();

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                IBlockState iblockstate = world.getBlockState(posDown.add(i, 0, j));

                if (iblockstate.getBlock().canSustainPlant(iblockstate, world, posDown.add(i, 0, j), net.minecraft.util.EnumFacing.UP, (net.minecraftforge.common.IPlantable) block)) {
                    f1 = 1.0F;

                    if (iblockstate.getBlock().isFertile(world, posDown.add(i, 0, j))) {
                        f1 = 3.0F;
                    }
                }

                if (i != 0 || j != 0) {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }
        BlockPos posNorth = pos.north();
        BlockPos posSouth = pos.south();
        BlockPos posWest = pos.west();
        BlockPos posEast = pos.east();
        boolean isWestOrEast = block == world.getBlockState(posWest).getBlock() || block == world.getBlockState(posEast).getBlock();
        boolean isNorthOrSouth = block == world.getBlockState(posNorth).getBlock() || block == world.getBlockState(posSouth).getBlock();

        if (isWestOrEast && isNorthOrSouth) {
            f /= 2.0F;
        } else {
            boolean flag2 = block == world.getBlockState(posWest.north()).getBlock() || block == world.getBlockState(posEast.north()).getBlock() || block == world.getBlockState(posEast.south()).getBlock() || block == world.getBlockState(posWest.south()).getBlock();

            if (flag2) {
                f /= 2.0F;
            }
        }
        return f;
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        if (state.getBlock() == this) {
            IBlockState soil = world.getBlockState(pos.down());
            return (world.getLight(pos) >= 8 || world.canSeeSky(pos)) && soil.getBlock().canSustainPlant(soil, world, pos.down(), EnumFacing.UP, this);
        }
        return this.canSustainBush(world.getBlockState(pos.down()));
    }

    @Override
    public void grow(@Nonnull World world, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        int i = state.getValue(AGE) + MathHelper.getInt(world.rand, 1, 1);
        int age = state.getValue(AGE);

        if (i > 14) {
            i = 14;
        }
        if (age == 6 && world.getBlockState(pos.up()).getBlock() instanceof BlockAir) {
            world.setBlockState(pos.up(), state.withProperty(AGE, 8), 2);
        } else if (age != 6 && age != 7 && age != 13) {
            world.setBlockState(pos, state.withProperty(AGE, i), 2);
        }
        if (age == 6 && world.getBlockState(pos.up()).getBlock() instanceof BlockDoubleCrop && world.getBlockState(pos.up()) == state.withProperty(AGE, 13)) {
            world.setBlockState(pos, state.withProperty(AGE, 7), 2);
            world.setBlockState(pos.up(), state.withProperty(AGE, 14), 2);
        }
    }

    @Override
    public void dropBlockAsItemWithChance(World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, float chance, int fortune) {
        super.dropBlockAsItemWithChance(world, pos, state, chance, 0);
    }

    @Override
    public boolean canGrow(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, boolean isClient) {
        int age = state.getValue(AGE);
        return age < 14 && age != 7;
    }

    @Override
    public boolean canUseBonemeal(@Nonnull World world, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        int age = world.getBlockState(pos).getValue(AGE);
        if (age < 14) {
            if (age == 13 && world.getBlockState(pos.down()).getBlock() instanceof BlockDoubleCrop) {
                if ((world.getBlockState(pos.down()).getValue(AGE)) == 6) {
                    world.playEvent(2005, new BlockPos(pos.down()), 0);
                    world.setBlockState(pos.down(), state.withProperty(AGE, (world.getBlockState(pos.down()).getValue(AGE) + 1)), 2);
                    world.setBlockState(pos, state.withProperty(AGE, age + 1), 2);
                    return true;
                }
            }
            if (age == 6 && world.getBlockState(pos.up()).getBlock() instanceof BlockDoubleCrop) {
                if ((world.getBlockState(pos.up()).getValue(AGE)) >= 8) {
                    world.playEvent(2005, new BlockPos(pos.up()), 0);
                    world.setBlockState(pos.up(), state.withProperty(AGE, (world.getBlockState(pos.up()).getValue(AGE) + 1)), 2);
                    return true;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(AGE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(AGE);
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AGE);
    }

    public BlockDoubleCrop set(@Nonnull ItemStack stack, int minDropValue, int maxDropValue) {
        if (stack.getItem().equals(ModItems.CROP_FOOD)) {
            crop = stack;
            minDropValueCrop = minDropValue;
            maxDropValueCrop = maxDropValue;
        }
        if (stack.getItem().equals(ModItems.CROP_SEEDS)) {
            seed = stack;
            minDropValueSeed = minDropValue;
            maxDropValueSeed = maxDropValue;
        }
        return this;
    }

    public BlockDoubleCrop setCrop(ItemCropProduct.ProductType productType, int minDropValue, int maxDropValue) {
        set(new ItemStack(ModItems.CROP_FOOD, 1, productType.getMetadata()), minDropValue, maxDropValue);
        if (productType.hasSeed()) {
            seed = new ItemStack(ModItems.CROP_SEEDS, 1, productType.getMetadata());
        }
        return this;
    }

    public BlockDoubleCrop setSeed(ItemCropProduct.ProductType productType, int minDropValue, int maxDropValue) {
        set(new ItemStack(ModItems.CROP_SEEDS, 1, productType.getMetadata()), minDropValue, maxDropValue);
        return this;
    }

    public boolean setRightClickHarvest() {
        return canRightClickHarvest = true;
    }

    @Nonnull
    private ItemStack notGrownDrop() {
        if (seed.isEmpty()) {
            return crop;
        }
        return seed;
    }

    @Override
    @Nonnull
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(AGE) == 7 ? crop.getItem() : notGrownDrop().getItem();
    }

    @Override
    @Nonnull
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, @Nonnull IBlockState state, int fortune) {
        List<ItemStack> ret = new java.util.ArrayList<>();
        int age = state.getValue(AGE);
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;

        if (age == 14) {
            if (!crop.isEmpty() && maxDropValueCrop > 0) {
                int cropDrop = MathHelper.getInt(rand, minDropValueCrop, maxDropValueCrop);
                if (cropDrop == 0) {
                    if (rand.nextInt(100) >= 50) {
                        ret.add(crop.copy());
                    }
                }
                for (int i = 0; i < cropDrop + fortune; ++i) {
                    ret.add(crop.copy());
                }
            }

            if (!seed.isEmpty() && maxDropValueSeed > 0) {
                int seedDrop = MathHelper.getInt(rand, minDropValueSeed, maxDropValueSeed);
                if (seedDrop == 0) {
                    if (rand.nextInt(100) >= 25) {
                        ret.add(seed.copy());
                    }
                }
                for (int i = 0; i < seedDrop + fortune; ++i) {
                    ret.add(seed.copy());
                }
            }
        }
        if (age <= 7) {
            if (!notGrownDrop().isEmpty()) {
                ret.add(notGrownDrop().copy());
            }
        }
        return ret;
    }
}