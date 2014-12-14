package com.maelstrom.arcaneMechina.init;

import net.minecraft.block.Block;

import com.maelstrom.arcaneMechina.block.BlockOre;
import com.maelstrom.arcaneMechina.block.ItemMetaBlock;
import com.maelstrom.arcaneMechina.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

public class InitBlock {

	public static final Block gemOre = new BlockOre(BlockOre.gemStones, "gemOre");
	public static final Block metalOre = new BlockOre(BlockOre.preciousMetals, "metalOre");
	
	public static void init(){
		GameRegistry.registerBlock(gemOre, ItemMetaBlock.class, Reference.MOD_ID+".gemOre");
		GameRegistry.registerBlock(metalOre, ItemMetaBlock.class, Reference.MOD_ID+".metalOre");
	}

}
