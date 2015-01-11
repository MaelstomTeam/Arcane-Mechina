package com.maelstrom.arcaneMechina.common.world.custom;

import net.minecraft.world.World;

import com.maelstrom.arcaneMechina.common.world.Effect;

public class NoEffect extends Effect {

	@Override
	public void effect(World w, int x, int y, int z) {
		w.playSoundEffect(x+.5, y+.5, z+.5, "mob.endermen.portal", 1, 1);
	}

	@Override
	public boolean effectCheck(World w, int x, int y, int z) {
		return true;
	}

}
