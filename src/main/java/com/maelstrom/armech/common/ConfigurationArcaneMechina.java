package com.maelstrom.armech.common;

import net.minecraftforge.common.config.Configuration;

import com.maelstrom.snowcone.ConfigurationHelper;

public class ConfigurationArcaneMechina extends ConfigurationHelper.ConfigurationFile
{
	public ConfigurationArcaneMechina(Configuration config)
	{
		super(config, Reference.MODID);
	}


	public static boolean dropItemsIfPlacementIsInvalid = false;
	public static int pureDropChance = 10000;
	public static boolean pureDrops = true;
	public static int crystalGenAmount = 5;

	public void load()
	{
		configuration.load();
		
		configuration.addCustomCategoryComment(ConfigurationHelper.CATERGORIES.WORLDGEN, "");

		dropItemsIfPlacementIsInvalid = configuration.get(Configuration.CATEGORY_GENERAL, "dropItemsIfPlacementIsInvalid", false).getBoolean();
		pureDropChance = configuration.get(ConfigurationHelper.CATERGORIES.DROPS, "pureDropChance", 10000).getInt();
		pureDrops = configuration.get(ConfigurationHelper.CATERGORIES.DROPS, "pureDrops", true).getBoolean();
		crystalGenAmount = configuration.get(ConfigurationHelper.CATERGORIES.WORLDGEN, "crystalGenAmount", 5).getInt();
		configuration.save();
	}
}
