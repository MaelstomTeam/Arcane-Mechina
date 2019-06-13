package com.maelstrom.snowcone.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.maelstrom.snowcone.SC_Registry;
import com.maelstrom.snowcone.SnowCone;
import com.maelstrom.snowcone.item.sonic.SonicInventory;
import com.maelstrom.snowcone.item.sonic.SonicPacketProcessor;
import com.maelstrom.snowcone.network.PacketHandler;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(modid=SnowCone.MODID)
public class EventHandler {
	
	public static Map<UUID, SonicInventory> list = new HashMap<UUID, SonicInventory>();
	public static Map<UUID, Boolean> hasGottenBook = new HashMap<UUID, Boolean>();
	

	public static SonicInventory getInventory(UUID uuid)
	{
		return list.get(uuid);
	}
	public static boolean getRecieved(UUID uuid)
	{
		if(hasGottenBook.containsKey(uuid))
			return hasGottenBook.get(uuid);
		return false;
	}
	public static void setRecieved(UUID uuid, boolean bool)
	{
		if(hasGottenBook.containsKey(uuid))
		{
			hasGottenBook.replace(uuid, bool);
		}
		else
			hasGottenBook.put(uuid, bool);
	}
	
	@SubscribeEvent
	public static void OnWorldLoad(WorldEvent.Load load)
	{
		if(load.getWorld().isRemote)
			return;
		list.clear();
		hasGottenBook.clear();
		
		NBTTagCompound tag = null;
		try
		{
			FileInputStream stream = new FileInputStream(new File(load.getWorld().getSaveHandler().getWorldDirectory(), "data/SonicInventory.dat"));
			tag = CompressedStreamTools.readCompressed(stream);
			
			NBTTagList nbtList = (NBTTagList) tag.getTag("SonicList");
			for(int i = 0; i < nbtList.tagCount(); i++)
			{
				NBTTagCompound curr = (NBTTagCompound) nbtList.get(i);
				UUID uuid = UUID.fromString(curr.getString("UUID"));
				NBTTagCompound sonicData = curr.getCompoundTag("SonicInventory");
				SonicInventory sonic = SonicInventory.createInventory(sonicData,uuid);
				list.put(uuid, sonic);
			}
			
			NBTTagList hasGottenBookList = (NBTTagList)tag.getTag("RecievedBook");
			for(int i = 0; i < hasGottenBookList.tagCount(); i++)
			{
				hasGottenBook.put(UUID.fromString(((NBTTagCompound)hasGottenBookList.get(i)).getString("UUID")), ((NBTTagCompound)hasGottenBookList.get(i)).getBoolean("Recieved"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(tag != null)
			{
				String version = tag.getString("Version");
				if(!SnowCone.VERSION.equals(version))
				{
					//update file!
				}
			}
		}
	}
	private static boolean saveIgnore = true;
	@SubscribeEvent
	public static void OnWorldSave(WorldEvent.Save save)
	{
		if(save.getWorld().isRemote)
			return;
		if(saveIgnore)
		{
			saveIgnore = false;
			return;
		}
		NBTTagCompound nbt = new NBTTagCompound();
		{
			NBTTagList nbtTagList = new NBTTagList();
			for(Map.Entry<UUID, SonicInventory> entry : list.entrySet())
			{
				System.out.println(entry);
				UUID _uuid = entry.getKey();
				SonicInventory sonic = entry.getValue();
				
				NBTTagCompound sonicInventory = new NBTTagCompound();
				sonic.markDirty();
				sonicInventory.setTag("SonicInventory", sonic.getTag());
				sonicInventory.setString("UUID", _uuid.toString());
				nbtTagList.appendTag(sonicInventory);
			}
			nbt.setTag("SonicList", nbtTagList);
			nbt.setString("Version", SnowCone.VERSION);
			
			for(Map.Entry<UUID, Boolean> entry : hasGottenBook.entrySet())
			{
				UUID _uuid = entry.getKey();
				boolean recieved = entry.getValue();
				NBTTagCompound newNBT = new NBTTagCompound();
				newNBT.setString("UUID", _uuid.toString());
				newNBT.setBoolean("Recieved", recieved);
			}
		}
		try {
			FileOutputStream file = new FileOutputStream(new File(save.getWorld().getSaveHandler().getWorldDirectory(), "data/SonicInventory.dat"));
			CompressedStreamTools.writeCompressed(nbt, file);
			file.flush();
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	@SubscribeEvent
	@SideOnly(Side.SERVER)
	public static void OnPlayerJoinWorld(PlayerLoggedInEvent event)
	{
		EntityPlayerMP player = (EntityPlayerMP)event.player;
		for(int i = 0; i < player.inventory.getSizeInventory(); i++)
		{
			if(player.inventory.getStackInSlot(i).getItem() == SC_Registry.SonicScrewdriver)
			{
				SonicInventory inv = SonicInventory.getInventory(player.inventory.getStackInSlot(i));
				PacketHandler.INSTANCE.sendTo(new SonicPacketProcessor(i,inv.getTag()), player);
			}
		}
	}
	@SubscribeEvent
	public static void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<ItemStack> event) {
		if(event.getObject().getItem() == SC_Registry.SonicScrewdriver)
		{
			event.addCapability(new ResourceLocation(SnowCone.MODID), SonicInventory.getInventory(event.getObject()));
		}
	}
}
