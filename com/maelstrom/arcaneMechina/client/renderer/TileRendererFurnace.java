package com.maelstrom.arcaneMechina.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.maelstrom.arcaneMechina.common.reference.Texture;

public class TileRendererFurnace extends TileEntitySpecialRenderer {

	public TileRendererFurnace() {
	}
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float ticks) {
		GL11.glPushMatrix();
		
		GL11.glTranslated(x, y, z);
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		 
//		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F); 
//		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F); 
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
//		GL11.glDepthMask(false);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		renderBlock(Tessellator.instance, tile, x, y, z, ticks);
		switch(tile.getWorldObj().getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord)){
			case 0: { renderArray(Tessellator.instance, tile, x, y, z, ticks); break; }
			case 1: { renderBlock(Tessellator.instance, tile, x, y, z, ticks); break; }
		}
//		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
	
	private void renderBlock(Tessellator t, TileEntity tile, double x, double y, double z, float s) {
		this.bindTexture(new ResourceLocation("minecraft:textures/blocks/furnace_front_off.png"));

		t.startDrawingQuads();
		
		t.addVertexWithUV( 0, 0, 0, 0, 1);
		t.addVertexWithUV( .1, 1, .1, .1, 0);
		t.addVertexWithUV( .9, 1, .1, .9, 0);
		t.addVertexWithUV( 1, 0, 0, 1, 1);
		
		t.addVertexWithUV( 1, 0, 1, 0, 1);
		t.addVertexWithUV( .9, 1, .9, .1, 0);
		t.addVertexWithUV( .1, 1, .9, .9, 0);
		t.addVertexWithUV( 0, 0, 1, 1, 1);
		
		t.addVertexWithUV( 1, 0, 0, 0, 1);
		t.addVertexWithUV( .9, 1, .1, .1, 0);
		t.addVertexWithUV( .9, 1, .9, .9, 0);
		t.addVertexWithUV( 1, 0, 1, 1, 1);
		
		t.addVertexWithUV( 0, 0, 1, 0, 1);
		t.addVertexWithUV( .1, 1, 0.9, .1, 0);
		t.addVertexWithUV( .1, 1, .1, .9, 0);
		t.addVertexWithUV( 0, 0, 0, 1, 1);
		
		t.draw();
		
		Texture.bindTexture(new ResourceLocation("minecraft:textures/blocks/furnace_top.png"));

		t.startDrawingQuads();
		
		t.addVertexWithUV( .1, 1, .1, 0, 0);
		t.addVertexWithUV( .1, 1, .9, 1, 0);
		t.addVertexWithUV( .9, 1, .9, 1, 1);
		t.addVertexWithUV( .9, 1, .1, 0, 1);
		
		t.draw();
	}

	private void renderArray(Tessellator t, TileEntity tile, double x, double y, double z, float pticks) {
		Texture.bindTexture(Texture.furnaceSide);
		
		t.startDrawingQuads();
		
		t.addVertexWithUV( 0, 0, 0, 0, 1);
		t.addVertexWithUV( 0, 1, 0, 0, 0);
		t.addVertexWithUV( 1, 1, 0, 1, 0);
		t.addVertexWithUV( 1, 0, 0, 1, 1);
		
		t.addVertexWithUV( 1, 0, 1, 0, 1);
		t.addVertexWithUV( 1, 1, 1, 0, 0);
		t.addVertexWithUV( 0, 1, 1, 1, 0);
		t.addVertexWithUV( 0, 0, 1, 1, 1);
		
		t.addVertexWithUV( 1, 0, 0, 0, 1);
		t.addVertexWithUV( 1, 1, 0, 0, 0);
		t.addVertexWithUV( 1, 1, 1, 1, 0);
		t.addVertexWithUV( 1, 0, 1, 1, 1);
		
		t.addVertexWithUV( 0, 0, 1, 0, 1);
		t.addVertexWithUV( 0, 1, 1, 0, 0);
		t.addVertexWithUV( 0, 1, 0, 1, 0);
		t.addVertexWithUV( 0, 0, 0, 1, 1);
		
		t.draw();

		Texture.bindTexture(Texture.circle_4);

		GL11.glPushMatrix();
		GL11.glTranslated(0.5, (MathHelper.cos((Minecraft.getMinecraft().thePlayer.ticksExisted + pticks) * .02F) * (float) Math.PI * 0.1F)+.35, 0.5);
		GL11.glScaled(2.5, 2.5, 2.5);
		GL11.glRotated((Minecraft.getMinecraft().thePlayer.ticksExisted * (float)Math.PI * -.05F) * 5f, 0, 1, 0);
		arrayPart(t, tile, x, y, z, pticks);
		GL11.glPopMatrix();

//		GL11.glPushMatrix();
//		GL11.glTranslated(0.5, (MathHelper.cos((Minecraft.getMinecraft().thePlayer.ticksExisted + pticks) * .01F) * (float) Math.PI * 0.2F)+.7, 0.5);
//		GL11.glScaled(3.6, 3.6, 3.6);
//		GL11.glRotated((Minecraft.getMinecraft().thePlayer.ticksExisted * (float)Math.PI * .025F) * 5f, 0, 1, 0);
//		arrayPart(t, tile, x, y, z, pticks);
//		GL11.glPopMatrix();
		
	}
	
	public void arrayPart(Tessellator t, TileEntity tile, double x, double y, double z, float pticks){
		t.startDrawingQuads();
		
		t.setTranslation(-.5, 0, -.5);
		
		t.addVertexWithUV( 0, 0, 0, 0, 0);
		t.addVertexWithUV( 0, 0, 1, 0, 1);
		t.addVertexWithUV( 1, 0, 1, 1, 1);
		t.addVertexWithUV( 1, 0, 0, 1, 0);

		t.addVertexWithUV( 1, 0, 0, 1, 0);
		t.addVertexWithUV( 1, 0, 1, 1, 1);
		t.addVertexWithUV( 0, 0, 1, 0, 1);
		t.addVertexWithUV( 0, 0, 0, 0, 0);

		t.setTranslation(0, 0, 0);
		
		t.draw();
	}
}
