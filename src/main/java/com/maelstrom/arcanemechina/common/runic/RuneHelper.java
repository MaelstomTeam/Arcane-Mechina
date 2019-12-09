package com.maelstrom.arcanemechina.common.runic;

import com.maelstrom.arcanemechina.common.RecipeHelper;
import com.maelstrom.arcanemechina.common.Registry;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;

public class RuneHelper
{
	public static RuneContainer getNewMiningRune(){
		RuneContainer container = new RuneContainer();
		//declare
		RuneType.HoldingRune hold         = new RuneType.HoldingRune();
		RuneType.ToggleRune  toggle       = new RuneType.ToggleRune();
		RuneType.VaribleRune off          = new RuneType.VaribleRune();
		RuneType.VaribleRune on           = new RuneType.VaribleRune();
		RuneType.IORune      output_items = new RuneType.IORune();
		RuneType.IORune      input_items  = new RuneType.IORune();
		
		container.setSize(RuneSize.SMALL);
		
		hold.setPosition(8,13);
		toggle.setPosition(8, 8);
		on.setPosition(4, 5);
		off.setPosition(12, 5);
		
		//add to container
		container.addChild(hold);
		container.addChild(toggle);
		container.addChild(on);
		container.addChild(off);
		container.addChild(output_items);
		container.addChild(input_items);
		
		//modify values
		//hold.setInventorySlotContents(0, new ItemStack(Items.DIAMOND_PICKAXE));
		on.setValue((short) 200);
		off.setValue((short) 0);
		
		output_items.setDirection(Direction.NORTH);
		output_items.setInput(false);
		input_items .setDirection(Direction.SOUTH);
		
		//link to other runes
		hold.addLink(toggle);
		toggle.addLink(on);
		toggle.addLink(off);
		
		output_items.addLink(hold);
		input_items.addLink(hold);
		return container;
	}
	
	public static RuneContainer getNewCraftingRune(){
		RuneContainer container = new RuneContainer();
		//declare
		RuneType.HoldingRune hold = new RuneType.HoldingRune();
		RuneType.ToggleRune 	toggle = new RuneType.ToggleRune();
		RuneType.VaribleRune off = new RuneType.VaribleRune();
		RuneType.VaribleRune on = new RuneType.VaribleRune();
		RuneType.IORune output_items = new RuneType.IORune();
		RuneType.IORune input_items = new RuneType.IORune();
		RuneType.CraftingContainerRune recipe_rune = new RuneType.CraftingContainerRune();

		container.setSize(RuneSize.MEDIUM);
		//add to container
		container.addChild(hold);
		container.addChild(toggle);
		container.addChild(off);
		container.addChild(on);
		container.addChild(output_items);
		container.addChild(input_items);
		container.addChild(recipe_rune);
		//modify values
		ItemStack stone_reference = new ItemStack(Items.STICK);
		hold.setInventorySlotContents(0, new ItemStack(Items.CRAFTING_TABLE));
		ItemStack item = RecipeHelper.createFromListToItemStack(
				new ItemStack[][]{
					new ItemStack[] {stone_reference},
					new ItemStack[] {stone_reference}
				});
		recipe_rune.setInventorySlotContents(0, item);
		
		recipe_rune.setPosition(16, 8);
		hold.setPosition(9, 8);
		toggle.setPosition(4, 8);
		on.setPosition(1, 4);
		off.setPosition(1, 13);
		
		
		off.setValue((short) 19);
		on.setValue((short) 1);
		output_items.setDirection(Direction.EAST);
		input_items .setDirection(Direction.WEST);
		
		//link to other runes
		hold.addLink(toggle);
		hold.addLink(recipe_rune);
		toggle.addLink(off);
		toggle.addLink(on);
		output_items.addLink(hold);
		input_items.addLink(hold);
		return container;
	}
	

	public static RuneContainer fromItem(ItemStack drawn_rune)
	{
		RuneContainer rune = new RuneContainer();
		rune.readNBT(drawn_rune.getTag().getCompound("rune_data"));
		return rune;
	}
	
	public static ItemStack toItem(RuneContainer rune)
	{
		ItemStack item = new ItemStack(Registry.blueprint_rune);
		item.setDamage(1);
		CompoundNBT tag = rune.writeNBT();
		
		//replace current data if exists
		if(item.getOrCreateTag().contains("rune_data"))
			item.removeChildTag("rune_data");
		
		item.setTagInfo("rune_data",tag);
		
		return item;
	}

	public static boolean hasRune(ItemStack drawn_rune)
	{
		if(drawn_rune.getItem() == Registry.blueprint_rune)
			return drawn_rune.getTag().contains("rune_data");
		return false;
	}

	public static RuneContainer getEmpty()
	{
		RuneContainer rune = new RuneContainer();
		rune.setSize(RuneSize.TINY);
		return rune;
	}

}
