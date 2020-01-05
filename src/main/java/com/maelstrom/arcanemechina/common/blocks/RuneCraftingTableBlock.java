package com.maelstrom.arcanemechina.common.blocks;

import com.maelstrom.arcanemechina.common.Registry;

import net.minecraft.block.ContainerBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

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

}
