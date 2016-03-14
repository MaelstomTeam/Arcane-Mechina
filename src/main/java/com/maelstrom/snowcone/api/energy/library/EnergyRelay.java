package com.maelstrom.snowcone.api.energy.library;

import net.minecraft.tileentity.TileEntity;

public interface EnergyRelay extends IEnergyBase
{
	public TileEntity[] getParents();
	public TileEntity[] getChildern();
}