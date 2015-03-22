package com.maelstrom.arcaneMechina.common.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

import com.maelstrom.arcaneMechina.common.reference.Reference;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerJoinWorldEvent {
	
	@SubscribeEvent
	public void entityJoinWorld(EntityJoinWorldEvent event){
		if(event.entity instanceof EntityPlayer)
			playerJoinWorld(event, (EntityPlayer) event.entity);
	}
	
	private void playerJoinWorld(EntityJoinWorldEvent event, EntityPlayer ply){
		if(!event.world.isRemote)
			ply.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal(Reference.MOD_ID + ".login.notice")));
//		give helpers, modders and community a Rosario Amulet as thanx
		//if(Reference.isContributor(ply.getDisplayName()) && !hasLoggedBefore(ply)){
			//ply.inventory.addItemStackToInventory(new ItemStack(InitItem.rosarioAmulet));
		//}
	}

	private boolean hasLoggedBefore(EntityPlayer ply) {
		ArcaneMechinaNbt temp = new ArcaneMechinaNbt(ply);
		System.out.println(temp.hasLoggedPreviously());
		if(!temp.hasLoggedPreviously()){
			temp.setBoolean("hasLogged", true);
			return false;
		}
		temp.Update();
		return true;
	}
	
}