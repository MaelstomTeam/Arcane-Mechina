package com.maelstrom.arcanemechina.client.gui.book;

import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.maelstrom.arcanemechina.client.gui.PageAll;

@XmlRootElement(name = "Page")
//@XmlAccessorType(XmlAccessType.PROPERTY)
public class Page
{
    
    public Page(){
    	PageAll.allPages.add(this);
    }
    public Page(String title, List<String> text, List<Image> image)
    {
    	this.title = title;
    	this.text = text;
    	this.image = image;
    	PageAll.pages.put(id == null ? title : id,this);
    }
    @XmlElement(name = "title")
	public String title;
	@XmlElement(name = "titleRender")
	public boolean titleRender = true;
	@XmlElement(name = "text")
	public List<String> text;
	@XmlElement(name = "Image")
	public List<Image> image;
	@XmlElement(name = "id")
	public String id;
	@XmlElement(name = "next")
	public String next;
	@XmlElement(name = "prev")
	public String prev;
	@XmlElement(name = "pageBackground")
	public String pageBackground = "help_book_main1.png";
	
	public void initialize()
	{
		if(id == null)
			PageAll.LOGGER.info("     [ Page ID is null! page key set to  title \""+title+"\"");
		else
			PageAll.LOGGER.info("     [ Page ID available! setting key to Page ID \""+id+"\"");
    	PageAll.pages.put(id == null ? title : id,this);
	}
	
	public static Page load(String location) throws JAXBException
	{
		ClassLoader CLDR = Page.class.getClassLoader();
		InputStream inStrm = CLDR.getResourceAsStream(location);
		JAXBContext jaxbContext = JAXBContext.newInstance(Page.class);
		Unmarshaller jaxbu = jaxbContext.createUnmarshaller();
		Page page = (Page) jaxbu.unmarshal(inStrm);
		return page;
	}
}
