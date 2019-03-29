package com.maelstrom.snowcone.client;

import com.maelstrom.snowcone.item.sonic.SonicInventory;

import net.minecraft.item.ItemStack;

public class SonicColorHandler extends BasicColorHandler {

	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex) {
		SonicInventory s;
		try {
			s = SonicInventory.getInventory(stack,null);
	    	if(tintIndex > s.getColors().length)
	    		return 0xFFFFFF;
			return s.getColors()[tintIndex];
		}catch (Exception e){
			return 0xFFFFFF;
		}
	}

	public int colorMultiplier(int meta, int tintIndex) {
		return 0;
	}
}
