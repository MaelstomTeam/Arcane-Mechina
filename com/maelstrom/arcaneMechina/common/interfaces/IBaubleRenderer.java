package com.maelstrom.arcanemechina.common.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IBaubleRenderer
{
	
	//placeholder for later
	public static enum RenderLocation
	{
		HEAD,
		BODY,
		FLOOR;
	}
	
//	@SideOnly(Side.CLIENT)
	public abstract void onPlayerBaubleRenderer(EntityPlayer player, RenderPlayerEvent event);
	public abstract RenderLocation getRenderLocation();
	
	public static class Helper
	{
		//only used with head and body renderer
		public static void rotateWhileSneaking(EntityPlayer player)
		{
			if(player.isSneaking())
				GL11.glRotatef(28.64789f, 1f, 0f, 0f);
		}
	}
	
}