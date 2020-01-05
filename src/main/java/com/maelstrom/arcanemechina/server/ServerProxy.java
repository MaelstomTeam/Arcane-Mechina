package com.maelstrom.arcanemechina.server;

import com.maelstrom.snowcone.IProxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.crafting.RecipeManager;
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

	@Override
	public RecipeManager getRecipeManager(World world)
	{
		return world.getServer().getRecipeManager();
	}

}
