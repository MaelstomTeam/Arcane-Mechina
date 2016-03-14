package com.maelstrom.armech.common.event;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import com.maelstrom.armech.common.Reference;

public class EventHandler
{
	@SubscribeEvent
	public void ActivationEventHandler(ArrayActivatedEvent event)
	{

	}
	static boolean hasLogged = false;
	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent event)
	{
		if(!hasLogged)
		{
			event.player.addChatComponentMessage(new ChatComponentText("[" + EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + Reference.MODID.toUpperCase() + EnumChatFormatting.RESET + "] " + StatCollector.translateToLocal("message.armech.login1").replace("%player%", event.player.getName())));
			event.player.addChatComponentMessage(new ChatComponentText("[" + EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + Reference.MODID.toUpperCase() + EnumChatFormatting.RESET + "] " + StatCollector.translateToLocal("message.armech.login3")));
			hasLogged = true;
		}
	}
}
