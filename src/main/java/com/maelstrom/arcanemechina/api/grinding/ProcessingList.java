package com.maelstrom.arcanemechina.api.grinding;

import java.util.ArrayList;
import java.util.List;

import com.maelstrom.arcanemechina.api.ElementTypes;
import com.maelstrom.arcanemechina.common.block.BlockList;
import com.maelstrom.arcanemechina.common.items.ItemList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ProcessingList {
	public static List<ProcessingRecipe> list = create();

	private static List<ProcessingRecipe> create()
	{
		List<ProcessingRecipe> temp = new ArrayList<ProcessingRecipe>();
		
		for(int meta = 0; meta < ElementTypes.values().length; meta++)
		{
			temp.add(new ProcessingRecipe(new ItemStack(ItemList.Crystal, 1, meta), new ItemStack(ItemList.Dust, 1, meta), -1, 1));
			temp.add(new ProcessingRecipe(new ItemStack(BlockList.CrystalOre, 1, meta), new ItemStack(ItemList.Dust, 1, meta), .25f, 1));
		}
		temp.add(new ProcessingRecipe(new ItemStack(ItemList.Ingots, 1, 0), new ItemStack(ItemList.MetalDust, 1, 2), -1, 0));
		temp.add(new ProcessingRecipe(new ItemStack(BlockList.Ore, 1, 0), 	new ItemStack(ItemList.MetalDust, 1, 2), .5f, 1));
		temp.add(new ProcessingRecipe(new ItemStack(ItemList.Ingots, 1, 1), new ItemStack(ItemList.MetalDust, 1, 3), -1, 0));
		temp.add(new ProcessingRecipe(new ItemStack(BlockList.Ore, 1, 1), 	new ItemStack(ItemList.MetalDust, 1, 3), .5f, 1));
		temp.add(new ProcessingRecipe(new ItemStack(ItemList.Ingots, 1, 2), new ItemStack(ItemList.MetalDust, 1, 4), -1, 0));
		temp.add(new ProcessingRecipe(new ItemStack(BlockList.Ore, 1, 2), 	new ItemStack(ItemList.MetalDust, 1, 4), .5f, 1));

		temp.add(new ProcessingRecipe(new ItemStack(ItemList.Gems, 1, 6), 	new ItemStack(ItemList.MetalDust, 1, 5), -1, 0));
		temp.add(new ProcessingRecipe(new ItemStack(Items.QUARTZ, 1), 		new ItemStack(ItemList.MetalDust, 1, 5), -1, 0));
		temp.add(new ProcessingRecipe(new ItemStack(BlockList.Ore, 1, 9), 	new ItemStack(ItemList.MetalDust, 1, 5), .25f, 1));
		temp.add(new ProcessingRecipe(new ItemStack(Blocks.QUARTZ_ORE, 1), 	new ItemStack(ItemList.MetalDust, 1, 5), .25f, 1));

		temp.add(new ProcessingRecipe(new ItemStack(Items.IRON_INGOT, 1),	new ItemStack(ItemList.MetalDust, 1, 0), -1, 0));
		temp.add(new ProcessingRecipe(new ItemStack(Items.GOLD_INGOT, 1), 	new ItemStack(ItemList.MetalDust, 1, 1), -1, 0));
		temp.add(new ProcessingRecipe(new ItemStack(Blocks.IRON_ORE, 1),	new ItemStack(ItemList.MetalDust, 1, 0), .5f, 1));
		temp.add(new ProcessingRecipe(new ItemStack(Blocks.GOLD_ORE, 1), 	new ItemStack(ItemList.MetalDust, 1, 1), .5f, 1));
		
		return temp;
	}
	public static boolean containsInput(ItemStack input)
	{
		if(input == null || input.isEmpty())
			return false;
		for (ProcessingRecipe processingRecipe : list) {
			if(ItemStack.areItemsEqual(processingRecipe.input, input))
				return true;
		}
		return false;
	}
	
	public static ProcessingRecipe process(ItemStack input)
	{
		for (ProcessingRecipe processingRecipe : list) {
			if(ItemStack.areItemsEqual(processingRecipe.input, input))
				return processingRecipe;
		}
		return null;
	}
}
