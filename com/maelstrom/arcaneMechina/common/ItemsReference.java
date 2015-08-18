package com.maelstrom.arcanemechina.common;

import net.minecraft.item.Item;

import com.maelstrom.arcanemechina.common.items.ItemChalk;
import com.maelstrom.arcanemechina.common.items.ItemCraftingParts;
import com.maelstrom.arcanemechina.common.items.ItemGhostWingAmulet;
import com.maelstrom.arcanemechina.common.items.ItemMechanizedArcaneBook;
import com.maelstrom.arcanemechina.common.items.ItemRings;
import com.maelstrom.arcanemechina.common.items.ItemTieredCompass;

public class ItemsReference
{
	public static final Item chalk = new ItemChalk();
	public static final String chalkName = "chalk";
	
	public static final Item AMBook = new ItemMechanizedArcaneBook();
	public static final String AMBookName = "ambook";
	
	public static final Item amuletWing = new ItemGhostWingAmulet();
	public static final String amuletWingName = "pegasusWingAmulet";
	
	public static final Item rings = new ItemRings();
	public static final String ringName = "ringof";
	
	public static final Item tieredCompass = new ItemTieredCompass();
	public static final String tieiredCompassName = "tCompass";
	
	public static final String[] craftingItemNames = 
		{
			"GoldChainLink",
			"IronChainLink"
		};
	public static final Item craftingParts = new ItemCraftingParts(craftingItemNames);
	public static final String craftingPartsBaseName = "craftingPart";
}