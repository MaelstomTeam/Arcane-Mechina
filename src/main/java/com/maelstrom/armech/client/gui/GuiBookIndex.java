package com.maelstrom.armech.client.gui;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.maelstrom.armech.ArMechMain;
import com.maelstrom.armech.common.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;


//Brandon got a little annoyed and added this >.>
@SuppressWarnings("all")
public class GuiBookIndex extends GuiScreen
{
	private GuiButton reset;

	public boolean doesGuiPauseGame(){return false;}
	
	public void initGui()
	{
		GUIBookBase.page = Page.homePage;
		this.buttonList.clear();
		amount = 1;
		int posx = (width) / 2 - 120;
		int posy = (height) / 2 - 50;
		
		//buttonList.add(home = new GuiButton(0, width - 50, 0, 50, 20, "home"));
		buttonList.add(reset = new GuiButton(1, width / 2 - 55, 0, 110, 20, "RELOAD PAGE INIT()"));
		
		
		
		buttonList.add(new GuiButtonLinking(2, posx, posy,"Introduction", Page.intro));
		buttonList.add(new GuiButtonLinking(2, posx, posy + getPosYForButton(),"World Generation", Page.worldGen));
	}
	int amount = 1;
	public int getPosYForButton()
	{
		return amount++ * 14;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
    	if(Item.class.getCanonicalName() == "net.minecraft.item.Item")
    	{
    		GL11.glPushMatrix();
    		GL11.glScaled(.5, .5, .5);
    		drawString(Minecraft.getMinecraft().fontRendererObj, "MINECRAFT DECOMPILED WORKSPACE!" , 2, 2, Color.YELLOW.hashCode());
    		GL11.glPopMatrix();
    	}
		drawDefaultBackground();
		drawBaseBackground();
		int posx = (width - 256) / 2;
		int posy = (height - 256) / 2 + 50;
		
		//draw otherstuff!
		GL11.glPushMatrix();
		GL11.glTranslated(posx, posy, 0);
		fontRendererObj.drawString("INDEX", 13, 15, Color.BLACK.hashCode());
		GL11.glPopMatrix();
		
		this.reset.visible = isShiftKeyDown() && Reference.isDecompVersion();
		this.reset.displayString = isCtrlKeyDown() ? "RELOAD GUI INIT()" : "RELOAD PAGE INIT()";
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void actionPerformed(GuiButton button)
	{
		if(button.equals(reset) && Reference.isDecompVersion())
			if(isCtrlKeyDown())
				this.initGui();
			else
				Page.init();
		else if(button instanceof GuiButtonLinking)
		{
			GUIBookBase.page = ((GuiButtonLinking) button).getPage();
			Minecraft.getMinecraft().thePlayer.openGui(ArMechMain.INSTANCE, 0, Minecraft.getMinecraft().theWorld, 0, 0, 0);
		}
	}
	
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		if(mouseButton == 1)
		{
			GUIBookBase.page = Page.homePage;
			Minecraft.getMinecraft().thePlayer.openGui(ArMechMain.INSTANCE, 0, Minecraft.getMinecraft().theWorld, 0, 0, 0);
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
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
		this.mc.renderEngine.bindTexture(new ResourceLocation(Reference.MODID + ":textures/gui/help_book_main"+GUIBookBase.defaultBackground+".png"));
		
		// FIXME Increase size rendered!!
		//draws book background
		drawTexturedModalRect(posx, posy,0,0,256,256);
		
		//end matrix
		GL11.glPopMatrix();
	}
}
