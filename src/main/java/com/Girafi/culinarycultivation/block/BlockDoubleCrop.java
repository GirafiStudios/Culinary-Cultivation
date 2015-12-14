package com.Girafi.culinarycultivation.block;

import com.Girafi.culinarycultivation.handler.ConfigurationHandler;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockDoubleCrop extends BlockBush implements IGrowable {
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 14);
    public ItemStack itemCrop;
    public ItemStack itemSeed;
    private int minDropValueCrop;
    private int maxDropValueCrop;
    private int minDropValueSeed;
    private int maxDropValueSeed;
    private boolean canRightClickHarvest;

    public BlockDoubleCrop() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)));
        this.setTickRandomly(true);
        this.setCreativeTab((CreativeTabs) null);
        this.setHardness(0.0F);
        this.setStepSound(soundTypeGrass);
        this.disableStats();
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos) { //Might need to tweak this later on
        int age = (world.getBlockState(pos).getValue(AGE)).intValue();
        if (age <= 7) {
            this.setBlockBounds(0F, 0F, 0F, 1.0F, age == 0 ? 0.25F : age == 1 || age == 2 ? 0.5F : age == 3 || age == 4 ? 0.8F : 1.0F, 1.0F);
        }
        if (age >= 8) {
            this.setBlockBounds(0F, 0F, 0F, 1.0F, age == 8 || age == 9 || age == 10 ? 0.35F : 1.0F, 1.0F);
        }
    }

    @Override
    public String getUnlocalizedName() {
        String name = "tile." + GameData.getItemRegistry().getNameForObject(Item.getItemFromBlock(getBlockState().getBlock()));
        return name;
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World world, BlockPos pos) {
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
        return ground == Blocks.farmland || ground instanceof BlockDoubleCrop;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
        int age = (state.getValue(AGE)).intValue();
        if (age == 14 || age == 7) {
            if (ConfigurationHandler.CanRightClickHarvestAllCulinaryCultivationCrops) {
                this.rightClickHarvest(world, pos, state);
            } else if (canRightClickHarvest && ConfigurationHandler.CanRightClickHarvestAllCulinaryCultivationDoubleCrops) {
                this.rightClickHarvest(world, pos, state);
            }
        }
        return super.onBlockActivated(world, pos, state, player, side, hitX, hitY, hitZ);
    }

    public boolean rightClickHarvest(World world, BlockPos pos, IBlockState state) {
        int age = (state.getValue(AGE)).intValue();
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

    public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {
        super.onNeighborBlockChange(world, pos, state, neighborBlock);
        this.checkAndDropBlock(world, pos, state);
        int age = (state.getValue(AGE)).intValue();
        if (age == 7 && world.getBlockState(pos.up()).getBlock() instanceof BlockAir) {
            world.setBlockState(pos, state.withProperty(AGE, 6), 2);
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(world, pos, state, rand);

        if (world.getLightFromNeighbors(pos.up()) >= 9) {
            int age = (state.getValue(AGE)).intValue();

            if (age < 14) {
                float f = getGrowthChance(this, world, pos);

                if (rand.nextInt((int) (25.0F / f) + 1) == 0) {
                    if (age == 6 && world.getBlockState(pos.up()).getBlock() instanceof BlockAir) {
                        world.setBlockState(pos.up(), state.withProperty(AGE, 8), 2);
                    } else if (age != 6 && age != 7 && age != 13)
                        world.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(age + 1)), 2);
                }
                if (age == 6 && world.getBlockState(pos.up()).getBlock() instanceof BlockDoubleCrop && world.getBlockState(pos.up()) == state.getBlock().getStateFromMeta(13)) {
                    world.setBlockState(pos, state.withProperty(AGE, 7), 2);
                    world.setBlockState(pos.up(), state.withProperty(AGE, 14), 2);
                }
            }
        }
    }

    protected static float getGrowthChance(Block blockIn, World world, BlockPos pos) {
        float f = 1.0F;
        BlockPos blockpos1 = pos.down();
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                IBlockState iblockstate = world.getBlockState(blockpos1.add(i, 0, j));

                if (iblockstate.getBlock().canSustainPlant(world, blockpos1.add(i, 0, j), net.minecraft.util.EnumFacing.UP, (net.minecraftforge.common.IPlantable) blockIn)) {
                    f1 = 1.0F;

                    if (iblockstate.getBlock().isFertile(world, blockpos1.add(i, 0, j))) {
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
        boolean flag = blockIn == world.getBlockState(blockpos4).getBlock() || blockIn == world.getBlockState(blockpos5).getBlock();
        boolean flag1 = blockIn == world.getBlockState(blockpos2).getBlock() || blockIn == world.getBlockState(blockpos3).getBlock();

        if (flag && flag1) {
            f /= 2.0F;
        } else {
            boolean flag2 = blockIn == world.getBlockState(blockpos4.north()).getBlock() || blockIn == world.getBlockState(blockpos5.north()).getBlock() || blockIn == world.getBlockState(blockpos5.south()).getBlock() || blockIn == world.getBlockState(blockpos4.south()).getBlock();

            if (flag2) {
                f /= 2.0F;
            }
        }

        return f;
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        return (world.getLight(pos) >= 8 || world.canSeeSky(pos)) && world.getBlockState(pos.down()).getBlock().canSustainPlant(world, pos.down(), net.minecraft.util.EnumFacing.UP, this);
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
        this.grow(world, pos, state);
    }

    public void grow(World world, BlockPos pos, IBlockState state) {
        int i = (state.getValue(AGE)).intValue() + MathHelper.getRandomIntegerInRange(world.rand, 1, 1);
        int age = (state.getValue(AGE)).intValue();

        if (i > 14) {
            i = 14;
        }
        if (age == 6 && world.getBlockState(pos.up()).getBlock() instanceof BlockAir) {
            world.setBlockState(pos.up(), state.withProperty(AGE, 8), 2);
        } else if (age != 6 && age != 7 && age != 13) {
            world.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(i)), 2);
        }
        if (age == 6 && world.getBlockState(pos.up()).getBlock() instanceof BlockDoubleCrop && world.getBlockState(pos.up()) == state.getBlock().getStateFromMeta(13)) {
            world.setBlockState(pos, state.withProperty(AGE, 7), 2);
            world.setBlockState(pos.up(), state.withProperty(AGE, 14), 2);
        }
    }

    @Override
    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune) {
        super.dropBlockAsItemWithChance(world, pos, state, chance, 0);
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
        int age = (state.getValue(AGE)).intValue();
        return age < 14 && age != 7;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
        int age = (world.getBlockState(pos).getValue(AGE)).intValue();
        if (age < 14) {
            if (age == 13 && world.getBlockState(pos.down()).getBlock() instanceof BlockDoubleCrop) {
                if ((world.getBlockState(pos.down()).getValue(AGE)).intValue() == 6) {
                    world.playAuxSFX(2005, new BlockPos(pos.down()), 0);
                    world.setBlockState(pos.down(), state.withProperty(AGE, Integer.valueOf((world.getBlockState(pos.down()).getValue(AGE)).intValue() + 1)), 2);
                    world.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(age + 1)), 2);
                    return true;
                }
            }
            if (age == 6 && world.getBlockState(pos.up()).getBlock() instanceof BlockDoubleCrop) {
                if ((world.getBlockState(pos.up()).getValue(AGE)).intValue() >= 8) {
                    world.playAuxSFX(2005, new BlockPos(pos.up()), 0);
                    world.setBlockState(pos.up(), state.withProperty(AGE, Integer.valueOf((world.getBlockState(pos.up()).getValue(AGE)).intValue() + 1)), 2);
                    return true;
                }
            }
            return true;
        } else
            return false;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(AGE, Integer.valueOf(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(AGE)).intValue();
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[]{AGE});
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

    public boolean setRightClickHarvest() {
        this.canRightClickHarvest = true;
        return canRightClickHarvest;
    }

    protected ItemStack notGrownDrop() {
        if (itemSeed == null) {
            return itemCrop;
        }
        return itemSeed;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return (state.getValue(AGE)).intValue() == 7 ? itemCrop.getItem() : notGrownDrop().getItem();
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        List<ItemStack> ret = new java.util.ArrayList<ItemStack>();
        int age = (state.getValue(AGE)).intValue();
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;

        if (age == 14) {
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

        if (age <= 7) {
            if (notGrownDrop() != null) {
                ret.add(notGrownDrop().copy());
            }
        }
        return ret;
    }
}