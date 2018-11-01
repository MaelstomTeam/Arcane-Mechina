package com.maelstrom.arcanemechina.common.block;

import com.maelstrom.arcanemechina.api.ElementTypes;
import com.maelstrom.arcanemechina.api.RuneTypes;
import com.maelstrom.arcanemechina.common.items.ItemList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockList
{
	public static Block CrystalOre = new BlockOre(Material.ROCK, ElementTypes.AllNames());
	public static Block Ore = new BlockOre(Material.ROCK, ItemList.Ingots.subItemCount + ItemList.Gems.subItemCount, new String[] {"Copper", "Lead","Silver","Sapphire","Ruby", "Amethyst","WDiamond", "PDiamond", "YDiamond","Quartz"});
	public static BlockRune Rune = new BlockRune(RuneTypes.AllNames());
	public static BlockLog paperLog = new BlockLog();
	public static BlockLog paperLogDebarked = new BlockLog();
	public static Block paperLeaves = new BlockLeaf();
	public static BlockCustomSapling paperBarkSapling = new BlockCustomSapling();
	
}