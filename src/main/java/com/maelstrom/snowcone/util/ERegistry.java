package com.maelstrom.snowcone.util;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
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
	@Deprecated
	static class OreDictionaryNames
	{
		public static String ingotCopper;
		public static String ingotLead;
		public static String ingotSilver;
		public static String gemSapphire;
		public static String gemRuby;
		public static String gemAmethyst;
		public static String gemQuartz;
	}
}