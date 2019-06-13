package com.maelstrom.arcanemechina.common.tileentity;

import javax.annotation.Nullable;

import org.apache.commons.lang3.RandomUtils;

import com.maelstrom.arcanemechina.common.items.ItemList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

public class TileEntityCrystalizer extends TileEntity implements ISidedInventory, ITickable {
	
	ItemStack input = ItemStack.EMPTY;
	ItemStack output = ItemStack.EMPTY;
	ItemStack quartz_dust = ItemStack.EMPTY;
	public int quartz_starter = 0;
	public int crystalizationTime = 0;
	private static ItemStack q = new ItemStack(ItemList.MetalDust, 1, 5);
	public static final  int growthTimeMax = 24000/4;
	@Override
	public int getSizeInventory() {
		return 3;
	}

	@Override
	public boolean isEmpty() {
		return input != ItemStack.EMPTY && output != ItemStack.EMPTY && quartz_dust != ItemStack.EMPTY;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		switch(index)
		{
			case 0: return input;
			case 1: return output;
			case 2: return quartz_dust;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack stack = getStackInSlot(index);
		ItemStack stackClone = getStackInSlot(index).copy();
		if(count > stack.getCount())
		{
			removeStackFromSlot(index);
			return stackClone;
		}
		stack.shrink(count);
		stackClone.setCount(count);
		return stackClone;
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		ItemStack clone = ItemStack.EMPTY;
		switch(index)
		{
			case 0 : {
				clone = input.copy();
				input = ItemStack.EMPTY;
				break;
			}
			case 1 : {
				clone = output.copy();
				output = ItemStack.EMPTY;
				break;
			}
			case 2 : {
				clone = quartz_dust.copy();
				quartz_dust = ItemStack.EMPTY;
				break;
			}
		}
		return clone;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		switch(index)
		{
			case 0 : {
				input = stack;
				break;
			}
			case 1 : {
				output = stack;
				break;
			}
			case 2 : {
				quartz_dust = stack;
				break;
			}
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		if(player instanceof net.minecraftforge.common.util.FakePlayer)
			return false;
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if(stack.isEmpty())
			return false;
		switch(index)
		{
			case 0: {
				return stack.getItem() == ItemList.Dust && input.isEmpty();
			}
			case 1: {
				return false;
			}
			case 2 : {
				return ItemStack.areItemsEqual(stack, q);
			}
		}
		return false;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		input = ItemStack.EMPTY;
		output = ItemStack.EMPTY;
		quartz_dust = ItemStack.EMPTY;
		quartz_starter = 0;
		crystalizationTime = 0;
	}

	@Override
	public String getName() {
		return "TileEntityCrystalizer";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		switch(side) {
			case NORTH:
			case SOUTH:
			case EAST:
			case WEST:
			{
				return new int[] {2};
			}
			case DOWN:
			{
				return new int[] {1};
			}
			case UP:
			{
				return new int[] {0};
			}
		}
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing side) {
		switch(side) {
			case NORTH:
			case SOUTH:
			case EAST:
			case WEST:
			{
				return isItemValidForSlot(2, itemStackIn);
			}
			case DOWN:
			{
				return isItemValidForSlot(1, itemStackIn);
			}
			case UP:
			{
				return isItemValidForSlot(0, itemStackIn);
			}
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return direction == EnumFacing.DOWN;
	}

	@Override
	public void update() {
		//check for quartz level
		if((quartz_starter <= 0 || quartz_starter < 500) && (quartz_dust != ItemStack.EMPTY))
		{
			decrStackSize(2,1);
			quartz_starter += 500;
		}
		//if input is empty and output is empty or has same meta value then continue processing
		if(input != ItemStack.EMPTY && output.isEmpty())
		{
			if(getWorld().isRemote)
			{
				if(RandomUtils.nextFloat(0, 1) > .7f)
					getWorld().spawnParticle(EnumParticleTypes.CRIT, 
							pos.getX()+.45f+(RandomUtils.nextFloat(0f, .15f)), 
							pos.getY()+RandomUtils.nextFloat(0f, .05f)+.5f, 
							pos.getZ()+.45f+(RandomUtils.nextFloat(0f, .15f)), 
							RandomUtils.nextFloat(0,0.1f)-0.05f,
							.02,
							RandomUtils.nextFloat(0,0.1f)-0.05f);
			}
			if(quartz_starter > 0)
			{
				crystalizationTime += 1;
				if(RandomUtils.nextInt(0,100) == 0)
					quartz_starter -= 1;
			}
			if(crystalizationTime / growthTimeMax > 0)
			{
				quartz_starter -= 1;
				crystalizationTime = 0;
				if(output == ItemStack.EMPTY)
					output = new ItemStack(ItemList.Crystal, 1,input.getMetadata());
				else
					output.grow(1);
				this.forceSyncToClient();
			}
		}
	}
	
	public void forceSyncToClient() {
		getWorld().markBlockRangeForRenderUpdate(pos, pos);
		getWorld().notifyBlockUpdate(pos, getWorld().getBlockState(pos), getWorld().getBlockState(pos), 3);
		getWorld().scheduleBlockUpdate(pos,getBlockType(),0,0);
		this.markDirty();
	}
	
	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}
	@Override
    public void readFromNBT(NBTTagCompound compound)
    {
		super.readFromNBT(compound);
		NonNullList<ItemStack> temp = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, temp);
		this.input = temp.get(0) == ItemStack.EMPTY ? ItemStack.EMPTY : temp.get(0);
		this.output = temp.get(1) == ItemStack.EMPTY ? ItemStack.EMPTY : temp.get(1);
		this.quartz_dust = temp.get(2) == ItemStack.EMPTY ? ItemStack.EMPTY : temp.get(2);
		this.crystalizationTime = compound.getInteger("crystalizationTime");
		this.quartz_starter = compound.getInteger("quartz_starter");
    }

	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
		super.writeToNBT(compound);
		NonNullList<ItemStack> temp = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		temp.set(0, this.input == ItemStack.EMPTY  ? ItemStack.EMPTY : this.input);
		temp.set(1, this.output == ItemStack.EMPTY ? ItemStack.EMPTY : this.output);
		temp.set(2, this.quartz_dust == ItemStack.EMPTY ? ItemStack.EMPTY : this.quartz_dust);
		ItemStackHelper.saveAllItems(compound, temp, true);
		compound.setInteger("crystalizationTime", this.crystalizationTime);
		compound.setInteger("quartz_starter", this.quartz_starter);
		return compound;
    }
}
