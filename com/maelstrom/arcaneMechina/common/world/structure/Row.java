package com.maelstrom.arcaneMechina.common.world.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class Row {
	private Block[] blocks;
	private String row;
	private int center;
	public Row(String row, Object... o){
		this.row = row;
		int length = 0;
		HashMap<Character, Block> l;
		for(l = new HashMap(); length < o.length; length += 2)
			if(o[length] instanceof Character)
				if(o[length + 1] instanceof Block)
					l.put((Character)o[length], (Block)o[length + 1]);
				else if(o[length + 1] == null)
					l.put((Character)o[length], Blocks.air);
		this.center = (length / 2) + (length % 2);
		this.blocks = new Block[row.length()];
		for(int i = 0; i < row.length(); i++){
			try{
				blocks[i] = l.get(row.charAt(i));
			}catch(Exception e){
				System.out.println(e);
			}
		}
	}
	
	public Block[] getBlocks(){
		return blocks;
	}
}