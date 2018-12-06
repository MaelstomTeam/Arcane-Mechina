package com.maelstrom.arcanemechina.common.items;

import com.maelstrom.arcanemechina.api.ElementTypes;
import com.maelstrom.snowcone.item.MetaItem;
import com.maelstrom.snowcone.item.MetaItem.MetaItemWithNames;

import net.minecraft.item.Item;

public class ItemList
{
	public static ItemColoredMeta Crystal = new ItemColoredMeta(ElementTypes.AllNames());
	public static ItemColoredMeta Dust = new ItemColoredMeta(ElementTypes.AllNames());
	/*
	 * should be a list of Ingots
	 * Copper
	 * Lead
	 * Silver
	 */
	public static MetaItem Ingots = new MetaItemWithNames(new String[] {"Copper","Lead","Silver"});

	/*
	 * should be a list of Gems
	 * Sapphire
	 * Ruby
	 * Amethyst
	 * White Diamond
	 * Pink Diamond
	 * Yellow Diamond
	 * Overworld Quartz
	 * 
	 */
	public static MetaItem Gems = new MetaItemWithNames(new String[] {"Sapphire","Ruby", "Amethyst","WDiamond", "PDiamond", "YDiamond","Quartz"});
	
	/*
	 * should be a list of drops from entities and ect
	 * Pearl
	 */
	public static MetaItem Drops = new MetaItemWithNames(new String[] {"Pearl","Bark"});
	
	
	public static Item Chalk = new ItemChalk();
}
