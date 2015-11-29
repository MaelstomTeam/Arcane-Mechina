package com.maelstrom.arcanemechina.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.maelstrom.arcanemechina.client.model.ModelResearchTable;

public class ResearchTableRenderer extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTick)
	{
		GL11.glPushMatrix();
		
		GL11.glTranslated(x, y, z);
		
		Tessellator tessellator = Tessellator.instance;
		World world = tileEntity.getWorldObj();
		Block block = tileEntity.getBlockType();
		float blockBrightness = 1f / block.getLightValue(world, (int)x, (int)y, (int)z);
		
		tessellator.setColorOpaque_F(blockBrightness, blockBrightness, blockBrightness);
		
		ModelResearchTable.instance.renderTile(tileEntity);
		
		GL11.glPopMatrix();
	}

}
