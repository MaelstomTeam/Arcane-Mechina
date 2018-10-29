package com.maelstrom.snowcone;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SnowCone.MODID, name = SnowCone.NAME, version = SnowCone.VERSION)
public class SnowCone
{
	public static final String MODID = "snowcone";
	public static final String NAME = "Snowcone Util";
	public static final String VERSION = "lemon";
	
    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        logger.info("SNOWCONE PRE INIT");
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("SNOWCONE INIT");
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        logger.info("SNOWCONE POST INIT");
    }
}
