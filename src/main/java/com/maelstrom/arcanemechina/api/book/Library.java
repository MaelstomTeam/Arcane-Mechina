package com.maelstrom.arcanemechina.api.book;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Library {
	public static Logger LOGGER = LogManager.getLogger("arcanemechina] [Page");
	private static List<Book> allBooks = new ArrayList<Book>();
	private static Map<String, Book> books = new HashMap<String, Book>();

	static String getEmpty(String string) {

		char[] array = new char[string.length()];
		int pos = 0;
		while (pos < string.length()) {
			array[pos] = ' ';
			pos++;
		}
		return String.valueOf(array);
	}

	public static void init() {

		LOGGER.info("Clearing \"pages\" list because Nadir is parranoid");
		allBooks.clear();
		books.clear();
		String book_folder = "";
		String page_file = "";
		try {
			for (String curr_book : getLibraryFolders()) {
				book_folder = curr_book;
				Book book = new Book(book_folder);
				LOGGER.info(book_folder.toUpperCase());
				for (String curr_page : getFilesInFolder(book_folder)) {
					page_file = curr_page;
					book.getPageFromFile(page_file);
				}
			}

			LOGGER.info("Initializing all books");
			for (Book b : allBooks) {
				initializeBook(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(book_folder + "/" + page_file + " Could not be loaded");
			return;
		}
	}

	public static void registerBook(Book book) {
		allBooks.add(book);
	}

	public static void initializeBook(Book book) {
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

	public static String[] getLibraryFolders() throws Exception {
		String path = "library/";
		URL url = Library.class.getClassLoader().getResource(path);
		List<String> folders = new ArrayList<String>();
		if (url == null) {
			LOGGER.info("NULL");
		} else {
			if (url.getProtocol().equals("jar")) {
				String jarPath = url.getPath().substring(5, url.getPath().indexOf(33));
				LOGGER.info(jarPath);
				JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
				Enumeration<JarEntry> entries = jar.entries();
				HashSet<String> result = new HashSet<String>();
				while (entries.hasMoreElements()) {
					JarEntry entry_ = entries.nextElement();
					String name = entry_.getName();
					if (!name.startsWith(path))
						continue;
					String entry = name.substring(path.length());
					int checkSubDir = entry.indexOf("/");
					if (checkSubDir >= 0) {
						entry = entry.substring(0, checkSubDir);
					}
					if (entry.length() <= 2 || entry.equals(""))
						continue;
					entry.substring(1);
					if (entry.indexOf("/") != -1)
						continue;
					result.add(entry);
				}
				return result.toArray(new String[result.size()]);
			} else {
				path = "../../../../../library";
				url = Library.class.getResource(path);
				File dir = new File(url.toURI());
				for (String file : dir.list()) {
					File currFile = new File(Library.class.getResource(path + "/" + file).toURI());
					if (currFile.isDirectory())
						folders.add(file);
				}
			}
		}
		return (String[]) folders.toArray(new String[folders.size()]);
	}

	public static String[] getFilesInFolder(String book) throws Exception {
		if (book.isEmpty()) {
			return new String[0];
		}
		String path = "library/" + book + "/";
		URL url = Library.class.getClassLoader().getResource(path);
		ArrayList<String> files = new ArrayList<String>();
		if (url == null) {
			LOGGER.info("NULL");
		} else {
			if (url.getProtocol().equals("jar")) {
				String jarPath = url.getPath().substring(5, url.getPath().indexOf(33));
				JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
				Enumeration<JarEntry> entries = jar.entries();
				HashSet<String> result = new HashSet<String>();
				while (entries.hasMoreElements()) {
					String entry;
					JarEntry entry_ = entries.nextElement();
					String name = entry_.getName();
					if (!name.startsWith(path) || !(entry = name.substring(path.length())).contains(".xml"))
						continue;
					result.add(book + "/" + entry);
				}
				return result.toArray(new String[result.size()]);
			}
			path = "../../../../../library/" + book;
			url = Library.class.getResource(path);
			File dir = new File(url.toURI());
			for (String file : dir.list()) {
				File currFile = new File(Library.class.getResource(path + "/" + file).toURI());
				if (currFile.isDirectory()) {
					for (String s : Library.getFilesInFolder(book + "/" + file)) {
						files.add(s);
					}
					continue;
				}
				files.add(book + "/" + file);
			}
		}
		return files.toArray(new String[files.size()]);
	}
}