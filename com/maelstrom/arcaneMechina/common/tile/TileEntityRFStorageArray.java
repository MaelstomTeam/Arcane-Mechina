package com.maelstrom.arcaneMechina.common.tile;

import java.util.Random;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import cofh.api.energy.IEnergyStorage;
import cofh.api.energy.TileEnergyHandler;
import crazypants.enderio.power.IPowerStorage;
import net.minecraft.block.BlockRedstoneLight;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRFStorageArray extends TileEnergyHandler implements IEnergyStorage
{
//	public static Random random = new Random((int)(Math.PI * 100));
	public TileEntityRFStorageArray(){
		storage = new EnergyStorage(50000000, 500000, 500000);
	}
	
	@SuppressWarnings("unused")
	public void updateEntity() {
//		System.out.println(this.getEnergyStored());
		World w = this.worldObj;
		int x = this.xCoord;
		int y = this.yCoord;
		int z = this.zCoord;
		
		if(!w.isBlockIndirectlyGettingPowered(x, y, z)){
			transferEnergyToBlock(xCoord + 1, yCoord, zCoord, ForgeDirection.WEST);
			transferEnergyToBlock(xCoord, yCoord + 1, zCoord, ForgeDirection.DOWN);
			transferEnergyToBlock(xCoord, yCoord, zCoord + 1, ForgeDirection.NORTH);
			transferEnergyToBlock(xCoord - 1, yCoord, zCoord, ForgeDirection.EAST);
			transferEnergyToBlock(xCoord, yCoord - 1, zCoord, ForgeDirection.UP);
			transferEnergyToBlock(xCoord, yCoord, zCoord - 1, ForgeDirection.SOUTH);
		}
		
		if(false){
			float sunAng = w.getCelestialAngle(1f) * 360;
			
			float minEffAng1 = 7f;
			float noEffAng1 = 80f;
			
			float maxEffAng2 = 353f;
			float noEffAng2 = 280f;
	
			System.out.println(sunAng > noEffAng2 || sunAng < noEffAng1);//whole range
			System.out.println(sunAng < minEffAng1 || sunAng > maxEffAng2);//max eff
			System.out.println(sunAng);
		}
	}
	
	public void transferEnergyToBlock(int x, int y, int z, ForgeDirection dir){
		IEnergyReceiver tile;
		int transfer, cap, canTransfer;
		World w = this.worldObj;
		if(w.getTileEntity(x, y, z) instanceof IEnergyReceiver){
			tile = (IEnergyReceiver) w.getTileEntity(x, y, z);
			transfer = storage.getMaxExtract();
			cap = storage.getEnergyStored();
			
			if(transfer > cap)
				transfer = cap;
			
			canTransfer = tile.receiveEnergy(dir, transfer, true);
			if(canTransfer != 0)
				this.extractEnergy(tile.receiveEnergy(dir,canTransfer, false), false);
		}
	}
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored() {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored() {
		return storage.getMaxEnergyStored();
	}
}
