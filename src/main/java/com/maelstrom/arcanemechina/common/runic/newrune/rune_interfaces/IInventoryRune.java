package com.maelstrom.arcanemechina.common.runic.newrune.rune_interfaces;

import com.maelstrom.arcanemechina.ArcaneMechina;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;

public interface IInventoryRune extends IInventory
{
	public NonNullList<ItemStack> getAllItems();

	@Override
	public default void clear()
	{
		getAllItems().clear();
	}

	@Override
	public default boolean isEmpty()
	{
		for (ItemStack itemstack : getAllItems())
		{
			if (!itemstack.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public default ItemStack getStackInSlot(int index)
	{
		return getAllItems().get(index);
	}

	@Override
	public default ItemStack decrStackSize(int index, int count)
	{
		ItemStack itemstack = ItemStackHelper.getAndSplit(this.getAllItems(), index, count);
		if (!itemstack.isEmpty())
		{
			this.markDirty();
		}

		return itemstack;
	}

	@Override
	public default ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.getAllItems(), index);
	}

	@Override
	public default void setInventorySlotContents(int index, ItemStack stack)
	{
		this.getAllItems().set(index, stack);
		if (stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}

		this.markDirty();
	}

	// ignore these

	public abstract boolean isDirty();

	@Override
	public default boolean isUsableByPlayer(PlayerEntity player)
	{
		return false;
	}

	public default void addItem(ItemStack item)
	{
		int value = 0;
		for (ItemStack i2 : getAllItems().subList(10, 19))
		{
			if (canAddItem(item, i2))
			{
				if (i2.getCount() > 0)
					i2.grow(item.getCount());
				else
					this.setInventorySlotContents(value, item.copy());
				break;
			}
			value++;
		}
	}

	public default void addItem(ItemStack item, int index)
	{
		if (this.getStackInSlot(index).isEmpty())
			this.setInventorySlotContents(index, item.copy());
		else if (getStackInSlot(index).getCount() > 0)
			this.getStackInSlot(index).grow(item.getCount());
	}

	public default boolean canAddItem(ItemStack item, ItemStack item2)
	{

		if (item.isStackable() && item2.isStackable())
			if (ItemStack.areItemStackTagsEqual(item, item2))
				return true;
		if (item.isEmpty() || item2.isEmpty())
			return true;
		return false;
	}

	public default boolean canAddItem(ItemStack item)
	{
		for (ItemStack i : this.getAllItems())
		{
			if (canAddItem(item, i))
				return true;
		}
		return false;
	}

}
