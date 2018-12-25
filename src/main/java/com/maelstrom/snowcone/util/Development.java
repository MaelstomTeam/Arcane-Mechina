package com.maelstrom.snowcone.util;

import net.minecraft.init.Blocks;

public class Development {

	private static boolean ignoreDevelopment = false;
	public static boolean isDevEnviroment()
	{
		
		try {
			Blocks.class.getField("AIR");
			return true;
		}
		catch(Throwable e)
		{
			return false;
		}
		//TODO FIX ThIS DAMN THING
		//return ignoreDevelopment ? false : Item.class.getCanonicalName() == "net.minecraft.item.Item";
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
