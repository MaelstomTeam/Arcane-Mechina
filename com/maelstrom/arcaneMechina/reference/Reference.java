package com.maelstrom.arcaneMechina.reference;

import net.minecraft.util.ResourceLocation;

public class Reference {

	public static final String MOD_ID = "arcanemechina";
	public static final String MOD_NAME = "Arcane Mechina";
	public static final String MOD_VERSION = "0.1";
	public static final String MOD_DEPENDENCIES = "after:snowconeUtil@[1.0];after:NotEnoughItems;";
	public static final String PROXY_CLIENT = "com.maelstrom.arcaneMechina.proxy.ClientProxy";
	public static final String PROXY_SERVER = "com.maelstrom.arcaneMechina.proxy.ServerProxy";
	public static final String MODTAB_NAME = "arcaneMechina.tab";
	
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
