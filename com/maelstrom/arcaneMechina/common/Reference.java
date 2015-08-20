package com.maelstrom.arcanemechina.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;

import com.maelstrom.arcanemechina.common.creative.CreativeTab;

import cpw.mods.fml.common.network.IGuiHandler;

public class Reference
{

	public static final String MOD_ID = "arcanemechina";
	public static final String MOD_NAME = "Arcane Mechina";
	//find better way of making a version id!
	public static final String MOD_VERSION = "0.0.1";
	public static final String MOD_DEPENDENCIES = "required-after:snowconeUtil@[1.0];required-after:Baubles;after:Thaumcraft";
	public static final String PROXY_CLIENT = "com.maelstrom.arcanemechina.common.proxy.ClientProxy";
	public static final String PROXY_SERVER = "com.maelstrom.arcanemechina.common.proxy.ServerProxy";
	public static final boolean MOD_METADATA = true;
	
	public static final String[] maelstromTeam = { "hybolic", "Sporeknight", "thatphatkid", "brizzle1993" };
	public static final String[] helpers = {};
	public static final String[] community = {};
	
	public static final String[] chisel = {"AUTOMATIC_MAIDEN", "EoD", "Pokefenn", "TheCricket26"};
	public static final String[] thuamcraft = {"Azanor"};
	public static final String[] botania = {"Vazkii"};
	public static final String[] TeamCOFH = {"KingLemmingCoFH", "Cynycal", "Zeldo"};
	public static final String[] artists = {"brizzle1993", "Drullkus"};
	public static CreativeTab tab = new CreativeTab();
	public static final String tabName = "arcanemechina";
	public static final IGuiHandler guiHandler = new GuiHandler();

	public static boolean isContributor(String playerName)
	{
		for(String name : maelstromTeam) if(name.equals(playerName)) return true;
		for(String name : helpers) if(name.equals(playerName)) return true;
		for(String name : community) if(name.equals(playerName)) return true;
		for(String name : chisel) if(name.equals(playerName)) return true;
		for(String name : thuamcraft) if(name.equals(playerName)) return true;
		for(String name : botania) if(name.equals(playerName)) return true;
		for(String name : TeamCOFH) if(name.equals(playerName)) return true;
		for(String name : artists) if(name.equals(playerName)) return true;
		return false;
	}
	
	public static ResourceLocation getResource(String id){
		return new ResourceLocation(MOD_ID + ":" + id);
	}

	public static ResourceLocation getSoundResource(String id){
		return getResource("sounds/" + id);
	}

	public static ResourceLocation getTextureResource(String id){
		return getResource("textures/" + id);
	}

	public static ResourceLocation getGuiTextureResource(String id){
		return getResource("textures/gui/" + id);
	}

	
}
