package com.maelstrom.arcanemechina.common.blocks;

import com.maelstrom.arcanemechina.common.Registry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class RuneBlock extends ContainerBlock {

	public RuneBlock(Properties properties) {
		super(properties);
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return Registry.RUNE_TILE.create();
	}

	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	private static final VoxelShape X_AXIS_AABB = Block.makeCuboidShape(0d, 0.1d, 0d, 16d, 1.1d, 16d);

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return X_AXIS_AABB;
	}

	@Deprecated
	public boolean isSolid(BlockState state) {
		return false;
	}

	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

}
