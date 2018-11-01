package com.maelstrom.arcanemechina.common.creativetab;

import com.maelstrom.arcanemechina.common.items.ItemList;
import com.maelstrom.snowcone.util.CreativeTab_;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class CreativeTab_AM extends CreativeTab_{

	public CreativeTab_AM() {
		super("AM", new ItemStack(ItemList.Crystal));
	}
	public static class Runic extends CreativeTab_
	{
		public Runic() {
			super("AM.RUNIC", new ItemStack(ItemList.Chalk));
		}
	}
	public static class Vegetation extends CreativeTab_
	{
		public Vegetation() {
			super("AM.PLANT", new ItemStack(Blocks.SAPLING));
		}
	}
	public static class Mechanical extends CreativeTab_
	{
		public Mechanical() {
			super("AM.MECH", new ItemStack(Blocks.UNPOWERED_REPEATER));
		}
	}
	public static class LIBRARY extends CreativeTab_
	{
		public LIBRARY() {
			super("AM.LIBRARY", new ItemStack(ItemList.HelpBook));
		}
	}
}
