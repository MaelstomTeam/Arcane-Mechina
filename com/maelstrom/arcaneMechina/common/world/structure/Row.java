package com.maelstrom.arcaneMechina.common.world.structure;

import net.minecraft.block.Block;

public class Row {
	private Block[] blocks;
	public Row(Block[] b){
		blocks = b;
	}
	
	public Block[] getBlocks(){
		return blocks;
	}
}