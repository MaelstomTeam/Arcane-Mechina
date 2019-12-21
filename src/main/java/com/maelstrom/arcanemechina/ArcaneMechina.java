package com.maelstrom.arcanemechina;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.maelstrom.arcanemechina.client.gui.RuneCraftingGui;
import com.maelstrom.arcanemechina.client.tesr.RenderRune;
import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.container.RuneDrawingContainer;
import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
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
    	Registry.PROXY.init();
    }
}
