package com.maelstrom.snowcone.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import scala.tools.nsc.io.Jar;

public class JarStream
{
	public static List<String> GetResource(String location) throws IOException, URISyntaxException
	{
		File file = new File(Jar.class.getResource("../../../../assets/arcanemechina/book/").toURI());
		file.list();
		
		
		InputStream in = JarStream.class.getResourceAsStream(location);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		List<String> list = new ArrayList<String>();
		while(reader.ready())
			list.add(reader.readLine());
		return list;
	}
}
