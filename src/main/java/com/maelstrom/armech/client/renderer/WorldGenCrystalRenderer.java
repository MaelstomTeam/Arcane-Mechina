package com.maelstrom.armech.client.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import com.maelstrom.armech.client.model.ModelCrystal2;
import com.maelstrom.armech.common.tileentity.clientonly.TileEntityWorldGenCrystal;

public class WorldGenCrystalRenderer extends TileEntitySpecialRenderer<TileEntityWorldGenCrystal>
{

	ModelCrystal2 crystal;
	ResourceLocation texture;
	
	public WorldGenCrystalRenderer()
	{
		crystal = new ModelCrystal2();
		texture = new ResourceLocation("minecraft", "textures/blocks/stone.png");
	}

	@Override
	public void renderTileEntityAt(TileEntityWorldGenCrystal tile, double x, double y, double z, float partialTick, int unknown)
	{
		GL11.glPushMatrix();
		GL11.glTranslated(x,y,z);
		GlStateManager.enableLighting();
		GL11.glColor3f(1, 1, 1);
//		tile.META = 4;
		boolean skip = false;
		if(tile.META > 5) skip = true;
		if(tile.META < 0) skip = true;
		if(!skip)
			switch(EnumFacing.VALUES[tile.META])
			{
			case UP: { GL11.glTranslated(.5, 0, .5); break;}
			case DOWN:  { GL11.glTranslated(.5, 1, .5); GL11.glRotated(180, 1, 0, 0); break;}
			case EAST:  { GL11.glTranslated(0, .5, .5); GL11.glRotated(270, 0, 0, 1); break;}
			case NORTH: { GL11.glTranslated(.5, .5, 1); GL11.glRotated(270, 1, 0, 0); break;}
			case SOUTH: { GL11.glTranslated(.5, .5, 0); GL11.glRotated(90, 1, 0, 0); break;}
			case WEST: { GL11.glTranslated(1, .5, .5); GL11.glRotated(90, 0, 0, 1); break;}
			default:
				break;
			}
		else
			GL11.glTranslated(.5, 0, .5);

		GlStateManager.disableLighting();
		this.bindTexture(texture);
		crystal.renderStone();
		if(tile.switchgreen)
		{
			tile.green--;
			if(tile.green < 0)
			{
				tile.green = 0;
				tile.switchgreen = false;
			}
			
		}
		else
		{
			tile.green++;
			if(tile.green > 255)
			{
				tile.green = 255;
				tile.switchgreen = true;
			}
		}
		if(tile.switchred)
		{
			tile.red--;
			if(tile.red < 0)
			{
				tile.red = 0;
				tile.switchred = false;
			}
			
		}
		else
		{
			tile.red++;
			if(tile.red > 255)
			{
				tile.red = 255;
				tile.switchred = true;
			}
		}
		if(tile.switchblue)
		{
			tile.blue--;
			if(tile.blue < 0)
			{
				tile.blue = 0;
				tile.switchblue = false;
			}
			
		}
		else
		{
			tile.blue++;
			if(tile.blue > 255)
			{
				tile.blue = 255;
				tile.switchblue = true;
			}
		}
		crystal.renderCrystals(tile.red, tile.green, tile.blue);
		GL11.glColor3f(1f,1f,1f);
		GL11.glPopMatrix();
	}

}
