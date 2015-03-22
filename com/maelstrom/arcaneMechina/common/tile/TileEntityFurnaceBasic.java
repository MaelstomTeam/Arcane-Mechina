package com.maelstrom.arcaneMechina.common.tile;

import net.minecraft.tileentity.TileEntity;

import com.maelstrom.arcaneMechina.common.interfaces.IPower;

public class TileEntityFurnaceBasic extends TileEntity implements IPower {
	
	@Override
	public boolean acceptsPower() {
		return false;
	}

	@Override
	public boolean distributesPower() {
		return false;
	}

	@Override
	public int capacity() {
		return 0;
	}

	@Override
	public void link(IPower link) {
		
	}

	@Override
	public int currentPower() {
		return 0;
	}

	@Override
	public boolean consumePower(int i) {
		return false;
	}

	@Override
	public void recievePower(int i) {
		// TODO Auto-generated method stub
		
	}

}
