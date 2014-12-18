package com.maelstrom.arcaneMechina.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.maelstrom.arcaneMechina.interfaces.IBaubleRenderer;
import com.maelstrom.arcaneMechina.reference.Reference;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ContributorRenderHandler {
	
	public ResourceLocation array = Reference.getTextureResource("special/ArrayPlayer.png");
	
	@SubscribeEvent
	public void onPlayerRender(RenderPlayerEvent.Specials.Post event){
		EntityPlayer ply = event.entityPlayer;
			if(Reference.isContributor(ply.getDisplayName())){

				GL11.glPushMatrix();
				if(!ply.isInvisible()){
					//adds the sneaking rotation (THANK YOU BOTANIA!!)
					IBaubleRenderer.Helper.rotateWhileSneaking(ply);
					
					//bind texture
					Minecraft.getMinecraft().renderEngine.bindTexture(array);

					GL11.glRotated(event.renderer.modelBipedMain.bipedBody.rotateAngleY*90, 0, 1, 0);
					
					//test for armor and translate (vanilla items)
					if(ply.inventory.armorInventory[2] != null)
						GL11.glTranslated(0, 0, 0.05);

					GL11.glTranslated(0, .35, 0.15);
					
					//Change color per player
					if( ply.getDisplayName().equals("hybolic"))
						GL11.glColor3ub((byte)255, (byte)0, (byte)0);
					else if( ply.getDisplayName().equals("Sporeknight"))
						GL11.glColor3ub((byte)0, (byte)59, (byte)111);
					else if( ply.getDisplayName().equals("thatphatkid"))
						GL11.glColor3ub((byte)57, (byte)255, (byte)20);
					
					
					GL11.glRotated(ply.ticksExisted + event.partialRenderTick, 0, 0, 1);
					
					Tessellator tessellator = Tessellator.instance;
					
					//draw the plane
					tessellator.startDrawingQuads();
					tessellator.addVertexWithUV(-.3, -.3, 0, 0, 0);
					tessellator.addVertexWithUV(-.3, .3, 0, 0, 1);
					tessellator.addVertexWithUV(.3, .3, 0, 1, 1);
					tessellator.addVertexWithUV(.3, -.3, 0, 1, 0);
					tessellator.setTranslation(0, 0, 0);
					tessellator.draw();
					GL11.glPopMatrix();
				}
		}
	}
	
	private float getRGBValue(int value){
		float perInt = 1 / 255;
		return value * perInt;
	}

}
