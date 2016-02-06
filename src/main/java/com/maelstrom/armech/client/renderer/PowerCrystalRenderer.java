package com.maelstrom.armech.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.maelstrom.armech.client.model.ModelCrystal;
import com.maelstrom.armech.common.Reference;
import com.maelstrom.armech.common.tileentity.TileEntityCrystal;

public class PowerCrystalRenderer extends TileEntitySpecialRenderer<TileEntityCrystal>
{
	ModelCrystal crystal;
	ResourceLocation texture;
	
	public PowerCrystalRenderer()
	{
		crystal = new ModelCrystal();
		texture = new ResourceLocation(Reference.MODID, "models/obj/crystal_charge.png");
	}

	double rotation = 0d;
	@Override
	public void renderTileEntityAt(TileEntityCrystal tile, double x, double y, double z, float partialTick, int unknown)
	{
		GL11.glPushMatrix();
		
		GL11.glTranslated(x+.5, y-.875, z+.5);
		GL11.glScaled(2.75, 2.75, 2.75);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1f, 1f, 1f);

		int skylight = 200;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, skylight % 65536, skylight / 65536);
		rotation += (1d * partialTick);
		GL11.glRotated(rotation, 0, 1, 0);
		if(rotation > 180d)
			rotation -= 180d;
		
		
		
		
		
		
		
		
		bindTexture(texture);
		crystal.renderTile(tile);
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glColor3f(1f, 1f, 1f);
		GL11.glPopMatrix();
	}

}
