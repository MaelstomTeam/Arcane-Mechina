package com.maelstrom.arcanemechina.common.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import baubles.api.BaubleType;

import com.maelstrom.arcanemechina.common.ItemsReference;
import com.maelstrom.arcanemechina.common.Reference;

public class ItemSoulBound extends ItemBaubleEx {

	public ItemSoulBound()
	{
		super(ItemsReference.boundRingName, Reference.MOD_ID);
		this.setMaxStackSize(1);
	}
	private ItemSoulBound(String itemName, String Modid)
	{
		super(itemName, Modid);
	}
	private ItemSoulBound(String name)
	{
		this(name, Reference.MOD_ID);
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return BaubleType.RING;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player)
	{
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
	{
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
	{
		return false;
	}

}
