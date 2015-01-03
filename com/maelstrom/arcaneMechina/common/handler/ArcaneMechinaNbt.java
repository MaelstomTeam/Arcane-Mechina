package com.maelstrom.arcaneMechina.common.handler;

import net.minecraft.entity.player.EntityPlayer;

import com.maelstrom.arcaneMechina.common.reference.Reference;
import com.maelstrom.snowcone.nbt.PlayerNbt;

public class ArcaneMechinaNbt extends PlayerNbt{
	
	private boolean hasLogged = false;

	public ArcaneMechinaNbt(EntityPlayer ply) {
		super(ply, Reference.MOD_ID);
		hasLogged = this.getBoolean("hasLogged");
		Update();
	}
	

	public boolean hasLoggedPreviously() {
		return hasLogged;
	}

	@Override
	public void Update() {
		this.setBoolean("hasLogged", hasLogged);
	}

}
