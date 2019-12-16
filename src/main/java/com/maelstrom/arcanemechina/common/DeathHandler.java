package com.maelstrom.arcanemechina.common;

import com.maelstrom.arcanemechina.ArcaneMechina;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArcaneMechina.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DeathHandler
{

	@SubscribeEvent
	public static void RegisterItems(LivingDropsEvent event)
	{
		if(event.getEntity() instanceof ZombieEntity)
		{
			if(Math.random() * 100d <= 1d)
			{
				ItemEntity temp = (ItemEntity) event.getDrops().toArray()[0];
				event.getDrops().add(new ItemEntity(temp.getEntityWorld(), temp.posX, temp.posY, temp.posZ, new ItemStack(Items.FEATHER)));
			}
		}
	}
}
