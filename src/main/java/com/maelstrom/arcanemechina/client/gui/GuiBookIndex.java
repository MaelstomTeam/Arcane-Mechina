package com.maelstrom.arcanemechina.client.gui;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.maelstrom.arcanemechina.ArcaneMechina;
import com.maelstrom.arcanemechina.client.gui.book.Page;
import com.maelstrom.snowcone.util.Development;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class GuiBookIndex extends GuiScreen {
	private GuiButton reset;
	public static GuiBookIndex INSTANCE = new GuiBookIndex();
	public boolean doesGuiPauseGame(){return false;}
	public GuiBookIndex()
	{
	}

	int posX;
	int posY;
	int amount = 0;
	
	@SuppressWarnings("deprecation")
	public void initGui()
	{
		this.buttonList.clear();
		amount = 1;
		posX = ((width - 256) / 2) + 13;
		posY = ((height - 256) / 2) + 68;
		if(Development.isDevEnviroment())
			buttonList.add(reset = new GuiButton(1, width / 2 - 55, 0, 110, 20, "RELOAD PAGE INIT()"));
		buttonList.add(new GuiButtonLinking(2, posX, posY + getPosYForButton(), I18n.translateToLocal("book."+PageAll.pages.get("intro").id), PageAll.pages.get("intro")));
		for(Page p : PageAll.pages.values())
		{
			if(p.prev != null && !p.id.equals("intro") && p.prev.equals(PageAll.index.id) && !p.id.equals(PageAll.homePage.id))
				buttonList.add(new GuiButtonLinking(2, amount >= 22 ? (posX + 118) : posX, posY + getPosYForButton(), I18n.translateToLocal("book."+p.id), p));
		}
	}
	public int getPosYForButton()
	{
		return (amount++ % 22) * 6;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		drawDefaultBackground();
    	if(Development.isDevEnviroment())
    	{
    		GL11.glPushMatrix();
    		GL11.glScaled(.5, .5, .5);
    		drawString(Minecraft.getMinecraft().fontRenderer, "MINECRAFT DECOMPILED WORKSPACE!" , 2, 2, Color.DARK_GRAY.hashCode());
    		GL11.glPopMatrix();
			this.reset.visible = isShiftKeyDown() && Development.isDevEnviroment();
			this.reset.displayString = isCtrlKeyDown() ? "RELOAD GUI INIT()" : "RELOAD PAGE INIT()";
    	}
		drawBaseBackground();
		int posx = (width - 256) / 2;
		int posy = (height - 256) / 2 + 50;
		
		//draw otherstuff!
		GL11.glPushMatrix();
		GL11.glTranslated(posx, posy, 0);
		fontRenderer.drawString("INDEX", 13, 15, Color.BLACK.hashCode());
		GL11.glPopMatrix();
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void actionPerformed(GuiButton button)
	{
		if(button.equals(reset) && Development.isDevEnviroment())
			if(isCtrlKeyDown())
				this.initGui();
			else
				PageAll.init();
		else if(button instanceof GuiButtonLinking)
		{
			GuiBook.page = ((GuiButtonLinking) button).getPage();
			FMLCommonHandler.instance().showGuiScreen(GuiBook.INSTANCE);
		}
	}
	
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		if(mouseButton == 1)
		{
			GuiBook.page = PageAll.homePage;
			FMLCommonHandler.instance().showGuiScreen(GuiBook.INSTANCE);
			Minecraft.getMinecraft().player.playSound(GuiBook.event, 1f, 1f);
			return;
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	public void drawBaseBackground()
	{
		
		//start separate matrix
		GL11.glPushMatrix();
		
		//set color to white
		GL11.glColor3f(1f, 1f, 1f);
		
		//get center
		int posx = (width - 256) / 2;
		int posy = (height - 256) / 2 + 50;
		
		//bind texture
		this.mc.renderEngine.bindTexture(new ResourceLocation(ArcaneMechina.MODID + ":textures/gui/help_book_main"+GuiBook.defaultBackground+".png"));
		
		//draws book background
		drawTexturedModalRect(posx, posy,0,0,256,256);
		
		//end matrix
		GL11.glPopMatrix();
	}
	public void keyTyped(char typedChar, int keyCode) throws IOException
	{
		if(typedChar == 'e')
			keyTyped((char)1, 1);
		super.keyTyped(typedChar, keyCode);
	}
}