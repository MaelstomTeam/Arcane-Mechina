package com.maelstrom.arcanemechina.common.event;

import java.util.List;

import com.maelstrom.arcanemechina.ArcaneMechina;
import com.maelstrom.arcanemechina.common.block.BlockList;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid=ArcaneMechina.MODID)
public class EventHandler
{
	@SubscribeEvent
	public static void ActivationEventHandler(EventRuneActivation event)
	{
		if(event.getArray().getActivity().isValid(event.getWorld(), event.getRunePosition()))
		{
			event.getArray().getActivity().runActivity(event.getWorld(), event.getRunePosition());
		}
		else
		{
			MinecraftForge.EVENT_BUS.post(event.getArray().getActivity().getBackfireResult(event.getWorld(), event.getRunePosition(), event.getEntityActivator()));
		}
		event.setCanceled(true);
	}
	
	@SubscribeEvent
	public static void ActivationEventHandler(EventRuneBackfire event)
	{
		switch(event.getType())
		{
		case AIR:
			break;
		case DARK:
			break;
		case EARTH:
			break;
		case ETHERIC:
		{
			eventEtheric(event, event.world);
			break;
		}
		case FIRE:
		{
			eventFire(event);
			break;
		}
		case LIGHT:
			break;
		case OBLIVIOUS:
			break;
		case WATER:
			break;
		default:
			break;
		}
		event.setCanceled(true);
	}
	private static void eventEtheric(EventRuneBackfire event, World w)
	{
		double randomNumber = Math.random() * 100;
		switch(event.getSeverity())
		{
		case EXTREME:
			break;
		case HIGH:
			break;
		case MEDIUM:
		{
			if(!w.isRemote) {
				if(randomNumber >= 0 && randomNumber <=25)
				{
					EntityEnderman em = new EntityEnderman(w);
					em.setPosition(event.getRunePosition().getX(), event.getRunePosition().getY(), event.getRunePosition().getZ());
					w.spawnEntity(em);
				}
				else if(randomNumber > 25 && randomNumber <= 85)
				{
					for(int i = 0; i < (int)(Math.random() * 5); i++)
					{
						EntityEnderman em = new EntityEnderman(w);
						em.setPosition(event.getRunePosition().getX() + Math.random() * 10 - 5, event.getRunePosition().getY() + Math.random() * 10 - 5, event.getRunePosition().getZ() + Math.random() * 10 - 5);
						w.spawnEntity(em);
					}
					for(int i = 0; i < (int)(Math.random() * 3); i++)
					{
						EntityEndermite em = new EntityEndermite(w);
						em.setPosition(event.getRunePosition().getX() + Math.random() * 10 - 5, event.getRunePosition().getY() + Math.random() * 10 - 5, event.getRunePosition().getZ() + Math.random() * 10 - 5);
						w.spawnEntity(em);
					}
				}
				else if(randomNumber > 85 && randomNumber <= 100) {
					List <EntityLivingBase> list = w.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(event.getRunePosition().add(-3,-3,-3),event.getRunePosition().add(4,4,4)));
					for(EntityLivingBase entity : list)
					{
						double x = event.getRunePosition().getX() + (Math.random() * 32 - 16);
						double y = event.getRunePosition().getY() + (Math.random() * 32 - 16);
						double z = event.getRunePosition().getZ() + (Math.random() * 32 - 16);
						//find closest block to the ground otherwise freak the heck out we don't know where to put the player
						while(w.isAirBlock(new BlockPos(x,y,z)))
							if(y <= 0)
								break;
							else
								y--;
						while(!w.isAirBlock(new BlockPos(x,y,z)))
							if(y >= 255)
								break;
							else
								y++;
						if(y <= 255)
							entity.attemptTeleport(x,y,z);
					}
				}
			}
			break;
		}
		case LOW:
			break;
		case NONE:
			break;
		default:
			break;
		}
	}
	private static void eventFire(EventRuneBackfire event)
	{
		double randomNumber = Math.random() * 100;
		switch(event.getSeverity())
		{
		case EXTREME:
		{
			if(randomNumber >= 0 && randomNumber <= 25)
				event.world.createExplosion(null, event.getRunePosition().getX(), event.getRunePosition().getY(), event.getRunePosition().getZ(), 6, true);
			else if(randomNumber > 25 && randomNumber <= 75)
				event.world.createExplosion(null, event.getRunePosition().getX(), event.getRunePosition().getY(), event.getRunePosition().getZ(), 12, true);
			else if(randomNumber > 75 && randomNumber <= 99.9)
			{
				for(int x = -4; x < 5;x++)
					for(int y = -1; y < 2;y++)
						for(int z = -4; z < 5;z++)
							if(Math.random() > .2)
								if(event.world.isAirBlock(event.getRunePosition().add(x, y, z)) || event.world.getBlockState(event.getRunePosition().add(x, y, z)).getBlock() == BlockList.Rune)
									event.world.setBlockState(event.getRunePosition().add(x, y, z), Blocks.FIRE.getDefaultState(), 11);
			}
			break;
		}
		case HIGH:
			break;
		case LOW:
			break;
		case MEDIUM:
			break;
		case NONE:
			break;
		}
	}
}