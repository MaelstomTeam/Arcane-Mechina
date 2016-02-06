package com.maelstrom.armech.client.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.maelstrom.armech.ArMechMain;
import com.maelstrom.armech.common.Reference;
import com.maelstrom.snowcone.DevEnviroment;

@SuppressWarnings("all")
public class GuiBookBase extends GuiScreen
{
	public GuiBookBase()
	{
		super();
//		defaultBackground = rand.nextInt(3) + 1;
	}
	public static Random rand = new Random();
	public GuiButton  reset;
	
	public static Page page;
	public static int defaultBackground = 1;
	
	public boolean doesGuiPauseGame(){return false;}
	
	@SuppressWarnings("all")
	public void initGui()
	{
		//sets page if it hasn't been set already
		if(page == null)
			page = Page.homePage;
		else if(page == Page.index)
			Minecraft.getMinecraft().thePlayer.openGui(ArMechMain.INSTANCE, 1, Minecraft.getMinecraft().theWorld, 0, 0, 0);
		
		//clear list
		this.buttonList.clear();
		
//		create buttons
//		buttonList.add(closeButton = new GuiButton(0, 0, 0, 35, 20, "Close"));
//		buttonList.add(next = new GuiButton(1, width - 50, height - 20, 50, 20, "Next"));
//		buttonList.add(prev = new GuiButton(2, 0, height - 20, 50, 20, "Previous"));
//		buttonList.add(home = new GuiButton(3, width - 50, 0, 50, 20, "home"));
		buttonList.add(reset = new GuiButton(4, width / 2 - 55, 0, 110, 20, "RELOAD PAGE INIT()"));
	}

//	final String VERT = Util.r
	
	public void drawScreen(int mouseX, int mouseY, float partialTick)
	{
    	if(DevEnviroment.isDevEnviroment())
    	{
			Page.init();
    		GL11.glPushMatrix();
    		GL11.glScaled(.5, .5, .5);
    		drawString(Minecraft.getMinecraft().fontRendererObj, "MINECRAFT DECOMPILED WORKSPACE!" , 2, 2, Color.YELLOW.hashCode());
    		GL11.glPopMatrix();
    	}
    	
		//TODO instead of drawing default fade draw a fancy blurred background!
		//draw the default faded background
		drawDefaultBackground();
		
		//draw base background
		drawBaseBackground();
		

		//set posx & posy then translate difference
		int posx = (width - 256) / 2;
		int posy = (height - 256) / 2 + 50;
		boolean matrixStarted = false;
		try{
			GL11.glPushMatrix();
			matrixStarted = true;
			
			//perfect translation DO NOT TOUCH THIS!!
			GL11.glTranslated(posx, posy, 0);
			if(page.getDrawTitle())
				fontRendererObj.drawString(page.getTitle(), 13, 15, Color.BLACK.hashCode());
			GL11.glScaled(.5, .5, .5);
			
			List<String> lines = page.getText();
			int secondary = 0;
			for(int i = 0; i < lines.size(); i++)
			{
				if(i + secondary >= 50)
					throw new Exception("Number of lines in Page [" + page.getTitle() + "] is greater than 50!" );
				else{
					if(lines.get(i).contains("/n") && !lines.get(i).contains("<Image>"))
						for(String line : lines.get(i).split("/n"))
						{
							fontRendererObj.drawString(line, 39, 10 * (i + secondary + (i + secondary >= 25 ? -24 : 1)) + 45, Color.DARK_GRAY.hashCode());
							secondary++;
						}
					else
					{
						if(lines.get(i).contains("<Image>"))
						{
							drawImageFromText(lines.get(i));
							secondary--;
						}
						else
							fontRendererObj.drawString(lines.get(i), i + secondary >= 25 ? 270 : 39, 10 * (i + secondary + (i + secondary >= 25 ? -24 : 1)) + 45, Color.DARK_GRAY.hashCode());
					}
				}
			}
			GL11.glPopMatrix();
		}
		//incase page is a null draw the Error on gui!!
		catch(Exception e)
		{
			if(matrixStarted)
				GL11.glPopMatrix();
			GL11.glPushMatrix();
			drawDefaultBackground();
			int temp = 0;
			fontRendererObj.drawString(e.getLocalizedMessage(), 5, 28, Color.RED.hashCode());
			GL11.glScaled(.5, .5, .5);
			for(StackTraceElement part : e.getStackTrace())
				fontRendererObj.drawString(part.toString(), 20, 10 * (temp++ +3) + 45, Color.WHITE.hashCode());
			GL11.glPopMatrix();
		}
		
		this.reset.visible = isShiftKeyDown() && DevEnviroment.isDevEnviroment();
		this.reset.displayString = isCtrlKeyDown() ? "RELOAD GUI INIT()" : "RELOAD PAGE INIT()";
		
		//do noraml background stuff
		super.drawScreen(mouseX, mouseY, partialTick);
	}
	
	private void drawImageFromText(String string)
	{

		GL11.glPushMatrix();
		GL11.glColor3f(1f,1f,1f);
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		ResourceLocation image = Page.getResourceFromString(string);
		int width = (int)Page.getImageSize(string).getX();
		int height = (int)Page.getImageSize(string).getY();
		int x = (int)Page.getImageSize(string).getZ();
		int y = (int)Page.getImageSize(string).getW();
		double scale = Page.getImageScale(string);
		
		GL11.glTranslated(x, y, 0);
		GL11.glScaled(scale, scale, scale);
		
		this.mc.renderEngine.bindTexture(image);
		drawTexturedModalRect( 0, 0, 0, 0, width, height );
        GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
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
		if(page.getPageBackground() != -1)
			this.mc.renderEngine.bindTexture(new ResourceLocation(Reference.MODID + ":textures/gui/help_book_main"+page.getPageBackground()+".png"));
		else
			this.mc.renderEngine.bindTexture(new ResourceLocation(Reference.MODID + ":textures/gui/help_book_main"+defaultBackground+".png"));
		
		// FIXME Increase size rendered!!
		//draws book background
		drawTexturedModalRect(posx, posy,0,0,256,256);
		
		//end matrix
		GL11.glPopMatrix();
	}
	
	public void actionPerformed(GuiButton button)
	{
		if(button.equals(reset) && DevEnviroment.isDevEnviroment())
			if(isCtrlKeyDown())
				this.initGui();
			else
				Page.init();
	}
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		if(mouseButton == 1)
		{
			if(page.equals(Page.homePage))
				return;
			if(page.getPrevious() != null)
				page = page.getPrevious();
			else
			{
				page = Page.index;
				Minecraft.getMinecraft().thePlayer.openGui(ArMechMain.INSTANCE, 1, Minecraft.getMinecraft().theWorld, 0, 0, 0);
			}
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
			return;
		}
		else if(page.equals(Page.homePage) && mouseButton == 0)
		{
			page = Page.index;
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
			Minecraft.getMinecraft().thePlayer.openGui(ArMechMain.INSTANCE, 1, Minecraft.getMinecraft().theWorld, 0, 0, 0);
		}
		else if(mouseButton == 0)
			if(page.getNext() != null)
			{
				page = page.getNext();
				Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
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