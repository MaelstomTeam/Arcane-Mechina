package com.maelstrom.arcaneMechina.proxy;

import net.minecraftforge.common.MinecraftForge;

import com.maelstrom.arcaneMechina.handler.ContributorRenderHandler;
import com.maelstrom.snowcone.proxy.IProxy;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenderers(){
		MinecraftForge.EVENT_BUS.register(new ContributorRenderHandler());
	}
	
}
