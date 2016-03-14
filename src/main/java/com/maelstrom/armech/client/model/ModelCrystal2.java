package com.maelstrom.armech.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.maelstrom.armech.common.Reference;
import com.maelstrom.snowcone.modelloader.AdvancedModelLoader;
import com.maelstrom.snowcone.modelloader.IModelCustom;

public class ModelCrystal2 extends ModelBase
{
	IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.MODID,"models/obj/WorldGen-Crystal.obj"));

	public void renderCrystals(int i, int j, int k)
	{
		GL11.glPushMatrix();
			GlStateManager.disableLighting();
			GlStateManager.disableTexture2D();	
			int skylight = 200;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, skylight % 65536, skylight / 65536);
			GL11.glColor4d(1d/255*i,1d/255*j,1d/255*k,.2d);
			model.renderOnly("Crystals");
			GlStateManager.enableTexture2D();
			GlStateManager.enableLighting();
		GL11.glPopMatrix();
	}
	
	public void renderStone()
	{
		GL11.glPushMatrix();
			model.renderOnly("Stone");
		GL11.glPopMatrix();
	}
}