package com.maelstrom.armech.client.gui;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector4d;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

import com.maelstrom.armech.common.Reference;

public class Page
{
	@Deprecated
	public static final ArrayList<Page> allPages = new ArrayList<Page>();
	
	//do something special for this page
	public static Page homePage = new Page("homepage");
	
	//index!
	public static Page index = new Page("Index");
	public static Page worldGen = new Page("World Gen");//possibly a interlinking page
	public static Page CrystalGen = new Page("World Gen");
	public static Page intro = new Page("Introduction");
	public static Page pca = new Page("Power Crystal");
	//public static Page lyrics1 = new Page("Check List!");
	
	public static Page itemIndex = new Page("Item Index");
	public static Page dust = new Page("-------§1§lDust§r-------");

	public static Page arrays = new Page("Array Crafting");
	
	public static void init()
	{
		//ignore for now
		homePage.clearText();
		index.clearText();
		intro.clearText();
		worldGen.clearText();
		CrystalGen.clearText();
		
		itemIndex.clearText();
		dust.clearText();
		arrays.clearText();
		pca.clearText();
		
		arrays.setNext(pca);
		arrays.addTextLine("- §1Power Crystal");
		pca.addImage("powerCrystalCrafting.png", 256, 256, 270, 80, .8d);
		pca.addTextLine("using chalk, a redstone block, four item frames (containing §4fire, §1water, §6air §rand §7light §rdust crystals)");
		pca.addTextLine();
		pca.addTextLine("place a redstone block in the center then proceed to draw four arrows each being a 3x3 in size in formation of image to right. once done place an item frame on each side of the redstone block containing one crystal of each type descrubed above, this should complete the array.");
		for(int i = 0; i < 8; i++)
			pca.addTextLine();
		pca.addTextLine("§4Side Note§r: apparently this array can only be activated by crouching and using a drawing chalk on it. This is a very strange way of activation but seems to be the only way to make this work.");
		//==================================================================================
		//Brandon's awful attempt at making pages!
		//==================================================================================
		
		//setup homepage
		homePage.setNext(Page.index);
		homePage.addImage("Logo.png", 256, 256, 27, 30, .85d);
		homePage.addImage("alchemy-bw.png", 256, 256, 75, 165, .5d);
		homePage.addImage("navigation.png", 256, 256, 280, 230, 1.2d);
		//inserts 25 lines in order to have a blank page might add something better for this later :p
		for(int i = 0; i < 25; i++)
			homePage.addTextLine();
		homePage.removeTitleFromRenderer();
		//have a comment or something here!
		homePage.addTextLine("Thank you for using §o§lArcane Mechina§r!");
		homePage.addTextLine("In advance we would like to appologise for the §nvery little documentation§r we have on what things do as we are still figuring out how it all works together.");
		homePage.addTextLine("- §oMaelstrom Mod Team");
		homePage.setNext(index);
		
		//itemIndex page
		itemIndex.setNext(dust);
		itemIndex.addTextLine("- §1Dust§r");
		
		
		dust.addImage("dust.png", 256, 256, 300, 150, .45d);
		dust.addTextLine("§l§1Dust§r, is an source of elemental energy found within this New World.");
		dust.addTextLine();
		dust.addTextLine("§l§1Dust Crystals§r if ignited forms small explosion relitive to the elemental properties it's form of, this potential energy can be increase in a powdered state as well.");
		dust.addTextLine();
		dust.addTextLine("Pure forms of §l§1Dust Crystals§r can be harvested natrually but it's a rare occurrence.");
		dust.addTextLine();
		dust.addTextLine("Some users of §l§1Dust§r have shown it's usefulness as a source of power and storage cell as it stands some forms of crystals have the potential to store large amounts of engergy, although if not maintained can result in an explosive outcome! Another use of dust has been in propelent, circutry and more importanly runes.");
		dust.addTextLine();
		dust.addTextLine();
		dust.addTextLine();
		dust.addTextLine("NOTE: In it's powdered state some forms of §l§1Dust§r has shown properties similiar to fertilize increase growth of plantlife as well as being an explosive.");
		dust.addTextLine("This gives it both cultural and militaristic potential!");
		//==================================================================================
		//==================================================================================
		

		intro.addTextLine("well, this needs to be done by someone, but who knows the lore better then us?");
		
		//EXAMPLE!
		worldGen.addTextLine("world generation consists of Dust crystals and so on"); //add a line of text
		CrystalGen.addTextLine("Dust Crystal lore that contains information about its harest time and spawn levels and drops"); //add text to a different page!
		CrystalGen.addImage("dustCrystal.png", 256, 256, 250, 15, .94d);
		CrystalGen.removeTitleFromRenderer();
		CrystalGen.setPrevious(worldGen);//sets previous page for structureGen
		worldGen.setNext(CrystalGen); // sets next page for worldGen
		
	//	lyricsfrombatmetal(); // you see nothing NOTHING!! >.>
	}
	/**ignore this.. you see nothing >.>*/
	/*
	public static void lyricsfrombatmetal()
	{
		lyrics1.clearText();
		index.setNext(lyrics1);
		lyrics1.setPrevious(homePage);
		lyrics1.addTextLine("[✔] Knives");
		lyrics1.addTextLine("[✔] Rope");
		lyrics1.addTextLine("[✔] Dagger");
		lyrics1.addTextLine("[✔] Chains");
		lyrics1.addTextLine("[✔] Rock");
		lyrics1.addTextLine("[✔] Laser Beams");
		lyrics1.addTextLine("[✔] Acid");
		lyrics1.addTextLine("[✔] Body bag?");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("");
		lyrics1.addTextLine("\"There are no fingerprints");
		lyrics1.addTextLine("deep under water");
		lyrics1.addTextLine("Nothing to tie one to a crime");
		lyrics1.addTextLine("And if you seek vengeance");
		lyrics1.addTextLine("All you need are instruments of pain\"");
		lyrics1.addTextLine("- Dethklok Murmaider");
	}*/
	/**SUPER BASIC CONSTRUCTOR FTW*/
	public Page(String title)
	{
		this.title = title;
	}
	/**set's the previous page of the current page*/
	public Page setPrevious(Page page)
	{
		if(page == this)
			try {
				throw new Exception("linking to self is not allowed");
			} catch (Exception e) {
				e.printStackTrace();
			}
		else
			previous = page;
		return this;
	}
	/**set's the next page of the current page*/
	public Page setNext(Page page)
	{
		if(page == this)
			try {
				throw new Exception("linking to self is not allowed");
			} catch (Exception e) {
				e.printStackTrace();
			}
		else
		{
			next = page;
			next.setPrevious(this);
		}
		return this;
	}
	
	@Deprecated
	public Page setUpIndex(Page page)
	{
		if(page == this)
			try {
				throw new Exception("linking to self is not allowed");
			} catch (Exception e) {
				e.printStackTrace();
			}
		else
			upPage = page;
		return this;
	}
	/**DO NOT USE UNLESS CLEARING THE TEXT ARRAY IS SOMETHING YOU WANT!?*/
	public Page clearText()
	{
		text.clear();
		return this;
	}
	/**Adds a new line of text to the array*/
	public Page addTextLine(String text)
	{
		FontRenderer font = Minecraft.getMinecraft().fontRendererObj;
		if(font != null && text != null && text.replace(" ", "") != "")
		{
			String[] temp = text.split(" ");
			String temp2 = "";
			int temp3 = 0;
			for(String word : temp)
			{
				temp3++;
				if(font.getStringWidth(temp2+ " " + word) > 212)
				{
					this.text.add(temp2);
					temp2 = word;
				}
				else
					if(temp3 == 1)
						temp2 += word;
					else
						temp2 += " " + word;
				if((temp.length == temp3))
					this.text.add(temp2);
			}
		}
		else
			this.text.add(text);
		return this;
	}
	public Page addTextLine()
	{
		return addTextLine("");
	}
	/**stops the title from rendering*/
	public Page removeTitleFromRenderer()
	{
		this.drawTitle = false;
		return this;
	}
	/**temporary solution to the different backgrounds per page issue*/
	public Page setPageBackground(int backgroundIndex)
	{
		pageBackgorund = backgroundIndex;
		return this;
	}
	
	//=============================================================================
	//USED BY GUI SO I THOUGHT I WOULDN'T NEED TO DOCUMENT THESE FOR YOU TRAVIS! <3
	//=============================================================================
	

	private int pageBackgorund = -1;
	
	//page title! DUH!
	private String title;
	
	private ArrayList<String> text = new ArrayList<String>(); //FIXME: find way to wrap around pages
	
	//Self Explanatory!
	private Page previous;
	private Page next;
	
	//FIXME: NOT IMPLEMENTED!!
	//basically going up a directory
	private Page upPage;
	
	private boolean drawTitle = true;
	
	
	//FIXME: FIND WAY TO IMPLMENT BUTTONS!!
	//private ArrayList<GuiButton> buttonlist = new ArrayList<GuiButton>();
	

	public int getPageBackground()
	{
		return pageBackgorund;
	}
	public Page getNext()
	{
		return next;
	}
	public Page getUp()
	{
		return upPage == null ? homePage : upPage;
	}
	public Page getPrevious()
	{
		return previous;
	}
	public String getTitle()
	{
		if(this != homePage)
			return title;
		return "";
	}
	public boolean getDrawTitle() {
		return drawTitle;
	}
	public List<String> getText() {
		return text;
	}
	public static ResourceLocation getResourceFromString(String string)
	{
		String temp;
		if(string.contains("<Image>"))
		{
			temp = string.replace("<Image>", "");
			String[] temp2 = temp.split(",");
			return new ResourceLocation(temp2[0]);
		}
		return null;
	}
	public static Vector4d getImageSize(String string)
	{
		String temp;
		if(string.contains("<Image>"))
		{
			temp = string.replace("<Image>", "");
			String[] temp2 = temp.split(",");
			return new Vector4d(Integer.parseInt(temp2[1]), Integer.parseInt(temp2[2]), Integer.parseInt(temp2[3]), Integer.parseInt(temp2[4]));
		}
		return new Vector4d(256,256,0,0);
	}
	public static double getImageScale(String string)
	{
		String temp;
		if(string.contains("<Image>"))
		{
			temp = string.replace("<Image>", "");
			String[] temp2 = temp.split(",");
			return Double.parseDouble(temp2[5]);
		}
		return 1d;
	}
	/**
	 * width and height is being worked on for now defaults to 256
	 * @param imageLocation - the location of the image within "textures/gui/pages"n
	 * @param scale, the sale of the object (best use is to scale down small images and scale up big ones
	 * 
	 */
	public Page addImage(String imageLocation, int width, int height, int x, int y, double scale)
	{
		width = 256;
		height = 256;
		//FIXME: add a way to add width and height to images properly
		this.text.add("<Image>" + Reference.MODID + ":" + "textures/gui/pages/" + imageLocation + "," + width + "," + height + "," + x + "," + y + "," + scale);
		return this;
	}
	
}