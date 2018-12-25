package com.maelstrom.snowcone.item.sonic;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SonicPacketProcessor implements IMessage {

	public int slot;
	public boolean mod;
	public SonicPacketProcessor(){}
	public SonicPacketProcessor(int currentItem, boolean mode) {
		slot = currentItem;
		mod = mode;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		slot = buf.readInt();
		mod = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(slot);
		buf.writeBoolean(mod);
	}

}
