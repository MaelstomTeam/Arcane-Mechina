package com.maelstrom.snowcone.container.slot;

import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class FilteredSlot extends Slot
{

	private List<Class<? extends Item>> filter_list;

	public FilteredSlot(IInventory inventoryIn, int index, int xPosition, int yPosition, List<Class<? extends Item>> filter_list)
	{
		super(inventoryIn, index, xPosition, yPosition);
		this.filter_list = filter_list;
	}

	public boolean isItemValid(ItemStack stack)
	{
		if (stack != null && (stack.isEmpty() || checkWithFilters(stack.getItem())))
			return true;
		return false;
	}
	
	private boolean checkWithFilters(Item item)
	{
		for(Class<? extends Item> i : filter())
		{
			if(i.isAssignableFrom(item.getClass()))
				return true;
		}
		return false;
	}
	
	public List<Class<? extends Item>> filter()
	{
		return filter_list;
	}

	public void set_filter_list(List<Class<? extends Item>> filter_list)
	{
		this.filter_list = filter_list;
	}
}