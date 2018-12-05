package com.maelstrom.arcanemechina.client;

import com.maelstrom.arcanemechina.api.ElementTypes;
import com.maelstrom.snowcone.client.BasicColorHandler;

import net.minecraft.item.ItemStack;
import net.minecraft.world.BossInfo.Color;

public class BasicDustColorHandler extends BasicColorHandler {
	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex) {
		return colorMultiplier(stack.getMetadata(), tintIndex);
	}
	@Override
	public int colorMultiplier(int meta, int tintIndex) {
		if(tintIndex == 0)
			return ElementTypes.values()[meta > ElementTypes.values().length || meta < 0 ? 0 : meta].color.hashCode();
		else
			return Color.WHITE.hashCode();
	}
}
