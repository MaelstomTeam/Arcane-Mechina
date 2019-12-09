package com.maelstrom.arcanemechina.common.runic.rune_interfaces;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("deprecation")
public interface IRuneRenderer2 {

	public static void bindTexture(ResourceLocation location) {
		TextureManager texturemanager = Minecraft.getInstance().textureManager;
		if (texturemanager != null) {
			texturemanager.bindTexture(location);
		}

	}

	public static FontRenderer getFontRenderer() {
		return Minecraft.getInstance().fontRenderer;
	}

	public static ItemRenderer getItemRenderer() {
		return Minecraft.getInstance().getItemRenderer();
	}

	public static TextureManager getTextureManager() {
		return Minecraft.getInstance().getTextureManager();
	}

	public static void renderItem(ItemStack stack) {
		IBakedModel bakedmodel = getItemRenderer().getModelWithOverrides(stack);
		GlStateManager.pushMatrix();
		getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
		getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.translated(8, 0, 8);
		if(stack.getItem() instanceof BlockItem)
		{
			GlStateManager.scaled(.6,.6,.6);
			GlStateManager.translated(0, 8, 0);
			GlStateManager.rotated(90, 0, 1, 0);
		}
		else
		{
			GlStateManager.translated(0, 1.01, 0);
			GlStateManager.rotated(90, 1, 0, 0);
			GlStateManager.rotated(90, 0, 0, 1);
		}
		GlStateManager.scaled(16,16,16);
		bakedmodel = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(bakedmodel, ItemCameraTransforms.TransformType.NONE, false);
		getItemRenderer().renderItem(stack, bakedmodel);
		GlStateManager.popMatrix();
		getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
		getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
	}

	public void render(float particks);

}
