package com.maelstrom.arcanemechina.common.items;

import com.maelstrom.arcanemechina.api.ElementTypes;
import com.maelstrom.snowcone.item.MetaItem;
import com.maelstrom.snowcone.item.MetaItem.MetaItemWithNames;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ItemColoredMeta extends MetaItemWithNames implements IItemColor
{
	public ItemColoredMeta(String[] list) {
		super(list);
	}


	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex) {
		
		return ElementTypes.values()[stack.getItemDamage()].clr.hashCode();
	}

}
