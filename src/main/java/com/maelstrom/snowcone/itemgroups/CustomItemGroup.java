package com.maelstrom.snowcone.itemgroups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class CustomItemGroup extends ItemGroup {

	ItemStack icon = new ItemStack(Items.BARRIER);

	public CustomItemGroup(String label) {
		super(label);
	}

	public void setIcon(ItemStack item) {
		icon = item;
	}

	@Override
	public ItemStack createIcon() {
		return icon;
	}

}
