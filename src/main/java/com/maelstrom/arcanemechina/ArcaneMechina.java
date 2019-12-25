package com.maelstrom.arcanemechina;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.maelstrom.arcanemechina.common.Registry;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(ArcaneMechina.MODID)
@Mod.EventBusSubscriber(modid = ArcaneMechina.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ArcaneMechina {
	public static final String MODID = "arcanemechina";
	public static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void commonSide(final FMLCommonSetupEvent event)
    {
    	Registry.RegisterItemGroups();
    	Registry.PROXY.init();
    }
}
