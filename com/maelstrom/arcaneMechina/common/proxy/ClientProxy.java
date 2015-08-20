package com.maelstrom.arcanemechina.common.proxy;

import net.minecraftforge.common.MinecraftForge;

import com.maelstrom.arcanemechina.client.handler.render.BaubleRenderHandler;
import com.maelstrom.arcanemechina.client.registry.IconRegestry;
import com.maelstrom.arcanemechina.client.renderer.ResearchTableRenderer;
import com.maelstrom.arcanemechina.common.tileentity.TileEntityResearch;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy  extends CommonProxy{

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		MinecraftForge.EVENT_BUS.register(new BaubleRenderHandler());
		MinecraftForge.EVENT_BUS.register(new IconRegestry());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityResearch.class, new ResearchTableRenderer());
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

}