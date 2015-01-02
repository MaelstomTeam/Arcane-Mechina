package com.maelstrom.arcaneMechina.reference;

import com.maelstrom.arcaneMechina.creative.CreativeTabArcaneMechina;
import com.maelstrom.arcaneMechina.init.InitBlock;
import com.maelstrom.arcaneMechina.init.InitItem;
import com.maelstrom.snowcone.world.OreGenBlock;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class Reference {

	public static final String MOD_ID = "arcanemechina";
	public static final String MOD_NAME = "Arcane Mechina";
	public static final String MOD_VERSION = "@VERSION@";
	public static final String MOD_DEPENDENCIES = "after:snowconeUtil@[1.0];after:NotEnoughItems;after:Baubles;";
	public static final String PROXY_CLIENT = "com.maelstrom.arcaneMechina.proxy.ClientProxy";
	public static final String PROXY_SERVER = "com.maelstrom.arcaneMechina.proxy.ServerProxy";
	public static final String MODTAB_NAME = "arcaneMechina.tab";
	public static final boolean MOD_METADATA = true;

	public static final CreativeTabs MOD_TAB = new CreativeTabArcaneMechina(MOD_ID, new ItemStack(InitItem.wandOfDebug));


	public static final String[] gemStones = { "opal", "amber", "zircon", "tourmaline", "sapphire", "chalk" };
	public static final String[] preciousMetals = {"silver", "lead", "tin", "copper", "zinc"};


	//TODO Update
	//Temporary values
	public static final OreGenBlock[] worldGenOverworld = {
		new OreGenBlock(3, 1, 0, 128, InitBlock.gemOre, 0),
		new OreGenBlock(3, 1, 0, 128, InitBlock.gemOre, 1),
		new OreGenBlock(3, 1, 0, 128, InitBlock.gemOre, 2),
		new OreGenBlock(3, 1, 0, 128, InitBlock.gemOre, 3),
		new OreGenBlock(3, 1, 0, 128, InitBlock.gemOre, 4),
		new OreGenBlock(3, 1, 0, 128, InitBlock.gemOre, 5),

		new OreGenBlock(8, 1, 0, 128, InitBlock.metalOre, 0),
		new OreGenBlock(8, 1, 0, 128, InitBlock.metalOre, 1),
		new OreGenBlock(8, 1, 0, 128, InitBlock.metalOre, 2),
		new OreGenBlock(8, 1, 0, 128, InitBlock.metalOre, 3),
		new OreGenBlock(8, 1, 0, 128, InitBlock.metalOre, 4)
	};

	public static String[] maelstromTeam = { "hybolic", "Sporeknight", "thatphatkid", "brizzle1993" };
	public static String[] helpers = {};
	public static String[] community = {};
	
	//everything bellow i was asked to add so yah >.>
	public static String[] chisel = {"AUTOMATIC_MAIDEN", "EoD", "Pokefenn", "TheCricket26"};
	public static String[] thuamcraft = {"Azanor"};
	public static String[] botania = {"Vazkii"};
	public static String[] TeamCOFH = {"KingLemmingCoFH", "Cynycal", "Zeldo"};
	public static String[] artists = {"brizzle1993", "Drullkus"};
	//if i got any information from @Sporeknight it would be helpful :P

	public static boolean isContributor(String s){
		for(String name : maelstromTeam) if(name.equals(s)) return true;
		for(String name : helpers) if(name.equals(s)) return true;
		for(String name : community) if(name.equals(s)) return true;
		for(String name : chisel) if(name.equals(s)) return true;
		for(String name : thuamcraft) if(name.equals(s)) return true;
		for(String name : botania) if(name.equals(s)) return true;
		for(String name : TeamCOFH) if(name.equals(s)) return true;
		for(String name : artists) if(name.equals(s)) return true;
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
