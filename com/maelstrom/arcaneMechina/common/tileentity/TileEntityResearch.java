package com.maelstrom.arcanemechina.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.maelstrom.arcanemechina.common.ItemsReference;

public class TileEntityResearch extends TileEntity implements ISidedInventory {	
	
	//inventory slot 0
	private ItemStack researchBook = null;
	//inventory slot 1
	private ItemStack ink = null;
	//inventory slot 2
	private ItemStack quill = null;
	
	private int inventorySize = 3;
	
	@Override
	public int getSizeInventory()
	{
		return inventorySize;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		if(slot == 0)
			return researchBook;
		else if (slot == 1)
			return ink;
		else if (slot == 2)
			return quill;
		
		
		//none if it's not available
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		ItemStack temp = null;
		if(slot == 0)
		{
			temp = researchBook;
			researchBook = null;
		}
		else if(slot == 1)
		{
			temp = ink;
			ink = null;
		}
		else if(slot == 2)
		{
			temp = quill;
			quill = null;
		}
		return temp;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		ItemStack temp = null;
		if(slot == 0)
		{
			temp = researchBook;
			researchBook = null;
		}
		else if(slot == 1)
		{
			temp = ink;
			ink = null;
		}
		else if(slot == 2)
		{
			temp = quill;
			quill = null;
		}
		return temp;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack)
	{
        if(itemStack != null)
        {
			if(slot == 0 && itemStack.getItem() == ItemsReference.AMBook && this.getStackInSlot(slot) == null)
			{
				researchBook = itemStack;
				itemStack = null;
			}
		    else if(slot == 1 /*&& Stack.getItem() == ItemsReference.InkBottle*/ && this.getStackInSlot(slot) == null)
		    {
		    	ink = itemStack;
				itemStack = null;
		    }
		    else if(slot == 2 /*&& Stack.getItem() == ItemsReference.Quill*/ && this.getStackInSlot(slot) == null)
		    {
		    	quill = itemStack;
				itemStack = null;
		    }
        }
	}

	@Override
	public String getInventoryName()
	{
		return null;
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return false;
	}

	@Override
	public void openInventory() 
	{
	}

	@Override
	public void closeInventory()
	{
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack)
	{
        if(itemStack != null)
			if(slot == 0 && itemStack.getItem() == ItemsReference.AMBook && this.getStackInSlot(slot) == null)
		    	return true;
		    else if(slot == 1 /*&& Stack.getItem() == ItemsReference.InkBottle*/ && this.getStackInSlot(slot) == null)
		    	return true;
		    else if(slot == 2 /*&& Stack.getItem() == ItemsReference.Quill*/ && this.getStackInSlot(slot) == null)
		    	return true;
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		return new int[]{0,1,2,3,4,5,6};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemStack, int side)
	{
		return isItemValidForSlot(slot, itemStack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side)
	{
		return isItemValidForSlot(slot, itemStack);
	}

	public void setResearchBook(ItemStack itemStack)
	{
		researchBook = itemStack;
		markDirty();
	}

	public ItemStack getResearchBook()
	{
		return researchBook;
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound syncData = new NBTTagCompound();
		writeToNBT(syncData);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		readFromNBT(packet.func_148857_g());
	}
	
	@Override
    public void readFromNBT(NBTTagCompound nbt)
    {
		super.readFromNBT(nbt);
		
		NBTTagCompound items = nbt.getCompoundTag("items");
		
    	NBTTagCompound researchBookNBT = items.getCompoundTag("researchBook");
    	NBTTagCompound inkNBT   = items.getCompoundTag("ink");
    	NBTTagCompound quillNBT = items.getCompoundTag("quill");
    	
    	
    	researchBook = null;
    	ink = null;
    	quill = null;
    	
    	
    	researchBook = ItemStack.loadItemStackFromNBT(researchBookNBT);
    	ink = ItemStack.loadItemStackFromNBT(inkNBT);
    	quill = ItemStack.loadItemStackFromNBT(quillNBT);
    }
	
	@Override
    public void writeToNBT(NBTTagCompound nbt)
    {
    	super.writeToNBT(nbt);
    	NBTTagCompound items = new NBTTagCompound();
    	NBTTagCompound researchBookNBT = new NBTTagCompound();
    	NBTTagCompound inkNBT   = new NBTTagCompound();
    	NBTTagCompound quillNBT = new NBTTagCompound();
    	
    	//write item nbt
    	if(researchBook != null)
    		researchBook.writeToNBT(researchBookNBT);
    	if(ink != null)
    		ink.writeToNBT(inkNBT);
    	if(quill != null)
    		quill.writeToNBT(quillNBT);
    	
    	//place nbt into item nbt
    	items.setTag("researchBook", researchBookNBT);
    	items.setTag("ink", inkNBT);
    	items.setTag("quill", quillNBT);
    	
    	//place that into the main nbt
    	nbt.setTag("items", items);
    	
    	System.out.println(nbt);
    }
}
