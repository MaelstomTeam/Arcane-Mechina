package com.maelstrom.armech;

import com.maelstrom.armech.common.Reference;
import com.maelstrom.armech.common.Registry;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, version = Reference.VERSION)
public class ArcaneMechina {
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Registry.preInit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		Registry.init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		Registry.postInit();
	}
}
