package com.maelstrom.snowcone.modelloader.techne;

import net.minecraft.util.ResourceLocation;

import com.maelstrom.snowcone.modelloader.IModelCustom;
import com.maelstrom.snowcone.modelloader.IModelCustomLoader;
import com.maelstrom.snowcone.modelloader.ModelFormatException;

public class TechneModelLoader implements IModelCustomLoader
{

	@Override
	public String getType()
	{
		return "Techne model";
	}

	private static final String[] types = { "tcn" };

	@Override
	public String[] getSuffixes()
	{
		return types;
	}

	@Override
	public IModelCustom loadInstance(ResourceLocation resource) throws ModelFormatException
	{
		return new TechneModel(resource);
	}

}