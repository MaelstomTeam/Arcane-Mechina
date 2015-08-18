package com.maelstrom.arcanemechina.common.registery;

import net.minecraft.item.Item;

import com.maelstrom.arcanemechina.common.ItemsReference;
import com.maelstrom.arcanemechina.common.Reference;

public class ItemRegistry extends com.maelstrom.snowcone.registers.ItemRegistry {
	
	public static void registerALL()
	{
		registerItem(ItemsReference.chalk, ItemsReference.chalkName);
		registerItem(ItemsReference.AMBook, ItemsReference.AMBookName);
		registerItem(ItemsReference.amuletWing, ItemsReference.amuletWingName);
		registerItem(ItemsReference.rings, ItemsReference.ringName);
		registerItem(ItemsReference.tieredCompass, ItemsReference.tieiredCompassName);
		registerItem(ItemsReference.craftingParts, ItemsReference.craftingPartsBaseName);
	}
	
	private static void registerItem(Item item, String itemName)
	{
		registItem(item, itemName, Reference.MOD_ID, Reference.tab);
	}
}
