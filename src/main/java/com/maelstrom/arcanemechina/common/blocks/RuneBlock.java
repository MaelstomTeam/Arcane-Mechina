package com.maelstrom.arcanemechina.common.blocks;

import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.runic.newrune.RuneContainer;
import com.maelstrom.arcanemechina.common.runic.newrune.RuneType;
import com.maelstrom.arcanemechina.common.runic.newrune.RuneType.RedstoneIORune;
import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class RuneBlock extends ContainerBlock
{

	public static final BooleanProperty canPower 	   = BooleanProperty.create("can_power");
	public static final BooleanProperty canPowerStrong = BooleanProperty.create("can_power_strong");
	
	public RuneBlock(Properties properties)
	{
		super(properties);
		this.setDefaultState(getDefaultState().with(canPower, false));
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn)
	{
		return Registry.RUNE_TILE.create();
	}

	public BlockRenderType getRenderType(BlockState state)
	{
		return BlockRenderType.MODEL;
	}

	private static final VoxelShape X_AXIS_AABB = Block.makeCuboidShape(0d, 0.1d, 0d, 16d, 1.1d, 16d);

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return X_AXIS_AABB;
	}

	@Deprecated
	public boolean isSolid(BlockState state)
	{
		return false;
	}

	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Deprecated
	public boolean canProvidePower(BlockState state)
	{
		return state.get(canPower) || state.get(canPowerStrong);
	}

	@Deprecated
	public int getWeakPower(BlockState state, IBlockReader world, BlockPos pos, Direction side)
	{
		if(canProvidePower(state))
		{
			RuneContainer c = ((RuneTileEntity)world.getTileEntity(pos)).getRuneContainer();
			if(c.hasRune(RuneType.RedstoneIORune.class))
			{
				int maxForSide = 0;
				for(RuneType r : c.getRune(RuneType.RedstoneIORune.class))
				{
					RedstoneIORune rune = (RedstoneIORune) r;
					if(rune.canOutputRedstone() && rune.getSide() == side)
					{
						int newPower = rune.getPower();
						if(newPower > maxForSide)
							maxForSide = newPower;
					}
				}
				return maxForSide;
			}
		}
		return 0;
	}

	public int getStrongPower(BlockState state, IBlockReader world, BlockPos pos, Direction side)
	{
		if(canProvidePower(state))
		{
			RuneContainer c = ((RuneTileEntity)world.getTileEntity(pos)).getRuneContainer();
			if(c.hasRune(RuneType.RedstoneIORune.class))
			{
				int maxForSide = 0;
				for(RuneType r : c.getRune(RuneType.RedstoneIORune.class))
				{
					RedstoneIORune rune = (RedstoneIORune) r;
					if(rune.canOutputRedstone() && rune.getSide() == side)
					{
						int newPower = rune.getPower();
						if(newPower > maxForSide)
							maxForSide = newPower;
					}
				}
				return maxForSide;
			}
		}
		return 0;
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(canPower, canPowerStrong);
	}

}
