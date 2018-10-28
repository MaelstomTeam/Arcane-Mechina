package com.maelstrom.arcanemechina.client.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.maelstrom.arcanemechina.ArcaneMechina;
import com.maelstrom.arcanemechina.client.gui.book.Image;
import com.maelstrom.arcanemechina.client.gui.book.Page;
import com.maelstrom.snowcone.util.Development;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import scala.actors.Debug;

public class GuiBook extends GuiScreen {
	
	public static GuiBook INSTANCE = new GuiBook();
	public static ResourceLocation location = new ResourceLocation(ArcaneMechina.MODID,"sfx.page");
	public static SoundEvent event = new SoundEvent(location);
	public GuiBook()
	{
		super();
	}
	public static Random rand = new Random();
	public GuiButton reset;
	
	public static Page page;
	public static int defaultBackground = 1;
	
	public boolean doesGuiPauseGame(){return false;}
	
	@SuppressWarnings("all")
	public void initGui()
	{
		//sets page if it hasn't been set already
		if(page == null)
			page = PageAll.index;
		//force correct gui for index
		else if(page == PageAll.index) {
			FMLCommonHandler.instance().showGuiScreen(GuiBookIndex.INSTANCE);
		}		
		//clear list
		this.buttonList.clear();

		if(Development.isDevEnviroment())
			buttonList.add(reset = new GuiButton(4, width / 2 - 55, 0, 110, 20, "RELOAD PAGE INIT()"));
	}

//	final String VERT = Util.r
	
	public void drawScreen(int mouseX, int mouseY, float partialTick)
	{
		//draw the default faded background
		drawDefaultBackground();
    	if(Development.isDevEnviroment())
    	{
    		//PageAll.init();
    		GL11.glPushMatrix();
    		GL11.glScaled(.5, .5, .5);
    		drawString(Minecraft.getMinecraft().fontRenderer, "MINECRAFT DECOMPILED WORKSPACE!" , 2, 2, Color.DARK_GRAY.hashCode());
    		drawString(Minecraft.getMinecraft().fontRenderer, "Why hello there, it appears that this is minecraft version 1.12.2! How'd that happen?!" , 2, 12, Color.DARK_GRAY.hashCode());
    		GL11.glPopMatrix();
    	}
    	
		//draw base background
		drawBaseBackground();
		

		int posx = (width - 256) / 2;
		int posy = (height - 256) / 2 + 50;
		boolean matrixStarted = false;
		try{
			if(page == null)
				return;
			GL11.glPushMatrix();
			matrixStarted = true;
			GL11.glTranslated(posx, posy, 0);
			if(page.titleRender)
				fontRenderer.drawString(page.title, 13, 15, Color.BLACK.hashCode());
			GL11.glScaled(.5, .5, .5);
			
			List<String> lines = page.text;
			int secondary = 0;
			int lineSize = 23;
			int width = 210;
			if(lines != null)
				for(int i = 0; i < lines.size(); i++)
				{
					if(i + secondary >= 50)
						throw new Exception("Number of lines in Page [" + page.title + "] is greater than 50!" );
					else
					{
						if(fontRenderer.getStringWidth(lines.get(i)) >= width)
						{
							for(String s : fontRenderer.listFormattedStringToWidth(lines.get(i), width))
							{
								fontRenderer.drawString(s, i + secondary >= lineSize ? 270 : 39, 11 * (i + secondary + (i + secondary >= lineSize ? -25 : 1)) + 45,0);
								secondary++;
							}
						}
						else
						{
							fontRenderer.drawString(lines.get(i), i + secondary >= lineSize ? 270 : 39, 11 * (i + secondary + (i + secondary >= lineSize ? -25 : 1)) + 45,0);
						}
					}
				}
			if(page.image != null)
				for(Image img : page.image)
				{
					Debug.info(img.source);
					GL11.glPushMatrix();
					GL11.glColor3f(1f,1f,1f);
			    	GL11.glEnable(GL11.GL_BLEND);
			    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					ResourceLocation image = new ResourceLocation(ArcaneMechina.MODID,"textures/gui/pages/"+img.source);
					GL11.glTranslated(img.x,img.y, 0d);
					GL11.glScaled(img.scale, img.scale, img.scale);
					
					this.mc.renderEngine.bindTexture(image);
					drawTexturedModalRect( 0, 0, 0, 0, img.width, img.height );
			        GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();
				}
			GL11.glPopMatrix();
		}
		//draw the Error on gui
		catch(Exception e)
		{
			if(matrixStarted)
				GL11.glPopMatrix();
			GL11.glPushMatrix();
			drawDefaultBackground();
			int temp = 0;
			fontRenderer.drawString(e.getLocalizedMessage(), 5, 28, Color.RED.hashCode());
			GL11.glScaled(.5, .5, .5);
			for(StackTraceElement part : e.getStackTrace())
				fontRenderer.drawString(part.toString(), 20, 10 * (temp++ +3) + 45, Color.WHITE.hashCode());
			GL11.glPopMatrix();
		}
		if(Development.isDevEnviroment()) {
			this.reset.visible = isShiftKeyDown() && Development.isDevEnviroment();
			this.reset.displayString = isCtrlKeyDown() ? "RELOAD GUI INIT()" : "RELOAD PAGE INIT()";
		}
		//do normal background stuff
		super.drawScreen(mouseX, mouseY, partialTick);
	}
	

	/*
	 * draws regular page background
	 */
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
		this.mc.renderEngine.bindTexture(new ResourceLocation(ArcaneMechina.MODID,"textures/gui/"+page.pageBackground));
		
		//draws book background
		drawTexturedModalRect(posx, posy,0,0,256,256);
		
		//end matrix
		GL11.glPopMatrix();
	}
	
	public void actionPerformed(GuiButton button)
	{
		if(Development.isDevEnviroment() && button.equals(reset))
			if(isCtrlKeyDown())
				this.initGui();
			else
				PageAll.init();
	}
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		try {
			if(mouseButton == 1)
			{
				if(page.prev != null)
				{
					if(page.equals(PageAll.homePage))
						return;
					if(PageAll.pages.containsKey(page.prev))
					{
						page = PageAll.pages.get(page.prev);
						Minecraft.getMinecraft().player.playSound(GuiBook.event, 1f, 1f);
						FMLCommonHandler.instance().showGuiScreen(GuiBook.INSTANCE);
					}
				}
				else
				{
					page = PageAll.index;
					Minecraft.getMinecraft().player.playSound(GuiBook.event, 1f, 1f);
					FMLCommonHandler.instance().showGuiScreen(GuiBookIndex.INSTANCE);
				}
				return;
			}
			else if(page != null && page.equals(PageAll.homePage) && mouseButton == 0)
			{
				page = PageAll.index;
				Minecraft.getMinecraft().player.playSound(GuiBook.event, 1f, 1f);
				FMLCommonHandler.instance().showGuiScreen(GuiBookIndex.INSTANCE);
			}
			else if(mouseButton == 0)
			{
				if(page != null && page.next != null)
				{
					if(PageAll.pages.containsKey(page.next))
					{
						page = PageAll.pages.get(page.next);
						Minecraft.getMinecraft().player.playSound(GuiBook.event, 1f, 1f);
						FMLCommonHandler.instance().showGuiScreen(GuiBook.INSTANCE);
					}
				}
			}else if(page == null)
			{
				page = PageAll.index;
				Minecraft.getMinecraft().player.playSound(GuiBook.event, 1f, 1f);
				FMLCommonHandler.instance().showGuiScreen(GuiBook.INSTANCE);
			}
		}
		catch(Exception e)
		{
			
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	public void keyTyped(char typedChar, int keyCode) throws IOException
	{
		if(typedChar == 'e')
			keyTyped((char)1, 1);
		super.keyTyped(typedChar, keyCode);
	}
}
