package com.maelstrom.snowcone.client;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;

public class BasicColorHandler implements IItemColor {

	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex) {
		return ItemDye.DYE_COLORS[stack.getItemDamage()];
	}

	public int colorMultiplier(int meta, int tintIndex) {
		return ItemDye.DYE_COLORS[meta];
	}

}