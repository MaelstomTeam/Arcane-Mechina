package com.maelstrom.arcaneMechina.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;

import com.maelstrom.arcaneMechina.init.InitItem;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerJoinWorldEvent {
	
	@SubscribeEvent
	public void entityJoinWorld(EntityJoinWorldEvent event){
		if(event.entity instanceof EntityPlayer)
			playerJoinWorld(event, (EntityPlayer) event.entity);
	}
	
	private void playerJoinWorld(EntityJoinWorldEvent event, EntityPlayer ply){
//		give helpers, modders and contributers a Rosario Amulet as thanx
//		for(String name : ContributorRenderHandler.modders)
//			if(ply.getDisplayName().equals(name) && !hasLoggedBefore(ply))
//				PlayerHandler.getPlayerBaubles(ply).setInventorySlotContents(0, new ItemStack(InitItem.pegasusWingAmulet));
	}
	
}