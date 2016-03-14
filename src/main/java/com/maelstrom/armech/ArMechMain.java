package com.maelstrom.armech;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.maelstrom.armech.client.gui.Page;
import com.maelstrom.armech.common.ConfigurationArcaneMechina;
import com.maelstrom.armech.common.Reference;
import com.maelstrom.armech.common.pleasesortthis.OreGen;
import com.maelstrom.armech.common.registry.AMBlocks;
import com.maelstrom.armech.common.registry.AMCrafting;
import com.maelstrom.armech.common.registry.AMItems;
import com.maelstrom.armech.common.tab.Tab_ArMech;
import com.maelstrom.armech.proxy.CommonProxy;
import com.maelstrom.snowcone.ConfigurationHelper;


@Mod(modid = Reference.MODID, version = Reference.VERSION, guiFactory = "com.maelstrom.armech.client.GUIFactory")
public class ArMechMain
{
	@SidedProxy(clientSide=Reference.CLIENT_PROXY_CLASS, serverSide=Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	public static CreativeTabs tab_armech = new Tab_ArMech("tab_armech").setNoTitle();
	public static CreativeTabs tab_armech_dust = new Tab_ArMech("tab_armech.dust");
	public static CreativeTabs tab_armech_unimplemented = new Tab_ArMech("tab_armech.unimplemented").setTabIcon(new ItemStack(Blocks.barrier)).setNoTitle();
	
	@Instance(Reference.MODID)
	public static ArMechMain INSTANCE;
	
    @EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
    	Reference.config = new ConfigurationArcaneMechina(new Configuration(ConfigurationHelper.getMaelstromTeamConfigurationFile(event, Reference.MODID)));
    	
    	//try to create pages
		Page.init();
		
    	NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
    	INSTANCE = this;
    	
    	AMBlocks.init();
    	AMBlocks.register();
    	
    	AMItems.init();
    	AMItems.register();
    	
    	AMCrafting.registerRecipes();
    	((Tab_ArMech) tab_armech_dust).setTabIcon(new ItemStack(AMItems.dust_crystal,1,1));
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.registerRenders();
    }
    
    @EventHandler
    public void postinit(FMLPostInitializationEvent event)
    {
    	MinecraftForge.EVENT_BUS.register(new com.maelstrom.armech.common.event.EventHandler());
    	GameRegistry.registerWorldGenerator(new OreGen(), 0);
    }

}