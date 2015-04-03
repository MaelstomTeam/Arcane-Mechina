package com.maelstrom.arcaneMechina.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.maelstrom.arcaneMechina.common.reference.Reference;

/**
 * AMBook - hybolic
 * Created using Tabula 4.0.2
 */
public class ModelAMBook extends ModelBase {
    public ModelRenderer CoverGraphic;
    public ModelRenderer BackGraphic;
    public ModelRenderer group;
    public ModelRenderer Cover_Back;
    public ModelRenderer Cover_Front;
    public ModelRenderer Page2;
    public ModelRenderer Page1;
	private ResourceLocation AMBookGraphic = Reference.getTextureResource("models/AMBookGraphic.png");
	private ResourceLocation AMBook = Reference.getTextureResource("models/AMBook.png");

    public ModelAMBook() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.group = new ModelRenderer(this, 0, 0);
        this.group.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.group.addBox(0.0F, 0.0F, 0.0F, 0, 0, 0);
        this.BackGraphic = new ModelRenderer(this, 0, -9);
        this.BackGraphic.setRotationPoint(-0.0F, 0.0F, 0.0F);
        this.BackGraphic.addBox(-1.0F, -3.0F, 0.0F, 0, 14, 9);
        this.setRotateAngle(BackGraphic, 0.0F, -0.7853981633974483F, 0.0F);
        this.Cover_Front = new ModelRenderer(this, 0, 0);
        this.Cover_Front.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Cover_Front.addBox(0.0F, -3.0F, 0.0F, 1, 14, 9);
        this.setRotateAngle(Cover_Front, 0.0F, 0.7853981633974483F, 0.0F);
        this.group.addChild(this.Cover_Front);
        this.CoverGraphic = new ModelRenderer(this, 0, -9);
        this.CoverGraphic.setRotationPoint(-0.0F, 0.0F, 0.0F);
        this.CoverGraphic.addBox(1.0F, -3.0F, 0.0F, 0, 14, 9);
        this.BackGraphic.mirror = true;
        this.setRotateAngle(CoverGraphic, 0.0F, 0.7853981633974483F, 0.0F);
        this.Cover_Back = new ModelRenderer(this, 0, 0);
        this.Cover_Back.mirror = true;
        this.Cover_Back.setRotationPoint(-0.0F, 0.0F, 0.0F);
        this.Cover_Back.addBox(-1.0F, -3.0F, 0.0F, 1, 14, 9);
        this.setRotateAngle(Cover_Back, 0.0F, -0.7853981633974483F, 0.0F);
        this.group.addChild(this.Cover_Back);
        this.Page1 = new ModelRenderer(this, 20, 0);
        this.Page1.setRotationPoint(-0.0F, 0.0F, 0.0F);
        this.Page1.addBox(0.0F, -3.0F, 0.0F, 0, 14, 9);
        this.setRotateAngle(Page1, 0.0F, -0.39269908169872414F, 0.0F);
        this.group.addChild(this.Page1);
        this.Page2 = new ModelRenderer(this, 38, 0);
        this.Page2.setRotationPoint(-0.0F, 0.0F, 0.0F);
        this.Page2.addBox(0.0F, -3.0F, 0.0F, 0, 14, 9);
        this.setRotateAngle(Page2, 0.0F, 0.39269908169872414F, 0.0F);
        this.group.addChild(this.Page2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
    	GL11.glPushMatrix();
    	GL11.glScaled(0.1, 0.1, 0.1);
    	GL11.glRotated(-45, 0, 1, 0);
    	GL11.glTranslated(0.2, -1.5, -5);
    	Minecraft.getMinecraft().renderEngine.bindTexture(AMBookGraphic);
    	this.BackGraphic.render(f5);
        this.CoverGraphic.render(f5);
        Minecraft.getMinecraft().renderEngine.bindTexture(AMBook);
        this.group.render(f5);
        GL11.glPopMatrix();
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
