package com.maelstrom.arcanemechina.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.maelstrom.arcanemechina.client.gui.book.Page;
import com.maelstrom.snowcone.util.Development;


public class PageAll {
		public static Map<String, Page> pages = new HashMap<String, Page>();
		public static List<Page> allPages = new ArrayList<Page>();
		public static Page homePage;
		public static Page index;
		public static Logger LOGGER = LogManager.getLogger("arcanemechina] [Page");
		public static void init()
		{
			LOGGER.info("Clearing \"pages\" list because Nadir is parranoid");
			pages.clear();
			
			
			LOGGER.info("Page [ index (HARDCODE)");
			
			index = new Page();
			index.title = "index";
			index.id = "index";
			index.prev = "home_page";
			
			//LOAD ALL PAGES FROM LIBRARY
			getPageFromFile("assets/arcanemechina/book/home.xml");
			
			//manual file additions until a creeper is made
			getPageFromFile("assets/arcanemechina/book/array/power_crystal.xml");
			getPageFromFile("assets/arcanemechina/book/array/alchemy.xml");
			getPageFromFile("assets/arcanemechina/book/array/basic.xml");
			getPageFromFile("assets/arcanemechina/book/array/crafting.xml");
			getPageFromFile("assets/arcanemechina/book/array/infusion.xml");
			getPageFromFile("assets/arcanemechina/book/array/barrier.xml");
			getPageFromFile("assets/arcanemechina/book/chalk.xml");
			getPageFromFile("assets/arcanemechina/book/array.xml");
			getPageFromFile("assets/arcanemechina/book/dust_lore.xml");
			getPageFromFile("assets/arcanemechina/book/dust_item.xml");
			getPageFromFile("assets/arcanemechina/book/introduction.xml");
			
			
			
			
			
			LOGGER.info("Initializing all pages");
			
			for(Page p : allPages)
			{
				p.initialize();
			}
			LOGGER.info("Assigning home page!");
			homePage = pages.get("home_page");
			allPages.clear();
		}
		
		public static Page getPageFromFile(String location)
		{
			try {
				LOGGER.info("     [ " + location.split("/")[location.split("/").length - 1]);
				Page pageXML = Page.load(location);
				return pageXML;
			} catch (JAXBException e)
			{
				LOGGER.error("InfoBook: Unable to load \"" + location.split("/")[location.split("/").length - 1]+ "\"");
				if(Development.isDevEnviroment())
				{
					LOGGER.error(e.toString());
				}
				LOGGER.info("InfoBook: loading>");
				return null;
			}
		}
}
