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
	
	public static void init()
	{
		//ignore for now
		homePage.clearText();
		index.clearText();
		
		homePage.setNext(Page.index);
		homePage.addImage("Logo.png", 256, 256, 27, 30, .85d);
		homePage.addImage("alchemy-bw.png", 256, 256, 75, 165, .5d);
		
		//inserts 25 lines in order to have a blank page
		for(int i = 0; i < 25; i++)
			homePage.addTextLine("");
		homePage.removeTitleFromRenderer();
		
		//have something a little more.. Intro-y
		homePage.addTextLine("INTRO INTO ARCANE MECHINA!!");
		homePage.setNext(index);
		
		index.addTextLine("some fancy text about the mod that/ndoesn't break immersion!!");
		index.setPrevious(homePage);
	}
	
	public Page(String title)
	{
		this.title = title;
	}
	
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
	public Page clearText()
	{
		text.clear();
		return this;
	}
	public Page addTextLine(String text)
	{
		this.text.add(text);
		return this;
	}
	public Page removeTitleFromRenderer()
	{
		this.drawTitle = false;
		return this;
	}
	
	//=============================================================================
	//USED BY GUI SO I THOUGHT I WOULDN'T NEED TO DOCUMENT THESE FOR YOU TRAVIS! <3
	//=============================================================================
	
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