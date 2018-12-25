package com.maelstrom.snowcone.client.book;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.maelstrom.snowcone.SnowCone;
import com.maelstrom.snowcone.libraryAPI.Library;
import com.maelstrom.snowcone.libraryAPI.Page;
import com.maelstrom.snowcone.util.Development;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
//used only on client,
//          if the forge team has a better way to do this let me know!
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

@SuppressWarnings("deprecation")
public class GuiBookIndex extends GuiScreen {
	private GuiButton reset;
	public static GuiBookIndex INSTANCE = new GuiBookIndex();
	private static ResourceLocation bg = new ResourceLocation(SnowCone.MODID + ":textures/gui/help_book_main1.png");
	public boolean doesGuiPauseGame(){return false;}
	public GuiBookIndex()
	{
	}

	int posX;
	int posY;
	int amount = 0;
	
	public void initGui()
	{
		this.buttonList.clear();
		amount = 1;
		posX = ((width - 256) / 2) + 13;
		posY = ((height - 256) / 2) + 68;
		if(Development.isDevEnviroment())
			buttonList.add(reset = new GuiButton(1, width / 2 - 55, 0, 110, 20, "RELOAD PAGE INIT()"));
		buttonList.add(new GuiButtonLinking(2, posX, posY + getPosYForButton(), I18n.translateToLocal("book."+GuiBook.book.getPages().get("intro").id), GuiBook.book.getPages().get("intro")));
		for(Page p : GuiBook.book.getPages().values())
		{
			if(p.prev != null && !p.id.equals("intro") && p.prev.equals(GuiBook.book.index.id) && !p.id.equals(GuiBook.book.homePage.id))
				buttonList.add(new GuiButtonLinking(2, amount >= 22 ? (posX + 118) : posX, posY + getPosYForButton(), I18n.translateToLocal("book."+p.id), p));
		}
	}
	private int getPosYForButton()
	{
		return (amount++ % 22) * 6;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		drawDefaultBackground();

    	if(Development.isDevEnviroment())
    	{
    		//PageAll.init();
    		GL11.glPushMatrix();
    		GL11.glScaled(.5, .5, .5);
    		drawString(Minecraft.getMinecraft().fontRenderer, "MINECRAFT DECOMPILED WORKSPACE!", this.width-174/2, 2, Color.WHITE.hashCode());
    		drawString(Minecraft.getMinecraft().fontRenderer, "   MC_VERSION: " + MinecraftForge.MC_VERSION, 8, 2, Color.WHITE.hashCode());
    		drawString(Minecraft.getMinecraft().fontRenderer, "FORGE_VERSION: " + ForgeVersion.getVersion(), 2, 12, Color.WHITE.hashCode());
    		//drawString(Minecraft.getMinecraft().fontRenderer, mouseX+"", mouseX * 2, 22, Color.YELLOW.hashCode());
    		//drawString(Minecraft.getMinecraft().fontRenderer, mouseY+"", 22, mouseY*2, Color.YELLOW.hashCode());
    		GL11.glPopMatrix();
			this.reset.visible = isShiftKeyDown();
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
				Library.init();
		else if(button instanceof GuiButtonLinking)
		{
			GuiBook.book.page = ((GuiButtonLinking) button).getPage();
			FMLCommonHandler.instance().showGuiScreen(GuiBook.INSTANCE);
		}
	}
	
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		if(mouseButton == 1)
		{
			GuiBook.book.page = GuiBook.book.homePage;
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
		this.mc.renderEngine.bindTexture(bg);
		
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
