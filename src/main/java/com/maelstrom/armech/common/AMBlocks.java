package com.maelstrom.armech.common;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.maelstrom.armech.ArMechMain;
import com.maelstrom.armech.common.blocks.BlockDustOre;
import com.maelstrom.armech.common.blocks.BlockTest;
import com.maelstrom.armech.common.blocks.ItemBlockMeta;

public class AMBlocks
{
	public static Block test_block;
	public static Block dust_ore;
	
	public static void init()
	{
		test_block = new BlockTest(Material.rock).setUnlocalizedName("test_block");//.setCreativeTab(ArMechMain.tab_armech);
		dust_ore = new BlockDustOre();
	}
	public static void register()
	{
		GameRegistry.registerBlock(test_block, test_block.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(dust_ore, ItemBlockMeta.class, "dust_ore");
		ModelBakery.addVariantName(Item.getItemFromBlock(dust_ore), BlockDustOre.EnumType.allToString());
	}
	
	public static void registerRenders()
	{
		registerRenderer(test_block);
		for(int i = 0; i < BlockDustOre.EnumType.length(); i++)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(dust_ore), i, 
					new ModelResourceLocation(Reference.MODID + ":" + dust_ore.getUnlocalizedName().substring(5)+"_air", "inventory"));
	}
	private static void registerRenderer(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MODID +":"+item.getUnlocalizedName().substring(5), "inventory"));
	}
	
	
	
	
}
