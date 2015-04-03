package com.maelstrom.arcaneMechina;

import net.minecraftforge.common.MinecraftForge;

import com.maelstrom.arcaneMechina.common.blocks.ModBlocks;
import com.maelstrom.arcaneMechina.common.handler.PlayerJoinWorldEvent;
import com.maelstrom.arcaneMechina.common.items.ModItems;
import com.maelstrom.arcaneMechina.common.reference.Reference;
import com.maelstrom.arcaneMechina.common.tile.TileEntityArraySolar;
import com.maelstrom.arcaneMechina.common.tile.TileEntityFurnaceBasic;
import com.maelstrom.arcaneMechina.common.tile.TileEntityFurnaceBasicPower;
import com.maelstrom.arcaneMechina.common.tile.TileEntityGlyph;
import com.maelstrom.arcaneMechina.common.tile.TileEntityRFStorageArray;
import com.maelstrom.snowcone.proxy.IProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid=Reference.MOD_ID, name=Reference.MOD_NAME, version=Reference.MOD_VERSION, useMetadata = Reference.MOD_METADATA)
public class ArcaneMechina {
	
	@SidedProxy(clientSide = Reference.PROXY_CLIENT, serverSide = Reference.PROXY_SERVER)
	public static IProxy proxy;
	
	@Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
		ModItems.init();
		ModBlocks.init();
		ModRecipes.init();
	}

	@Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
		proxy.registerEvents();
		proxy.registerRenderers();
		GameRegistry.registerTileEntity(TileEntityFurnaceBasic.class, "furance.power");
		GameRegistry.registerTileEntity(TileEntityFurnaceBasicPower.class, "furance.power.storage");
		GameRegistry.registerTileEntity(TileEntityArraySolar.class, "array.solar");
		GameRegistry.registerTileEntity(TileEntityRFStorageArray.class, "array.storage");
		MinecraftForge.EVENT_BUS.register(new PlayerJoinWorldEvent());
		MinecraftForge.EVENT_BUS.register(ModItems.ghostWingAmulet);
	}
	
}