package com.maelstrom.arcanemechina.common.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;

import com.maelstrom.arcanemechina.common.ItemsReference;
import com.maelstrom.arcanemechina.common.items.ItemSoulBound;
import com.maelstrom.arcanemechina.common.items.PotionEffectFreeze;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityEvent
{
	public static final Potion potion = new PotionEffectFreeze("freeze", 25);
	public static final Potion potion2 = new PotionEffectFreeze("deathCount", 26);
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase entity = event.entityLiving;
		if(event.entityLiving.isPotionActive(potion) && event.entityLiving.getActivePotionEffect(potion).getDuration() > 0)
		{
	    	if(entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode)
	    		return;
			entity.setPosition(((int)entity.posX) - .5, (int)entity.posY + (entity.height / 2), ((int)entity.posZ) - .5);
	    	entity.motionY = 0;
		}
		else if(event.entityLiving.isPotionActive(potion2))
		{
			if(event.entityLiving.getActivePotionEffect(potion2).getDuration() < 1200)
				entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 2, 0));
			if(event.entityLiving.getActivePotionEffect(potion2).getDuration() == 200)
				entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 2));
			if(event.entityLiving.getActivePotionEffect(potion2).getDuration() == 100)
				entity.addPotionEffect(new PotionEffect(Potion.blindness.id, 200, 0));
	    	if(event.entityLiving.getActivePotionEffect(potion2).getDuration() <= 1)
	    	{
	    		if(entity instanceof EntityPlayer && !entity.worldObj.isRemote)
	    			((EntityPlayer)entity).inventory.dropAllItems();
	    		entity.setHealth(0f);
	    	}
		}
	}
	
	@SubscribeEvent
	public void onPlayerTick(LivingHurtEvent event)
	{
		if(event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			InventoryBaubles inventory = PlayerHandler.getPlayerBaubles(player);
			boolean canSurvive = false;
			int slot = -1;
			//int armorSlot = -1;
			
			if(inventory.getStackInSlot(1) != null && inventory.getStackInSlot(1).getItem() == ItemsReference.boundRing)
			{
				canSurvive = true;
				slot = 1;
			}
			else if(inventory.getStackInSlot(2) != null && inventory.getStackInSlot(2).getItem() == ItemsReference.boundRing)
			{
				canSurvive = true;
				slot = 2;
			}/*
			else
				for(int i = 0; player.inventory.armorInventory.length < i; i++)
					if(player.inventory.armorInventory[i] != null && player.inventory.armorInventory[i].getItem() instanceof ItemSoulBound)
					{
						canSurvive = true;
						armorSlot = i;
					}
			*/
			
			if(event.entityLiving.getHealth() - event.ammount <= 0 && canSurvive)
			{
				event.setCanceled(true);
				event.entityLiving.setHealth(event.entityLiving.getMaxHealth());
				event.entityLiving.extinguish();
				if(slot > -1)
					inventory.setInventorySlotContents(slot, null);
				/*
				if(armorSlot > -1)
					player.inventory.armorInventory[armorSlot] = null;*/
			}	
		}
	}
}
 