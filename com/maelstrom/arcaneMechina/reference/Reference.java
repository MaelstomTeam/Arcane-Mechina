package com.maelstrom.arcaneMechina.reference;

import com.maelstrom.arcaneMechina.creative.CreativeTabArcaneMechina;
import com.maelstrom.arcaneMechina.init.InitItem;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class Reference {

	public static final String MOD_ID = "arcanemechina";
	public static final String MOD_NAME = "Arcane Mechina";
	public static final String MOD_VERSION = "0.1";
	public static final String MOD_DEPENDENCIES = "after:snowconeUtil@[1.0];after:NotEnoughItems;after:Baubles;";
	public static final String PROXY_CLIENT = "com.maelstrom.arcaneMechina.proxy.ClientProxy";
	public static final String PROXY_SERVER = "com.maelstrom.arcaneMechina.proxy.ServerProxy";
	public static final String MODTAB_NAME = "arcaneMechina.tab";
	public static final boolean MOD_METADATA = true;
	
	public static final CreativeTabs MOD_TAB = new CreativeTabArcaneMechina(MOD_ID, new ItemStack(InitItem.wandOfDebug));
	
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
