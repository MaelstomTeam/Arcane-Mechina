package com.maelstrom.arcanemechina.common.block;

import com.maelstrom.arcanemechina.api.ElementTypes;
import com.maelstrom.arcanemechina.api.RuneTypes;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockList
{
	public static BlockColoredMeta CrystalOre = new BlockColoredMeta(Material.ROCK, ElementTypes.AllNames());
	public static Block Ore = new BlockCustomOre(Material.ROCK);
	public static BlockRune Rune = new BlockRune(RuneTypes.AllNames());
	public static BlockCustomLog paperLog = new BlockCustomLog();
	public static BlockCustomLog paperLogDebarked = new BlockCustomLog();
	public static Block leaves = new BlockCustomLeaf();
	public static Block planks = new BlockCustomPlanks();
	public static BlockCustomSapling paperBarkSapling = new BlockCustomSapling(paperLog, leaves, 20, 25, 5, 1);
	public static BlockMortar mortar = new BlockMortar();
	public static BlockCrystalizer crystalizer = new BlockCrystalizer();
	
}