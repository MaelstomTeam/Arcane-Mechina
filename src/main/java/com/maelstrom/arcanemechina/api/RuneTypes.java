package com.maelstrom.arcanemechina.api;

import java.awt.Color;

import net.minecraft.util.IStringSerializable;

public enum RuneTypes implements IStringSerializable
{
	RUNIC				(Color.LIGHT_GRAY, 		"Runic"),
	VALIANT				(Color.PINK, 			"Valiant"),
	ETHERAL				(Color.MAGENTA, 		"Ethereal"),
	OBLIVIOUS			(new Color(128,0,128), 	"Daedric"),
	ACTIVATION_SIGIL	(Color.GREEN, 	"Activation_Sigil");
	public Color color = Color.WHITE;
	public String name;
	static int index = -1;
	private RuneTypes(Color color, String name)
	{
		this.color = color;
		this.name = name.toLowerCase();
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
	@Override
	public String getName() {
		return name;
	}
	
	public int getIndex()
	{
		if(index == -1)
		{
			for(int i = 0; i < values().length; i++)
				if(values()[i] == this)
					index = i;
		}
		return index;
	}
}
