package com.maelstrom.armech.common;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.maelstrom.armech.client.renderer.PowerCrystalRenderer;
import com.maelstrom.armech.common.blocks.BlockChalk;
import com.maelstrom.armech.common.blocks.BlockDustOre;
import com.maelstrom.armech.common.blocks.BlockPowerStorageCrystal;
import com.maelstrom.armech.common.blocks.BlockRuneCenter;
import com.maelstrom.armech.common.blocks.BlockTest;
import com.maelstrom.armech.common.blocks.ItemBlockMeta;
import com.maelstrom.armech.common.tileentity.TileEntityCrystal;
import com.maelstrom.armech.common.tileentity.TileEntityPurifier;

public class AMBlocks
{
	public static Block test_block;
	public static Block dust_ore;
	public static Block block_chalk;
	public static Block rune_block;
	public static Block power_crystal;
	
	public static void init()
	{
		test_block = new BlockTest(Material.rock).setUnlocalizedName("test_block");//.setCreativeTab(ArMechMain.tab_armech);
		dust_ore = new BlockDustOre();
		block_chalk = new BlockChalk();
		rune_block = new BlockRuneCenter();
		power_crystal = new BlockPowerStorageCrystal();
	}
	public static void register()
	{
		GameRegistry.registerBlock(test_block, test_block.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(block_chalk, block_chalk.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(rune_block, rune_block.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(power_crystal, power_crystal.getUnlocalizedName().substring(5));
		
		GameRegistry.registerBlock(dust_ore, ItemBlockMeta.class, "dust_ore");
			ModelBakery.addVariantName(Item.getItemFromBlock(dust_ore), BlockDustOre.EnumType.allToString());

    	GameRegistry.registerTileEntity(TileEntityPurifier.class, "armech.purifier");
		GameRegistry.registerTileEntity(TileEntityCrystal.class, "armech.crystal");
	}
	
	public static void registerRenders()
	{
		registerRenderer(test_block);
		registerRenderer(rune_block);
		for(int i = 0; i < BlockDustOre.EnumType.length(); i++)
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(dust_ore), i, new ModelResourceLocation(Reference.MODID + ":" + dust_ore.getUnlocalizedName().substring(5)+"_air", "inventory"));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrystal.class, new PowerCrystalRenderer());
	}
	private static void registerRenderer(Block block)
	{
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MODID +":"+item.getUnlocalizedName().substring(5), "inventory"));
	}
	
}
