package com.maelstrom.snowcone.modelloader.obj;

import net.minecraft.util.ResourceLocation;

import com.maelstrom.snowcone.modelloader.IModelCustom;
import com.maelstrom.snowcone.modelloader.IModelCustomLoader;
import com.maelstrom.snowcone.modelloader.ModelFormatException;

public class ObjModelLoader implements IModelCustomLoader
{

	@Override
	public String getType()
	{
		return "OBJ model";
	}

	private static final String[] types = { "obj" };

	@Override
	public String[] getSuffixes()
	{
		return types;
	}

	@Override
	public IModelCustom loadInstance(ResourceLocation resource) throws ModelFormatException
	{
		return new WavefrontObject(resource);
	}
}