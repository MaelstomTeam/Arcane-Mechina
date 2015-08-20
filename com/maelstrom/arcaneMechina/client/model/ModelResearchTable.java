package com.maelstrom.arcanemechina.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.maelstrom.arcanemechina.common.Reference;
import com.maelstrom.arcanemechina.common.tileentity.TileEntityResearch;

public class ModelResearchTable extends ModelBase
{
	public static final ModelResearchTable instance = new ModelResearchTable();
	public ModelResearchTable()
	{
		
	}
	public void renderTile(TileEntity table)
	{
		if(table instanceof TileEntityResearch)
			renderTile((TileEntityResearch) table, table.getWorldObj(), table.xCoord, table.yCoord, table.zCoord);
	}
	private void renderTile(TileEntityResearch table, World world, int x, int y, int z)
	{
        GL11.glTranslated(0, 1.5, 0);
		GL11.glScaled(0.0625F, 0.0625F, 0.0625F);
		int ticksExisted = Minecraft.getMinecraft().thePlayer.ticksExisted;
        GL11.glPushMatrix();
	        GL11.glRotated(180, 0, 0, 1);
	        GL11.glTranslated(0, 4, 0);
			GL11.glScaled(0.5F, 0.5F, 0.5F);
			if(!table.hasInk())
			{
		        GL11.glPushMatrix();
		        	GL11.glTranslated(0, 4, 0);
			        GL11.glTranslated(-16, 0, -16);
//			    	GL11.glRotated(-Math.toDegrees(Math.atan2((x + .5) - Minecraft.getMinecraft().thePlayer.posX, (z + .5) - Minecraft.getMinecraft().thePlayer.posZ)) - 90, 0, 1, 0);
			    	GL11.glTranslated(-7, -19, 16);
			    	GL11.glRotated(14 - MathHelper.cos((ticksExisted) * .05F) * (float) Math.PI * 3F, -1, 0, 1);
			    	GL11.glRotated(MathHelper.cos((ticksExisted) * .05F) * (float) Math.PI * 3F, 0, 1, 0);
		        	GL11.glTranslated(0, -0.2625 + MathHelper.cos((ticksExisted) * .075F) * (float) Math.PI * 0.4F, 0);
			    	
					ModelInkWell.instance.renderInkWell();
				GL11.glPopMatrix();
			}
			if(!table.hasQuill())
			{
		        GL11.glPushMatrix();
//			    	GL11.glScaled(2,2,2);
		        	GL11.glTranslated(0, 4, 0);
			        GL11.glTranslated(-16, 0, 16);
//			    	GL11.glRotated(-Math.toDegrees(Math.atan2((x + .5) - Minecraft.getMinecraft().thePlayer.posX, (z + .5) - Minecraft.getMinecraft().thePlayer.posZ)), 0, 1, 0);
			    	GL11.glTranslated(9, -14, -14);
			    	GL11.glRotated(185, 0, 1, 0);
			    	GL11.glRotated(315 + MathHelper.cos((ticksExisted) * .03F) * (float) Math.PI * 4f, 0, 0, 1);
			    	GL11.glRotated(35, 1, 0, 0);
		        	GL11.glTranslated(0, -0.2625 - MathHelper.cos((ticksExisted) * .05F) * (float) Math.PI * 0.04F, 0);
		        	GL11.glTranslated(0, MathHelper.cos((ticksExisted) * .08267F) * (float) Math.PI * .58922f, 0);
					GL11.glTranslated(4, -20, -19);
					ModelQuill.instance.renderQuill();
				GL11.glPopMatrix();
			}
			if(table.hasResearchBook())
			{
		        GL11.glPushMatrix();
			    	GL11.glScaled(16,16,16);
					GL11.glTranslated(-1, .49, 1);
					ModelResearchBook.instance.renderResearchBook(x,z);
				GL11.glPopMatrix();
			}

        GL11.glPopMatrix();
	}
	
	private static class ModelVisualArray extends ModelBase
	{
		private ResourceLocation tableTopTexture = Reference.getTextureResource("models/TableTop.png");
	    
	    public void renderTableArray()
	    {
	    	int ticksExisted = Minecraft.getMinecraft().thePlayer.ticksExisted;
	    	
	    	GL11.glPushMatrix();
	    		Minecraft.getMinecraft().renderEngine.bindTexture(tableTopTexture);
	    		
//	    		GL11.glColor3d(0, 0, 0);
				
	    		GL11.glRotated(ticksExisted % 360, 0, 1, 0);
	    		GL11.glTranslated(0, -.3, 0);
				GL11.glTranslated(0, -0.1625 + MathHelper.cos((ticksExisted) * .05F) * (float) Math.PI * 0.04F, 0);
				GL11.glScaled(6,6,6);
				
				Tessellator tess = Tessellator.instance;
		    	tess.startDrawingQuads();
		    	tess.setTranslation(-.5, 0, -.5);
		    	tess.addVertexWithUV(1, 0, 0, 1, 0);
		    	tess.addVertexWithUV(1, 0, 1, 1, 1);
		    	tess.addVertexWithUV(0, 0, 1, 0, 1);
		    	tess.addVertexWithUV(0, 0, 0, 0, 0);
		    	tess.draw();
		    	
	    		GL11.glColor3d(1, 1, 1);


	    		GL11.glTranslated(0, MathHelper.cos((ticksExisted) * .05F) * (float) Math.PI * 0.005F, 0);
				GL11.glScaled(.2, .2, .2);
				
		    	GL11.glPushMatrix();
	    			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("minecraft:textures/items/iron_sword.png"));
		    		GL11.glTranslated(1.365, 0, 1.365);	
		    		GL11.glRotated(90, 0, 1, 0);
			    	tess.startDrawingQuads();
			    	tess.addVertexWithUV(1, 0, 0, 1, 0);
			    	tess.addVertexWithUV(1, 0, 1, 1, 1);
			    	tess.addVertexWithUV(0, 0, 1, 0, 1);
			    	tess.addVertexWithUV(0, 0, 0, 0, 0);
			    	tess.draw();
		    	GL11.glPopMatrix();
		    	

		    	GL11.glPushMatrix();
	    			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("minecraft:textures/items/iron_pickaxe.png"));
		    		GL11.glTranslated(-1.365, 0, -1.365);	
		    		GL11.glRotated(-90, 0, 1, 0);
			    	tess.startDrawingQuads();
			    	tess.addVertexWithUV(1, 0, 0, 1, 0);
			    	tess.addVertexWithUV(1, 0, 1, 1, 1);
			    	tess.addVertexWithUV(0, 0, 1, 0, 1);
			    	tess.addVertexWithUV(0, 0, 0, 0, 0);
			    	tess.draw();
		    	GL11.glPopMatrix();
		    	

		    	GL11.glPushMatrix();
	    			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("minecraft:textures/items/iron_shovel.png"));
		    		GL11.glTranslated(1.365, 0, -1.365);	
		    		GL11.glRotated(180, 0, 1, 0);
			    	tess.startDrawingQuads();
			    	tess.addVertexWithUV(1, 0, 0, 1, 0);
			    	tess.addVertexWithUV(1, 0, 1, 1, 1);
			    	tess.addVertexWithUV(0, 0, 1, 0, 1);
			    	tess.addVertexWithUV(0, 0, 0, 0, 0);
			    	tess.draw();
		    	GL11.glPopMatrix();
		    	

		    	GL11.glPushMatrix();
	    			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("minecraft:textures/items/iron_axe.png"));
		    		GL11.glTranslated(-1.365, 0, 1.365);
		    		GL11.glRotated(180, 0, 1, 0);
			    	tess.startDrawingQuads();
			    	tess.addVertexWithUV(1, 0, 0, 0, 1);
			    	tess.addVertexWithUV(1, 0, 1, 1, 1);
			    	tess.addVertexWithUV(0, 0, 1, 1, 0);
			    	tess.addVertexWithUV(0, 0, 0, 0, 0);
			    	tess.draw();
		    	GL11.glPopMatrix();

		    	tess.setTranslation(0,0,0);

		    GL11.glPopMatrix();
	    }
	}
	private static class ModelInkWell extends ModelBase
	{
		public static final ModelInkWell instance = new ModelInkWell();
		private ModelRenderer bottle;
		private ModelRenderer bottleRim;
		private ModelRenderer bottleRim2;
		private ResourceLocation inkWellTexture = Reference.getTextureResource("models/InkWell.png");
		
	    public ModelInkWell() {
	        this.textureWidth = 32;
	        this.textureHeight = 32;
	        this.bottleRim = new ModelRenderer(this, 0, 11);
	        this.bottleRim.setRotationPoint(0.0F, 0.0F, 0.0F);
	        this.bottleRim.addBox(-3.5F, -3.75F, -3.5F, 7, 4, 7, 0.0F);
	        this.bottle = new ModelRenderer(this, 0, 0);
	        this.bottle.setRotationPoint(0.0F, 0.0F, 0.0F);
	        this.bottle.addBox(-3.0F, 0.0F, -3.0F, 6, 5, 6, 0.0F);
	        this.bottleRim2 = new ModelRenderer(this, 0, 22);
	        this.bottleRim2.setRotationPoint(0.0F, 0.0F, 0.0F);
	        this.bottleRim2.addBox(-4.0F, -3.5F, -4.0F, 8, 2, 8, 0.0F);
	        this.bottle.addChild(this.bottleRim);
	        this.bottle.addChild(this.bottleRim2);
	        
	    }
	    
	    public void renderInkWell()
	    {
	    	Minecraft.getMinecraft().renderEngine.bindTexture(inkWellTexture);
	    	bottle.render(1f);
	    }
	}
	private static class ModelQuill extends ModelBase
	{
		public static final ModelQuill instance = new ModelQuill();
		private ResourceLocation quillTexture = Reference.getTextureResource("models/Quill.png");
		

	    public ModelRenderer MainQuill;
	    public ModelRenderer SubQuill1;
	    public ModelRenderer SubQuill2;
	    public ModelRenderer plane1;
	    public ModelRenderer plane2;
	    public ModelRenderer plane3;
	    
	    public ModelQuill() {
	        this.textureWidth = 32;
	        this.textureHeight = 32;
	        this.MainQuill = new ModelRenderer(this, 0, 0);
	        this.MainQuill.setRotationPoint(0.0F, 0.0F, 0.0F);
	        this.MainQuill.addBox(-0.5F, 0.0F, -0.5F, 1, 12, 1, 0.0F);
	        this.SubQuill2 = new ModelRenderer(this, 0, 19);
	        this.SubQuill2.setRotationPoint(0.0F, -5.0F, 0.0F);
	        this.SubQuill2.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
	        this.setRotateAngle(SubQuill2, 0.0F, 0.0F, -0.08726646259971647F);
	        this.plane1 = new ModelRenderer(this, 5, 1);
	        this.plane1.setRotationPoint(0.0F, 0.0F, 0.0F);
	        this.plane1.addBox(-2.0F, 0.0F, 0.0F, 4, 10, 0, 0.0F);
	        this.plane2 = new ModelRenderer(this, 5, 14);
	        this.plane2.setRotationPoint(0.0F, 0.0F, 0.0F);
	        this.plane2.addBox(-2.0F, -5.0F, 0.0F, 4, 5, 0, 0.0F);
	        this.SubQuill1 = new ModelRenderer(this, 0, 13);
	        this.SubQuill1.setRotationPoint(0.0F, 0.0F, 0.0F);
	        this.SubQuill1.addBox(-0.5F, -5.0F, -0.5F, 1, 5, 1, 0.0F);
	        this.setRotateAngle(SubQuill1, -0.0F, 0.0F, -0.08726646259971647F);
	        this.plane3 = new ModelRenderer(this, 5, 20);
	        this.plane3.setRotationPoint(0.0F, -6.0F, 0.0F);
	        this.plane3.addBox(-2.0F, 0.0F, 0.0F, 4, 6, 0, 0.0F);
	        this.SubQuill1.addChild(this.SubQuill2);
	        this.MainQuill.addChild(this.plane1);
	        this.SubQuill1.addChild(this.plane2);
	        this.MainQuill.addChild(this.SubQuill1);
	        this.SubQuill2.addChild(this.plane3);
	    }
	    
	    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
	        modelRenderer.rotateAngleX = x;
	        modelRenderer.rotateAngleY = y;
	        modelRenderer.rotateAngleZ = z;
	    }
	    
	    public void renderQuill()
	    {
	    	Minecraft.getMinecraft().renderEngine.bindTexture(quillTexture);
	    	MainQuill.render(1f);
	    }
	}
	private static class ModelResearchBook extends ModelBase
	{

		public static final ModelResearchBook instance = new ModelResearchBook();
		private static final ModelBook bookInstance = new ModelBook();
		
		private ResourceLocation researchBookTexture = Reference.getTextureResource("models/ResearchBook.png");
		
	    public ModelResearchBook()
	    {
	    }
	    
	    public void renderResearchBook(double x, double z)
	    {
	    	int ticksExisted = Minecraft.getMinecraft().thePlayer.ticksExisted;
	    	Minecraft.getMinecraft().renderEngine.bindTexture(researchBookTexture);
	    	
	        GL11.glPushMatrix();
	        	GL11.glTranslated(0, -0.2625 - MathHelper.cos((ticksExisted) * .05F) * (float) Math.PI * 0.04F, 0);
	        	GL11.glEnable(GL11.GL_CULL_FACE);
	        	GL11.glTranslated(0, -.3, 0);
		    	GL11.glRotated(270, 0, 0, 1);
//		    	GL11.glRotated(Math.toDegrees(Math.atan2((x + .5) - Minecraft.getMinecraft().thePlayer.posX, (z + .5) - Minecraft.getMinecraft().thePlayer.posZ)) - 90, 1, 0, 0);
		    	GL11.glRotated(25, 0, 0, 1);
		    	GL11.glScaled(2,2,2);
		    	bookInstance.render((Entity)null, ticksExisted, 0f, .0f, .9f, .0f, 0.0625F);
			GL11.glPopMatrix();
	    }
	}
}