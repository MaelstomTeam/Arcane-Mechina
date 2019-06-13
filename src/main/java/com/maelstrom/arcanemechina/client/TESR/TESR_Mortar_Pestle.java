package com.maelstrom.arcanemechina.client.TESR;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.maelstrom.arcanemechina.common.tileentity.TileEntityMortar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.ForgeHooksClient;

public class TESR_Mortar_Pestle extends TESR_Renderer_Base<TileEntityMortar> {

	@Override
	public void render(TileEntityMortar te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{

		Random random = new Random();
		GL11.glPushMatrix();
		GL11.glTranslated(.5, 0.2, .4);
		if(te.input != null && !te.input.isEmpty())
			for(int i = 0; i < (te.input.getCount() / 16) + 1; i++)
				renderItem(te.input, x-.13,y+(0.03*i),z-.15, 90f, 1, 0, 0);
		if(te.output != null && !te.output.isEmpty())
			for(int i = 0; i < (te.output.getCount() / 16) + 1; i++)
				renderItem(te.output, x+.13,y+(0.03*i),z+.15, 90f, 1, 0, 0);
		GL11.glPopMatrix();
	}
	
}
