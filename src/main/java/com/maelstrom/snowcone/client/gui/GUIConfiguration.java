package com.maelstrom.snowcone.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiConfig;

import com.maelstrom.snowcone.ConfigurationHelper;
import com.maelstrom.snowcone.SnowconeMain;

public class GUIConfiguration extends GuiConfig
{
	public GUIConfiguration(GuiScreen parentScreen)
	{
		super(parentScreen, ConfigurationHelper.getConfig2(SnowconeMain.config.getConfig()), SnowconeMain.MODID, true, true, "Snowcone Configuration Menu");
		this.titleLine2 = "../"+SnowconeMain.config.getConfig().getConfigFile().getParentFile().getName()+"/"+SnowconeMain.config.getConfig().getConfigFile().getName();

	}
}