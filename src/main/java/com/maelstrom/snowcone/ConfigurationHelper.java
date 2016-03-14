package com.maelstrom.snowcone;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHelper
{
	public static File getCustomConfigurationFolder(FMLPreInitializationEvent event, String folder, String modid) throws IOException{
		while(folder.startsWith("/"))
			folder.replaceFirst("/", "");
		while(folder.endsWith("/"))
			folder.substring(0, folder.length()-1);
		if(!(folder.equals(null) || folder.equals("")))
			return new File(event.getModConfigurationDirectory().getAbsoluteFile(), "/" + folder + "/" + modid + ".cfg").getCanonicalFile();
		throw new NullPointerException(folder);
	}

	public static File getMaelstromTeamConfigurationFile(FMLPreInitializationEvent event, String modid){
		return new File(event.getModConfigurationDirectory().getAbsoluteFile(), "/MAELSTROM TEAM/" + modid + ".cfg");
	}
	
	public static List<IConfigElement> getConfig2(Configuration configuration)
	{
		List<IConfigElement> configs = new ArrayList<IConfigElement>();
		for(String catergory : configuration.getCategoryNames())
			configs.add(new ConfigElement(configuration.getCategory(catergory)));
		return configs;
	}

	public static class CATERGORIES
	{
		public static final String WORLDGEN = "world generation";
		public static final String DROPS = "drops";
	}

	public static abstract class ConfigurationFile
	{

		public abstract void load();

		protected Configuration configuration;
		protected String MODID;
		
		public ConfigurationFile(Configuration config, String modid)
		{
			configuration = config;
			MODID = modid;
			load();
			MinecraftForge.EVENT_BUS.register(this);
		}
		
		public Configuration getConfig()
		{
			return configuration;
		}
		
		@SubscribeEvent
		public void onConfigChanged(OnConfigChangedEvent event)
		{
			if(event.modID == MODID)
			{
				configuration.save();
				load();
			}
		}
	}
}

