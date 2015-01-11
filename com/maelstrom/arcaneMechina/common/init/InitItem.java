package com.maelstrom.arcaneMechina.common.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import com.maelstrom.arcaneMechina.common.item.ItemChalk;
import com.maelstrom.arcaneMechina.common.item.ItemDebug;
import com.maelstrom.arcaneMechina.common.item.ItemMetadataBased;
import com.maelstrom.arcaneMechina.common.item.ItemPegasusWingAmulet;
import com.maelstrom.arcaneMechina.common.item.ItemRosarioAmulet;
import com.maelstrom.arcaneMechina.common.item.ItemScrewdriver;
import com.maelstrom.arcaneMechina.common.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

public class InitItem {

	public static Item wandOfDebug = new ItemDebug("WandODebug");
	public static Item pegasusWingAmulet = new ItemPegasusWingAmulet("pegasusWingAmulet");
	public static Item ScrewDriver = new ItemScrewdriver("ScrewDriver");
	public static Item rosarioAmulet = new ItemRosarioAmulet("rosarioAmulet");
	public static Item ingot = new ItemMetadataBased("ingot", Reference.preciousMetals);
	public static Item gem = new ItemMetadataBased("gem", Reference.gemStones);
	public static Item chalkfire = new ItemChalk("chalkfire");
	public static Item chalkice = new ItemChalk("chalkice");
	public static Item chalkenergy = new ItemChalk("chalkenergy");
	public static Item chalkearth = new ItemChalk("chalkearth");
	public static Item chalkair = new ItemChalk("chalkair");
	public static Item chalk = new ItemChalk("chalk");
	
	public static void init(){
		registerItem(ScrewDriver);
		GameRegistry.registerItem(wandOfDebug, wandOfDebug.getUnlocalizedName(), Reference.MOD_ID);
		registerItem(pegasusWingAmulet);
		registerItem(rosarioAmulet);
		registerItem(ingot);
		registerItem(gem);
		
		registerItem(chalk);
		
		registerItem(chalkfire);
		registerItem(chalkice);
		registerItem(chalkearth);
		registerItem(chalkair);
		registerItem(chalkenergy);
	}
	
	private static void registerItem(Item item){
		GameRegistry.registerItem(item, item.getUnlocalizedName(), Reference.MOD_ID);
		item.setCreativeTab(Reference.MOD_TAB);
	}
}
