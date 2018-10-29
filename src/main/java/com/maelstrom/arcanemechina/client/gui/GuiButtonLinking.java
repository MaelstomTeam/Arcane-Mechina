package com.maelstrom.arcanemechina.client.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.maelstrom.arcanemechina.api.book.Page;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class GuiButtonLinking extends GuiButton {
	private Page linkedPage;
	public GuiButtonLinking(int buttonId, int x, int y, String buttonText, Page page) {
		super(buttonId, x, y, buttonText);
		this.width = (int)(Minecraft.getMinecraft().fontRenderer.getStringWidth(buttonText) * 0.75);
		this.height = 6;
		linkedPage = page;
	}
	
	public Page getPage()
	{
		return linkedPage;
	}
	
	public void playPressSound(SoundHandler sound)
	{
		sound.playSound(PositionedSoundRecord.getMasterRecord(GuiBook.event, 1f));
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partial)
	{
		if(this.visible)
		{
			FontRenderer fontRenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
			GlStateManager.color(1f, 1f, 1f);
			hovered =  mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
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
			float scale = 0.75f;
			GL11.glScaled(scale, scale, scale);
			fontRenderer.drawString(displayString, (int)(this.x / scale) + (this.width / 2) - this.width/2, (int)(this.y / scale) + (this.height / 2) - 6 / 2, l);
			if(!enabled)
				this.drawHorizontalLine((int)(this.x / scale) + (this.width / 2) - this.width/2, (int)(this.x / scale) + fontRenderer.getStringWidth(displayString) + 4, (int)(this.y / scale) + (this.height / 2), Color.DARK_GRAY.hashCode());
			GL11.glPopMatrix();
		}
	}
}
