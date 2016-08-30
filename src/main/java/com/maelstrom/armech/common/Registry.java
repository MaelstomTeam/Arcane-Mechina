package com.maelstrom.armech.common;

import com.maelstrom.armech.common.reference.ModBlocks;
import com.maelstrom.armech.common.reference.ModItems;
import com.maelstrom.armech.common.reference.Reference;
import com.maelstrom.snowconeUtil.OreDictNames;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class Registry
{
	//pre initialization
	public static void preInit() {
		registerBlock(ModBlocks.dustOre.setCreativeTab(Reference.tabDust), Reference.dustOreName);
		ModBlocks.dustOre.setHarvestLevel("pickaxe", 2);
		registerOreDictionaryBlock(ModBlocks.copperOre, Reference.copperOreName, OreDictNames.CopperOre);
		ModBlocks.copperOre.setHarvestLevel("pickaxe", 1);
			//due to an unexplainable error the icon for tabBlocks cannot be set until after the block has be initialised
			Reference.tabBlocks.setCreativeTabIcon(Item.getItemFromBlock(ModBlocks.copperOre));
		registerOreDictionaryItem(ModItems.ingotCopper, Reference.copperIngotName, OreDictNames.CopperIngot);
		registerItem(ModItems.armechBook, Reference.armechBookName);
		registerItem(ModItems.dustCrystal.setCreativeTab(Reference.tabDust), Reference.dustCrystalName);
	}

	//initialization
	public static void init(){
	}

	//post initialization
	public static void postInit() {
		//add shappless recipe for armech book
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.armechBook), Items.BOOK, ModItems.dustCrystal);
		//add smelting recipe for copper
		GameRegistry.addSmelting(ModBlocks.copperOre, new ItemStack(ModItems.ingotCopper), 0.7f);
	}
	//register item function
	private static void registerItem(Item item, String itemID){
		//set item registry name to itemID
		item.setRegistryName(Reference.MODID, itemID);
		//set item unlocalised name to itemID
		item.setUnlocalizedName(Reference.MODID + "." + itemID);
		//check if item has a creative tab if not add it to the default tab
		if(item.getCreativeTab() == null)
			//set item creative tab to tabItems of ArMech
			item.setCreativeTab(Reference.tabItems);
		//register item
		GameRegistry.register(item);
		//change this to a proxy version
		//add a model to the item in inventory
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, itemID), "inventory"));
	}

	public static void registerOreDictionaryItem(Item item, String itemID, String oreDict)
	{
		registerItem(item,itemID);
		//register item to ore dictionary using vaule from "String oreDict"
		OreDictionary.registerOre(oreDict, item);
	}

	public static void registerOreDictionaryBlock(Block block, String blockID, String oreDict)
	{
		registerBlock(block, blockID);
		//register block to ore dictionary using vaule from "String oreDict"
		OreDictionary.registerOre(oreDict, block);
	}

	public static void registerBlock(Block block, String blockID)
	{
		//set block registry name to blockID
		block.setRegistryName(Reference.MODID, blockID);
		//set block unlocalised name to "modid.blockID"
		block.setUnlocalizedName(Reference.MODID + "." + blockID);
		//check if block has a creative tab if not add it to the default tab
		if(block.getCreativeTabToDisplayOn() == null)
			//set block creative tab
			block.setCreativeTab(Reference.tabBlocks);
		//create itemblock variable using block
		ItemBlock itemBlock = (ItemBlock) new ItemBlock(block);
		//set itemblock registry name
		itemBlock.setRegistryName(block.getRegistryName());
		//register the block
		GameRegistry.register(block);
		//register the itemblock
		GameRegistry.register(itemBlock);
		//change this to a proxy version
		//add a model to the block in inventory
		ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, blockID), "inventory"));
	}
}