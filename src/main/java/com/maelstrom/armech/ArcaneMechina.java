package com.maelstrom.armech;

import com.maelstrom.armech.common.Registry;
import com.maelstrom.armech.common.reference.Reference;
import com.maelstrom.armech.common.worldgen.WorldGenerator;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
		GameRegistry.registerWorldGenerator(new WorldGenerator(), 5);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		Registry.postInit();
	}
}
