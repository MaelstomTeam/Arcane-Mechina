package com.maelstrom.arcanemechina;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.maelstrom.arcanemechina.client.tesr.RenderRune;
import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod(ArcaneMechina.MODID)
@Mod.EventBusSubscriber(modid = ArcaneMechina.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ArcaneMechina {
	public static final String MODID = "arcanemechina";
	public static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void commonSide(final FMLCommonSetupEvent event)
    {
    	Registry.RegisterItemGroups();
    }

    @SubscribeEvent
    public static void clientSide(final FMLClientSetupEvent event)
    {
    	Registry.RegisterModels();
    	ClientRegistry.bindTileEntitySpecialRenderer(RuneTileEntity.class, new RenderRune());
    }
    
    @SubscribeEvent
    public static void serverSide(FMLServerStartingEvent event)
    {
    }

}
