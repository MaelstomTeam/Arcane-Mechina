package com.maelstrom.armech.common.reference;

import com.maelstrom.armech.common.creativetab.CustomCreativeTab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class Reference {
	public static final String MODID = "armech";
	public static final String VERSION = "1.0";
	
	
	public static final CustomCreativeTab tabBlocks = new CustomCreativeTab(Item.getItemFromBlock(ModBlocks.copperOre), "blocks");
	public static final CustomCreativeTab tabItems = new CustomCreativeTab(ModItems.armechBook, "items");
	public static final CustomCreativeTab tabDust = new CustomCreativeTab(ModItems.dustCrystal, "items.dust");
	
	
	public static final String dustOreName = "dustOre";
	public static final String copperOreName = "copperOre";
	public static final String armechBookName = "armechBook";
	public static final String dustCrystalName = "dustCrystal";
}