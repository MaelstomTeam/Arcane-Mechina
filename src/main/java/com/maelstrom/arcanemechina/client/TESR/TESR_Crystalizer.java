package com.maelstrom.arcanemechina.client.TESR;

import org.lwjgl.opengl.GL11;

import com.maelstrom.arcanemechina.common.items.ItemList;
import com.maelstrom.arcanemechina.common.tileentity.TileEntityCrystalizer;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TESR_Crystalizer extends TESR_Renderer_Base<TileEntityCrystalizer> {
	private static ItemStack itemstack = new ItemStack(ItemList.Crystal, 1, 0);

	private static float one_sixteenth = 0.0625f;
	@Override
	public void render(TileEntityCrystalizer te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		one_sixteenth = 0.0625f;
		float per = ((float)te.crystalizationTime) / ((float)te.growthTimeMax); 
		float quartzValue = te.quartz_starter / 1000f;
		if(!te.isEmpty()) {
		GL11.glPushMatrix();
			GL11.glTranslated(x, y, z);
			//draw quartz buffer
			GL11.glPushMatrix();
				GL11.glTranslated(one_sixteenth*4.54, one_sixteenth*9, one_sixteenth*4.54);
				GL11.glRotated(45f, 0, 1, 0);
		        GlStateManager.pushMatrix();
			        RenderHelper.disableStandardItemLighting();
			        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			        if (Minecraft.isAmbientOcclusionEnabled()) {
			            GlStateManager.shadeModel(GL11.GL_SMOOTH);
			        } else {
			            GlStateManager.shadeModel(GL11.GL_FLAT);
			        }

		        	GL11.glScaled(.999, 0.95, .999);
			        World world = te.getWorld();
			        if(quartzValue > 0)
			        	GL11.glScaled(one_sixteenth, (one_sixteenth*4)*quartzValue, one_sixteenth);
			        else
			        	GL11.glScaled(0f, 0f, 0f);
			        GlStateManager.translate(-te.getPos().getX()-.5, -te.getPos().getY(), -te.getPos().getZ()-.5);
			        Tessellator tessellator = Tessellator.getInstance();
			        BufferBuilder bufferBuilder = tessellator.getBuffer();
			        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
			        Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer()
			        .renderModel(world,
			        		Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(Blocks.CONCRETE_POWDER.getDefaultState()),
			        		Blocks.QUARTZ_BLOCK.getDefaultState(),
			        		te.getPos(),
			        		bufferBuilder,
			        		true);
			        tessellator.draw();
		
			        RenderHelper.enableStandardItemLighting();
		        GlStateManager.popMatrix();
			GL11.glPopMatrix();
			//draw items
			GL11.glPushMatrix();
			GL11.glTranslated(one_sixteenth*9, one_sixteenth*8.3, one_sixteenth*9);
				GL11.glPushMatrix();
					GL11.glScaled(.65, .65, .65);
					GL11.glScalef(1f-per, 1f-per, 1f-per);
					GL11.glRotated(-45f, 0, 1, 0);
					renderItem(te.getStackInSlot(0), x,y,z);
					GL11.glRotated(90f, 0, 1, 0);
					renderItem(te.getStackInSlot(0), x,y,z);
				GL11.glPopMatrix();
				GL11.glPushMatrix();
					GL11.glTranslated(0, one_sixteenth, 0);
					GL11.glTranslated(0, (one_sixteenth*4)-(one_sixteenth*4)*per, 0);
					GL11.glScalef(per, per, per);
					itemstack.setItemDamage(te.getStackInSlot(0).getItemDamage());
					GL11.glRotated(-45f, 0, 1, 0);
					renderItem(itemstack, x,y,z);
					GL11.glRotated(90f, 0, 1, 0);
					renderItem(itemstack, x,y,z);
				GL11.glPopMatrix();
			GL11.glPopMatrix();
		GL11.glPopMatrix();
		}
	}
}
