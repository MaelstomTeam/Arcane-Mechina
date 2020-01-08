package com.maelstrom.snowcone.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.INBTSerializable;

public class QuickInventory implements IInventory, INBTSerializable<CompoundNBT>
{
	private final NonNullList<ItemStack> stackList;
	public QuickInventory(int slotcount)
	{
		this.stackList = NonNullList.withSize(slotcount, ItemStack.EMPTY);
	}
	public QuickInventory()
	{
		this(9);
	}

	@Override
	public void clear()
	{
	      this.stackList.clear();
	}

	@Override
	public int getSizeInventory()
	{
		return stackList.size();
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack itemstack : this.stackList)
		{
			if (!itemstack.isEmpty())
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return index >= this.getSizeInventory() ? ItemStack.EMPTY : this.stackList.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack itemstack = ItemStackHelper.getAndSplit(this.stackList, index, count);
		return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.stackList, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.stackList.set(index, stack);
	}

	@Override
	public void markDirty()
	{
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player)
	{
		return true;
	}
	
	@Override
	public CompoundNBT serializeNBT()
	{
		CompoundNBT nbt = new CompoundNBT();
		ItemStackHelper.saveAllItems(nbt, stackList);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt)
	{
		if(nbt != null && !nbt.isEmpty())
			ItemStackHelper.loadAllItems(nbt, stackList);
	}

}
