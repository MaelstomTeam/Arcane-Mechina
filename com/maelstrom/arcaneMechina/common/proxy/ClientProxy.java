package com.maelstrom.arcaneMechina.common.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import com.maelstrom.arcaneMechina.client.model.ModelSubsonicScrewdriver;
import com.maelstrom.arcaneMechina.client.renderer.ItemRenderer;
import com.maelstrom.arcaneMechina.client.renderer.TileRendererFurnace;
import com.maelstrom.arcaneMechina.common.handler.BaubleRenderHandler;
import com.maelstrom.arcaneMechina.common.handler.ContributorRenderHandler;
import com.maelstrom.arcaneMechina.common.init.InitItem;
import com.maelstrom.arcaneMechina.common.tileentity.TileEntityFurnace;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenderers(){
		//yes they theoretically could just be one class but i rather them be split for easy of coding
		MinecraftForge.EVENT_BUS.register(new ContributorRenderHandler());
		MinecraftForge.EVENT_BUS.register(new BaubleRenderHandler());
		
//		ITEM RENDERER
//			SUBSONIC SCREWDRIVER
		MinecraftForgeClient.registerItemRenderer(InitItem.ScrewDriver, new ItemRenderer(new ModelSubsonicScrewdriver()));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFurnace.class, new TileRendererFurnace());
	}
	
}