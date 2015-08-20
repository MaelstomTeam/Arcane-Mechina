package com.maelstrom.arcanemechina.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.maelstrom.arcanemechina.common.ItemsReference;
import com.maelstrom.arcanemechina.common.Reference;
import com.maelstrom.snowcone.extendables.ExtendableItem;

public class ItemInkWell extends ExtendableItem {

	public ItemInkWell() {
		super(ItemsReference.inkWellName, Reference.MOD_ID);
		setMaxStackSize(1);
		setMaxDamage(100);
	}
}
