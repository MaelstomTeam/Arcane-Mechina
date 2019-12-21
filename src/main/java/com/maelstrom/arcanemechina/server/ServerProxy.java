package com.maelstrom.arcanemechina.server;

import com.maelstrom.arcanemechina.common.IProxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ServerProxy implements IProxy
{

	@Override
	public void init()
	{
	}

	@Override
	public World getClientWorld()
	{
		throw new IllegalStateException("Only run this on the client!");
	}

	@Override
	public PlayerEntity getClientPlayer()
	{
		throw new IllegalStateException("Only run this on the client!");
	}

}
