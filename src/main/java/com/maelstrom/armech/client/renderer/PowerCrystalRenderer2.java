package com.maelstrom.armech.client.renderer;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.maelstrom.armech.client.model.ModelCrystal;
import com.maelstrom.armech.common.Reference;
import com.maelstrom.armech.common.tileentity.TileEntityCrystalConduit;

public class PowerCrystalRenderer2 extends TileEntitySpecialRenderer<TileEntityCrystalConduit>
{
	ModelCrystal crystal;
	ResourceLocation texture;
	
	public PowerCrystalRenderer2()
	{
		crystal = new ModelCrystal();
		texture = new ResourceLocation(Reference.MODID, "models/obj/crystal_charge.png");
	}

	double rotation = 0d;
	@Override
	public void renderTileEntityAt(TileEntityCrystalConduit tile, double x, double y, double z, float partialTick, int unknown)
	{
		GL11.glPushMatrix();
		
		GL11.glColor3f(1f, 1f, 1f);
		GL11.glTranslated(x+.5, y, z+.5);
		GL11.glDisable(GL11.GL_LIGHTING);

		int skylight = 200;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, skylight % 65536, skylight / 65536);
		tile.rotation += (1d * partialTick);
		GL11.glRotated(tile.rotation, 0, 1, 0);
		if(tile.rotation > 60d)
			tile.rotation -= 60d;
		
		
		
		
		
		
		
		
		bindTexture(texture);
		crystal.renderTile(tile);
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glColor3f(1f, 1f, 1f);
		GL11.glPopMatrix();
	}

}
