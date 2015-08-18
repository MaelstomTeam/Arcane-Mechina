package com.maelstrom.arcanemechina.common.proxy;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.maelstrom.arcanemechina.common.ItemsReference;
import com.maelstrom.arcanemechina.common.Reference;
import com.maelstrom.arcanemechina.common.registery.BlockRegistry;
import com.maelstrom.arcanemechina.common.registery.ItemRegistry;
import com.maelstrom.arcanemechina.library.AMBookHelper;
import com.maelstrom.snowcone.proxy.IProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy implements IProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		ItemRegistry.registerALL();
		BlockRegistry.registerALL();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(ItemsReference.amuletWing);
		registerCrafting();
	}


	private void registerCrafting()
	{
		//commented out non-oredict recipes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsReference.chalk), "XO", "OX", 'X', new ItemStack(Items.clay_ball), 'O', "dyeWhite"));
//		GameRegistry.addShapelessRecipe(new ItemStack(ItemsReference.itemAMBook), new ItemStack(ItemsReference.itemChalk), new ItemStack(Items.writable_book), new ItemStack(Items.iron_ingot));
//		GameRegistry.addShapelessRecipe(new ItemStack(ItemsReference.itemAMBook), new ItemStack(ItemsReference.itemChalk), new ItemStack(Items.book), new ItemStack(Items.feather), new ItemStack(Items.dye, 1, 0), new ItemStack(Items.iron_ingot));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemsReference.AMBook), new ItemStack(ItemsReference.chalk), new ItemStack(Items.writable_book), "ingotIron"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemsReference.AMBook), new ItemStack(ItemsReference.chalk), new ItemStack(Items.book), new ItemStack(Items.feather), "dyeBlack", "ingotIron"));
		
		
		
		
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsReference.rings, 1, 0), "NTN","I I","NIN", 'I', "ingotGold", 'N', "nuggetGold", 'T', "gemEmerald"));
//		GameRegistry.addShapedRecipe(new ItemStack(ItemsReference.rings, 1, 0), "NTN","I I","NIN", 'I', new ItemStack(Items.gold_ingot), 'N', new ItemStack(Items.gold_nugget), 'T', new ItemStack(Items.emerald));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsReference.rings, 1, 1), "NTN","I I","NIN", 'I', "ingotGold", 'N', "nuggetGold", 'T', "gemDiamond"));
//		GameRegistry.addShapedRecipe(new ItemStack(ItemsReference.rings, 1, 1), "NTN","I I","NIN", 'I', new ItemStack(Items.gold_ingot), 'N', new ItemStack(Items.gold_nugget), 'T', new ItemStack(Items.diamond));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsReference.rings, 1, 2), "NIN","I I","NIN", 'I', "ingotGold", 'N', "nuggetGold"));
//		GameRegistry.addShapedRecipe(new ItemStack(ItemsReference.rings, 1, 1), "NIN","I I","NIN", 'I', new ItemStack(Items.gold_ingot), 'N', new ItemStack(Items.gold_nugget));

		if(!OreDictionary.doesOreNameExist("nuggetIron"))
			GameRegistry.addShapedRecipe(new ItemStack(ItemsReference.rings, 1, 3), " I ","ISI"," I ", 'I', new ItemStack(Items.iron_ingot), 'S', new ItemStack(Items.stick));
		else
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsReference.rings, 1, 3), "NIN","I I","NIN", 'I', "ingotIron", 'N', "nuggetIron"));
		
//		GameRegistry.addShapedRecipe(new ItemStack(ItemsReference.rings, 1, 4), "CSC", "S S", "CSC", 'C', new ItemStack(Blocks.cobblestone), 'S', new ItemStack(Blocks.stone));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsReference.rings, 1, 4), "CSC", "S S", "CSC", 'C', "cobblestone", 'S', "stone"));
	}
	
	
	//==================================================================================================================================================================
	//NOTES:
	//		COMMON ORE DICTIONARY NAMES>
	//									http://www.minecraftforge.net/wiki/Common_Oredict_names
	//==================================================================================================================================================================
}
