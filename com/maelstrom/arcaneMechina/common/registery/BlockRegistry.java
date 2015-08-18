package com.maelstrom.arcanemechina.common.registery;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

import com.maelstrom.arcanemechina.common.BlocksReference;
import com.maelstrom.arcanemechina.common.Reference;
import com.maelstrom.arcanemechina.common.tileentity.TileEntityArray;
import com.maelstrom.arcanemechina.common.tileentity.TileEntityResearch;
import com.maelstrom.arcanemechina.common.tileentity.TileWardTest;

import cpw.mods.fml.common.registry.GameRegistry;

public class BlockRegistry extends com.maelstrom.snowcone.registers.BlockRegistry
{
	
	public static void registerALL()
	{
		registerBasicBlock(BlocksReference.glyphBlock, BlocksReference.glyphName, false);
		registerBlockAndTile(BlocksReference.arrayBlock, BlocksReference.arrayName, TileEntityArray.class, false);
		registerBlockAndTile(BlocksReference.rStand, BlocksReference.rStandName, TileEntityResearch.class, false);
	}
	
	
	
	//=============================================
	//==refer to super class for functions bellow==
	//=============================================
	
	private static void registerBasicBlock(Block block, String blockName, boolean addToTab)
	{
		if(addToTab)
			registerBlock(block, blockName, Reference.tab);
		else
			registerBlock(block, blockName, null);
	}

	private static void registerBlockWithHarvest(Block block, String blockName, HarvestType type, int harvestLevel, boolean addToTab)
	{
		if(addToTab)
			registerBlock(block, blockName, type, harvestLevel, Reference.tab, null);
		else
			registerBlock(block, blockName, type, harvestLevel, null, null);
	}

	private static void registerBlockAndTileWithHarvest(Block block, String blockName, HarvestType type, int harvestLevel, Class<? extends TileEntity> tile, boolean addToTab)
	{
		if(addToTab)
			registerBlock(block, blockName, type, harvestLevel, Reference.tab, tile);
		else
			registerBlock(block, blockName, type, harvestLevel, null, tile);
	}
	private static void registerBlockAndTile(Block block, String blockName, Class<? extends TileEntity> tile, boolean addToTab)
	{
		if(addToTab)
			registerBlock(block, blockName, null, 0, Reference.tab, tile);
		else
			registerBlock(block, blockName, null, 0, null, tile);
	}
}
