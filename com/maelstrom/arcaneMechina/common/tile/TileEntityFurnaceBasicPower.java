package com.maelstrom.arcaneMechina.common.tile;

import com.maelstrom.arcaneMechina.common.interfaces.IPower;

import net.minecraft.tileentity.TileEntity;

public class TileEntityFurnaceBasicPower extends TileEntityFurnaceBasic implements IPower {
	
	public int pLevel = 0;
	
	@Override
	public boolean acceptsPower() {
		return true;
	}

	@Override
	public boolean distributesPower() {
		return true;
	}

	@Override
	public int capacity() {
		return 320;
	}

	@Override
	public void link(IPower link) {}

	@Override
	public int currentPower() {
		return pLevel;
	}

	@Override
	public boolean consumePower(int i) {
		if(pLevel >= i){
			pLevel -= i;
			return true;
		}
		return false;
	}

	@Override
	public void recievePower(int i) {
		if(pLevel < capacity()){
			pLevel += i;
		}
	}

}
