package com.maelstrom.snowcone.common;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.maelstrom.snowcone.SC_Registry;
import com.maelstrom.snowcone.SnowCone;
import com.maelstrom.snowcone.item.sonic.SonicInventory;
import com.mojang.authlib.GameProfile;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class CommandSnowCone implements ICommand {

	private final List<String> alias;
	
	public CommandSnowCone()
	{
		alias = new ArrayList<String>();
		alias.add("snowcone");
	}
	
	@Override
	public int compareTo(ICommand arg0) {
		return 0;
	}

	@Override
	public String getName() {
		return "snowcone";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "snowcone <sonic> <reset:create:remove> <uuid>";
	}

	@Override
	public List<String> getAliases() {
		return alias;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		switch(args[0])
		{
		case "sonic":
		{
			EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
			switch(args[1])
			{
			case "reset": 
			{
				if(args.length > 2)
				{
					try
					{
						SonicInventory.reset(SonicInventory.getInventory(UUID.fromString(args[2])));
					}
					catch(Exception e)
					{
						throw new CommandException(args[2] +" UUID is invalid!");
					}
				}
				else
				{
					ItemStack main = player.getHeldItemMainhand();
					ItemStack off = player.getHeldItemOffhand();
					if(main != null && main.getItem() == SC_Registry.SonicScrewdriver)
					{
						SonicInventory.reset(SonicInventory.getInventory(main, player));
					}
					else if(off != null && off.getItem() == SC_Registry.SonicScrewdriver)
					{
						SonicInventory.reset(SonicInventory.getInventory(off, player));
					}
					else
					{
						for(SonicInventory s : EventHandler.list.values())
						{
							SonicInventory.reset(s);
						}
					}
					break;
				}
			}
			case "create":
			{
				if(args.length > 2)
				{
					try
					{
						SonicInventory.createInventory(UUID.fromString(args[2]));
						
						ItemStack is = new ItemStack(SC_Registry.SonicScrewdriver);
			    		NBTTagCompound tag = new NBTTagCompound();
			        	tag.setString("UUID", args[2]);
			        	is.setTagInfo("SonicInventory", tag);
						player.addItemStackToInventory(is);
					}
					catch(Exception e)
					{
						throw new CommandException(args[2] +" UUID is invalid!");
					}
				}
				else
				{
					SonicInventory.createInventory(sender.getCommandSenderEntity().getUniqueID());
					
					ItemStack is = new ItemStack(SC_Registry.SonicScrewdriver);
					SC_Registry.SonicScrewdriver.onCreated(is, sender.getEntityWorld(), player);
					player.addItemStackToInventory(is);
				}
				break;
			}
			case "remove":

			{
				if(args.length > 2)
				{
					EventHandler.list.remove(UUID.fromString(args[2]));
				}
				break;
			}
			}
			break;
		}
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		World world = sender.getEntityWorld();
		if(!world.isRemote)
			if(FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getOppedPlayers().getEntry(((EntityPlayerMP)sender.getCommandSenderEntity()).getGameProfile()) != null)
				return true;
		return FMLCommonHandler.instance().getMinecraftServerInstance().isSinglePlayer();
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		List<String> list = new ArrayList<String>();
		switch(args.length)
		{
		case 1:
		{
			list.add("sonic");
			break;
		}
		case 2:
		{
			list.add("create");
			list.add("remove");
			list.add("reset");
			break;
		}
		case 3:
		{
			list.add("");
			for(UUID uuid : EventHandler.list.keySet())
				list.add(uuid.toString());
			break;
		}
		}
		return list;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}

}
