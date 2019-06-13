package com.maelstrom.arcanemechina.client.TESR;

import org.lwjgl.opengl.GL11;

import com.maelstrom.arcanemechina.common.tileentity.TileEntityMortar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.ForgeHooksClient;

public abstract class TESR_Renderer_Base<T extends TileEntity> extends TileEntitySpecialRenderer<T> {
	protected void renderItem(ItemStack itemstack, double x, double y, double z) {
		renderItem(itemstack, x, y, z, 0, 0, 0, 0);
	}
	protected void renderItem(ItemStack itemstack, double x, double y, double z, double a, double x2, double y2, double z2) {

		EntityItem item = new EntityItem(getWorld());
		x = 0.0;
		y = 0.0;
		z = 0.0;


		boolean flag = false;
		if (this.bindEntityTexture(item)) {
			Minecraft.getMinecraft().getRenderManager().renderEngine.getTexture(this.getEntityTexture(item)).setBlurMipmap(false, false);
			flag = true;
		}
		GlStateManager.enableRescaleNormal();
		GlStateManager.alphaFunc(516, 0.1F);
		GlStateManager.enableBlend();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,GlStateManager.DestFactor.ZERO);
		GlStateManager.pushMatrix();
		IBakedModel ibakedmodel = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(itemstack, getWorld(), (EntityLivingBase) null);
		int j = this.transformModelCount(item, x, y, z, 0f, ibakedmodel);
		GL11.glRotated(a, x2, y2, z2);
		boolean flag1 = ibakedmodel.isGui3d();



		if (!flag1) 
		{ 
			float f3 = -0.0F * (float)(j - 1) * 0.5F; float f4 = -0.0F * (float)(j - 1) * 0.5F; float f5 = -0.09375F * (float)(j - 1) * 0.5F; 
			GlStateManager.translate(f3, f4, f5); 
		}



		if (flag1) 
		{
			GlStateManager.pushMatrix();
			ibakedmodel = ForgeHooksClient.handleCameraTransforms(ibakedmodel,ItemCameraTransforms.TransformType.GROUND, false);
			Minecraft.getMinecraft().getRenderItem().renderItem(itemstack, ibakedmodel);
			GlStateManager.popMatrix();
		} 
		else 
		{
			GlStateManager.pushMatrix();
			ibakedmodel = ForgeHooksClient.handleCameraTransforms(ibakedmodel,ItemCameraTransforms.TransformType.GROUND, false);
			Minecraft.getMinecraft().getRenderItem().renderItem(itemstack, ibakedmodel);
			GlStateManager.popMatrix();
		}


		GlStateManager.popMatrix();
		GlStateManager.disableRescaleNormal();
		GlStateManager.disableBlend();
		this.bindEntityTexture(item);


		if (flag) {
			Minecraft.getMinecraft().renderEngine.getTexture(this.getEntityTexture(item)).restoreLastBlurMipmap();
		}
	}
	protected boolean bindEntityTexture(EntityItem entity) {
		ResourceLocation resourcelocation = this.getEntityTexture(entity);


		if (resourcelocation == null) {
			return false;
		} else {
			this.bindTexture(resourcelocation);
			return true;
		}
	}


	protected ResourceLocation getEntityTexture(EntityItem entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}


	private int transformModelCount(EntityItem itemIn, double x, double y, double z, float p_177077_8_, IBakedModel bakedModel) {
		ItemStack itemstack = itemIn.getItem();
		Item item = itemstack.getItem();


		if (item == null) {
			return 0;
		} else {
			boolean flag = bakedModel.isGui3d();
			int i = 1;
			float f = 0.25F;
			float f1 = shouldBob() ? MathHelper.sin(((float) itemIn.getAge() + p_177077_8_) / 10.0F + itemIn.hoverStart) * 0.1F + 0.1F : 0;
			float f2 = bakedModel.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y;
			GlStateManager.translate((float) x, (float) y, (float) z);


			if (flag || Minecraft.getMinecraft().getRenderManager().options != null) {
				float f3 = (((float) itemIn.getAge() + p_177077_8_) / 20.0F + itemIn.hoverStart) * (180F / (float) Math.PI);
				//GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
			}


			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			return i;
		}
	}


	public boolean shouldSpreadItems() {
		return false;
	}


	public boolean shouldBob() {
		return false;
	}

	public void renderTileEntityAt(T tileentity, double x, double y, double z, float partialTick,
			int breakStage) {
		render(tileentity, x, y, z, partialTick, breakStage, 1f);
	}
}
