package com.maelstrom.snowcone.util;

import net.minecraft.item.Item;

public class Development {

	private static boolean ignoreDevelopment = false;
	public static boolean isDevEnviroment()
	{
		return ignoreDevelopment ? false : Item.class.getCanonicalName() == "net.minecraft.item.Item";
	}
	public static void toggleIgnoreDevelopmentSetup()
	{
		ignoreDevelopment = !ignoreDevelopment;
	}
	public static void ignoreDevelopmentSetup()
	{
		ignoreDevelopment = true;
	}
}
