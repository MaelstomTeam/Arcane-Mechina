package com.maelstrom.arcaneMechina.client.model;

import org.lwjgl.opengl.GL11;

import com.maelstrom.arcaneMechina.common.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

/**
 * g - 
 * Created using Tabula 4.0.2
 */
public class ModelSubsonicScrewdriver extends ModelBase {
	
    public ModelRenderer hilt;
    public ModelRenderer nill;
    public ModelRenderer innerCrystal;
    public ModelRenderer crystal;
    public ModelRenderer _3_1;
    public ModelRenderer _4_1;
    public ModelRenderer _3_2;
    public ModelRenderer _4_2;
    
	private ResourceLocation texture = Reference.getTextureResource("models/screwdriver.png");

    public ModelSubsonicScrewdriver() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this._4_2 = new ModelRenderer(this, 0, 0);
        this._4_2.setRotationPoint(0.0F, -5.2F, 0.2F);
        this._4_2.addBox(-1.0F, -6.0F, -0.8F, 2, 7, 1);
        this.setRotateAngle(_4_2, -1.5707963267948966F, 0.0F, 0.0F);
        this.hilt = new ModelRenderer(this, 1, 3);
        this.hilt.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.hilt.addBox(-2.5F, 0.0F, -2.5F, 5, 24, 5);
        this._3_2 = new ModelRenderer(this, 0, 0);
        this._3_2.setRotationPoint(0.0F, -5.2F, 0.2F);
        this._3_2.addBox(-1.0F, -6.0F, -0.8F, 2, 7, 1);
        this.setRotateAngle(_3_2, -1.5707963267948966F, 0.0F, 0.0F);
        this.crystal = new ModelRenderer(this, 21, 0);
        this.crystal.setRotationPoint(0.0F, 0.1F, 0.0F);
        this.crystal.addBox(-1.5F, -6.0F, -1.5F, 3, 6, 3);
        this.nill = new ModelRenderer(this, 0, 0);
        this.nill.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.nill.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0);
        this.innerCrystal = new ModelRenderer(this, 22, 1);
        this.innerCrystal.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.innerCrystal.addBox(-1.0F, -4.9F, -1.0F, 2, 5, 2);
        this.setRotateAngle(innerCrystal, 0.0F, 0.7853981633974483F, 0.0F);
        this._4_1 = new ModelRenderer(this, 21, 9);
        this._4_1.setRotationPoint(0.0F, 0.0F, 3.0F);
        this._4_1.addBox(-1.5F, -5.0F, -0.8F, 3, 7, 1);
        this.setRotateAngle(_4_1, 0.7853981633974483F, -3.141592653589793F, 0.0F);
        this._3_1 = new ModelRenderer(this, 21, 9);
        this._3_1.setRotationPoint(0.0F, 0.0F, -3.0F);
        this._3_1.addBox(-1.5F, -5.0F, -0.8F, 3, 7, 1);
        this.setRotateAngle(_3_1, 0.7853981633974483F, 0.0F, 0.0F);
        this.nill.addChild(this._3_1);
        this._4_1.addChild(this._4_2);
        this.nill.addChild(this._4_1);
        this.crystal.addChild(this.innerCrystal);
        this.hilt.addChild(this.nill);
//        this.hilt.addChild(this.crystal);
        this._3_1.addChild(this._3_2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    	//should work
    	float r = f1;
    	float g = f3;
    	float b = f4;
    	
    	GL11.glPushMatrix();
    	
	    	GL11.glTranslated(0.6, .9, 0);
	    	GL11.glRotated(130, 1, 0, 0);
	    	Minecraft.getMinecraft().renderEngine.bindTexture(texture);
	    	if(entity != null)
	    		nill.rotateAngleY = MathHelper.cos((entity.ticksExisted + f2) * .3F) * (float) Math.PI;
	    	
	        this.hilt.render(f5);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        GL11.glColor4d(r, f, b, .75);
	        this.crystal.render(f5);
        
    	GL11.glPopMatrix();
    }
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
