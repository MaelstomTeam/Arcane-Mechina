package com.maelstrom.arcanemechina.api.book;

import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Page")
public class Page
{
    public Book book;
    public Page(){
    }
    public Page(String title, List<String> text, List<Image> image, Book book)
    {
    	book.addToAllPages(this);
    	this.title = title;
    	this.text = text;
    	this.image = image;
    }
    public Page(String title) {
    	book.addToPages(this);
    	this.title = title;
    	this.id = title;
	}
	public Page(String title, Book book2) {
		book = book2;
		book2.addToPages(this);
    	this.title = title;
    	this.id = title;
	}
	public String title;
	public boolean titleRender = true;
	public List<String> text;
	//unimplemented
	public List<String> button;
	@XmlElement(name = "Image")
	public List<Image> image;
	public String id;
	public String next;
	public String prev;
	public String pageBackground = "help_book_main1.png";
	
	public void initialize()
	{
		if(id == null)
			Library.LOGGER.info("     [ Page ID is null! \""+title+"\"");
		else
			Library.LOGGER.info("     [ Page ID available! \""+id+"\"");
		book.addToPages(this);
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
	public Page setPrev(String previous) {
		this.prev = previous;
		return this;
	}
}
