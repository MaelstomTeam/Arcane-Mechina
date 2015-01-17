package com.maelstrom.arcaneMechina.common.world.custom;

import net.minecraft.world.World;

import com.maelstrom.arcaneMechina.common.world.Effect;

public class EffectNone extends Effect {

	@Override
	public void effect(World w, int x, int y, int z) {
	}

	@Override
	public boolean effectCheck(World w, int x, int y, int z) {
		return true;
	}

}
