package com.maelstrom.arcanemechina.common.blocks;

import javax.annotation.Nullable;

import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.runic.RuneContainer;
import com.maelstrom.arcanemechina.common.runic.RuneHelper;
import com.maelstrom.arcanemechina.common.runic.RuneType;
import com.maelstrom.arcanemechina.common.runic.RuneType.RedstoneIORune;
import com.maelstrom.arcanemechina.common.runic.rune_interfaces.IInventoryRune;
import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class RuneBlock extends ContainerBlock
{

	public static final BooleanProperty canPowerWeak              = BooleanProperty.create("can_power_weak");
	public static final BooleanProperty canPowerStrong            = BooleanProperty.create("can_power_strong");
	public static final EnumProperty<SPECIAL_RUNE> specialRune    = EnumProperty   .create("other_rune", SPECIAL_RUNE.class);
	
	public static enum SPECIAL_RUNE implements IStringSerializable
	{
		NONE("none"),
		CUSTOM("custom"),
		SMALL_TRANSMUTE("small_transmute"),
		LARGE_TRANSMUTE("large_transmute");
		
		String name;
		SPECIAL_RUNE(String name)
		{
			this.name = name;
		}
		
		@Override
		public String getName()
		{
			return name;
		}
		
	}
	
	public RuneBlock(Properties properties)
	{
		super(properties);
		this.setDefaultState(getDefaultState().with(canPowerWeak, false).with(specialRune,SPECIAL_RUNE.NONE));
	}
	
    @Nullable
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        if (state.get(specialRune) == SPECIAL_RUNE.CUSTOM)
            return Registry.RUNE_TILE.create();
        return null;
    }

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn)
	{
		return null;
	}

	public BlockRenderType getRenderType(BlockState state)
	{
		if(state.get(specialRune) == SPECIAL_RUNE.CUSTOM)
			return BlockRenderType.INVISIBLE;
		return BlockRenderType.MODEL;
	}

	private static final VoxelShape Single_Block 			= Block.makeCuboidShape(0d, 0d, 0d, 16d, 1d, 16d);
	private static final VoxelShape Single_Block_Halfsize 	= Block.makeCuboidShape(0d, 0d, 0d, 16d, 0.5d, 16d);
	private static final VoxelShape Two_Block 				= Block.makeCuboidShape(-8d, 0d, -8d, 24d, 1d, 24d);
	private static final VoxelShape RUNE 					= Block.makeCuboidShape(5.5d, 0d, 5.5d, 10.5d, 1d, 10.5d);

	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context)
	{
		if(context.hasItem(Registry.chalk))
			if(state.get(specialRune) == SPECIAL_RUNE.LARGE_TRANSMUTE)
			{
				return Two_Block;
			}
			else if(state.get(specialRune) == SPECIAL_RUNE.CUSTOM)
			{
				if(world != null && world.getTileEntity(pos) != null)
				{
					((RuneTileEntity)world.getTileEntity(pos)).setShape(null);
					if(((RuneTileEntity)world.getTileEntity(pos)).getShape() == null)
					{
						VoxelShape new_shape = Single_Block_Halfsize;
						for(RuneType rune : ((RuneTileEntity)world.getTileEntity(pos)).getRuneContainer().getChildren())
						{
							if(rune != null)
							{
								if(!(rune instanceof RuneType.IORune))
									new_shape = AddShapesWithOffset(RUNE, new_shape, rune.getPosition());
							}
						}
						((RuneTileEntity)world.getTileEntity(pos)).setShape(new_shape);
					}
					return ((RuneTileEntity)world.getTileEntity(pos)).getShape();
				}
			}
		return Single_Block;
	}
	
	private VoxelShape AddShapesWithOffset(VoxelShape vox1, VoxelShape vox2, Vec2f offset)
	{
		return VoxelShapes.combine(vox1.withOffset(offset.x/16-0.5, 1/16f, offset.y/16-0.5), vox2, IBooleanFunction.OR);
	}

	public boolean isSolid(BlockState state)
	{
		return false;
	}

	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	public boolean canProvidePower(BlockState state)
	{
		return state.get(canPowerWeak) || state.get(canPowerStrong);
	}

	public int getWeakPower(BlockState state, IBlockReader world, BlockPos pos, Direction side)
	{
		if(state.get(canPowerWeak))
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
		if(state.get(canPowerStrong))
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

	public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
	{
		builder.add(canPowerWeak, canPowerStrong, specialRune);
	}
	
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
    {
    	if(world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof RuneTileEntity)
    	{
    		ItemStack temp = RuneHelper.toItem(((RuneTileEntity)world.getTileEntity(pos)).rune);
    		RuneContainer temp2 = RuneHelper.fromItem(temp);
    		for(RuneType rune : temp2.getChildren())
    			if(rune instanceof IInventoryRune)
    				((IInventoryRune)rune).clear();
    		return RuneHelper.toItem(((RuneTileEntity)world.getTileEntity(pos)).rune);
    	}
    	return super.getPickBlock(state, target, world, pos, player);
    }

}
