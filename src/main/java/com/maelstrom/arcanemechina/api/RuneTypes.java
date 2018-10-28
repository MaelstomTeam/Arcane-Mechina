package com.maelstrom.arcanemechina.api;

import java.awt.Color;

public enum RuneTypes
{
	RUNIC		(Color.LIGHT_GRAY, 		"Runic"),
	VALIANT		(Color.PINK, 			"Valiant"),
	ETHERAL		(Color.MAGENTA, 		"Ethereal"),
	OBLIVIOUS	(new Color(128,0,128), 	"Daedric");
	
	public Color color = Color.WHITE;
	public String name;
	private RuneTypes(Color color, String name)
	{
		this.color = color;
		this.name = name;
	}
	private static String[] names;
	public static String[] AllNames()
	{
		if(names == null)
		{
			names = new String[RuneTypes.values().length];
			for(int i = 0; i < names.length; i++)
				names[i] = RuneTypes.values()[i].name;
		}
		return names;
	}
}
