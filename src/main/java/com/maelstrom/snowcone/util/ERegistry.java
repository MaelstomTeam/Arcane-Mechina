package com.maelstrom.snowcone.util;

import java.util.ArrayList;

import com.maelstrom.arcanemechina.common.block.BlockColoredMeta;
import com.maelstrom.arcanemechina.common.items.ItemColoredMeta;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.oredict.OreDictionary;

public abstract class ERegistry
{
	public abstract String getMODID();
	public abstract CreativeTabs getTab();
	
	//pre Initialization
	public abstract void preInitialization();
	
	//Initialization
	public abstract void Initialization();
	
	//post Initialization
	public abstract void postInitialization();
	public abstract ArrayList<Item> itemList();
	public abstract ArrayList<Block> listBlock();
	
	public void registerItem(Item item, String name)
	{
		registerItem(item, name, getTab());
	}
	
	public void registerItem(Item item, String name, CreativeTabs tab)
	{
		item.setUnlocalizedName(getMODID() + "." + name);
		item.setRegistryName(getMODID(), name);
		if(item.getCreativeTab() == null)
			item.setCreativeTab(tab);
		itemList().add(item);
	}
	
	public static void registerItemModel(Item item)
	{
		if(item.getHasSubtypes())
		{
			NonNullList<ItemStack> listOfSubItems = NonNullList.create();
			item.getSubItems(item.getCreativeTab(), listOfSubItems);
			for(ItemStack itemStacks : listOfSubItems)
			{
				registerItemModel(item, itemStacks.getMetadata());
			}
		}
		else
		{
			registerItemModel(item, 0);
		}
	}
	
	public void registerBlock(Block block, String name)
	{
		registerBlock(block,name,getTab());
	}
	
	public void registerBlock(Block block, String name, CreativeTabs tab)
	{
		block.setUnlocalizedName(getMODID() + "." + name);
		block.setRegistryName(getMODID(), name);
		block.setCreativeTab(tab);
		
		listBlock().add(block);
	}
	
	public static void registerItemModel(Item item, int meta)
	{
		if(item instanceof IHasName)
		{
			if(item.getHasSubtypes())
				ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName() + "/" + ((IHasName)item).getNameFromMeta(meta), "inventory"));
			else
				ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
		else
			if(item.getHasSubtypes())
				ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName() + "_" + meta, "inventory"));
			else
				ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	public static void registerIItemColor(ItemColoredMeta item)
	{
		IItemColor itemColor = item.getColorHandler();
		if(itemColor != null && item != null)
		{
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(itemColor, item);
		}
	}

	public static void registerIItemColor(BlockColoredMeta block)
	{
		IItemColor blockColor = block.getColorHandler();
		if(blockColor != null && block != null)
		{
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(blockColor, ItemBlock.getItemFromBlock(block));
		}
	}

	public static void registerIBlockColor(BlockColoredMeta block) {
		IBlockColor blockColor = block;
		if(blockColor != null && block != null)
		{
			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(blockColor, block);
		}
	}
	
	public abstract void registerOreDictionaryEntries();

	public static void registerOreDictionaryValue(String oredict, ItemStack itemstack)
	{
		OreDictionary.registerOre(oredict, itemstack);
	}
	
	public static void registerOreDictionaryValue(String oredict, Item item)
	{
		registerOreDictionaryValue(oredict, new ItemStack(item));
	}
	
	public static void registerOreDictionaryValue(String oredict, Block block)
	{
		registerOreDictionaryValue(oredict, new ItemStack(block));
	}
	
	public static void SetCreativeTab(Item i, CreativeTabs tab)
	{
		i.setCreativeTab(tab);
	}
	public static void SetCreativeTab(Block b, CreativeTabs tab)
	{
		b.setCreativeTab(tab);
	}
	
	/***
	 * 
	 *need better way to make a class with static variables for ore dictionary names
	 */
	public static class OreDictionaryNames
	{
		public static final String paper = "paper";
		public static class Ingot{
			public static final String Copper = "ingotCopper";
			public static final String Lead = "ingotLead";
			public static final String Silver = "ingotSilver";
		}
		public static class Gem
		{
			public static final String Sapphire = "gemSapphire";
			public static final String Ruby = "gemRuby";
			public static final String Amethyst = "gemAmethyst";
			public static final String Quartz = "gemQuartz";
			public static final String Diamond = "gemDiamond";
		}
		public static class Ore
		{
			public static final String Coal = "oreCoal";
			public static final String Iron = "oreIron";
			public static final String Gold = "oreGold";
			public static final String Diamond = "oreDiamond";
			public static final String Emerald = "oreEmerald";
			public static final String Quartz = "oreQuratz";
			public static final String Lapis = "oreLapis";
			public static final String Redstone = "oreRedstone";
			//
			public static final String gemSapphire = "oreSapphire";
			public static final String gemRuby = "oreRuby";
			public static final String gemAmethyst = "oreAmethyst";
			public static final String Copper = "oreCopper";
			public static final String Lead = "oreLead";
			public static final String Silver = "oreSilver";
		}
		public static class BlockOf
		{

			public static final String Coal = "blockCoal";
			public static final String Iron = "blockIron";
			public static final String Gold = "blockGold";
			public static final String Diamond = "blockDiamond";
			public static final String Emerald = "blockEmerald";
			public static final String Quartz = "blockQuratz";
			public static final String Lapis = "blockLapis";
			public static final String Redstone = "blockRedstone";
			//
			public static final String gemSapphire = "blockSapphire";
			public static final String gemRuby = "blockRuby";
			public static final String gemAmethyst = "blockAmethyst";
			public static final String Copper = "blockCopper";
			public static final String Lead = "blockLead";
			public static final String Silver = "blockSilver";
		}
	}
}