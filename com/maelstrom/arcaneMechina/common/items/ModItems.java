package com.maelstrom.arcaneMechina.common.items;

import com.maelstrom.arcaneMechina.common.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ModItems {

	public static Item ScrewDriver = new ItemScrewdriver("ScrewDriver");
	public static Item glyphicChalk = new Chalk("chalk");
	public static Item ghostWingAmulet = new ItemGhostWingAmmulet("pegasusWingAmulet");
	
	
	public static void init(){
		registerItem(ScrewDriver);
		registerItem(glyphicChalk);
		registerItem(ghostWingAmulet);
	}
	
	private static void registerItem(Item item){
		GameRegistry.registerItem(item, item.getUnlocalizedName(), Reference.MOD_ID);
		item.setCreativeTab(Reference.MOD_TAB);
	}

}
