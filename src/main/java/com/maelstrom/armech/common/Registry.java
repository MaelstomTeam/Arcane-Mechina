package com.maelstrom.armech.common;

import com.maelstrom.armech.common.reference.ModBlocks;
import com.maelstrom.armech.common.reference.ModItems;
import com.maelstrom.armech.common.reference.Reference;
import com.maelstrom.snowconeUtil.OreDictNames;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class Registry
{
	public static void preInit() {
		registerBlock(ModBlocks.dustOre, Reference.dustOreName);
		registerOreDictionaryBlock(ModBlocks.copperOre, Reference.copperOreName, OreDictNames.CopperOre);
		registerItem(ModItems.armechBook, Reference.armechBookName);
		registerItem(ModItems.dustCrystal, Reference.dustCrystalName);
	}

	public static void init(){
	}

	public static void postInit() {
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.armechBook), net.minecraft.init.Items.BOOK, ModItems.dustCrystal);
	}

	private static void registerItem(Item item, String itemID){
		item.setRegistryName(Reference.MODID, itemID);
		item.setUnlocalizedName(itemID);
		item.setCreativeTab(Reference.tabItems);
		GameRegistry.register(item);
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, itemID), "inventory"));
	}

	public static void registerOreDictionaryItem(Item item, String itemID, String oreDict)
	{
		registerItem(item,itemID);
		OreDictionary.registerOre(oreDict, item);
	}

	public static void registerOreDictionaryBlock(Block block, String blockID, String oreDict)
	{
		registerBlock(block, blockID);
		OreDictionary.registerOre(oreDict, block);
	}

	public static void registerBlock(Block block, String blockID)
	{
		block.setRegistryName(Reference.MODID, blockID);
		block.setUnlocalizedName(blockID);
		block.setCreativeTab(Reference.tabBlocks);
		ItemBlock itemBlock = (ItemBlock) new ItemBlock(block);
		itemBlock.setRegistryName(block.getRegistryName());
		GameRegistry.register(block);
		GameRegistry.register(itemBlock);
		ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, blockID), "inventory"));
	}
}