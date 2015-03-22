package com.maelstrom.arcaneMechina.common.blocks;

import net.minecraft.block.Block;

import com.maelstrom.arcaneMechina.common.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
	
	public static final Block glyphBlank = new BlockChalkGlyph("glyphblank");
	
	//do whatever to this!
	public static final Block basicPowerGen = new BlockPowerFurnace("pfurnace");
	
	public static final Block gemOre = new BlockOre(Reference.gemStones, "gemOre");
	public static final Block metalOre = new BlockOre(Reference.preciousMetals, "metalOre");

	public static void init(){
		GameRegistry.registerBlock(glyphBlank, Reference.MOD_ID + glyphBlank.getUnlocalizedName());
		GameRegistry.registerBlock(basicPowerGen, Reference.MOD_ID + basicPowerGen.getUnlocalizedName());
		GameRegistry.registerBlock(gemOre, ItemMetaBlock.class, Reference.MOD_ID+".gemOre");
		GameRegistry.registerBlock(metalOre, ItemMetaBlock.class, Reference.MOD_ID+".metalOre");
	}
}
