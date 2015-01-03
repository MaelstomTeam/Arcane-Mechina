package com.maelstrom.arcaneMechina.common.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class InitRecipe {
	
	public static void init(){
    	GameRegistry.addRecipe(new ItemStack(InitItem.pegasusWingAmulet, 1), new Object[]{
    	" G ",
    	"GNG",
    	"FGF",
    	'F', Items.feather,
    	'G', Items.gold_ingot,
    	'N', Items.nether_star
    	});
    	GameRegistry.addRecipe(new ItemStack(InitItem.pegasusWingAmulet, 1), new Object[]{
    	" G ",
    	"FNG",
    	"GF ",
    	'F', Items.feather,
    	'G', Items.gold_ingot,
    	'N', Items.nether_star
    	});
	}
	
	
}
