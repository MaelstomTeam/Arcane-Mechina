package com.maelstrom.armech.common;

import com.maelstrom.armech.common.block.BlockBase;
import com.maelstrom.armech.common.block.BlockOre;
import com.maelstrom.armech.common.block.BlockOreDust;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Blocks {
	public static Block dustOre = new BlockOreDust();
	public static Block copperOre = new BlockOre();
}