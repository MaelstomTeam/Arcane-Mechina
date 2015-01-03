package com.maelstrom.arcaneMechina.common.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.maelstrom.arcaneMechina.common.block.BlockArrayFurnace;
import com.maelstrom.arcaneMechina.common.block.BlockChalkGlyph;
import com.maelstrom.arcaneMechina.common.block.BlockCraftingTable;
import com.maelstrom.arcaneMechina.common.block.BlockOre;
import com.maelstrom.arcaneMechina.common.block.BlockResearchTable;
import com.maelstrom.arcaneMechina.common.block.ItemMetaBlock;
import com.maelstrom.arcaneMechina.common.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

public class InitBlock {

	public static final Block gemOre = new BlockOre(Reference.gemStones, "gemOre");
	public static final Block metalOre = new BlockOre(Reference.preciousMetals, "metalOre");
	public static final Block arrayFurnace = new BlockArrayFurnace(Material.rock, "arrayFurnace");
	public static final Block glyph = new BlockChalkGlyph("glyph");

	public static final Block rTable = new BlockResearchTable("RTable");
	public static final Block cTable = new BlockCraftingTable(new String[]{"magicCrafting","techCrafting"}, "CTable");
	public static final Block magiTechTable = new BlockCraftingTable("MagiTechTable");
	
	public static void init(){
		GameRegistry.registerBlock(gemOre, ItemMetaBlock.class, Reference.MOD_ID+".gemOre");
		GameRegistry.registerBlock(metalOre, ItemMetaBlock.class, Reference.MOD_ID+".metalOre");
		GameRegistry.registerBlock(arrayFurnace, Reference.MOD_ID+".arrayFurnace");
		GameRegistry.registerBlock(glyph, Reference.MOD_ID+".glyph");
		GameRegistry.registerBlock(rTable, Reference.MOD_ID+".RTable");
		GameRegistry.registerBlock(cTable, ItemMetaBlock.class, Reference.MOD_ID+".CTable");
		GameRegistry.registerBlock(magiTechTable, Reference.MOD_ID+".MagiTechTable");
	}

}
