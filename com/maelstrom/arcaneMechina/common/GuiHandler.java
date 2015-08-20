package com.maelstrom.arcanemechina.common;

import com.maelstrom.arcanemechina.client.guis.MechinizedArcaneGui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID)
		{
			case 1 : return null; //container
			default : return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID)
		{
			case 1 : return new MechinizedArcaneGui();
			default : return null;
		}
	}

}
