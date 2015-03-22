package com.maelstrom.arcaneMechina;

import com.maelstrom.arcaneMechina.common.items.ModItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModRecipes {

	public static void init() {
    	GameRegistry.addRecipe(new ItemStack(ModItems.ghostWingAmulet, 1), new Object[]{
    	" G ",
    	"FNG",
    	"GF ",
    	'F', Items.feather, 'G', Items.gold_ingot, 'N', Items.nether_star
    	});
	}

}
