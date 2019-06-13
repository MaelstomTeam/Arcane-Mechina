package com.maelstrom.arcanemechina.common.tileentity;

import java.util.Random;
import java.util.logging.Logger;

import javax.annotation.Nullable;

import com.maelstrom.arcanemechina.api.grinding.ProcessingList;
import com.maelstrom.arcanemechina.api.grinding.ProcessingRecipe;
import com.maelstrom.arcanemechina.common.block.BlockList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.datafix.walkers.ItemStackData;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMortar extends TileEntity implements ISidedInventory {
	
	public String customName;
	public ItemStack input = ItemStack.EMPTY;
	public ItemStack output = ItemStack.EMPTY;
	public int procTime = 0;
	
	@Override
	public String getName() {
		if(hasCustomName())
			return customName;
		return "mortar_pestle";
	}

	@Override
	public boolean hasCustomName() {
		return customName != null && !customName.isEmpty();
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		switch(side)
		{
		case UP:
			return new int[] {0,1};
		default:
			return new int[0];
		}
		
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack input_item, EnumFacing direction) {
		if(direction == EnumFacing.UP) {
			if(slot == 0)
			{
				if(output.isEmpty() && input.isEmpty())
					return true;
				else if(direction == EnumFacing.UP && ItemStack.areItemsEqual(input,input_item)
				&& input_item.getCount() + input.getCount() <= input_item.getMaxStackSize())
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack output_item, EnumFacing direction) {
		if(direction == EnumFacing.UP) {
			if(slot == 1)
			{
				if(output != ItemStack.EMPTY && direction == EnumFacing.UP)
					return true;
			}
		}
		return false;
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public boolean isEmpty() {
		return input.isEmpty() && output.isEmpty();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		switch(index)
		{
		case 0: return input;
		case 1: return output;
		default: return ItemStack.EMPTY;
		}
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		forceUpdate();
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
	public ItemStack removeStackFromSlot(int index) {
		forceUpdate();
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
		}
		return clone;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		forceUpdate();
		switch(index)
		{
		case 0:
			input = stack;
			break;
		case 1:
			output = stack;
			break;
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		if(player instanceof net.minecraftforge.common.util.FakePlayer)
			return false;
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return canInsertItem(index,stack,EnumFacing.UP);
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		input = ItemStack.EMPTY;
		output = ItemStack.EMPTY;
		procTime = 0;
	}
	@Override
    public void readFromNBT(NBTTagCompound compound)
    {
		super.readFromNBT(compound);
		NonNullList<ItemStack> temp = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, temp);
		this.input = temp.get(0) == ItemStack.EMPTY ? ItemStack.EMPTY : temp.get(0);
		this.output = temp.get(1) == ItemStack.EMPTY ? ItemStack.EMPTY : temp.get(1);
		this.procTime = compound.getInteger("processingTime");
		this.customName = compound.getString("customName");
    }

	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
		super.writeToNBT(compound);
		NonNullList<ItemStack> temp = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		temp.set(0, this.input);
		temp.set(1, this.output);
		ItemStackHelper.saveAllItems(compound, temp, true);
		compound.setInteger("processingTime", this.procTime);
		if(hasCustomName())
			compound.setString("customName", customName);
		return compound;
    }
	public boolean canProcess()
	{
		return !input.isEmpty();
	}
	public void process(Random rand) {
		procTime += rand.nextInt(4);
		if(procTime >= 100)
		{
			procTime -= 100;
			ProcessingRecipe temp = ProcessingList.process(input);
			input.shrink(1);
			if(input.getCount() <= 0 || input.isEmpty())
				input = ItemStack.EMPTY;
			if(!output.isEmpty())
				output.grow(temp.getOutput(rand).getCount());
			else
				output = temp.getOutput(rand);
		}
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

	public void forceUpdate() {

		getWorld().markBlockRangeForRenderUpdate(pos, pos);
		getWorld().notifyBlockUpdate(pos, getWorld().getBlockState(pos), getWorld().getBlockState(pos), 3);
		getWorld().scheduleBlockUpdate(pos,blockType,0,0);
		this.markDirty();
	}
}
