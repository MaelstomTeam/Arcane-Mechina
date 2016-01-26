package com.maelstrom.armech.client.gui;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
@SuppressWarnings("all")
public class GuiButtonLinking extends GuiButton {

	private Page linkedPage;
	public GuiButtonLinking(int buttonId, int x, int y, String buttonText, Page page) {
		super(buttonId, x, y, buttonText);
		this.width = 100;
		this.height = 10;
//		this.xPosition = x / 2;
//		this.yPosition = y / 2;
		linkedPage = page;
	}
	
	public Page getPage()
	{
		return linkedPage;
	}
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY)
	{
		if(this.visible)
		{
			FontRenderer fontRenderer = mc.fontRendererObj;
			bindTexture(buttonTextures);
			GlStateManager.color(1f, 1f, 1f);
			hovered =  ((mouseX >= this.xPosition) && (mouseY >= this.yPosition) && (mouseX < this.xPosition + this.width) && (mouseY < this.yPosition + this.height));
			int k = getHoverState(hovered);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GlStateManager.blendFunc(770, 771);
			mouseDragged(mc,mouseX,mouseY);
			int l = Color.GRAY.hashCode();
			if(packedFGColour != 0)
				l = packedFGColour;
			else if(!enabled)
				l = Color.LIGHT_GRAY.hashCode();
			else if (hovered)
				l = Color.DARK_GRAY.hashCode();
			GL11.glPushMatrix();
			//for debug purposes
			if(false)
				drawTexturedModalRect((this.xPosition), (this.yPosition), 0, 46 + k * 20, this.width, this.height);
			fontRenderer.drawString(displayString, (this.xPosition) + (this.width / 2) - 45, (this.yPosition) + (this.height / 2) - 6 / 2, l);
			if(!enabled)
				this.drawHorizontalLine((this.xPosition) + (this.width / 2) - 46, this.xPosition + fontRenderer.getStringWidth(displayString) + 4, (this.yPosition) + (this.height / 2), Color.DARK_GRAY.hashCode());
			GL11.glPopMatrix();
		}
	}
	
	public void bindTexture(ResourceLocation location)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(location);
	}
}
