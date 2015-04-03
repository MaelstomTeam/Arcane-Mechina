package com.maelstrom.arcaneMechina.common.blocks;

import net.minecraft.block.Block;
import cofh.api.energy.EnergyStorage;

import com.maelstrom.arcaneMechina.common.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
	
	public static final Block glyphBlank = new BlockChalkGlyph("glyphblank");
	public static final BlockAllSideArray solar = new BlockAllSideArray("solar");//, new EnergyStorage(10000, 0, 2048));
	public static final BlockCompressedArray lunar = new BlockCompressedArray("lunar", new EnergyStorage(10000, 0, 2048));
	public static final BlockCompressedArray wind = new BlockCompressedArray("wind", new EnergyStorage(10000, 0, 2048));
	public static final BlockCompressedArray geo = new BlockCompressedArray("geo", new EnergyStorage(10000, 0, 2048));
	public static final BlockCompressedArray exportPower = new BlockCompressedArray("exportPower", new EnergyStorage(10000, 0, 2048));
	public static final BlockCompressedArray importPower = new BlockCompressedArray("importPower", new EnergyStorage(10000, 0, 2048));
	public static final BlockCompressedArray basicCompression = new BlockCompressedArray("basicCompression", new EnergyStorage(10000, 0, 2048));
	
	//do whatever to this!
	public static final Block basicPowerGen = new BlockPowerFurnace("pfurnace");
	
	public static final Block gemOre = new BlockOre(Reference.gemStones, "gemOre");
	public static final Block metalOre = new BlockOre(Reference.preciousMetals, "metalOre");
	public static final Block rfStorageArray = new BlockRFStorageArray("rfStorageArray");

	public static void init(){
		GameRegistry.registerBlock(glyphBlank, Reference.MOD_ID + glyphBlank.getUnlocalizedName());
		GameRegistry.registerBlock(basicPowerGen, Reference.MOD_ID + basicPowerGen.getUnlocalizedName());
		GameRegistry.registerBlock(rfStorageArray, Reference.MOD_ID + rfStorageArray.getUnlocalizedName());
		GameRegistry.registerBlock(gemOre, ItemMetaBlock.class, Reference.MOD_ID+".gemOre");
		GameRegistry.registerBlock(metalOre, ItemMetaBlock.class, Reference.MOD_ID+".metalOre");
		GameRegistry.registerBlock(solar, Reference.MOD_ID + solar.getUnlocalizedName());
		GameRegistry.registerBlock(lunar, Reference.MOD_ID + lunar.getUnlocalizedName());
		GameRegistry.registerBlock(wind, Reference.MOD_ID + wind.getUnlocalizedName());
		GameRegistry.registerBlock(geo, Reference.MOD_ID + geo.getUnlocalizedName());
		GameRegistry.registerBlock(exportPower, Reference.MOD_ID + exportPower.getUnlocalizedName());
		GameRegistry.registerBlock(importPower, Reference.MOD_ID + importPower.getUnlocalizedName());
		GameRegistry.registerBlock(basicCompression, Reference.MOD_ID + basicCompression.getUnlocalizedName());
	}
}
