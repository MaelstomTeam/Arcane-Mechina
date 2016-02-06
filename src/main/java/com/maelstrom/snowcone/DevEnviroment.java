package com.maelstrom.snowcone;

import net.minecraft.item.Item;

public class DevEnviroment
{
	private static boolean ignoreDev = false;
	public static boolean isDevEnviroment()
	{
		return ignoreDev ? false : Item.class.getCanonicalName() == "net.minecraft.item.Item";
	}
	public static void toggleIgnoreDevSetup()
	{
		ignoreDev = !ignoreDev;
	}
	public static void ignoreDevSetup()
	{
		ignoreDev = true;
	}
	
}
