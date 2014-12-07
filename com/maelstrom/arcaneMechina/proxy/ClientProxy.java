package com.maelstrom.arcaneMechina.proxy;

import net.minecraftforge.common.MinecraftForge;

import com.maelstrom.arcaneMechina.handler.BaubleRenderHandler;
import com.maelstrom.arcaneMechina.handler.ContributorRenderHandler;
import com.maelstrom.snowcone.proxy.IProxy;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenderers(){
		//yes they theoretically could just be one class but i rather them be split for easy of coding
		MinecraftForge.EVENT_BUS.register(new ContributorRenderHandler());
		MinecraftForge.EVENT_BUS.register(new BaubleRenderHandler());
	}
	
}
