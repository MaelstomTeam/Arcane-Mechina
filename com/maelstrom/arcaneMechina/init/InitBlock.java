package com.maelstrom.arcaneMechina.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.maelstrom.arcaneMechina.block.BlockArrayFurnace;
import com.maelstrom.arcaneMechina.block.BlockOre;
import com.maelstrom.arcaneMechina.block.ItemMetaBlock;
import com.maelstrom.arcaneMechina.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

public class InitBlock {

	public static final Block gemOre = new BlockOre(Reference.gemStones, "gemOre");
	public static final Block metalOre = new BlockOre(Reference.preciousMetals, "metalOre");
	public static final Block arrayFurnace = new BlockArrayFurnace(Material.rock, "arrayFurnace");
	public static void init(){
		GameRegistry.registerBlock(gemOre, ItemMetaBlock.class, Reference.MOD_ID+".gemOre");
		GameRegistry.registerBlock(metalOre, ItemMetaBlock.class, Reference.MOD_ID+".metalOre");
		GameRegistry.registerBlock(arrayFurnace, Reference.MOD_ID+".arrayFurnace");
	}

}
