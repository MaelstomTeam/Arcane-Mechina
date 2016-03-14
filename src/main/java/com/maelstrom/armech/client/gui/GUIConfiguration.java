package com.maelstrom.armech.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiConfig;

import com.maelstrom.armech.common.Reference;
import com.maelstrom.snowcone.ConfigurationHelper;

public class GUIConfiguration extends GuiConfig
{

	public GUIConfiguration(GuiScreen parentScreen)
	{
		super(parentScreen, ConfigurationHelper.getConfig2(Reference.config.getConfig()), Reference.MODID, false, false, "Arcane Mechina Configuration Menu");
		this.titleLine2 = "../"+Reference.config.getConfig().getConfigFile().getParentFile().getName()+"/"+Reference.config.getConfig().getConfigFile().getName();
	}

}
