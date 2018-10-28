package com.maelstrom.arcanemechina.api;

import java.awt.Color;

public enum ElementTypes {
	FIRE		(Color.RED, 			"Fire"),
	WATER		(Color.BLUE, 			"Water"),
	EARTH		(Color.GREEN,			"Earth"),
	AIR			(Color.YELLOW, 			"Air"),
	DARK		(Color.BLACK,			"Dark"),
	LIGHT		(Color.WHITE, 			"Light"),
	ETHERIC		(Color.MAGENTA, 		"Etheral"),
	OBLIVIOUS	(new Color(128,0,128),	"Daedric");
	
	public Color clr = Color.WHITE;
	public String name;
	private ElementTypes(Color color, String name)
	{
		clr = color;
		this.name = name;
	}
	private static String[] names;
	public static String[] AllNames()
	{
		if(names == null)
		{
			names = new String[ElementTypes.values().length];
			for(int i = 0; i < names.length; i++)
				names[i] = ElementTypes.values()[i].name;
		}
		return names;
	}
}