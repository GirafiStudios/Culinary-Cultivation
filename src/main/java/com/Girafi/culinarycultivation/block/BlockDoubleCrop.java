package com.Girafi.culinarycultivation.block;

import com.Girafi.culinarycultivation.reference.Paths;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockDoubleCrop extends BlockBush implements IGrowable { //TODO Fix all the things! Espacially related to the age amount change
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 14); //TODO Figure out why I can't call this at all, when extending BlockCrop
    public ItemStack itemCrop;
    public ItemStack itemSeed;
    private int minDropValueCrop;
    private int maxDropValueCrop;
    private int minDropValueSeed;
    private int maxDropValueSeed;

    public BlockDoubleCrop () {
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)));
        this.setTickRandomly(true);
        float f = 0.5F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
        this.setCreativeTab((CreativeTabs)null);
        this.setHardness(0.0F);
        this.setStepSound(soundTypeGrass);
        this.disableStats();
    }

    @Override
    public String getUnlocalizedName() {
        String name = "tile." + Paths.ModAssets + GameRegistry.findUniqueIdentifierFor(getBlockState().getBlock()).name;
        return name;
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos) {
        if (itemSeed == null) {
            return itemCrop.getItem();
        }
        return itemSeed.getItem();
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    protected boolean canPlaceBlockOn(Block ground) {
        return ground == Blocks.farmland || ground instanceof BlockDoubleCrop ;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);

        if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
            int age = ((Integer)state.getValue(AGE)).intValue();

            if (age < 14) {
                float f = getGrowthChance(this, worldIn, pos);

                if (rand.nextInt((int)(25.0F / f) + 1) == 0) {
                    if (age == 6 && worldIn.getBlockState(pos.up()).getBlock() instanceof BlockAir) {
                        worldIn.setBlockState(pos.up(), state.withProperty(AGE, 8), 2);
                    } else if (age != 6 && age != 7 && age != 13)
                    worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(age + 1)), 2);
                }
                if (age == 6 && worldIn.getBlockState(pos.up()).getBlock() instanceof BlockDoubleCrop && worldIn.getBlockState(pos.up()) == state.getBlock().getStateFromMeta(13)) {
                    worldIn.setBlockState(pos, state.withProperty(AGE, 7), 2);
                    worldIn.setBlockState(pos.up(), state.withProperty(AGE, 14), 2);
                }
                if (age == 7 && worldIn.getBlockState(pos.up()).getBlock() instanceof BlockAir) {
                    worldIn.setBlockState(pos, state.withProperty(AGE, 6), 2);
                }
            }
        }
    }

    protected static float getGrowthChance(Block blockIn, World worldIn, BlockPos pos) {
        float f = 1.0F;
        BlockPos blockpos1 = pos.down();
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                IBlockState iblockstate = worldIn.getBlockState(blockpos1.add(i, 0, j));

                if (iblockstate.getBlock().canSustainPlant(worldIn, blockpos1.add(i, 0, j), net.minecraft.util.EnumFacing.UP, (net.minecraftforge.common.IPlantable)blockIn)) {
                    f1 = 1.0F;

                    if (iblockstate.getBlock().isFertile(worldIn, blockpos1.add(i, 0, j))) {
                        f1 = 3.0F;
                    }
                }

                if (i != 0 || j != 0) {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        BlockPos blockpos2 = pos.north();
        BlockPos blockpos3 = pos.south();
        BlockPos blockpos4 = pos.west();
        BlockPos blockpos5 = pos.east();
        boolean flag = blockIn == worldIn.getBlockState(blockpos4).getBlock() || blockIn == worldIn.getBlockState(blockpos5).getBlock();
        boolean flag1 = blockIn == worldIn.getBlockState(blockpos2).getBlock() || blockIn == worldIn.getBlockState(blockpos3).getBlock();

        if (flag && flag1) {
            f /= 2.0F;
        } else {
            boolean flag2 = blockIn == worldIn.getBlockState(blockpos4.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos5.north()).getBlock() || blockIn == worldIn.getBlockState(blockpos5.south()).getBlock() || blockIn == worldIn.getBlockState(blockpos4.south()).getBlock();

            if (flag2) {
                f /= 2.0F;
            }
        }

        return f;
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        return (worldIn.getLight(pos) >= 8 || worldIn.canSeeSky(pos)) && worldIn.getBlockState(pos.down()).getBlock().canSustainPlant(worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this);
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        this.grow(worldIn, pos, state);
    }

    public void grow(World worldIn, BlockPos pos, IBlockState state) {
        int i = ((Integer) state.getValue(AGE)).intValue() + MathHelper.getRandomIntegerInRange(worldIn.rand, 1, 1);
        int age = ((Integer) state.getValue(AGE)).intValue();

        if (i > 14 ) {
            i = 14;
        }
        if (age == 6 && worldIn.getBlockState(pos.up()).getBlock() instanceof BlockAir) {
            worldIn.setBlockState(pos.up(), state.withProperty(AGE, 8), 2);
        } else if (age != 6 && age != 7 && age != 13) {
            worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(i)), 2);
        }
        if (age == 6 && worldIn.getBlockState(pos.up()).getBlock() instanceof BlockDoubleCrop && worldIn.getBlockState(pos.up()) == state.getBlock().getStateFromMeta(13)) {
            worldIn.setBlockState(pos, state.withProperty(AGE, 7), 2);
            worldIn.setBlockState(pos.up(), state.withProperty(AGE, 14), 2);
        }
        if (age == 7 && worldIn.getBlockState(pos.up()).getBlock() instanceof BlockAir) {
            worldIn.setBlockState(pos, state.withProperty(AGE, 6), 2);
        }
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        super.dropBlockAsItemWithChance(worldIn, pos, state, chance, 0);
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return ((Integer)state.getValue(AGE)).intValue() < 14;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) { //TODO If you bonemeal the bottom block and it's fully grown, bonemeal the top block. Fix bonemealing if the bottom block is state 6 and the bottom is 13
        int age = ((Integer) worldIn.getBlockState(pos).getValue(AGE)).intValue();
        if (age == 7) {
            return  false;
        }
        if(age < 14) {
            return true;
        } else
            return false;
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(AGE, Integer.valueOf(meta));
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((Integer)state.getValue(AGE)).intValue();
    }

    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] {AGE});
    }

    public BlockDoubleCrop setModCrop(ItemStack item, int minDropValue, int maxDropValue) {
        itemCrop = item;
        minDropValueCrop = minDropValue;
        maxDropValueCrop = maxDropValue;
        return this;
    }

    public BlockDoubleCrop setModSeed(ItemStack stack, int minDropValue, int maxDropValue) {
        itemSeed = stack;
        minDropValueSeed = minDropValue;
        maxDropValueSeed = maxDropValue;
        return this;
    }

    protected ItemStack notGrownDrop() {
        if (itemSeed == null) {
            return itemCrop;
        }
        return itemSeed;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ((Integer) state.getValue(AGE)).intValue() == 7 ? itemCrop.getItem() : notGrownDrop().getItem();
    }
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        List<ItemStack> ret = new java.util.ArrayList<ItemStack>();
        int age = ((Integer) state.getValue(AGE)).intValue();
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;

        if (age >= 14) {
            int cropDrop = MathHelper.getRandomIntegerInRange(rand, minDropValueCrop, maxDropValueCrop);
            if (cropDrop == 0) {
                if (rand.nextInt(100) >= 50) {
                    ret.add(itemCrop.copy());
                }
            }
            for (int i = 0; i < cropDrop + fortune; ++i) {
                ret.add(itemCrop.copy());
            }

            if (itemSeed != null) {
                int seedDrop = MathHelper.getRandomIntegerInRange(rand, minDropValueSeed, maxDropValueSeed);
                if (seedDrop == 0) {
                    if (rand.nextInt(100) >= 25) {
                        ret.add(itemSeed.copy());
                    }
                }
                for (int i = 0; i < seedDrop + fortune; ++i) {
                    ret.add(itemSeed.copy());
                }
            }
        }

        if (age <= 13) {
            if (notGrownDrop() != null) {
                ret.add(notGrownDrop().copy());
            }
        }
        return ret;
    }
}
