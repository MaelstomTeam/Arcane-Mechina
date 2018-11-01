package com.maelstrom.arcanemechina.common.block;

import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCustomSapling extends BlockBush implements IGrowable
{
	public BlockCustomSapling()
    {
        super(Material.GRASS);
    }
    public void generateTree(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
    	generate(worldIn, rand, pos.add(0, 0, 0));
    }
    
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return true;
	}


    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            super.updateTick(worldIn, pos, state, rand);

            if (!worldIn.isAreaLoaded(pos, 1)) return;
            if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0)
            {
                this.grow(worldIn, rand, pos, state);
            }
        }
    }
    
	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return (double)worldIn.rand.nextFloat() < 0.45D;
	}
	
	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		this.generateTree(worldIn, pos, state, rand);
	}

	//totally stolen from tree generation
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
    	IBlockState metaWood = BlockList.paperLog.getDefaultState();
    	IBlockState metaLeaves = BlockList.paperLeaves.getDefaultState();
        int i = rand.nextInt(13) + 15;
        boolean flag = true;

        if (position.getY() >= 1 && position.getY() + i - 1 <= worldIn.getHeight())
        {
            for (int y = position.getY(); y <= position.getY() + 1 + i; ++y)
            {
                int j = 1;

                if (y == position.getY())
                {
                    j = 0;
                }

                if (y >= position.getY() + 1 + i - 2)
                {
                    j = 5;
                }

                for (int x = position.getX() - j; x <= position.getX() + j && flag; ++x)
                {
                    for (int z = position.getZ() - j; z <= position.getZ() + j && flag; ++z)
                    {
                        if (y >= 0 && y < worldIn.getHeight())
                        {
                            if (!worldIn.isAirBlock(position.add(x, y, z)))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag)
            {
                return false;
            }
            else
            {
                IBlockState state = worldIn.getBlockState(position.down());

                if (state.getBlock().canSustainPlant(state, worldIn, position.down(), net.minecraft.util.EnumFacing.UP, (net.minecraft.block.BlockSapling)Blocks.SAPLING) && position.getY() < worldIn.getHeight() - i - 1)
                {
                    state.getBlock().onPlantGrow(state, worldIn, position.down(), position);

                    for (int y = position.getY() - 5 + i; y <= position.getY() + i; ++y)
                    {
                        int i4 = y - (position.getY() + i);
                        int j1 = 1;

                        for (int x = position.getX() - j1; x <= position.getX() + j1; ++x)
                        {
                            int l1 = x - position.getX();

                            for (int z = position.getZ() - j1; z <= position.getZ() + j1; ++z)
                            {
                                int j2 = z - position.getZ();

                                if (Math.abs(l1) != j1 || Math.abs(j2) != j1 || rand.nextInt(2) != 0 && i4 != 0)
                                {
                                    BlockPos blockpos = new BlockPos(x, y, z);
                                    state = worldIn.getBlockState(blockpos);

                                    if (state.getBlock().isAir(state, worldIn, blockpos) || state.getBlock().isLeaves(state, worldIn, blockpos) || state.getMaterial() == Material.VINE || state.getBlock() == this)
                                    {
                                        worldIn.setBlockState(blockpos, metaLeaves);
                                    }
                                }
                            }
                        }
                    }

                    for (int j3 = 0; j3 < i; ++j3)
                    {
                        BlockPos upN = position.up(j3);
                        state = worldIn.getBlockState(upN);

                        if (state.getBlock().isAir(state, worldIn, upN) || state.getBlock().isLeaves(state, worldIn, upN) || state.getMaterial() == Material.VINE)
                        {
                            worldIn.setBlockState(position.up(j3), metaWood);
                        }
                    }

                    worldIn.setBlockState(position, metaWood);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }
}