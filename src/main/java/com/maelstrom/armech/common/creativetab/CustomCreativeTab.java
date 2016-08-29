package com.maelstrom.armech.common.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class CustomCreativeTab extends CreativeTabs {

	Item itemTabItem;
	public CustomCreativeTab(Item item, String extra) {
		super("armech." + extra);
		itemTabItem = item;
	}

	@Override
	public Item getTabIconItem() {
		if(itemTabItem == null)
			return Item.getItemFromBlock(Blocks.BARRIER);
		return itemTabItem;
	}
	
	public void setCreativeTabIcon(Item item)
	{
		itemTabItem = item;
	}

}
