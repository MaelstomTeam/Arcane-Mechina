package com.maelstrom.arcanemechina.common.creative;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import com.maelstrom.arcanemechina.common.ItemsReference;
import com.maelstrom.arcanemechina.common.Reference;
import com.maelstrom.snowcone.extendables.ExtendableCreativeTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTab extends ExtendableCreativeTab
{
	public CreativeTab()
	{
		super(Reference.MOD_ID, new ItemStack(ItemsReference.AMBook));
	}
}
