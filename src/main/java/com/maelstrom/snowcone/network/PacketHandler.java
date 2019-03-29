package com.maelstrom.snowcone.network;

import java.util.UUID;

import com.maelstrom.snowcone.SnowCone;
import com.maelstrom.snowcone.item.sonic.SonicInventory;
import com.maelstrom.snowcone.item.sonic.SonicPacketProcessor;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler implements IMessageHandler<SonicPacketProcessor, IMessage> {
	
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(SnowCone.MODID);
	
	@Override
	public IMessage onMessage(SonicPacketProcessor message, MessageContext ctx)
	{
		if(ctx.side == Side.SERVER)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			switch(message.type)
			{
			case 0 : {
				int slot = message.slot;
				boolean mode = message.mod;
				player.getServerWorld().addScheduledTask(() -> {
					ItemStack stack = player.inventory.getStackInSlot(slot);
					SonicInventory sonic = SonicInventory.getInventory(stack,player);
					sonic.setCurrentItem(sonic.getIndex() + (mode ? 1 : -1));
					sonic.markDirty();
				});
				break;
			}
			case 1:
			{
				int slot = message.slot;
				SonicInventory inventory;
				if(slot == -1)
					inventory = SonicInventory.createInventory(message.tag,message.uuid);
				else
					inventory = SonicInventory.getInventory(player.inventory.getStackInSlot(slot));
				PacketHandler.INSTANCE.sendTo(new SonicPacketProcessor(slot, inventory.getTag()), player);
			}
			}
		}
		else if( ctx.side == Side.CLIENT) {
			EntityPlayer player = Minecraft.getMinecraft().player;
			switch (message.type) {
			case 1:
			{
				SonicInventory.createInventory(message.tag, UUID.fromString(player.inventory.getStackInSlot(message.slot).getSubCompound("SonicInventory").getString("UUID")));
			}
			}
		}
		
		return null;
	}
}
