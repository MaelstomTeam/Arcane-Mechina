package com.maelstrom.armech.common;

import java.util.Iterator;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Tab_ArMech extends CreativeTabs {

	public Tab_ArMech(String label) {
		super(label);
	}

	ItemStack override_item;
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		if(override_item == null)
			return AMItems.help_book;
		return override_item.getItem();
	}
	@Override
	public ItemStack getIconItemStack()
	{
		if(override_item != null)
			return override_item;
		return super.getIconItemStack();
	}
	
	public Tab_ArMech setTabIcon(ItemStack item)
	{
		override_item = item;
		return this;
	}
	
	//FIXME: think about removal, it's just a visual thing and might mess up things
	@SideOnly(Side.CLIENT)
	public void displayAllReleventItems(List items) {
		super.displayAllReleventItems(items);
	}
}