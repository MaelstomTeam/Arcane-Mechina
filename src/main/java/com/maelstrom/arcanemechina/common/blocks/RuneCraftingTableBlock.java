package com.maelstrom.arcanemechina.common.blocks;

import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.tileentity.RuneCraftingTileEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkHooks;

public class RuneCraftingTableBlock extends ContainerBlock
{

	public RuneCraftingTableBlock(Properties prop)
	{
		super(prop);
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn)
	{
		return Registry.RUNE_CRAFT_TILE.create();
	}

	public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if (player instanceof ServerPlayerEntity && !(player instanceof FakePlayer) && world.getTileEntity(pos) instanceof RuneCraftingTileEntity)
			NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) world.getTileEntity(pos), pos);
		return true;
	}

	public boolean isSolid(BlockState state)
	{
		return false;
	}

	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
}
