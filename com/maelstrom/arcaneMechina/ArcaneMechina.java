package com.maelstrom.arcaneMechina;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import com.maelstrom.arcaneMechina.handler.PlayerJoinWorldEvent;
import com.maelstrom.arcaneMechina.init.InitBlock;
import com.maelstrom.arcaneMechina.init.InitItem;
import com.maelstrom.arcaneMechina.init.InitRecipe;
import com.maelstrom.arcaneMechina.reference.Reference;
import com.maelstrom.arcaneMechina.tileentity.TileEntityFurnace;
import com.maelstrom.arcaneMechina.world.WorldGen;
import com.maelstrom.snowcone.proxy.IProxy;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid=Reference.MOD_ID, name=Reference.MOD_NAME, version=Reference.MOD_VERSION, useMetadata = Reference.MOD_METADATA)
public class ArcaneMechina {
	
	@SidedProxy(clientSide = Reference.PROXY_CLIENT, serverSide = Reference.PROXY_SERVER)
	public static IProxy proxy;
	
	@Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
		InitItem.init();
		InitBlock.init();
	}
	
	@Mod.EventHandler
    public void postInit(FMLInitializationEvent event){
		InitRecipe.init();
		if(Loader.isModLoaded("NotEnoughItems")){
			codechicken.nei.api.API.hideItem(new ItemStack(InitItem.wandOfDebug));
		}
		GameRegistry.registerWorldGenerator(new WorldGen(), 10);
		GameRegistry.registerTileEntity(TileEntityFurnace.class, Reference.MOD_ID+".TileEntityFurnace");
	}
	
	@Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
		proxy.registerEvents();
		proxy.registerRenderers();
		MinecraftForge.EVENT_BUS.register(new PlayerJoinWorldEvent());
		
		//jump boost for pegasus wings 1.5 blocks
		MinecraftForge.EVENT_BUS.register(InitItem.pegasusWingAmulet);
	}
}