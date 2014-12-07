package com.maelstrom.arcaneMechina.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;

import org.lwjgl.opengl.GL11;

import com.maelstrom.arcaneMechina.reference.Reference;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ContributorRenderHandler {
	
	public ResourceLocation array = Reference.getTextureResource("special/ArrayPlayer.png");
	public int arrayRotationZ = 0;
	
	public String[] modders = { "hybolic", "Sporeknight" };
	
	@SubscribeEvent
	public void onPlayerRender(RenderPlayerEvent.Specials.Post event){
		
		EntityPlayer ply = event.entityPlayer;
			
			//for loop for modders string list
			for(String name : modders)
				if(ply.getDisplayName().equals(name)){
					
					GL11.glPushMatrix();
	
					//bind texture
					Minecraft.getMinecraft().renderEngine.bindTexture(array);
					
					//test for armor and translate (vanilla items)
					if(ply.inventory.armorInventory[2] != null)
						GL11.glTranslated(0, 0, 0.05);
	
					GL11.glTranslated(0, .35, 0.15);
					GL11.glRotated(arrayRotationZ, 0, 0, 1);
					
					Tessellator tessellator = Tessellator.instance;
					
					//Change color per player
					if( ply.getDisplayName().equals("hybolic"))
						GL11.glColor3f(1, 0, 0);
					else if( ply.getDisplayName().equals("Sporeknight"))
						GL11.glColor3f(0, 0, 1);
					
					//draw the plane
					tessellator.startDrawingQuads();
					tessellator.addVertexWithUV(-.3, -.3, 0, 0, 0);
					tessellator.addVertexWithUV(-.3, .3, 0, 0, 1);
					tessellator.addVertexWithUV(.3, .3, 0, 1, 1);
					tessellator.addVertexWithUV(.3, -.3, 0, 1, 0);
					tessellator.setTranslation(0, 0, 0);
					tessellator.draw();
					
					GL11.glPopMatrix();
					
					//rotational things
					arrayRotationZ++;
					if(arrayRotationZ > 359)
						arrayRotationZ = 0;
			}
	}

}
