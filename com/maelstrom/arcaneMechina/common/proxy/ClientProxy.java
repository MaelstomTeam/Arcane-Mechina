package com.maelstrom.arcanemechina.common.proxy;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

import com.maelstrom.arcanemechina.client.handler.render.BaubleRenderHandler;
import com.maelstrom.arcanemechina.client.texture.TieredCompassTexture;
import com.maelstrom.arcanemechina.common.ItemsReference;
import com.maelstrom.arcanemechina.common.Reference;
import com.maelstrom.arcanemechina.common.registery.IconRegestry;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy  extends CommonProxy{

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		MinecraftForge.EVENT_BUS.register(new BaubleRenderHandler());
		MinecraftForge.EVENT_BUS.register(new IconRegestry());
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