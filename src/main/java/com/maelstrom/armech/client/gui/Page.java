package com.maelstrom.armech.client.gui;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector4d;

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
	public static Page structureGen = new Page("World Gen");
	public static Page intro = new Page("Introduction");

	public static Page lyrics1 = new Page("Check List!");

	
	public static void init()
	{
		//ignore for now
		homePage.clearText();
		index.clearText();
		intro.clearText();
		worldGen.clearText();
		structureGen.clearText();
		//==================================================================================
		//Brandon's awful attempt at making the home and index pages!
		//==================================================================================
		
		//setup homepage
		homePage.setNext(Page.index);
		homePage.addImage("Logo.png", 256, 256, 27, 30, .85d);
		homePage.addImage("alchemy-bw.png", 256, 256, 75, 165, .5d);
		homePage.addImage("navigation.png", 256, 256, 280, 230, 1.2d);
		//inserts 25 lines in order to have a blank page might add something better for this later :p
		for(int i = 0; i < 25; i++)
			homePage.addTextLine("");
		homePage.removeTitleFromRenderer();
		//have a comment or something here!
		homePage.addTextLine("INTRO INTO ARCANE MECHINA!!");
		homePage.setNext(index);
		
		index.setPrevious(homePage); //sets the next page

		//==================================================================================
		//==================================================================================
		

		intro.addTextLine("well, this needs to be done by someone who knows the lore better then us?");
		
		//EXAMPLE!
		worldGen.addTextLine("world generation consists of"); //add a line of text
		worldGen.addTextLine("Dust crystals and so on"); //and again
		structureGen.addTextLine("You're kidding right?"); //add text to a different page!
		structureGen.removeTitleFromRenderer(); //do not render title
		structureGen.setPrevious(worldGen);//sets previous page for structureGen
		worldGen.setNext(structureGen); // sets next page for worldGen
		
		lyricsfrombatmetal(); // you see nothing NOTHING!! >.>
	}
	
	/**ignore this.. you see nothing >.>*/
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
	}
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
			next = page;
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
		/*FIXME: add handling for word wrapping!
		 * including line max length and find a way to make sure words do not overflow
		 */
		this.text.add(text);
		return this;
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