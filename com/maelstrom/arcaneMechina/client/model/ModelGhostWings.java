package com.maelstrom.arcaneMechina.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.maelstrom.arcaneMechina.interfaces.IBaubleRenderer;
import com.maelstrom.arcaneMechina.reference.Reference;

/**
 * Wings - hybolic Created using Tabula 4.0.2
 */
public class ModelGhostWings extends ModelBase {
	public static ModelGhostWings wingsTemp = new ModelGhostWings();

	public ModelRenderer WingIn;
	public ModelRenderer Bone2_;
	public ModelRenderer Bone3_;
	public ModelRenderer Bone4_;
	public ModelRenderer Bone5_;
	public ModelRenderer Bone6_;
	public ModelRenderer Skin1_;

	public ModelRenderer WingOut;
	public ModelRenderer Bone2;
	public ModelRenderer Bone3;
	public ModelRenderer Bone4;
	public ModelRenderer Bone5;
	public ModelRenderer Bone6;
	public ModelRenderer Skin1;
	public ModelRenderer Skin2;
	public ModelRenderer Skin3;
	public ModelRenderer Skin4;
	public ModelRenderer Skin5;

	private ResourceLocation texture = Reference
			.getTextureResource("models/wings.png");

	public ModelGhostWings() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.Bone5 = new ModelRenderer(this, 42, 1);
		this.Bone5.mirror = true;
		this.Bone5.setRotationPoint(0.0F, 0.0F, 0.5F);
		this.Bone5.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 10);
		this.Bone4_ = new ModelRenderer(this, 42, 1);
		this.Bone4_.setRotationPoint(0.0F, 0.0F, 0.5F);
		this.Bone4_.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 10);
		this.Skin2 = new ModelRenderer(this, 18, 1);
		this.Skin2.mirror = true;
		this.Skin2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Skin2.addBox(0.0F, 0.0F, 0.0F, 0, 7, 10);
		this.WingOut = new ModelRenderer(this, 0, 0);
		this.WingOut.mirror = true;
		this.WingOut.setRotationPoint(-2.0F, 1.0F, 1.0F);
		this.WingOut.addBox(-1.0F, -1.0F, -2.0F, 2, 3, 8);
		this.Bone2 = new ModelRenderer(this, 22, 0);
		this.Bone2.mirror = true;
		this.Bone2.setRotationPoint(0.0F, 0.0F, 6.0F);
		this.Bone2.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 9);
		this.Bone5_ = new ModelRenderer(this, 42, 1);
		this.Bone5_.setRotationPoint(0.0F, 0.0F, 0.5F);
		this.Bone5_.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 10);
		this.Skin1 = new ModelRenderer(this, 20, 9);
		this.Skin1.mirror = true;
		this.Skin1.setRotationPoint(0.0F, 0.0F, 1.0F);
		this.Skin1.addBox(0.0F, 0.0F, 0.0F, 0, 7, 9);
		this.Bone4 = new ModelRenderer(this, 42, 1);
		this.Bone4.mirror = true;
		this.Bone4.setRotationPoint(0.0F, 0.0F, 0.5F);
		this.Bone4.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 10);
		this.Skin3 = new ModelRenderer(this, 0, 14);
		this.Skin3.mirror = true;
		this.Skin3.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Skin3.addBox(0.0F, 0.0F, 0.0F, 0, 7, 10);
		this.Bone5.addChild(this.Skin3);
		this.Bone6 = new ModelRenderer(this, 42, 1);
		this.Bone6.mirror = true;
		this.Bone6.setRotationPoint(0.0F, 0.0F, 0.5F);
		this.Bone6.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 10);
		this.Bone3_ = new ModelRenderer(this, 42, 1);
		this.Bone3_.setRotationPoint(0.0F, 0.0F, 9.0F);
		this.Bone3_.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 10);
		this.Skin5 = new ModelRenderer(this, 0, 2);
		this.Skin5.mirror = true;
		this.Skin5.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Skin5.addBox(0.0F, -1.0F, -2.0F, 0, 7, 9);
		this.Skin4 = new ModelRenderer(this, 0, 8);
		this.Skin4.mirror = true;
		this.Skin4.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Skin4.addBox(0.0F, 0.0F, -1.0F, 0, 6, 10);
		this.Bone2_ = new ModelRenderer(this, 22, 0);
		this.Bone2_.setRotationPoint(0.0F, 0.0F, 6.0F);
		this.Bone2_.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 9);
		this.Skin1_ = new ModelRenderer(this, 20, 15);
		this.Skin1_.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Skin1_.addBox(0.0F, 0.0F, 0.0F, 0, 5, 10);
		this.Bone3 = new ModelRenderer(this, 42, 1);
		this.Bone3.mirror = true;
		this.Bone3.setRotationPoint(0.0F, 0.0F, 9.0F);
		this.Bone3.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 10);
		this.Bone6_ = new ModelRenderer(this, 42, 1);
		this.Bone6_.setRotationPoint(0.0F, 0.0F, 0.5F);
		this.Bone6_.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 10);
		this.WingIn = new ModelRenderer(this, 0, 0);
		this.WingIn.setRotationPoint(2.0F, 1.0F, 1.0F);
		this.WingIn.addBox(-1.0F, -1.0F, -2.0F, 2, 3, 8);

		this.WingOut.addChild(this.Bone2);
		this.Bone2.addChild(this.Bone3);
		this.Bone3.addChild(this.Bone4);
		this.Bone3.addChild(this.Bone5);
		this.Bone3.addChild(this.Bone6);
		this.Bone3.addChild(this.Skin1);
		this.Bone4.addChild(this.Skin2);
		this.Bone5.addChild(this.Skin3);
		this.Bone2.addChild(this.Skin4);
		this.WingOut.addChild(this.Skin5);

		this.WingIn.addChild(this.Bone2_);
		this.Bone2_.addChild(this.Bone3_);
		this.Bone3_.addChild(this.Bone4_);
		this.Bone3_.addChild(this.Bone5_);
		this.Bone3_.addChild(this.Bone6_);
		this.Bone3_.addChild(this.Skin1_);

		this.setRotateAngle(WingIn, -1.2740903539558606F, 0.35F, 0.0F);
		this.setRotateAngle(Bone2_, 2.8797932657906435F, -0.2F, 0.0F);
		this.setRotateAngle(Bone3_, -2.443460952792061F, 0.1F, 0.0F);
		this.setRotateAngle(Bone4_, -0.18203784098300857F, 0.0F, 0.0F);
		this.setRotateAngle(Bone5_, -0.3490658503988659F, 0.0F, 0.0F);
		this.setRotateAngle(Bone6_, -0.5235987755982988F, 0.0F, 0.0F);
		this.setRotateAngle(WingOut, 0.3F, -.6f, 0.0F);
		this.setRotateAngle(Bone2, 0.3F, -.1f, 0.0F);
		this.setRotateAngle(Bone3, 0.3F, -.1f, 0.0F);
		this.setRotateAngle(Bone4, -0.7853981633974483F, 0.0F, 0.0F);
		this.setRotateAngle(Bone5, -1.5707963267948966F, 0.0F, 0.0F);
		this.setRotateAngle(Bone6, -2.367539130330308F, 0.0F, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		if (entity instanceof EntityPlayer) {
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(1F, 1F, 1F, 0.5F);
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			
			EntityPlayer ply = (EntityPlayer) entity;
			if(ply == Minecraft.getMinecraft().thePlayer)
				if(Minecraft.getMinecraft().currentScreen instanceof InventoryEffectRenderer)
					renderPiece(WingIn, f5);
				else
					if (ply.capabilities.isFlying){
						if (ply.capabilities.isCreativeMode)
							renderPiece(WingIn, f5);
						else
							renderPiece(WingOut, f5);
					}
					else if (ply.fallDistance > 2f && ply.isSneaking()){
						if (ply.capabilities.isCreativeMode)
							renderPiece(WingIn, f5);
						else
							renderPiece(WingOut, f5);
					}
					else
						renderPiece(WingIn, f5);
			else
				if (ply.capabilities.isFlying){
					if (ply.capabilities.isCreativeMode)
						renderPiece(WingIn, f5);
					else
						renderPiece(WingOut, f5);
				}
				else if (ply.fallDistance > 2f && ply.isSneaking()){
					if (ply.capabilities.isCreativeMode)
						renderPiece(WingIn, f5);
					else
						renderPiece(WingOut, f5);
				}
				else
					renderPiece(WingIn, f5);
			if(!ply.onGround){
				WingOut.rotateAngleY = -1f + MathHelper.cos((entity.ticksExisted + f2) * .3F) * (float) Math.PI * 0.08F;
				Bone2.rotateAngleY = -0.25132743f + MathHelper.cos((entity.ticksExisted + f2) * .3F) * (float) Math.PI * 0.2F;
				Bone3.rotateAngleY = -0.25132743f + MathHelper.cos((entity.ticksExisted + f2) * .3F) * (float) Math.PI * 0.2F;
			}
			GL11.glPopMatrix();
		}
	}

	private void renderPiece(ModelRenderer model, float f) {
		model.render(f);
		GL11.glScalef(-1, 1, 1);
		model.render(f);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y,
			float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
