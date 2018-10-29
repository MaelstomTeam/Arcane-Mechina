package com.maelstrom.arcanemechina.api.book;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Library {
		public static Logger LOGGER = LogManager.getLogger("arcanemechina] [Page");
		private static List<Book> allBooks = new ArrayList<Book>();
		private static Map<String,Book> books = new HashMap<String,Book>();
		static String getEmpty(String string)
		{

			char[] array = new char[string.length()];
			int pos = 0;
			while (pos < string.length())
			{
				array[pos] = ' ';
				pos++;
			}
			return String.valueOf(array);
		}
		public static void init()
		{
			
			LOGGER.info("Clearing \"pages\" list because Nadir is parranoid");
			allBooks.clear();
			books.clear();
			String book_folder = "";
			String page_file = "";
			try {
				for(String curr_book : getLibraryFolders())
				{
					book_folder = curr_book;
					Book book = new Book(book_folder);
					LOGGER.info(book_folder.toUpperCase());
					for(String curr_page : getFilesInFolder(book_folder))
					{
						page_file = curr_page;
						book.getPageFromFile(page_file);
					}
				}
				
				LOGGER.info("Initializing all books");
				for(Book b : allBooks)
				{
					initializeBook(b);
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error(book_folder+"/"+page_file+ " Could not be loaded");
				return;
			}
		}
		public static void registerBook(Book book)
		{
			allBooks.add(book);
		}
		public static void initializeBook(Book book)
		{
			books.put(book.title, book);
			book.initialize();
		}
		public static Book getBook(String page_id) {
			return books.get(page_id);
		}
		public static Book[] getLibrary() {
			Book[] book = new Book[books.size()];
			return books.values().toArray(book);
		}
		
		public static List<String> getLibraryFolders() throws Exception
		{
			final String path = "../../../../../library";
			URL url = Library.class.getResource(path);
			List<String> folders = new ArrayList<String>();
			if(url == null)
				LOGGER.info("NULL");
			else
			{
				File dir = new File(url.toURI());
				for(String file : dir.list())
				{
					File currFile = new File(Library.class.getResource(path+"/"+file).toURI());
					if(currFile.isDirectory())
						folders.add(file);
				}
			}
			return folders;
		}
		public static List<String> getFilesInFolder(String book) throws Exception
		{
			if(book.isEmpty())
				return new ArrayList<String>();
			final String path = "../../../../../library/"+book;
			URL url = Library.class.getResource(path);
			List<String> files = new ArrayList<String>();
			if(url == null)
				LOGGER.info("NULL");
			else
			{
				File dir = new File(url.toURI());
				for(String file : dir.list())
				{
					File currFile = new File(Library.class.getResource(path+"/"+file).toURI());
					if(currFile.isDirectory())
						files.addAll(getFilesInFolder(book+"/"+file));
					else
						files.add(book+"/"+file);
				}
			}
			return files;
		}
}
