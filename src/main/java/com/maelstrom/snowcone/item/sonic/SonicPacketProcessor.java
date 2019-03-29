package com.maelstrom.snowcone.item.sonic;

import java.util.UUID;

import org.apache.commons.lang3.CharSet;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SonicPacketProcessor implements IMessage {

	public int slot;
	public boolean mod;
	public int type;
	public NBTTagCompound tag;
	public UUID uuid;
	public SonicPacketProcessor(){}
	public SonicPacketProcessor(int currentItem, boolean mode) {
		slot = currentItem;
		mod = mode;
		type = 0;
	}
	public SonicPacketProcessor(int currentSlot, NBTTagCompound tagc)
	{
		slot = currentSlot;
		type = 2;
		if(tagc == null)
			tag = new NBTTagCompound();
		else
			tag = tagc;
	}
	public SonicPacketProcessor(ItemStack is)
	{
		slot = -1;
		type = 2;
		tag = SonicInventory.getInventory(is).getTag();
		uuid = UUID.fromString(is.getSubCompound("SonicInventory").getString("UUID"));
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		type = buf.readInt();
		slot = buf.readInt();
		mod = buf.readBoolean();
		if(type == 1)
		{
			tag = ByteBufUtils.readTag(buf);
			if(tag.hasKey("UUID"))
				uuid = UUID.fromString(tag.getString("UUID"));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(type);
		buf.writeInt(slot);
		if(type == 0)
			buf.writeBoolean(mod);
		buf.writeBoolean(false);
		if(type == 1)
		{
			if(uuid != null)
				tag.setString("UUID", uuid.toString());
			ByteBufUtils.writeTag(buf, tag);
		}
	}

}
