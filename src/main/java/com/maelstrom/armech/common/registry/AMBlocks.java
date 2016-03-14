package com.maelstrom.armech.common.registry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.maelstrom.armech.ArMechMain;
import com.maelstrom.armech.client.renderer.PowerCrystalRenderer;
import com.maelstrom.armech.client.renderer.PowerCrystalRenderer2;
import com.maelstrom.armech.client.renderer.WorldGenCrystalRenderer;
import com.maelstrom.armech.common.Reference;
import com.maelstrom.armech.common.blocks.BlockChalk;
import com.maelstrom.armech.common.blocks.BlockCreativeHelper;
import com.maelstrom.armech.common.blocks.BlockPowerStorageCrystal;
import com.maelstrom.armech.common.blocks.BlockRelayCrystal;
import com.maelstrom.armech.common.blocks.BlockRuneCenter;
import com.maelstrom.armech.common.blocks.BlockTest;
import com.maelstrom.armech.common.blocks.BlockDustCrystal;
import com.maelstrom.armech.common.tileentity.OLD_TileEntityCrystal;
import com.maelstrom.armech.common.tileentity.OLD_TileEntityPurifier;
import com.maelstrom.armech.common.tileentity.TileEntityCreativeHelper;
import com.maelstrom.armech.common.tileentity.TileEntityCrystalConduit;
import com.maelstrom.armech.common.tileentity.clientonly.TileEntityWorldGenCrystal;

public class AMBlocks
{
	public static Block test_block;
	public static Block block_chalk;
	public static Block rune_block;
	public static Block power_crystal;
	public static Block relay_crystal;
	public static Block dust_ore;
	
	
	
	//power gen
	public static Block creativeBlock;
	
	public static void init()
	{
		test_block = new BlockTest(Material.rock).setUnlocalizedName("test_block").setCreativeTab(ArMechMain.tab_armech_unimplemented);
		block_chalk = new BlockChalk().setCreativeTab(ArMechMain.tab_armech_unimplemented);
		rune_block = new BlockRuneCenter().setCreativeTab(ArMechMain.tab_armech_unimplemented);
		power_crystal = new BlockPowerStorageCrystal().setCreativeTab(ArMechMain.tab_armech_unimplemented);
		relay_crystal = new BlockRelayCrystal().setCreativeTab(ArMechMain.tab_armech_unimplemented);
		dust_ore = new BlockDustCrystal().setCreativeTab(ArMechMain.tab_armech_unimplemented);

		
		
		
		creativeBlock = new BlockCreativeHelper();
	}
	public static void register()
	{
		GameRegistry.registerBlock(test_block, test_block.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(block_chalk, block_chalk.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(rune_block, rune_block.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(power_crystal, power_crystal.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(relay_crystal, relay_crystal.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(dust_ore, dust_ore.getUnlocalizedName().substring(5));
		
		GameRegistry.registerBlock(creativeBlock, creativeBlock.getUnlocalizedName().substring(5));
		
    	GameRegistry.registerTileEntity(OLD_TileEntityPurifier.class, "armech.purifier");
		GameRegistry.registerTileEntity(OLD_TileEntityCrystal.class, "armech.crystal");
		GameRegistry.registerTileEntity(TileEntityCrystalConduit.class, "armech.crystalrelay");
		GameRegistry.registerTileEntity(TileEntityWorldGenCrystal.class, "armech.dust_crystal");
		

		GameRegistry.registerTileEntity(TileEntityCreativeHelper.class, "armech.lightning_rod");
	}
	
	public static void registerRenders()
	{
		registerRenderer(test_block);
		registerRenderer(rune_block);
		registerRenderer(power_crystal);
		registerRenderer(relay_crystal);
		registerRenderer(dust_ore);
		
		ClientRegistry.bindTileEntitySpecialRenderer(OLD_TileEntityCrystal.class, new PowerCrystalRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrystalConduit.class, new PowerCrystalRenderer2());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWorldGenCrystal.class, new WorldGenCrystalRenderer());
	}
	
	private static void registerRenderer(Block block)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MODID +":"+Item.getItemFromBlock(block).getUnlocalizedName().substring(5), "inventory"));
	}
	
}
