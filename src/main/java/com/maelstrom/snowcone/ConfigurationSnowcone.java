package com.maelstrom.snowcone;

import net.minecraftforge.common.config.Configuration;

import com.maelstrom.snowcone.ConfigurationHelper.ConfigurationFile;

public class ConfigurationSnowcone extends ConfigurationFile
{
	public ConfigurationSnowcone(Configuration config)
	{
		super(config, SnowconeMain.MODID);
	}

	public boolean secret = false;

	public void load()
	{
		configuration.load();
		secret = configuration.get(Configuration.CATEGORY_CLIENT, "secret", false).getBoolean();
		if(configuration.hasChanged())
			configuration.save();
	}
}
