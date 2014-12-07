package com.maelstrom.arcaneMechina.init;

import net.minecraft.item.Item;

import com.maelstrom.arcaneMechina.Item.WandOfDebug;
import com.maelstrom.arcaneMechina.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

public class InitItem {
	
	public static Item wandOfDebug = new WandOfDebug("WandODebug");	
	
	public static void init(){
		GameRegistry.registerItem(wandOfDebug, wandOfDebug.getUnlocalizedName(), Reference.MOD_ID);
		
		afterRegistry();
	}
	
	private static void afterRegistry(){
		wandOfDebug.setCreativeTab(Reference.MOD_TAB);
	}
}
