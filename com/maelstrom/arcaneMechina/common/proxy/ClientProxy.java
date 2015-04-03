package com.maelstrom.arcaneMechina.common.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import com.maelstrom.arcaneMechina.client.model.ModelAMBook;
import com.maelstrom.arcaneMechina.client.model.ModelSubsonicScrewdriver;
import com.maelstrom.arcaneMechina.client.renderer.ItemRendererC;
import com.maelstrom.arcaneMechina.client.renderer.TileRendererFurnace;
import com.maelstrom.arcaneMechina.common.handler.BaubleRenderHandler;
import com.maelstrom.arcaneMechina.common.handler.ContributorRenderHandler;
import com.maelstrom.arcaneMechina.common.items.ModItems;
import com.maelstrom.arcaneMechina.common.tile.TileEntityFurnaceBasic;

import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenderers(){
		//yes they theoretically could just be one class but i rather them be split for easy of reading
		MinecraftForge.EVENT_BUS.register(new ContributorRenderHandler());
		MinecraftForge.EVENT_BUS.register(new BaubleRenderHandler());
		
//		ITEM RENDERER
//			SUBSONIC SCREWDRIVER
		MinecraftForgeClient.registerItemRenderer(ModItems.ScrewDriver, new ItemRendererC(new ModelSubsonicScrewdriver()));
		MinecraftForgeClient.registerItemRenderer(ModItems.arcaneMechina, new ItemRendererC(new ModelAMBook()));
//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFurnaceBasic.class, new TileRendererFurnace());
	}
	
}