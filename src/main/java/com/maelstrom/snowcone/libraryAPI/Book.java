package com.maelstrom.snowcone.libraryAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import com.maelstrom.snowcone.util.Development;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Book {
	private Map<String, Page> pages = new HashMap<String, Page>();
	private List<Page> allPages = new ArrayList<Page>();
	public Page homePage;
	public Page index = new Page("index",this).setPrev("home_page");
	public String title;
	public boolean firstTimeOpening = true;
	
	public Page page;
	public Book(String title)
	{
		this.title = title;
		Library.registerBook(this);
	}
	
	public void addToPages(Page page)
	{
		pages.put(page.id == null ? page.title : page.id, page);
	}
	
	public void addToAllPages(Page page)
	{
		allPages.add(page);
	}
	public Map<String, Page> getPages()
	{
		return pages;
	}
	public void initialize()
	{
		allPages.add(index);
		for(Page p : allPages)
		{
			if(p.book == null)
				p.book = this;
			p.initialize();
		}
		Library.LOGGER.info("     [ Assigning home page to " + title + "!");
		homePage = pages.get("home_page");
		allPages.clear();
	}
	public void clear()
	{
		pages.clear();
		allPages.clear();
	}
	@SideOnly(Side.CLIENT)
	public Page getPageFromFile(String location)
	{
		try {
			Library.LOGGER.info("     [ " + location.split("/")[location.split("/").length - 1]);
			Page pageXML = Page.load("library/"+location);
			pageXML.book = this;
	    	addToAllPages(pageXML);
			return pageXML;
		} catch (JAXBException e)
		{
			Library.LOGGER.error("InfoBook: Unable to load \"" + location.split("/")[location.split("/").length - 1]+ "\"");
			if(Development.isDevEnviroment())
			{
				Library.LOGGER.error(e.toString());
			}
			Library.LOGGER.info("InfoBook: loading>");
			return null;
		}
	}
}
