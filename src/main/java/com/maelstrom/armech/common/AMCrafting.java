package com.maelstrom.armech.common;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.maelstrom.armech.common.blocks.BlockDustOre;

public class AMCrafting {
	
	private static void registerOreDictonary()
	{
		for(int i = 0; i < Reference.dustNames.length; i++)
		{
			if(i!=19)
			{
				OreDictionary.registerOre("AM_dust_crystal", new ItemStack(AMItems.dust_crystal,1,i));
				OreDictionary.registerOre("AM_dust_disolved", new ItemStack(AMItems.glass_jar_dust,1,i));
				OreDictionary.registerOre("AM_dust", new ItemStack(AMItems.dust_dust,1,i));
			}
		}
		for(int i = 0; i < BlockDustOre.EnumType.length(); i++)
			OreDictionary.registerOre("AM_dust_ore", new ItemStack(AMBlocks.dust_ore,1,i));
	}

	public static void registerRecipes()
	{
		registerOreDictonary();
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AMItems.glass_jar,1), true, new Object[]{"gwg","g g","ggg", 'g', "blockGlass",'w', "slabWood"}));
		
		GameRegistry.addShapedRecipe(new ItemStack(AMItems.gold_rod, 2), "  g"," g ","g  ", 'g', Items.gold_ingot);
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AMItems.morter_and_pestal), true, "wsw"," w ", 'w', "cobblestone", 's', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AMItems.morter_and_pestal), true, "wsw"," w ", 'w', "stone", 's', "stickWood"));
		GameRegistry.addShapedRecipe(new ItemStack(AMItems.morter_and_pestal), "wsw", " w ", 'w', Blocks.stone, 's', Items.stick);
		
		for(int i = 0; i < Reference.dustNames.length; i++)
			if(i != 19)
				GameRegistry.addShapelessRecipe(new ItemStack(AMItems.glass_jar_dust,1,i), AMItems.glass_jar, Items.water_bucket, new ItemStack(AMItems.dust_dust,1,i));
		
		for(int i = 0; i < Reference.dustNames.length; i++)
			if(i != 19)
				GameRegistry.addShapelessRecipe(new ItemStack(AMItems.dust_dust,1,i), AMItems.morter_and_pestal, new ItemStack(AMItems.dust_crystal,1,i));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(AMItems.help_book), "AM_dust_crystal", Items.book, Items.feather, "dyeBlack"));
//		GameRegistry.addShapedRecipe(new ItemStack(AMBlocks.test_block), "g g", "ihi", "ici", 'g', AMItems.gold_rod, 'h', Blocks.iron_trapdoor, 'i', Items.iron_ingot, 'c', Items.cauldron);
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(AMBlocks.test_block), true, new Object[]{"g g", "ihi", "ici", 'g', AMItems.gold_rod, 'i', "ingotIron", 'h', Blocks.iron_trapdoor, 'c', Items.cauldron}));
	}
}