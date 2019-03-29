package com.maelstrom.snowcone;

import java.util.Random;

import org.apache.logging.log4j.Logger;

import com.maelstrom.snowcone.common.CommandSnowCone;
import com.maelstrom.snowcone.libraryAPI.Library;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = SnowCone.MODID, name = SnowCone.NAME, version = SnowCone.VERSION, dependencies = SnowCone.DEPENDENCIES)
public class SnowCone
{
	public static final String MODID = "snowcone";
	public static final String NAME = "Snowcone Util";
	public static final String VERSION = "lemon";
	public static final String DEPENDENCIES = "after:mcjtylib_ng;after:enderio;after:cofhcore;after:draconicevolution;";
	private static SC_Registry registry = new SC_Registry();
	
    public static Logger logger;
	public static Random random;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	random = new Random();
    	random.setSeed(847269687967847982L);
    	
    	//Config.RegisterKeybinds();
    	
    	//temp
    	//MinecraftForge.EVENT_BUS.register(new Config());
    	
    	
        logger = event.getModLog();
        logger.info("SNOWCONE PRE INIT");
		Library.init();
        registry.preInitialization();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("SNOWCONE INIT");
        registry.Initialization();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        logger.info("SNOWCONE POST INIT");
        registry.postInitialization();
    }
	
    @EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		logger.info("Registering command");
		event.registerServerCommand(new CommandSnowCone());
	}
}
