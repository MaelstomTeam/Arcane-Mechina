package com.maelstrom.arcaneMechina;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderPlayerEvent.Specials.Post;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IBaubleRenderer {
	
	//placeholder for later
	public static enum RenderLocation{
		HEAD,
		BODY,
		FLOOR;
	}
	
	@SideOnly(Side.CLIENT)
	public void onPlayerBaubleRenderer(EntityPlayer player, RenderPlayerEvent.Specials.Post event);
	public RenderLocation getRenderLocation();
	
	public static class Helper {
		//@SelfReminder onnly have this used with head and body renderers
		public static void rotateWhileSneaking(EntityPlayer player){
			if(player.isSneaking())
				GL11.glRotatef(28.64789f, 1f, 0f, 0f);
		}
	}
	
}