package com.maelstrom.armech.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.maelstrom.armech.client.gui.GUIBookBase;
import com.maelstrom.armech.client.gui.GuiBookIndex;
import com.maelstrom.armech.common.AMBlocks;
import com.maelstrom.armech.common.AMItems;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenders()
	{
		AMItems.registerRenders();
		AMBlocks.registerRenders();
	}


	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x,y,z));
//		if(tileEntity != null);
		switch(ID)
		{
		case 0:{return new GUIBookBase();}
		case 1:{return new GuiBookIndex();}
		}
		return null;
	}
}
