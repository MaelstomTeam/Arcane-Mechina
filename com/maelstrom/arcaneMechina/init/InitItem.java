package com.maelstrom.arcaneMechina.init;

import net.minecraft.item.Item;

import com.maelstrom.arcaneMechina.Item.ItemPegasusWingAmulet;
import com.maelstrom.arcaneMechina.Item.ItemScrewdriver;
import com.maelstrom.arcaneMechina.Item.ItemDebug;
import com.maelstrom.arcaneMechina.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

public class InitItem {

	public static Item wandOfDebug = new ItemDebug("WandODebug");
	public static Item pegasusWingAmulet = new ItemPegasusWingAmulet("pegasusWingAmulet");
	public static Item ScrewDriver = new ItemScrewdriver("ScrewDriver");
	
	public static void init(){
		registerItem(ScrewDriver);
		registerItem(wandOfDebug);
		registerItem(pegasusWingAmulet);
	}
	
	private static void registerItem(Item item){
		GameRegistry.registerItem(item, item.getUnlocalizedName(), Reference.MOD_ID);
		item.setCreativeTab(Reference.MOD_TAB);
	}
}
