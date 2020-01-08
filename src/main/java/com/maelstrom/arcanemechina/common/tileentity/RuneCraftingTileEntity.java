package com.maelstrom.arcanemechina.common.tileentity;

import com.maelstrom.arcanemechina.client.gui.RuneWorldCraftingGui;
import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.container.RuneCraftingContainer;
import com.maelstrom.arcanemechina.common.container.inventory.BlueprintInventory;
import com.maelstrom.snowcone.inventory.QuickInventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.extensions.IForgeTileEntity;

public class RuneCraftingTileEntity extends TileEntity implements IInventory, ITickableTileEntity, IForgeTileEntity, INamedContainerProvider
{
	private BlueprintInventory blueprint_inventory;
	private QuickInventory inventory;
	
	public RuneCraftingTileEntity(TileEntityType<?> tileEntityTypeIn)
	{
		super(tileEntityTypeIn);
		blueprint_inventory = new BlueprintInventory();
		inventory = new QuickInventory();
	}

	public RuneCraftingTileEntity() {
		this(Registry.RUNE_CRAFT_TILE);
	}

	@Override
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
	{
		return new RuneCraftingContainer(id,world,pos,inv,player);
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return RuneWorldCraftingGui.title;
	}

	@Override
	public void tick()
	{
		
	}

	@Override
	public void clear()
	{
		inventory.clear();
		blueprint_inventory.clear();
	}

	@Override
	public int getSizeInventory()
	{
		return inventory.getSizeInventory() + blueprint_inventory.getSizeInventory();
	}

	@Override
	public boolean isEmpty()
	{
		return inventory.isEmpty() || blueprint_inventory.isEmpty();
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		if(index < inventory.getSizeInventory())
			return inventory.getStackInSlot(index);
		else if(index - inventory.getSizeInventory() < blueprint_inventory.getSizeInventory())
			return blueprint_inventory.getStackInSlot(index - inventory.getSizeInventory());
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if(index < inventory.getSizeInventory())
			return inventory.decrStackSize(index, count);
		else if(index - inventory.getSizeInventory() < blueprint_inventory.getSizeInventory())
			return blueprint_inventory.decrStackSize(index - inventory.getSizeInventory(), count);
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		if(index < inventory.getSizeInventory())
			return inventory.removeStackFromSlot(index);
		else if(index - inventory.getSizeInventory() < blueprint_inventory.getSizeInventory())
			return blueprint_inventory.removeStackFromSlot(index - inventory.getSizeInventory());
		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if(index < inventory.getSizeInventory())
			inventory.setInventorySlotContents(index,stack);
		else
			if(index - inventory.getSizeInventory() < blueprint_inventory.getSizeInventory())
				blueprint_inventory.setInventorySlotContents(index - inventory.getSizeInventory(),stack);
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player)
	{
		return true;
	}

	@Override
	public void read(CompoundNBT compound) {
		try {
			CompoundNBT blue_inv = (CompoundNBT) compound.get("blueprint");
			if (!blue_inv.isEmpty()) {
				blueprint_inventory.deserializeNBT(blue_inv);
			}
			CompoundNBT inv = (CompoundNBT) compound.get("inventory");
			if (!inv.isEmpty()) {
				inventory.deserializeNBT(inv);
			}
		}finally {}
		super.read(compound);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		if(blueprint_inventory != null)
			compound.put("blueprint", blueprint_inventory.serializeNBT());
		if(inventory != null)
			compound.put("inventory", inventory.serializeNBT());
		return super.write(compound);
	}
	
}
