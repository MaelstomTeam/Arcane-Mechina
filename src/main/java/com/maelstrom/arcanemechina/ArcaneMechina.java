package com.maelstrom.arcanemechina;

import org.apache.logging.log4j.Logger;

import com.maelstrom.arcanemechina.common.creativetab.CreativeTab_AM;
import com.maelstrom.arcanemechina.common.registry.Registry;
import com.maelstrom.snowcone.SnowCone;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ArcaneMechina.MODID, name = ArcaneMechina.NAME, version = ArcaneMechina.VERSION, dependencies=ArcaneMechina.DEPENDENCEIS)
public class ArcaneMechina
{
	@Instance(ArcaneMechina.MODID)
	public static ArcaneMechina INSTANCE;
	
	public static final String MODID = "arcanemechina";
	public static final String NAME = "Arcane Mechina";
	public static final String VERSION = "1.0.0";
	public static final String DEPENDENCEIS = "required-after:snowcone@["+SnowCone.VERSION+"]";
	
    public static Logger LOGGER;
	public static CreativeTabs MainTab;
	public static CreativeTabs Runic;
	public static CreativeTabs Vegitation;
	public static CreativeTabs Mechanical;
	public static CreativeTabs Library;
	
	public static Registry MODREGISTRY = new Registry();
	
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	
    	//Page.init();
        LOGGER = event.getModLog();
        
        LOGGER.info("ARCANE MECHINA PRE INIT");
        MainTab = new CreativeTab_AM();
        Runic = new CreativeTab_AM.Runic();
        Vegitation = new CreativeTab_AM.Vegetation();
        Mechanical = new CreativeTab_AM.Mechanical();
        Library = new CreativeTab_AM.LIBRARY();
        
        MODREGISTRY.preInitialization();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        LOGGER.info("ARCANE MECHINA INIT");
        MODREGISTRY.Initialization();
        
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        LOGGER.info("ARCANE MECHINA POST INIT");
        MODREGISTRY.postInitialization();
    }
}
