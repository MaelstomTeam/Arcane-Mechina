package com.maelstrom.arcanemechina.common.creativetab;

import com.maelstrom.arcanemechina.common.block.BlockList;
import com.maelstrom.arcanemechina.common.items.ItemList;
import com.maelstrom.snowcone.util.CreativeTabCustom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTab_AM extends CreativeTabCustom{

	public CreativeTab_AM() {
		super("AM", new ItemStack(ItemList.Crystal));
	}
	public static class Runic extends CreativeTabCustom
	{
		public Runic() {
			super("AM.RUNIC", new ItemStack(ItemList.Chalk));
		}
	}
	public static class Vegetation extends CreativeTabCustom
	{
		public Vegetation() {
			super("AM.PLANT", new ItemStack(Item.getItemFromBlock(BlockList.paperBarkSapling)));
		}
	}
	public static class Mechanical extends CreativeTabCustom
	{
		public Mechanical() {
			super("AM.MECH", null);
		}
	}
	public static class LIBRARY extends CreativeTabCustom
	{
		public LIBRARY() {
			super("AM.LIBRARY", new ItemStack(ItemList.HelpBook));
		}
	}
}
