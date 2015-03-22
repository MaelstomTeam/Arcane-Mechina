package com.maelstrom.arcaneMechina.common.tile;

import com.maelstrom.arcaneMechina.common.interfaces.IPower;

import net.minecraft.tileentity.TileEntity;

public class TileEntityGlyph extends TileEntity implements IPower {
	
	protected int level = 0;
	public IPower link;
	public int pLevel = 0;
	
//	public void updateEntity() {
//		int amount = 1;
//		if(consumePower(amount)){
//			pLevel += amount;
//		}
//	}

	@Override
	public boolean acceptsPower() {
		return true;
	}

	@Override
	public boolean distributesPower() {
		if(level > 0)
			return true;
		return false;
	}

	@Override
	public void link(IPower link) {
		this.link = link;
	}

	@Override
	public int capacity() {
		return 3200;
	}

	@Override
	public int currentPower() {
		return pLevel;
	}

	@Override
	public boolean consumePower(int i) {
		if(link != null)
			return link.consumePower(i);
		return false;
	}

	@Override
	public void recievePower(int i) {
		// TODO Auto-generated method stub
		
	}

}
