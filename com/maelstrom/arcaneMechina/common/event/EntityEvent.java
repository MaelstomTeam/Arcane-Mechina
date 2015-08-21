package com.maelstrom.arcanemechina.common.event;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.maelstrom.arcanemechina.common.items.PotionEffectFreeze;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityEvent
{
	Potion potion = new PotionEffectFreeze();
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event)
	{
		if(event.entityLiving.isPotionActive(potion))
		{
			if(event.entityLiving.getActivePotionEffect(potion).getDuration() > 0)
			{
				potion.affectEntity(event.entityLiving, null, event.entityLiving.getActivePotionEffect(potion).getAmplifier(), 0);
			}
		}
	}
}
