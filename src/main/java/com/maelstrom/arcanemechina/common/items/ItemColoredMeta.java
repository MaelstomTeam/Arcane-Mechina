package com.maelstrom.arcanemechina.common.items;

import com.maelstrom.arcanemechina.client.BasicDustColorHandler;
import com.maelstrom.snowcone.block.IItemColored;
import com.maelstrom.snowcone.client.BasicColorHandler;
import com.maelstrom.snowcone.item.MetaItem.MetaItemWithNames;

public class ItemColoredMeta extends MetaItemWithNames implements IItemColored
{
	public ItemColoredMeta(String[] list) {
		super(list);
	}
	
	public BasicColorHandler getColorHandler()
	{
		return new BasicDustColorHandler();
	}
	

}
