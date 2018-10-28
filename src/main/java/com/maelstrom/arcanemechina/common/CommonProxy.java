package com.maelstrom.arcanemechina.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public abstract class CommonProxy implements IGuiHandler
{
	
	public void registerRenderers() {}

	@Override
	public abstract Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z);

	@Override
	public abstract Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z);

	public abstract void preInit() ;
	public abstract void init() ;
	public abstract void postInit() ;
}
