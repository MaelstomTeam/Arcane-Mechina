package com.maelstrom.snowcone;

import java.util.ArrayList;

import com.maelstrom.snowcone.item.ItemHelpBook;
import com.maelstrom.snowcone.util.CreativeTabCustom;
import com.maelstrom.snowcone.util.ERegistry;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid=SnowCone.MODID)
public class SC_Registry extends ERegistry{
	public static Item HelpBook = new ItemHelpBook();
	public static Item SonicScrewdriver = new Item();
	public static CreativeTabs tab = new LIBRARY();
	private static ArrayList<Item> items = new ArrayList<Item>();

	@Override
	public String getMODID() {
		return SnowCone.MODID;
	}

	@Override
	public CreativeTabs getTab() {
		return tab;
	}

	@Override
	public void preInitialization() {
		// TODO Auto-generated method stub
		registerItem(HelpBook, "helpbook",tab);
		registerItem(SonicScrewdriver, "sonic",CreativeTabs.TOOLS);
	}

	@Override
	public void Initialization() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postInitialization() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Item> itemList() {
		return items;
	}

	@Override
	public ArrayList<Block> listBlock() {
		return new ArrayList<Block>();
	}

	@Override
	public void registerOreDictionaryEntries() {
		// TODO Auto-generated method stub
		
	}
	

	private static class LIBRARY extends CreativeTabCustom
	{
		public LIBRARY() {
			super("SC.LIBRARY", new ItemStack(HelpBook));
		}
	}
	

	
	@SubscribeEvent
	public static void onItemRegister(final RegistryEvent.Register<Item> event) {
		SnowCone.logger.info("EVENT Registering Items/ItemBlocks");
        final IForgeRegistry<Item> registry = event.getRegistry();
		for(Item i : items)
			registry.registerAll(i);
	}
	
	@SubscribeEvent
    @SideOnly(Side.CLIENT)
	public static void onModelEvent(final ModelRegistryEvent event) {
		SnowCone.logger.info("EVENT Registering Models");
		for(Item i : items)
		{
			registerItemModel(i);
		}
	}

}
