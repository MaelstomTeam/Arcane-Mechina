package com.maelstrom.snowcone.api.energy.library;

import net.minecraft.tileentity.TileEntity;

public interface IEnergyBase
{
	public long getEnergy();
	public long getMaxEnergy();
	public void setEnergy(long amount);
	public void addEnergy(long amount);

	public TileEntity getParent();
	public TileEntity getChild();
	public void addParent(TileEntity tile);
	public void addChild(TileEntity tile);
	
}
