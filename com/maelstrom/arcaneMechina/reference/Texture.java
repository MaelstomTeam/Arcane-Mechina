package com.maelstrom.arcaneMechina.reference;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class Texture {

	//symbols
	public static final ResourceLocation symbol_AIR = new ResourceLocation(Reference.MOD_ID+":textures/symbols/Air.png");
	public static final ResourceLocation symbol_EARTH = new ResourceLocation(Reference.MOD_ID+":textures/symbols/Earth.png");
	public static final ResourceLocation symbol_FIRE = new ResourceLocation(Reference.MOD_ID+":textures/symbols/Fire.png");
	public static final ResourceLocation symbol_WATER = new ResourceLocation(Reference.MOD_ID+":textures/symbols/Water.png");
	public static final ResourceLocation symbol_ELEMENTS = new ResourceLocation(Reference.MOD_ID+":textures/symbols/Elements.png");
	
	
	public static final ResourceLocation symbol_Copper = new ResourceLocation(Reference.MOD_ID+":textures/symbols/Copper.png");
	public static final ResourceLocation symbol_Gold = new ResourceLocation(Reference.MOD_ID+":textures/symbols/Gold.png");
	public static final ResourceLocation symbol_Iron = new ResourceLocation(Reference.MOD_ID+":textures/symbols/Iron.png");
	public static final ResourceLocation symbol_Lead = new ResourceLocation(Reference.MOD_ID+":textures/symbols/Lead.png");
	public static final ResourceLocation symbol_Mercury = new ResourceLocation(Reference.MOD_ID+":textures/symbols/Mercury.png");
	public static final ResourceLocation symbol_Nickel = new ResourceLocation(Reference.MOD_ID+":textures/symbols/Nickel.png");
	public static final ResourceLocation symbol_Redstone = new ResourceLocation(Reference.MOD_ID+":textures/symbols/Redstone.png");
	public static final ResourceLocation symbol_Salt = new ResourceLocation(Reference.MOD_ID+":textures/symbols/Salt.png");
	public static final ResourceLocation symbol_Silver = new ResourceLocation(Reference.MOD_ID+":textures/symbols/Silver.png");
	public static final ResourceLocation symbol_Sulphur = new ResourceLocation(Reference.MOD_ID+":textures/symbols/Sulphur.png");
	public static final ResourceLocation symbol_Tin = new ResourceLocation(Reference.MOD_ID+":textures/symbols/Tin.png");
	

	//circle textures
	public static final ResourceLocation circle_3 = new ResourceLocation(Reference.MOD_ID+":textures/circles/3Point.png");
	public static final ResourceLocation circle_4 = new ResourceLocation(Reference.MOD_ID+":textures/circles/4Point.png");
	
	
	//block textures
	public static final ResourceLocation furnaceSide = new ResourceLocation(Reference.MOD_ID+":textures/blocks/furnace_side_lower.png");
	
	
	public static void bindTexture(ResourceLocation rl){
		Minecraft.getMinecraft().renderEngine.bindTexture(rl);
	}
}