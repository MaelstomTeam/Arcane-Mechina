package com.maelstrom.snowcone.client;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

import com.maelstrom.snowcone.client.gui.GUIConfiguration;

public class GUIFactory implements IModGuiFactory
{

	@Override
	public void initialize(Minecraft paramMinecraft)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass()
	{
		return GUIConfiguration.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement paramRuntimeOptionCategoryElement)
	{
		return null;
	}

}
