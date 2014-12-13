package com.maelstrom.arcaneMechina.block;

import com.maelstrom.arcaneMechina.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;


//make class meta base
public class BlockOre extends Block {
	private String[] gemStones = { "opal", "amber", "zircon", "tourmaline", "saphire" };
	private String[] preciousMetals = {};
	
	public BlockOre() {
		super(Material.iron);
		this.setBlockName(Reference.MOD_ID+"."+"ore_");
	}
	
	
}
