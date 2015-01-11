package com.maelstrom.arcaneMechina.common.world;

import net.minecraft.world.World;

public abstract class Effect {

	public abstract void effect(World w, int x, int y, int z);
	public abstract boolean effectCheck(World w, int x, int y, int z);
	
}