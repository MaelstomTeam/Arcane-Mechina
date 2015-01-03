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

import com.maelstrom.arcaneMechina.common.interfaces.IBaubleRenderer;
import com.maelstrom.arcaneMechina.common.reference.Reference;

/**
 * Wings - hybolic Created using Tabula 4.0.2
 * Animation by hybolic
 * Texture by hybolic
 * Function by hybolic
 * Name by Sporeknight
 * :P
 */
public class ModelGhostWings extends ModelBase {
	public static ModelGhostWings wingsTemp = new ModelGhostWings();
	
	public ModelRenderer skin6;
	public ModelRenderer wingOut;
	public ModelRenderer bone2;
	public ModelRenderer bone3;
	public ModelRenderer bone4;
	public ModelRenderer bone5;
	public ModelRenderer bone6;
	public ModelRenderer skin1;
	public ModelRenderer skin2;
	public ModelRenderer skin3;
	public ModelRenderer skin4;
	public ModelRenderer skin5;

	private ResourceLocation texture = Reference
			.getTextureResource("models/wings.png");

	public ModelGhostWings() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.bone5 = new ModelRenderer(this, 42, 1);
		this.bone5.mirror = true;
		this.bone5.setRotationPoint(0.0F, 0.0F, 0.5F);
		this.bone5.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 10);
		this.skin2 = new ModelRenderer(this, 18, 1);
		this.skin2.mirror = true;
		this.skin2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.skin2.addBox(0.0F, 0.0F, 0.0F, 0, 7, 10);
		this.wingOut = new ModelRenderer(this, 0, 0);
		this.wingOut.mirror = true;
		this.wingOut.setRotationPoint(-2.0F, 1.0F, 1.0F);
		this.wingOut.addBox(-1.0F, -1.0F, -2.0F, 2, 3, 8);
		this.bone2 = new ModelRenderer(this, 22, 0);
		this.bone2.mirror = true;
		this.bone2.setRotationPoint(0.0F, 0.0F, 6.0F);
		this.bone2.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 9);
		this.skin1 = new ModelRenderer(this, 20, 9);
		this.skin1.mirror = true;
		this.skin1.setRotationPoint(0.0F, 0.0F, 1.0F);
		this.skin1.addBox(0.0F, 0.0F, 0.0F, 0, 7, 9);
		this.bone4 = new ModelRenderer(this, 42, 1);
		this.bone4.mirror = true;
		this.bone4.setRotationPoint(0.0F, 0.0F, 0.5F);
		this.bone4.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 10);
		this.skin3 = new ModelRenderer(this, 0, 14);
		this.skin3.mirror = true;
		this.skin3.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.skin3.addBox(0.0F, 0.0F, 0.0F, 0, 7, 10);
		this.bone5.addChild(this.skin3);
		this.bone6 = new ModelRenderer(this, 42, 1);
		this.bone6.mirror = true;
		this.bone6.setRotationPoint(0.0F, 0.0F, 0.5F);
		this.bone6.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 10);
		this.skin5 = new ModelRenderer(this, 0, 2);
		this.skin5.mirror = true;
		this.skin5.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.skin5.addBox(0.0F, -1.0F, -2.0F, 0, 7, 9);
		this.skin4 = new ModelRenderer(this, 0, 8);
		this.skin4.mirror = true;
		this.skin4.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.skin4.addBox(0.0F, 0.0F, -1.0F, 0, 6, 10);
		this.skin6 = new ModelRenderer(this, 20, 15);
		this.skin6.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.skin6.addBox(0.0F, 0.0F, 0.0F, 0, 5, 10);
		this.bone3 = new ModelRenderer(this, 42, 1);
		this.bone3.mirror = true;
		this.bone3.setRotationPoint(0.0F, 0.0F, 9.0F);
		this.bone3.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 10);

		this.wingOut.addChild(this.bone2);
		this.wingOut.addChild(this.skin5);
		this.bone2.addChild(this.bone3);
		this.bone3.addChild(this.bone4);
		this.bone3.addChild(this.bone5);
		this.bone3.addChild(this.bone6);
		this.bone3.addChild(this.skin6);
		this.bone3.addChild(this.skin1);
		this.bone4.addChild(this.skin2);
		this.bone5.addChild(this.skin3);
		this.bone2.addChild(this.skin4);

		this.setRotateAngle(wingOut, 0.3F, -.6f, 0.0F);
		this.setRotateAngle(bone2, 0.3F, -.1f, 0.0F);
		this.setRotateAngle(bone3, 0.6F, -.1f, 0.0F);
		this.setRotateAngle(bone4, -0.7853981633974483F, 0.0F, 0.0F);
		this.setRotateAngle(bone5, -1.5707963267948966F, 0.0F, 0.0F);
		this.setRotateAngle(bone6, -2.367539130330308F, 0.0F, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		if (entity instanceof EntityPlayer) {
			
			EntityPlayer ply = (EntityPlayer) entity;
			
			GL11.glPushMatrix();
			GL11.glDepthMask(true); 
			GL11.glEnable(GL11.GL_BLEND);

			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			
			GL11.glColor4f(1F, 1F, 1F, 0.5F);
			
			renderPiece(wingOut, f5);
			
			if(ply.capabilities.isFlying || (ply.isSneaking() && ply.fallDistance > 1.5f)){
				skin6.isHidden = true;
				skin1.isHidden = false;
				skin2.isHidden = false;
				skin3.isHidden = false;
				skin4.isHidden = false;
				skin5.isHidden = false;
				this.setRotateAngle(bone2, 0.3f, -0.25132743f + MathHelper.cos((entity.ticksExisted + f2) * .3F) * (float) Math.PI * 0.2F, 0.0F);
				this.setRotateAngle(bone3, 0.6f, -0.25132743f + MathHelper.cos((entity.ticksExisted + f2) * .3F) * (float) Math.PI * 0.2F, 0.0F);
				this.setRotateAngle(wingOut, 0.3f, -1f + MathHelper.cos((entity.ticksExisted + f2) * .3F) * (float) Math.PI * 0.08F, 0.0F);
				this.setRotateAngle(bone4, -0.7853981633974483F, 0.0F, 0.0F);
				this.setRotateAngle(bone5, -1.5707963267948966F, 0.0F, 0.0F);
				this.setRotateAngle(bone6, -2.367539130330308F, 0.0F, 0.0F);
			}else if(ply.isSneaking() && ply.onGround){
				skin6.isHidden = true;
				skin1.isHidden = false;
				skin2.isHidden = false;
				skin3.isHidden = false;
				skin4.isHidden = false;
				skin5.isHidden = false;
				this.setRotateAngle(bone2, 0.3f, -0.25132743f + MathHelper.cos((entity.ticksExisted + f2) * .05F) * (float) Math.PI * 0.1F, 0.0F);
				this.setRotateAngle(bone3, 0.6f, -0.25132743f + MathHelper.cos((entity.ticksExisted + f2) * .05F) * (float) Math.PI * 0.1F, 0.0F);
				this.setRotateAngle(wingOut, 0.3f, -1f + MathHelper.cos((entity.ticksExisted + f2) * .05F) * (float) Math.PI * 0.04F, 0.0F);
				this.setRotateAngle(bone4, -0.7853981633974483F, 0.0F, 0.0F);
				this.setRotateAngle(bone5, -1.5707963267948966F, 0.0F, 0.0F);
				this.setRotateAngle(bone6, -2.367539130330308F, 0.0F, 0.0F);
			}else{
				skin6.isHidden = false;
				skin1.isHidden = true;
				skin2.isHidden = true;
				skin3.isHidden = true;
				skin4.isHidden = true;
				skin5.isHidden = true;
				this.setRotateAngle(wingOut, -1.2740903539558606F, -0.35F, 0.0F);
				this.setRotateAngle(bone2, 2.8797932657906435F, .2f, 0.0F);
				this.setRotateAngle(bone3, -2.443460952792061F, -.1f, 0.0F);
				this.setRotateAngle(bone4, -0.18203784098300857F, 0.0F, 0.0F);
				this.setRotateAngle(bone5, -0.3490658503988659F, 0.0F, 0.0F);
				this.setRotateAngle(bone6, -0.5235987755982988F, 0.0F, 0.0F);
			}
			GL11.glDisable(GL11.GL_BLEND);
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
