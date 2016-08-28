package com.maelstrom.armech.common.creativetab;

import com.maelstrom.armech.common.reference.ModBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTabBlocks extends CreativeTabs {

	public CreativeTabBlocks() {
		super("armech.blocks");
	}

	@Override
	public Item getTabIconItem() {
		return Item.getItemFromBlock(ModBlocks.dustOre);
	}

}
