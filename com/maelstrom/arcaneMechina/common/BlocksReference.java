package com.maelstrom.arcanemechina.common;

import net.minecraft.block.Block;

import com.maelstrom.arcanemechina.common.blocks.BlockArray;
import com.maelstrom.arcanemechina.common.blocks.BlockChalkGlyph;
import com.maelstrom.arcanemechina.common.blocks.BlockResearchStand;

public class BlocksReference
{
	public static final Block arrayBlock = new BlockArray();
	public static final String arrayName = "arrayBlock";
	
	public static final Block glyphBlock = new BlockChalkGlyph();
	public static final String glyphName = "glyphBlock";
	
	public static final Block rStand = new BlockResearchStand();
	public static final String rStandName = "researchStand";
}