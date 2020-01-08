package com.maelstrom.arcanemechina.common.container.inventory;

import com.maelstrom.arcanemechina.common.items.BlueprintItem;
import com.maelstrom.snowcone.inventory.QuickInventory;

import net.minecraft.item.ItemStack;

public class BlueprintInventory extends QuickInventory
{
	public BlueprintInventory()
	{
		super(1);
	}

	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if(stack.getItem() instanceof BlueprintItem)
			return true;
		return false;
	}

}
