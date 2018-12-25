package com.maelstrom.snowcone.network;

import com.maelstrom.snowcone.SnowCone;
import com.maelstrom.snowcone.item.sonic.SonicInventory;
import com.maelstrom.snowcone.item.sonic.SonicPacketProcessor;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class PacketHandler implements IMessageHandler<SonicPacketProcessor, IMessage> {
	
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(SnowCone.MODID);
	
	@Override
	public IMessage onMessage(SonicPacketProcessor message, MessageContext ctx)
	{
		EntityPlayerMP player = ctx.getServerHandler().player;
		
		int slot = message.slot;
		boolean mode = message.mod;
		player.getServerWorld().addScheduledTask(() -> {
			ItemStack stack = player.inventory.getStackInSlot(slot);
			SonicInventory sonic = SonicInventory.getInventory(stack);
			sonic.setCurrentItem(sonic.getIndex() + (mode ? 1 : -1));
			sonic.writeData();
			stack.setTagInfo("ItemInventory", sonic.getCompound());
		});
		
		return null;
	}
}
