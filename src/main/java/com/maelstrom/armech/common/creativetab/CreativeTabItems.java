package com.maelstrom.armech.common.creativetab;

import com.maelstrom.armech.common.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabItems extends CreativeTabs {

	public CreativeTabItems() {
		super("armech.items");
	}

	@Override
	public Item getTabIconItem() {
		return ModItems.armechBook;
	}

}
