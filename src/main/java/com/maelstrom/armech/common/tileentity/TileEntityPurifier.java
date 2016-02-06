package com.maelstrom.armech.common.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;

import com.maelstrom.armech.common.items.ItemDustMixture;

public class TileEntityPurifier extends TileEntity implements ITickable, ISidedInventory
{
	
	public TileEntityPurifier()
	{
		jars = new ItemStack[4];
		jarDistilTime = new int[]{0,0,0,0};
	}
	
	public int getJarID(int id)
	{
		return jars[id] != null ? jars[id].getItemDamage() : -1;
	}

	@Override
	public void update()
	{
		for (int i = 0; i < jars.length; i++)
			if(jars[i] != null && !(jarDistilTime[i] > 2400))
				jarDistilTime[i]++;
	}

	public int getJarCount()
	{
		int amount = 0;
		for(ItemStack is : jars)
		{
			if(is != null)
				amount++;
		}
		return amount;
	}
	
	
	//==================================================================
	//update when you can as some methods i do not understand - Brandon
	//==================================================================
	

	public ItemStack[] jars;
	public int[] jarDistilTime;
	
	@Override
	public void clear() {
		for(int i = 0; i < jars.length ; i++)
			jars[i] = null;
	}

	@Override
	public void closeInventory(EntityPlayer arg0){}//?

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
			
		ItemStack temp = jars[slot].copy();
		jars[slot] = null;
		jarDistilTime[slot] = 0;
		if(!worldObj.isRemote)
			worldObj.markBlockForUpdate(getPos());
		return temp;
	}

	@Override
	public int getField(int arg0) {//?
		return 0;
	}

	@Override
	public int getFieldCount() {//?
		return 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public int getSizeInventory() {
		return 4;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return jars[slot];
	}

	@Override
	public ItemStack removeStackFromSlot(int slot)
	{
		return jars[slot];
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
		if(jars==null)
			jars = new ItemStack[4];
		return jars[slot] == null && itemstack.stackSize == 1 && itemstack.getItem() instanceof ItemDustMixture;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)//?
	{
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player){}//?

	@Override
	public void setField(int arg0, int arg1) {}//?

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack)
	{
		jars[slot] = itemstack;
		jarDistilTime[slot] = 0;
		if(!worldObj.isRemote)
			worldObj.markBlockForUpdate(getPos());
	}

	@Override
	public IChatComponent getDisplayName() {//?
		return null;
	}

	@Override
	public String getName() {
		return "tile.armech.purifier";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, EnumFacing face) {
		return (face.equals(EnumFacing.UP) && jars[slot] != null);// || (face.equals(EnumFacing.DOWN) && jars[slot] != null && jarDistilTime[slot] >= 2400);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, EnumFacing face) {
		return face.equals(EnumFacing.UP) && isItemValidForSlot(slot, itemstack);
	}

	@Override
	public int[] getSlotsForFace(EnumFacing face)
	{
		return face == EnumFacing.UP ? new int[]{0,1,2,3} :new int[]{};
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound syncData = new NBTTagCompound();
		writeToNBT(syncData);
		return new S35PacketUpdateTileEntity(this.pos, 1, syncData);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		readNBT(packet.getNbtCompound());
		if(worldObj.isRemote)
			worldObj.markBlockRangeForRenderUpdate(pos, pos);
	}
	
	public void readNBT(NBTTagCompound compound)
    {
    	this.jars = new ItemStack[getSizeInventory()];

        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        
        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;
            if (j < this.jars.length)
            {
            	jars[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }

	@Override
	public void readFromNBT(NBTTagCompound compound)
    {
    	super.readFromNBT(compound);
    	this.jars = new ItemStack[getSizeInventory()];

        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        
        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;
            if (j < this.jars.length)
            {
            	jars[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
//        int[] distil = compound.getIntArray("DistilTimes");
//        if(distil.length == 4)
//        	jarDistilTime = distil;
//        else
//        {
//        	System.out.println("\"jarDistilTime\" array is an incorrect length! setting array to default of \"{0,0,0,0}\"");
//        	jarDistilTime = new int[]{0,0,0,0};
//        }
		
    }

	@Override
    public void writeToNBT(NBTTagCompound compound)
    {
    	super.writeToNBT(compound);
    	
    	NBTTagList itemsList = new NBTTagList();
    	for(int i = 0; i < this.jars.length; i++)
    	{
    		if(jars[i] != null)
    		{
	            NBTTagCompound itemstackTag = new NBTTagCompound();
	            itemstackTag.setByte("Slot", (byte)i);
	            jars[i].writeToNBT(itemstackTag);
	            itemsList.appendTag(itemstackTag);
    		}
    	}
    	compound.setTag("Items", itemsList);
//		compound.setIntArray("DistilTimes", jarDistilTime);
    }
}