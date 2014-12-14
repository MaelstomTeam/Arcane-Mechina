package com.maelstrom.arcaneMechina.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import com.maelstrom.arcaneMechina.item.ItemDebug;
import com.maelstrom.arcaneMechina.item.ItemPegasusWingAmulet;
import com.maelstrom.arcaneMechina.item.ItemRosarioAmulet;
import com.maelstrom.arcaneMechina.item.ItemScrewdriver;
import com.maelstrom.arcaneMechina.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

public class InitItem {

	public static Item wandOfDebug = new ItemDebug("WandODebug");
	public static Item pegasusWingAmulet = new ItemPegasusWingAmulet("pegasusWingAmulet");
	public static Item ScrewDriver = new ItemScrewdriver("ScrewDriver");
	public static Item rosarioAmulet = new ItemRosarioAmulet("rosarioAmulet");
	
	public static void init(){
		registerItem(ScrewDriver);
		registerItem(wandOfDebug);
		registerItem(pegasusWingAmulet);
		registerItem(rosarioAmulet);
	}
	
	private static void registerItem(Item item){
		GameRegistry.registerItem(item, item.getUnlocalizedName(), Reference.MOD_ID);
		item.setCreativeTab(Reference.MOD_TAB);
	}
}
