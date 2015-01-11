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

	public static final Block glyphblank = new BlockChalkGlyph("glyph");
	
	public static final Block glyphfire = new BlockChalkGlyph("glyphfire");
	public static final Block glyphice = new BlockChalkGlyph("glyphice");
	public static final Block glyphearth = new BlockChalkGlyph("glyphearth");
	public static final Block glyphair = new BlockChalkGlyph("glyphair");
	public static final Block glyphenergy = new BlockChalkGlyph("glyphenergy");
	

	public static final Block glyphCenter = new BlockChalkGlyph("ArrayCenter");

	public static final Block rTable = new BlockResearchTable("RTable");
	public static final Block cTable = new BlockCraftingTable(new String[]{"magicCrafting","techCrafting"}, "CTable");
	public static final Block magiTechTable = new BlockCraftingTable("MagiTechTable");
	
	public static void init(){
		GameRegistry.registerBlock(gemOre, ItemMetaBlock.class, Reference.MOD_ID+".gemOre");
		GameRegistry.registerBlock(metalOre, ItemMetaBlock.class, Reference.MOD_ID+".metalOre");
		GameRegistry.registerBlock(arrayFurnace, Reference.MOD_ID+".arrayFurnace");
		GameRegistry.registerBlock(glyphblank, Reference.MOD_ID+".glyph");
		GameRegistry.registerBlock(glyphfire, Reference.MOD_ID+"."+glyphfire.getUnlocalizedName());
		GameRegistry.registerBlock(glyphice, Reference.MOD_ID+"."+glyphice.getUnlocalizedName());
		GameRegistry.registerBlock(glyphearth, Reference.MOD_ID+"."+glyphearth.getUnlocalizedName());
		GameRegistry.registerBlock(glyphair, Reference.MOD_ID+"."+glyphair.getUnlocalizedName());
		GameRegistry.registerBlock(glyphenergy, Reference.MOD_ID+"."+glyphenergy.getUnlocalizedName());
		GameRegistry.registerBlock(glyphCenter, Reference.MOD_ID+"."+glyphCenter.getUnlocalizedName());
		GameRegistry.registerBlock(rTable, Reference.MOD_ID+".RTable");
		GameRegistry.registerBlock(cTable, ItemMetaBlock.class, Reference.MOD_ID+".CTable");
		GameRegistry.registerBlock(magiTechTable, Reference.MOD_ID+".MagiTechTable");
	}

}
