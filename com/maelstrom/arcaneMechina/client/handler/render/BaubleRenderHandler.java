package com.maelstrom.arcanemechina.client.handler.render;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.RenderPlayerEvent;

import org.lwjgl.opengl.GL11;

import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;

import com.maelstrom.arcanemechina.common.interfaces.IBaubleRenderer;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BaubleRenderHandler {
	
	@SubscribeEvent
	public void onPlayerRender(RenderPlayerEvent.Specials.Post event){
		EntityPlayer ply = event.entityPlayer;
		InventoryBaubles baubleInv = PlayerHandler.getPlayerBaubles(ply);
		
		for(int i = 0; i < baubleInv.getSizeInventory(); i++){
			ItemStack is = baubleInv.getStackInSlot(i);
			if(is != null && is.getItem() instanceof IBaubleRenderer && event.entityLiving.getActivePotionEffect(Potion.invisibility) == null){
				
				if(!ply.isInvisible()){
					GL11.glTranslated(0, 0, 0.1);
					IBaubleRenderer.Helper.rotateWhileSneaking(ply);
					
					GL11.glPushMatrix();
					GL11.glColor4f(1F, 1F, 1F, 1F);
					
					if(((IBaubleRenderer) is.getItem()).getRenderLocation().equals(IBaubleRenderer.RenderLocation.BODY)){
						GL11.glRotated(event.renderer.modelBipedMain.bipedBody.rotateAngleY*90, 0, 1, 0);
						((IBaubleRenderer) is.getItem()).onPlayerBaubleRenderer(ply, event);
					}else if(((IBaubleRenderer) is.getItem()).getRenderLocation().equals(IBaubleRenderer.RenderLocation.HEAD)){
						GL11.glRotated(event.renderer.modelBipedMain.bipedHead.rotateAngleY*90, 0, 1, 0);
						((IBaubleRenderer) is.getItem()).onPlayerBaubleRenderer(ply, event);
					}else if(((IBaubleRenderer) is.getItem()).getRenderLocation().equals(IBaubleRenderer.RenderLocation.FLOOR)){
						((IBaubleRenderer) is.getItem()).onPlayerBaubleRenderer(ply, event);
					}
				}
				
				GL11.glPopMatrix();
			}
		}
		
		
	}

}
