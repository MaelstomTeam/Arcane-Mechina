package com.maelstrom.arcanemechina.client;

import com.maelstrom.arcanemechina.client.gui.GuiBook;
import com.maelstrom.arcanemechina.client.gui.GuiBookIndex;
import com.maelstrom.arcanemechina.client.gui.PageAll;
import com.maelstrom.arcanemechina.common.CommonProxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {}

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
			case 0:{return new GuiBook();}
			case 1:{return new GuiBookIndex();}
			default: return null;
			}
		}
		return null;
	}

	@Override
	public void preInit() {
		// TODO Auto-generated method stub
		PageAll.init();
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}

	@Override
	public void postInit() {
		// TODO Auto-generated method stub
		
	}

}
