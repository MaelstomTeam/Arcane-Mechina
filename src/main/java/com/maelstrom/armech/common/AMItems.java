package com.maelstrom.armech.common;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.maelstrom.armech.ArMechMain;
import com.maelstrom.armech.common.blocks.BlockDustOre;
import com.maelstrom.armech.common.items.*;

public class AMItems
{
	
	public static Item test_item;
	public static Item help_book;
	public static Item dust_crystal;
	public static Item glass_jar;
	public static Item glass_jar_dust;
	public static Item morter_and_pestal;
	public static Item gold_rod;
	public static Item dust_dust; //not implemented
	
	public static void init()
	{
		test_item = new Item().setUnlocalizedName("test_item");//.setCreativeTab(ArMechMain.tab_armech);
		help_book = new ItemAMBook();
		dust_crystal = new ItemDustCrystal();
		glass_jar = new Item().setUnlocalizedName("glass_jar").setCreativeTab(ArMechMain.tab_armech);
		glass_jar_dust = new ItemDustMixture();
		morter_and_pestal = new Item().setUnlocalizedName("morter_and_pestal").setCreativeTab(ArMechMain.tab_armech);
		gold_rod = new Item().setUnlocalizedName("gold_rod").setCreativeTab(ArMechMain.tab_armech);
	}
	
	public static void register()
	{
		registerItem(test_item);
		registerItem(dust_crystal);
		registerItem(help_book);
		registerItem(glass_jar);
		registerItem(glass_jar_dust);
		registerItem(morter_and_pestal);
		registerItem(gold_rod);
	}
	
	public static void registerRenders()
	{
		registerRenderer(test_item);
//		registerRenderer(dust_crystal);
		for(int i = 0; i < Reference.dustNames.length; i++)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(dust_crystal, i, new ModelResourceLocation(Reference.MODID +":"+dust_crystal.getUnlocalizedName().substring(5), "inventory"));
		registerRenderer(help_book);
		registerRenderer(glass_jar);
		for(int i = 0; i < Reference.dustNames.length; i++)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(glass_jar_dust, i, new ModelResourceLocation(Reference.MODID +":"+glass_jar_dust.getUnlocalizedName().substring(5), "inventory"));
		registerRenderer(morter_and_pestal);
		registerRenderer(gold_rod);
	}
	
	private static void registerItem(Item item)
	{
		GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5),Reference.MODID);
	}
	
	private static void registerRenderer(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MODID +":"+item.getUnlocalizedName().substring(5), "inventory"));
	}
}