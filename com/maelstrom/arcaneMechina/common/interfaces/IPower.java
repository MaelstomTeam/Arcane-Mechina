package com.maelstrom.arcaneMechina.common.interfaces;

import net.minecraft.world.World;

public interface IPower {

	public boolean acceptsPower();
	public boolean distributesPower();
	public int capacity();
	public int currentPower();
	public boolean consumePower(int i);
	public void link(IPower link);
	public void recievePower(int i);

}
