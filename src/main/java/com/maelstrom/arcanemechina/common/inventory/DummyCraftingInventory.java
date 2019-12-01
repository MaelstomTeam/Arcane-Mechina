package com.maelstrom.arcanemechina.common.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.NonNullList;

public class DummyCraftingInventory extends CraftingInventory
{

	public static CraftingInventory instance = new DummyCraftingInventory();
	private final NonNullList<ItemStack> stackList;
	private final int width;
	private final int height;

	private DummyCraftingInventory()
	{
		super(DummyContainer.instance, 3, 3);
		this.stackList = NonNullList.withSize(3 * 3, ItemStack.EMPTY);
		this.width = 3;
		this.height = 3;
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	public int getSizeInventory()
	{
		return this.stackList.size();
	}

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

	/**
	 * Returns the stack in the given slot.
	 */
	public ItemStack getStackInSlot(int index)
	{
		return index >= this.getSizeInventory() ? ItemStack.EMPTY : this.stackList.get(index);
	}

	/**
	 * Removes a stack from the given slot and returns it.
	 */
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.stackList, index);
	}

	/**
	 * Removes up to a specified number of items from an inventory slot and returns
	 * them in a new stack.
	 */
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack itemstack = ItemStackHelper.getAndSplit(this.stackList, index, count);
		if (!itemstack.isEmpty())
		{
		}

		return itemstack;
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be
	 * crafting or armor sections).
	 */
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.stackList.set(index, stack);
	}

	/**
	 * For tile entities, ensures the chunk containing the tile entity is saved to
	 * disk later - the game won't think it hasn't changed and skip it.
	 */
	public void markDirty()
	{
	}

	/**
	 * Don't rename this method to canInteractWith due to conflicts with Container
	 */
	public boolean isUsableByPlayer(PlayerEntity player)
	{
		return true;
	}

	public void clear()
	{
		this.stackList.clear();
	}

	public int getHeight()
	{
		return this.height;
	}

	public int getWidth()
	{
		return this.width;
	}

	public void fillStackedContents(RecipeItemHelper helper)
	{
		for (ItemStack itemstack : this.stackList)
		{
			helper.accountPlainStack(itemstack);
		}

	}

	private static class DummyContainer extends Container
	{

		public static DummyContainer instance = new DummyContainer();

		private DummyContainer()
		{
			super(ContainerType.CRAFTING, -332);
		}

		@Override
		public boolean canInteractWith(PlayerEntity playerIn)
		{
			return false;
		}

	}
}
