package com.maelstrom.arcaneMechina;

import net.minecraft.item.ItemStack;

import com.maelstrom.arcaneMechina.init.InitItem;
import com.maelstrom.arcaneMechina.reference.Reference;
import com.maelstrom.snowcone.proxy.IProxy;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=Reference.MOD_ID, name=Reference.MOD_NAME, version=Reference.MOD_VERSION, useMetadata = Reference.MOD_METADATA)
public class ArcaneMechina {
	
	@SidedProxy(clientSide = Reference.PROXY_CLIENT, serverSide = Reference.PROXY_SERVER)
	public static IProxy proxy;
	
	@Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
		InitItem.init();
	}
	
	@Mod.EventHandler
    public void postInit(FMLInitializationEvent event){
		if(Loader.isModLoaded("after:NotEnoughItems")){
			codechicken.nei.api.API.hideItem(new ItemStack(InitItem.wandOfDebug));
		}
	}
	
	@Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
		proxy.registerEvents();
		proxy.registerRenderers();
	}
}