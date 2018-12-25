package com.maelstrom.arcanemechina.client;

import com.maelstrom.arcanemechina.ArcaneMechina;
import com.maelstrom.arcanemechina.common.CommonProxy;
import com.maelstrom.arcanemechina.common.block.BlockColoredMeta;
import com.maelstrom.arcanemechina.common.items.ItemColoredMeta;
import com.maelstrom.snowcone.block.IItemColored;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		for(Item item : ArcaneMechina.MODREGISTRY.itemList())
			if(item instanceof ItemColoredMeta)
				ArcaneMechina.MODREGISTRY.registerIItemColor((IItemColored)item);
		for(Block block: ArcaneMechina.MODREGISTRY.listBlock())
			if(block instanceof BlockColoredMeta)
			{
				ArcaneMechina.MODREGISTRY.registerIItemColor((IItemColored)block);
				ArcaneMechina.MODREGISTRY.registerIBlockColor((IBlockColor)block);
			}
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(world.isRemote) {
			//TileEntity tileEntity = world.getTileEntity(new BlockPos(x,y,z));
			switch(ID)
			{
			default: return null;
			}
		}
		return null;
	}

	@Override
	public void preInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}

	@Override
	public void postInit() {
		// TODO Auto-generated method stub
		registerRenderers();
	}

}
